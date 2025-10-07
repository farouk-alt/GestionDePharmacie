package vente.web.portlet;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import gestion_de_pharmacie.service.StockLocalServiceUtil;
import gestion_de_pharmacie.service.VenteLocalServiceUtil;
import org.osgi.service.component.annotations.Reference;
import vente.web.constants.VenteWebPortletKeys;
import gestion_de_pharmacie.service.UtilisateurLocalServiceUtil;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.dao.orm.*;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author farou
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
            "com.liferay.portlet.add-default-resource=true",   // <-- add this
            "javax.portlet.display-name=VenteWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + VenteWebPortletKeys.VENTEWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class VenteWebPortlet extends MVCPortlet {

    @Reference
    private gestion_de_pharmacie.service.VenteLocalService _venteLocalService;



    private static final String PS_USER_EMAIL = "USER_EMAIL"; // same key used by your dashboard/login
    private static final String UTILISATEUR_EMAIL_COL = "email"; // change to "adresseEmail" if that's your column

    private String getAppSessionEmail(PortletRequest req) {
        try {
            PortletSession ps = req.getPortletSession(false);
            if (ps != null) {
                Object v = ps.getAttribute(PS_USER_EMAIL, PortletSession.APPLICATION_SCOPE);
                if (v instanceof String) {
                    String s = ((String) v).trim();
                    if (!s.isEmpty()) return s;
                }
            }
        } catch (Exception ignore) {}
        return null;
    }



    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        System.out.println("VenteWebPortlet doView");
        try {
            List<Stock> stocks = StockLocalServiceUtil.getStocks(-1, -1);
            List<Map<String,Object>> rows = new ArrayList<>();

            for (Stock s : stocks) {
                if (s.getQuantiteDisponible() <= 0) continue;
                Medicament m = MedicamentLocalServiceUtil.fetchMedicament(s.getIdMedicament());
                if (m == null) continue;

                Map<String,Object> r = new HashMap<>();
                r.put("idMedicament", m.getIdMedicament());
                r.put("nom", m.getNom());
                r.put("prix", m.getPrixUnitaire());
                r.put("dispo", s.getQuantiteDisponible());
                rows.add(r);
            }
            rows.sort(Comparator.comparing(o -> String.valueOf(o.get("nom")).toLowerCase()));
            req.setAttribute("meds", rows);
            req.setAttribute("medsCount", rows.size());


        } catch (Exception e) {
            // show something instead of blank list
            req.setAttribute("meds", Collections.emptyList());
            req.setAttribute("errorMsg", "Erreur lors du chargement des articles: " + e.getMessage());
        }
        // expose role for the JSP
        req.setAttribute("userRole", getUserRole(req));

        String mvc = ParamUtil.getString(req, "mvcPath", "/view.jsp");
        if ("/history.jsp".equals(mvc)) {
            // nothing to preload for now
        }

        super.doView(req, res);
    }
    private String getUserRole(PortletRequest req) {
        try {
            PortletSession ps = req.getPortletSession(false);
            if (ps != null) {
                Object v = ps.getAttribute("USER_ROLE", PortletSession.APPLICATION_SCOPE);
                if (v instanceof String) return (String) v;
            }
        } catch (Exception ignore) {}
        return "";
    }
    @ProcessAction(name = "deleteAllHistory")
    public void deleteAllHistory(ActionRequest request, ActionResponse response) {
        // restrict to admins
        String role = getUserRole(request);
        if (!"ADMIN".equalsIgnoreCase(role) && !"SUPER_ADMIN".equalsIgnoreCase(role)) {
            response.setRenderParameter("mvcPath", "/history.jsp");
            response.setRenderParameter("errorMsg", "Action non autorisée.");
            return;
        }

        try {
            // Load all ventes (paged = -1, -1) and delete them.
            // If you have a cascade helper, use it; otherwise this will rely on DB FKs or service hooks.
            @SuppressWarnings("unchecked")
            java.util.List<gestion_de_pharmacie.model.Vente> all =
                    (java.util.List<gestion_de_pharmacie.model.Vente>)(java.util.List<?>)
                            _venteLocalService.getVentes(-1, -1);

            for (gestion_de_pharmacie.model.Vente v : all) {
                try {
                    // Prefer a cascade method if your service exposes one, e.g.:
                    // _venteLocalService.deleteVenteCascade(v.getIdVente());
                    _venteLocalService.deleteVente(v);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            response.setRenderParameter("successMsg", "Historique des ventes supprimé.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setRenderParameter("errorMsg", "Erreur lors de la suppression de l’historique.");
        }

        // Back to the history screen after deletion
        response.setRenderParameter("mvcPath", "/history.jsp");
    }


    @ProcessAction(name = "checkout")
    public void checkout(ActionRequest request, ActionResponse response)
            throws IOException, PortletException {

        System.out.println("[VENTE] checkout invoked");

        // log all params for sanity
        request.getParameterMap().forEach((k,v) ->
                System.out.println("[VENTE] param " + k + "=" + java.util.Arrays.toString(v))
        );

        try {
            // Try namespaced first, then fallback to plain
            final String ns = com.liferay.portal.kernel.util.PortalUtil
                    .getPortletNamespace(vente.web.constants.VenteWebPortletKeys.VENTEWEB);

            long[] ids  = com.liferay.portal.kernel.util.ParamUtil.getLongValues(request, ns + "idMedicament");
            int[]  qtys = com.liferay.portal.kernel.util.ParamUtil.getIntegerValues(request, ns + "qty");

            if (ids.length == 0)  ids  = com.liferay.portal.kernel.util.ParamUtil.getLongValues(request, "idMedicament");
            if (qtys.length == 0) qtys = com.liferay.portal.kernel.util.ParamUtil.getIntegerValues(request, "qty");

            System.out.println("[VENTE] ids=" + java.util.Arrays.toString(ids)
                    + " qtys=" + java.util.Arrays.toString(qtys));

            // Guard: align lengths and drop zero/negative qtys
            java.util.List<Long> idList = new java.util.ArrayList<>();
            java.util.List<Integer> qtyList = new java.util.ArrayList<>();
            for (int i = 0; i < Math.min(ids.length, qtys.length); i++) {
                if (qtys[i] > 0) { idList.add(ids[i]); qtyList.add(qtys[i]); }
            }
            long[] cleanIds  = idList.stream().mapToLong(Long::longValue).toArray();
            int[]  cleanQtys = qtyList.stream().mapToInt(Integer::intValue).toArray();

            if (cleanIds.length == 0) {
                throw new IllegalArgumentException("Panier vide (aucune quantité > 0).");
            }

            long userId = resolveUtilisateurId(request);
            System.out.println("[CHECKOUT] resolved userId=" + userId + " (will be saved on Vente)");
            if (userId == 0) {
                response.setRenderParameter("errorMsg", "Vous devez être connecté pour enregistrer une vente.");
                response.setRenderParameter("mvcPath", "/view.jsp");
                return;
            }
            _venteLocalService.createVente(userId, cleanIds, cleanQtys);


            response.setRenderParameter("successMsg", "Vente enregistrée !");
        } catch (Exception e) {
            e.printStackTrace();
            response.setRenderParameter("errorMsg", "Erreur: " + e.getMessage());
        }

        response.setRenderParameter("mvcPath", "/view.jsp");
    }




    @Override
    public void serveResource(ResourceRequest req, ResourceResponse res) throws IOException {
        res.setCharacterEncoding("UTF-8");

        // ✅ canonical routing
        String rid = req.getResourceID();

        if ("whoami".equals(rid)) { serveWhoAmI(req, res); return; }
        if (rid == null || rid.isBlank()) {
            rid = ParamUtil.getString(req, "id"); // fallback if someone calls it manually
        }

        if ("history".equals(rid))      { serveHistory(req, res); return; }
        if ("venteDetails".equals(rid)) { serveVenteDetails(req, res); return; }
        if ("exportCsv".equals(rid))    { exportCsv(req, res); return; }

        // ====== ORIGINAL SEARCH (kept intact) ======
        res.setContentType("application/json; charset=UTF-8");
        String q = ParamUtil.getString(req, "q");
        if (q.isEmpty()) {
            String ns = com.liferay.portal.kernel.util.PortalUtil.getPortletNamespace(
                    com.liferay.portal.kernel.util.PortalUtil.getPortletId(req));
            q = ParamUtil.getString(req, ns + "q");
        }
        java.util.List<java.util.Map<String,Object>> out = new java.util.ArrayList<>();
        try {
            java.util.List<Medicament> meds = new java.util.ArrayList<>();
            if (!q.isEmpty()) {
                meds.addAll(MedicamentLocalServiceUtil.findByCodeBarre(q));
                if (meds.isEmpty()) meds.addAll(MedicamentLocalServiceUtil.findByCode(q));
                if (meds.isEmpty()) {
                    int limit = 10;
                    com.liferay.portal.kernel.dao.orm.DynamicQuery dq =
                            MedicamentLocalServiceUtil.dynamicQuery()
                                    .add(com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.ilike("nom", "%" + q + "%"))
                                    .addOrder(com.liferay.portal.kernel.dao.orm.OrderFactoryUtil.asc("nom"));
                    @SuppressWarnings("unchecked")
                    java.util.List<Object> byNameObj = MedicamentLocalServiceUtil.dynamicQuery(dq, 0, limit);
                    for (Object o : byNameObj) if (o instanceof Medicament) meds.add((Medicament)o);
                }
            }
            for (Medicament m : meds) {
                Stock s = StockLocalServiceUtil.fetchStockByMedicamentId(m.getIdMedicament());
                int dispo = (s == null) ? 0 : s.getQuantiteDisponible();
                java.util.Map<String,Object> row = new java.util.LinkedHashMap<>();
                row.put("id", m.getIdMedicament());
                row.put("nom", m.getNom());
                row.put("code", m.getCode());
                row.put("codeBarre", m.getCodeBarre());
                row.put("prix", m.getPrixUnitaire());
                row.put("dispo", dispo);
                out.add(row);
            }
        } catch (Exception ignore) {}
        String json = new com.liferay.portal.kernel.json.JSONFactoryUtil().looseSerializeDeep(out);
        res.getWriter().write(json);
    }
    private void serveHistory(ResourceRequest req, ResourceResponse res) throws IOException {
        res.setContentType("application/json; charset=UTF-8");

        int page    = ParamUtil.getInteger(req, ns(req, "page"), ParamUtil.getInteger(req, "page", 1));
        int size    = ParamUtil.getInteger(req, ns(req, "size"), ParamUtil.getInteger(req, "size", 20));
        long userId = ParamUtil.getLong   (req, ns(req, "userId"), ParamUtil.getLong   (req, "userId"));
        String fromStr  = ParamUtil.getString(req, ns(req, "from"), ParamUtil.getString(req, "from"));
        String toStr    = ParamUtil.getString(req, ns(req, "to"),   ParamUtil.getString(req, "to"));
        String userLike = ParamUtil.getString(req, ns(req, "user"), ParamUtil.getString(req, "user"));

        Date from = parseDateTime(fromStr);
        Date to   = parseDateTime(toStr);

        int start = Math.max(0, (page - 1) * size);
        int end   = start + size;

        // ---------- Resolve "userLike" -> userIds ----------
        List<Long> userIds = null;
        if (userLike != null && !userLike.isBlank()) {
            DynamicQuery dqU = UtilisateurLocalServiceUtil.dynamicQuery()
                    .add(
                            RestrictionsFactoryUtil.disjunction()
                                    .add(RestrictionsFactoryUtil.ilike("nom", "%" + userLike + "%"))
                                    .add(RestrictionsFactoryUtil.ilike("prenom", "%" + userLike + "%"))
                                    .add(RestrictionsFactoryUtil.ilike(UTILISATEUR_EMAIL_COL, "%" + userLike + "%")) // <= same column everywhere
                    );

            List<?> raw = UtilisateurLocalServiceUtil.dynamicQuery(dqU);
            userIds = new ArrayList<>();
            for (Object o : raw) {
                if (o instanceof gestion_de_pharmacie.model.Utilisateur) {
                    userIds.add(((gestion_de_pharmacie.model.Utilisateur) o).getIdUtilisateur());
                }
            }
            if (userIds.isEmpty()) {
                var out = new LinkedHashMap<String, Object>();
                out.put("items", Collections.emptyList());
                out.put("total", 0);
                res.getWriter().write(
                        com.liferay.portal.kernel.json.JSONFactoryUtil.getJSONFactory().looseSerializeDeep(out)
                );
                return;
            }
        }

        // ---------- Load ventes ----------
        List<gestion_de_pharmacie.model.Vente> ventes;
        int total;

        if (userIds != null) {
            // list (ordered + paged)
            DynamicQuery dqList = _venteLocalService.dynamicQuery()
                    .add(RestrictionsFactoryUtil.in("idUtilisateur", userIds))
                    .addOrder(OrderFactoryUtil.desc("dateVente"));
            if (from != null) dqList.add(RestrictionsFactoryUtil.ge("dateVente", from));
            if (to   != null) dqList.add(RestrictionsFactoryUtil.le("dateVente", to));

            @SuppressWarnings("unchecked")
            List<gestion_de_pharmacie.model.Vente> list =
                    (List<gestion_de_pharmacie.model.Vente>)(List<?>) _venteLocalService.dynamicQuery(dqList, start, end);

            // count (same filters, NO order, with projection)
            DynamicQuery dqCount = _venteLocalService.dynamicQuery()
                    .add(RestrictionsFactoryUtil.in("idUtilisateur", userIds));
            if (from != null) dqCount.add(RestrictionsFactoryUtil.ge("dateVente", from));
            if (to   != null) dqCount.add(RestrictionsFactoryUtil.le("dateVente", to));
            dqCount.setProjection(ProjectionFactoryUtil.rowCount());
            Number cnt = (Number) _venteLocalService.dynamicQuery(dqCount).get(0);

            ventes = list;
            total  = cnt.intValue();

        } else if (userId > 0) {
            ventes = _venteLocalService.findByUserAndDate(userId, from, to, start, end);
            total  = _venteLocalService.findByUserAndDate(userId, from, to, -1, -1).size();

        } else {
            ventes = _venteLocalService.findByDateRange(from, to, start, end);
            total  = _venteLocalService.countByDateRange(from, to);
        }

        // ---------- Bulk fetch Utilisateur names/emails ----------
        Set<Long> uids = new HashSet<>();
        for (var v : ventes) if (v.getIdUtilisateur() > 0) uids.add(v.getIdUtilisateur());

        Map<Long, String> userNames  = new HashMap<>();
        Map<Long, String> userEmails = new HashMap<>();
        if (!uids.isEmpty()) {
            DynamicQuery dqU = UtilisateurLocalServiceUtil.dynamicQuery()
                    .add(RestrictionsFactoryUtil.in("idUtilisateur", new ArrayList<>(uids)));
            List<?> rows = UtilisateurLocalServiceUtil.dynamicQuery(dqU);
            for (Object o : rows) {
                if (o instanceof gestion_de_pharmacie.model.Utilisateur) {
                    var u = (gestion_de_pharmacie.model.Utilisateur) o;
                    userNames.put(u.getIdUtilisateur(), (u.getPrenom() + " " + u.getNom()).trim());
                    // ⚠️ Use the real getter for your email field:
                    // If your field is "adresseEmail", change to u.getAdresseEmail()
                    userEmails.put(u.getIdUtilisateur(), u.getEmail());
                }
            }
        }

        // ---------- Build response items ----------
        var items = new ArrayList<Map<String, Object>>();
        for (var v : ventes) {
            var r = new LinkedHashMap<String, Object>();
            r.put("idVente", v.getIdVente());
            r.put("date",    v.getDateVente());
            r.put("userId",  v.getIdUtilisateur());

            String display = userNames.get(v.getIdUtilisateur());
            if (display == null || display.isBlank()) {
                display = userEmails.get(v.getIdUtilisateur());
                if (display == null) display = (v.getIdUtilisateur() == 0 ? "" : String.valueOf(v.getIdUtilisateur()));
            }
            r.put("userName", display);

            r.put("montant", v.getMontantTotal());
            items.add(r);
        }

        var out = new LinkedHashMap<String, Object>();
        out.put("items", items);
        out.put("total", total);

        res.getWriter().write(
                com.liferay.portal.kernel.json.JSONFactoryUtil.getJSONFactory().looseSerializeDeep(out)
        );
    }


    private static String ns(ResourceRequest req, String name) {
        // helper to try namespaced first, then plain
        String withNs = com.liferay.portal.kernel.util.PortalUtil.getPortletNamespace(
                com.liferay.portal.kernel.util.PortalUtil.getPortletId(req)) + name;
        return withNs;
    }

    private void serveVenteDetails(ResourceRequest req, ResourceResponse res) throws IOException {
        res.setContentType("application/json; charset=UTF-8");

        String ns = com.liferay.portal.kernel.util.PortalUtil.getPortletNamespace(
                com.liferay.portal.kernel.util.PortalUtil.getPortletId(req));

        long idVente = ParamUtil.getLong(req, ns + "idVente");
        if (idVente == 0) idVente = ParamUtil.getLong(req, "idVente"); // fallback

        System.out.println("[DETAILS] idVente=" + idVente); // sanity log

        var lines = _venteLocalService.getDetails(idVente);

        var out = new java.util.ArrayList<java.util.Map<String,Object>>();
        for (var d : lines) {
            var r = new java.util.LinkedHashMap<String,Object>();
            r.put("idMedicament", d.getIdMedicament());
            r.put("quantite",     d.getQuantite());
            r.put("prixUnitaire", d.getPrixUnitaire());
            r.put("sousTotal",    d.getSousTotal());
            out.add(r);
        }
        res.getWriter().write(new com.liferay.portal.kernel.json.JSONFactoryUtil().looseSerializeDeep(out));
    }

    private void exportCsv(ResourceRequest req, ResourceResponse res) throws IOException {
        res.setContentType("text/csv; charset=UTF-8");
        res.setProperty("Content-Disposition", "attachment; filename=ventes.csv");

        String ns = com.liferay.portal.kernel.util.PortalUtil.getPortletNamespace(
                com.liferay.portal.kernel.util.PortalUtil.getPortletId(req));

        String fromStr  = com.liferay.portal.kernel.util.ParamUtil.getString(req, ns + "from",
                com.liferay.portal.kernel.util.ParamUtil.getString(req, "from"));
        String toStr    = com.liferay.portal.kernel.util.ParamUtil.getString(req, ns + "to",
                com.liferay.portal.kernel.util.ParamUtil.getString(req, "to"));
        String userLike = com.liferay.portal.kernel.util.ParamUtil.getString(req, ns + "user",
                com.liferay.portal.kernel.util.ParamUtil.getString(req, "user"));
        long userId     = com.liferay.portal.kernel.util.ParamUtil.getLong(req, ns + "userId",
                com.liferay.portal.kernel.util.ParamUtil.getLong(req, "userId", 0));

        java.util.Date from = parseDateTime(fromStr);
        java.util.Date to   = parseDateTime(toStr);

        java.util.List<gestion_de_pharmacie.model.Vente> ventes;

        if (userLike != null && !userLike.isBlank()) {
            // 1) Resolve userLike -> userIds
            DynamicQuery dqU = UtilisateurLocalServiceUtil.dynamicQuery()
                    .add(
                            RestrictionsFactoryUtil.disjunction()
                                    .add(RestrictionsFactoryUtil.ilike("nom", "%" + userLike + "%"))
                                    .add(RestrictionsFactoryUtil.ilike("prenom", "%" + userLike + "%"))
                                    .add(RestrictionsFactoryUtil.ilike(UTILISATEUR_EMAIL_COL, "%" + userLike + "%")) // <-- use constant
                    );


            java.util.List<Object> rawUsers =
                    gestion_de_pharmacie.service.UtilisateurLocalServiceUtil.dynamicQuery(dqU);

            java.util.List<Long> userIds = new java.util.ArrayList<>();
            for (Object o : rawUsers) {
                if (o instanceof gestion_de_pharmacie.model.Utilisateur) {
                    userIds.add(((gestion_de_pharmacie.model.Utilisateur) o).getIdUtilisateur());
                }
            }

            // 2) Build ventes dynamic query with IN(userIds) + dates
            com.liferay.portal.kernel.dao.orm.DynamicQuery dq =
                    _venteLocalService.dynamicQuery()
                            .addOrder(com.liferay.portal.kernel.dao.orm.OrderFactoryUtil.desc("dateVente"));

            if (!userIds.isEmpty()) {
                dq.add(com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.in("idUtilisateur", userIds));
            } else {
                // nobody matched -> empty CSV header only
                java.io.PrintWriter w = res.getWriter();
                w.println("idVente;date;heure;userId;montantTotal");
                w.flush();
                return;
            }

            if (from != null) dq.add(com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.ge("dateVente", from));
            if (to   != null) dq.add(com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.le("dateVente", to));

            // 3) SAFE conversion from List<?> to List<Vente>
            java.util.List<?> rawVentes = _venteLocalService.dynamicQuery(dq);
            ventes = new java.util.ArrayList<>(rawVentes.size());
            for (Object o : rawVentes) {
                if (o instanceof gestion_de_pharmacie.model.Vente) {
                    ventes.add((gestion_de_pharmacie.model.Vente)o);
                }
            }

        } else if (userId > 0) {
            // use your typed convenience method
            ventes = _venteLocalService.findByUserAndDate(userId, from, to, -1, -1);
        } else {
            // typed convenience method
            ventes = _venteLocalService.findByDateRange(from, to, -1, -1);
        }

        // Write CSV
        java.io.PrintWriter w = res.getWriter();
        w.println("idVente;date;heure;userId;montantTotal");
        for (gestion_de_pharmacie.model.Vente v : ventes) {
            java.util.Date d = v.getDateVente();
            w.printf("%d;%tF;%tR;%d;%.2f%n",
                    v.getIdVente(), d, d, v.getIdUtilisateur(), v.getMontantTotal());
        }
        w.flush();
    }

    private static Date parseDateTime(String s) {
        if (s == null || s.isBlank()) return null;
        try {
            // Accept HTML <input type="datetime-local"> like 2025-09-29T14:05
            if (s.length() == 16 && s.charAt(10) == 'T') {
                s = s.replace('T',' ') + ":00";
                return java.sql.Timestamp.valueOf(s);
            }
            // Accept yyyy-MM-dd
            if (s.length() == 10) {
                return java.sql.Date.valueOf(s);
            }
            // Fallback try Timestamp
            return java.sql.Timestamp.valueOf(s.replace('T',' ') );
        } catch (Exception e) { return null; }
    }



    private long resolveUtilisateurId(PortletRequest req) {
        try {
            // 1) Prefer your app’s session login
            String email = getAppSessionEmail(req);

            // 2) Fallback to Liferay portal user if app session not set
            if (email == null) {
                ThemeDisplay td = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
                if (td == null || !td.isSignedIn()) return 0L;
                email = td.getUser().getEmailAddress();
            }

            // Lookup by email in your domain table
            DynamicQuery dq = UtilisateurLocalServiceUtil.dynamicQuery()
                    .add(RestrictionsFactoryUtil.eq(UTILISATEUR_EMAIL_COL, email));
                    dq.setLimit(0, 1);

            List<?> rows = UtilisateurLocalServiceUtil.dynamicQuery(dq);
            if (!rows.isEmpty() && rows.get(0) instanceof gestion_de_pharmacie.model.Utilisateur) {
                return ((gestion_de_pharmacie.model.Utilisateur) rows.get(0)).getIdUtilisateur();
            }

            // Auto-create if not present (use real setter names)
            long newId = com.liferay.counter.kernel.service.CounterLocalServiceUtil
                    .increment(gestion_de_pharmacie.model.Utilisateur.class.getName());
            var u = gestion_de_pharmacie.service.UtilisateurLocalServiceUtil.createUtilisateur(newId);
            u.setEmail(email);                     // change to setAdresseEmail(...) if needed
            u.setPrenom("");                       // optional
            u.setNom("");                          // optional
            u = gestion_de_pharmacie.service.UtilisateurLocalServiceUtil.updateUtilisateur(u);
            return u.getIdUtilisateur();

        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    // Run once to repair old ventes with idUtilisateur=0
// attach your old ventes to your real Utilisateur
    public void backfillUtilisateurIds(PortletRequest req) {
        long myId = resolveUtilisateurId(req);
        if (myId == 0) return;

        DynamicQuery dq = _venteLocalService.dynamicQuery()
                .add(RestrictionsFactoryUtil.eq("idUtilisateur", 0L));
        @SuppressWarnings("unchecked")
        var bad = (java.util.List<gestion_de_pharmacie.model.Vente>)(java.util.List<?>) _venteLocalService.dynamicQuery(dq);

        for (var v : bad) { v.setIdUtilisateur(myId); _venteLocalService.updateVente(v); }
    }

    private void serveWhoAmI(ResourceRequest req, ResourceResponse res) throws IOException {
        res.setContentType("application/json; charset=UTF-8");
        ThemeDisplay td = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);

        Map<String,Object> out = new LinkedHashMap<>();
        out.put("portalSignedIn", td != null && td.isSignedIn());
        if (td != null) {
            out.put("portalEmail", td.getUser().getEmailAddress());
            out.put("portalName", td.getUser().getFullName());
        }
        out.put("appSessionEmail", getAppSessionEmail(req));
        out.put("resolvedUtilisateurId", resolveUtilisateurId(req));

        res.getWriter().write(
                com.liferay.portal.kernel.json.JSONFactoryUtil.getJSONFactory().looseSerializeDeep(out)
        );
    }



}
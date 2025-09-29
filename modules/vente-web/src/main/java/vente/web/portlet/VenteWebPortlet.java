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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

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
        super.doView(req, res);
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

            long userId = 0L; // map your Utilisateur if needed
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
        res.setContentType("application/json; charset=UTF-8");

        String q = ParamUtil.getString(req, "q");

        if (q.isEmpty()) {
            String ns = com.liferay.portal.kernel.util.PortalUtil.getPortletNamespace(
                    com.liferay.portal.kernel.util.PortalUtil.getPortletId(req));
            q = ParamUtil.getString(req, ns + "q");
        }

        List<Map<String, Object>> out = new ArrayList<>();

        try {
            List<Medicament> meds = new ArrayList<>();

            if (!q.isEmpty()) {
                // 1) exact barcode match
                meds.addAll(MedicamentLocalServiceUtil.findByCodeBarre(q));

                // 2) exact internal code
                if (meds.isEmpty()) {
                    meds.addAll(MedicamentLocalServiceUtil.findByCode(q));
                }

                // 3) name contains (case-insensitive)
                if (meds.isEmpty()) {
                    int limit = 10;

                    com.liferay.portal.kernel.dao.orm.DynamicQuery dq =
                            MedicamentLocalServiceUtil.dynamicQuery()
                                    .add(
                                            com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil.ilike(
                                                    "nom", "%" + q + "%"
                                            )
                                    )
                                    .addOrder(
                                            com.liferay.portal.kernel.dao.orm.OrderFactoryUtil.asc("nom")
                                    );

                    @SuppressWarnings("unchecked")
                    List<Object> byNameObj = MedicamentLocalServiceUtil.dynamicQuery(dq, 0, limit);
                    List<Medicament> byName = new ArrayList<>();
                    for (Object o : byNameObj) {
                        if (o instanceof Medicament) {
                            byName.add((Medicament) o);
                        }
                    }

                    meds.addAll(byName);
                }
            }

            for (Medicament m : meds) {
                Stock s = StockLocalServiceUtil.fetchStockByMedicamentId(m.getIdMedicament());
                int dispo = (s == null) ? 0 : s.getQuantiteDisponible();

                Map<String, Object> row = new LinkedHashMap<>();
                row.put("id", m.getIdMedicament());
                row.put("nom", m.getNom());
                row.put("code", m.getCode());
                row.put("codeBarre", m.getCodeBarre());
                row.put("prix", m.getPrixUnitaire());
                row.put("dispo", dispo);
                out.add(row);
            }
        } catch (Exception ignore) { }

        String json = new com.liferay.portal.kernel.json.JSONFactoryUtil().looseSerializeDeep(out);
        res.getWriter().write(json);
    }


}
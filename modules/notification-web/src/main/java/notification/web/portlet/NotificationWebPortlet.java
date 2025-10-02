package notification.web.portlet;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import gestion_de_pharmacie.model.Notification;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.NotificationLocalService;
import gestion_de_pharmacie.service.UtilisateurLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Notifications portlet – uses APP session identity first (USER_EMAIL), falls back to Liferay portal user.
 */
@Component(
        property = {
                "com.liferay.portlet.display-category=category.hidden",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=Notifications",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=notification_web_NotificationWebPortlet",
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class NotificationWebPortlet extends MVCPortlet {

    // Keep these in sync with VenteWebPortlet
    private static final String PS_USER_EMAIL = "USER_EMAIL";     // app session key
    private static final String UTILISATEUR_EMAIL_COL = "email";   // change if your column is "adresseEmail"

    @Reference
    private NotificationLocalService notificationLocalService;

    @Reference
    private UtilisateurLocalService utilisateurLocalService;


    /* =========================
       RESOURCES: unread & list
       ========================= */
    @Override
    public void serveResource(ResourceRequest req, ResourceResponse res) throws IOException, PortletException {
        res.setCharacterEncoding("UTF-8");

        String rid = req.getResourceID();                    // from <liferay-portlet:resourceURL id="...">
        String cmd = ParamUtil.getString(req, "cmd");        // fallback for manual calls
        String op  = (rid != null && !rid.isEmpty()) ? rid : cmd;

        long uid = resolveUtilisateurId(req);
        System.out.println("[serveResource] op=" + op + " uid=" + uid);

        if ("unread".equals(op)) {
            int c = (uid > 0) ? safeCountUnread(uid) : 0;
            System.out.println("[unread] count=" + c + " for uid=" + uid);
            res.setContentType("application/json; charset=UTF-8");
            res.getWriter().write("{\"unread\":" + c + "}");
            return;
        }

        if ("list".equals(op)) {
            res.setContentType("application/json; charset=UTF-8");
            List<Map<String,Object>> out = new ArrayList<>();

            if (uid == 0) {
                System.out.println("[list] uid=0 (not mapped) -> returning empty list");
                res.getWriter().write("[]");
                return;
            }

            // Latest first for THIS user
            DynamicQuery dq = notificationLocalService.dynamicQuery()
                    .add(RestrictionsFactoryUtil.eq("idUtilisateur", uid))
                    .addOrder(OrderFactoryUtil.desc("dateCreation"));

            @SuppressWarnings("unchecked")
            List<Notification> rows = (List<Notification>)(List<?>)
                    notificationLocalService.dynamicQuery(dq, 0, 50);

            System.out.println("[list] rows=" + rows.size() + " for uid=" + uid);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (Notification n : rows) {
                Map<String,Object> r = new LinkedHashMap<>();
                r.put("id", n.getIdNotification());
                r.put("idNotification", n.getIdNotification()); // keep both keys for JSP safety
                r.put("type", n.getType());
                r.put("message", n.getMessage());
                r.put("status", n.getStatut());  // JSP checks 'status'
                r.put("statut", n.getStatut());  // and we still expose 'statut'
                r.put("date", n.getDateCreation() != null ? sdf.format(n.getDateCreation()) : "");
                out.add(r);

                System.out.println("[list] n#" + n.getIdNotification()
                        + " type=" + n.getType()
                        + " statut=" + n.getStatut()
                        + " date=" + r.get("date"));
            }

            String json = com.liferay.portal.kernel.json.JSONFactoryUtil
                    .getJSONFactory().looseSerializeDeep(out);
            res.getWriter().write(json);
            return;
        }

        System.out.println("[serveResource] unknown op=" + op);
        super.serveResource(req, res);
    }

    /* =========================
       Identity mapping (APP first)
       ========================= */
    private long resolveUtilisateurId(PortletRequest req) {
        try {
            String email = getAppSessionEmail(req);
            if (email == null) {
                ThemeDisplay td = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
                if (td == null || !td.isSignedIn()) {
                    System.out.println("[resolveUtilisateurId] no USER_EMAIL in app session and not signed in");
                    return 0L;
                }
                email = td.getUser().getEmailAddress();
                System.out.println("[resolveUtilisateurId] fallback portal email=" + email);
            } else {
                System.out.println("[resolveUtilisateurId] app session email=" + email);
            }

            // Lookup in your domain table
            DynamicQuery dq = utilisateurLocalService.dynamicQuery()
                    .add(RestrictionsFactoryUtil.eq(UTILISATEUR_EMAIL_COL, email));
            dq.setLimit(0, 1);

            @SuppressWarnings("unchecked")
            List<Utilisateur> rows = (List<Utilisateur>)(List<?>)
                    utilisateurLocalService.dynamicQuery(dq);

            System.out.println("[resolveUtilisateurId] rows=" + rows.size());

            if (!rows.isEmpty()) {
                long id = rows.get(0).getIdUtilisateur();
                System.out.println("[resolveUtilisateurId] mapped to idUtilisateur=" + id);
                return id;
            }

            System.out.println("[resolveUtilisateurId] no Utilisateur row for email=" + email + " -> uid=0");
            return 0L;

        } catch (Exception e) {
            System.out.println("[resolveUtilisateurId] failed " + e);
            e.printStackTrace();
            return 0L;
        }
    }

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

    private int safeCountUnread(long uid) {
        try {
            DynamicQuery dq = notificationLocalService.dynamicQuery()
                    .add(RestrictionsFactoryUtil.eq("idUtilisateur", uid))
                    .add(RestrictionsFactoryUtil.eq("statut", "UNREAD"));
            @SuppressWarnings("unchecked")
            List<?> rows = notificationLocalService.dynamicQuery(dq);
            return rows.size();
        } catch (Exception e) {
            System.out.println("[safeCountUnread] failed for uid=" + uid + " err=" + e);
            e.printStackTrace();
            return 0;
        }
    }
    @ProcessAction(name = "delete_all")
    public void deleteAll(ActionRequest request, ActionResponse response) throws PortletException {
        request.getParameterMap().forEach((k,v) ->
                System.out.println("[delete_all] param " + k + "=" + java.util.Arrays.toString(v)));

        long uid = resolveUtilisateurId(request);
        System.out.println("[delete_all] for uid=" + uid);

        if (uid > 0) {
            try {
                int deleted;
                try {
                    deleted = notificationLocalService.deleteAllForUser(uid); // your per-user method
                } catch (NoSuchMethodError | RuntimeException ex) {
                    // fallback if you only have a global method during testing
                    deleted = notificationLocalService.deleteAllNotifications();
                }
                System.out.println("[delete_all] removed=" + deleted);
            } catch (Exception e) {
                System.out.println("[delete_all] ERROR " + e);
                throw new PortletException(e);
            }
        }

        response.setRenderParameter("mvcPath", "/view.jsp");
        response.setRenderParameter("ts", String.valueOf(System.currentTimeMillis()));
    }
    @ProcessAction(name = "mark_read")
    public void markRead(ActionRequest request, ActionResponse response) throws PortletException {
        // Try namespaced, plain, then original servlet request (belt & suspenders)
        String ns = PortalUtil.getPortletNamespace(PortalUtil.getPortletId(request));

        long id = ParamUtil.getLong(request, ns + "idNotification");
        if (id == 0) id = ParamUtil.getLong(request, "idNotification");
        if (id == 0) {
            // final fallback if container filtered the plain param
            javax.servlet.http.HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(
                    PortalUtil.getHttpServletRequest(request));
            try { id = Long.parseLong(httpReq.getParameter(ns + "idNotification")); } catch (Exception ignore) {}
            if (id == 0) try { id = Long.parseLong(httpReq.getParameter("idNotification")); } catch (Exception ignore) {}
        }

        System.out.println("[mark_read] resolved id=" + id);
        if (id > 0) {
            notificationLocalService.markAsRead(id);
        }

        response.setRenderParameter("mvcPath", "/view.jsp");
        response.setRenderParameter("ts", String.valueOf(System.currentTimeMillis()));
    }

}

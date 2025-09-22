package notification.web.portlet;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import gestion_de_pharmacie.model.Notification;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.NotificationLocalService;
import gestion_de_pharmacie.service.UtilisateurLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component(
        immediate = true,
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

    @Reference private NotificationLocalService _notifLocalService;
    @Reference private UtilisateurLocalService  _userLocalService;

    @Override
    public void doView(RenderRequest req, RenderResponse res)
            throws IOException, PortletException {

        long uid = resolveUid(req);

        List<Notification> notifs = (uid > 0)
                ? _notifLocalService.getByUserStatus(
                uid, "UNREAD", QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
                : Collections.emptyList();

        req.setAttribute("notifs", notifs);
        super.doView(req, res);
    }

    private long resolveUid(PortletRequest req){
        PortletSession s = req.getPortletSession();
        String email = (String) s.getAttribute("userEmail", PortletSession.APPLICATION_SCOPE);
        if (email == null || email.isEmpty()) return 0;

        Utilisateur u = _userLocalService.getUtilisateurByEmail(email); // wrapper you added
        return (u != null) ? u.getIdUtilisateur() : 0;
    }
    @ProcessAction(name = "markAllRead")
    public void markAllRead(ActionRequest req, ActionResponse res) {
        long uid = resolveUid(req);
        if (uid <= 0) return;
        List<Notification> unread = _notifLocalService.getByUserStatus(uid, "UNREAD", QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
        for (Notification n : unread) {
            n.setStatut("READ");
            _notifLocalService.updateNotification(n);
        }
    }

}

package notification.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import gestion_de_pharmacie.model.Notification;
import gestion_de_pharmacie.service.NotificationLocalServiceUtil;

import javax.portlet.*;
import org.osgi.service.component.annotations.Component;
import java.io.IOException;
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

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        // You can add simple filtering/paging later; for now fetch all
        List<Notification> notifs = NotificationLocalServiceUtil.getNotifications(-1, -1);
        req.setAttribute("notifs", notifs);
        super.doView(req, res);
    }
}

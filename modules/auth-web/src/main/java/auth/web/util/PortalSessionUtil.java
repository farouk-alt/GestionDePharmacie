// common util (e.g., in a small shared module or duplicate in each portlet for now)
package auth.web.util;

// .../util/PortalSessionUtil.java
import com.liferay.portal.kernel.util.PortalUtil;
import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PortalSessionUtil {
    public static HttpSession httpSession(ActionRequest req) {
        HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);
        httpReq = PortalUtil.getOriginalServletRequest(httpReq);
        return httpReq.getSession();
    }
    public static HttpSession httpSession(RenderRequest req) {
        HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);
        httpReq = PortalUtil.getOriginalServletRequest(httpReq);
        return httpReq.getSession();
    }
}


package authentification.web.portlet;

import authentification.web.constants.AuthentificationWebPortletKeys;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.*;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.UtilisateurLocalServiceUtil;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;

/**
 * @author farou
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=AuthentificationWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/login.jsp",
		"javax.portlet.name=" + AuthentificationWebPortletKeys.AUTHENTIFICATIONWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class AuthentificationWebPortlet extends MVCPortlet {
    // Action method for registration
    public void register(ActionRequest request, ActionResponse response) throws PortalException {
        String fullname = ParamUtil.getString(request, "fullname");
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");
        String confirmPassword = ParamUtil.getString(request, "confirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Les mots de passe ne correspondent pas !");
            return;
        }

        String[] names = fullname.split(" ", 2);
        String prenom = names.length > 1 ? names[0] : fullname;
        String nom = names.length > 1 ? names[1] : "";

        ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

        // Default role for registration: PHARMACIEN
        Utilisateur utilisateur = UtilisateurLocalServiceUtil.addUtilisateur(
                serviceContext.getUserId(),
                serviceContext.getCompanyId(),
                email,
                password,
                prenom,
                nom,
                "PHARMACIEN",
                serviceContext
        );

// Then explicitly set the role
        UtilisateurLocalServiceUtil.updateUtilisateur(utilisateur);

        // Optionally, redirect to login page
        response.setRenderParameter("mvcPath", "/login.jsp");
    }

    // Action method for login
    public void login(ActionRequest request, ActionResponse response) throws PortalException, IOException {
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");

        Utilisateur utilisateur = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);

        if (utilisateur != null && utilisateur.getMotDePasse().equals(password)) {
            // Login successful → store in session
            request.getPortletSession().setAttribute("currentUser", utilisateur, PortletSession.APPLICATION_SCOPE);
            // Redirect to dashboard (or your main portlet)
            response.sendRedirect("/");
        } else {
            request.setAttribute("loginError", "Email ou mot de passe incorrect !");
        }
    }
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        String action = ParamUtil.getString(renderRequest, "action");

        if ("register".equals(action)) {
            include("/register.jsp", renderRequest, renderResponse);
        } else {
            include("/login.jsp", renderRequest, renderResponse);
        }
    }


}
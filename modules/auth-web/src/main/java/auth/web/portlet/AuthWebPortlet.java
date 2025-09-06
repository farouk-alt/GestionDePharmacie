package auth.web.portlet;

import auth.web.constants.AuthWebPortletKeys;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.UtilisateurLocalServiceUtil;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component(
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=AuthWeb",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/pharmacy-login.jsp",
                "javax.portlet.init-param.content-type=text/html; charset=UTF-8",
                "javax.portlet.name=" + AuthWebPortletKeys.AUTHWEB,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class AuthWebPortlet extends MVCPortlet {

    // Action method for registration
    public void register(ActionRequest request, ActionResponse response) throws Exception {
        String fullname = ParamUtil.getString(request, "fullname");
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");
        String confirmPassword = ParamUtil.getString(request, "confirmPassword");

        System.out.println("Register attempt - Fullname: " + fullname + ", Email: " + email +
                ", Password: " + password + ", Confirm Password: " + confirmPassword);

        // Validation
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Les mots de passe ne correspondent pas !");
            response.setRenderParameter("mvcPath", "/pharmacy-register.jsp");
            return;
        }

        // Check if email already exists
        if (UtilisateurLocalServiceUtil.getUtilisateurByEmail(email) != null) {
            request.setAttribute("errorMessage", "Cet email est déjà utilisé !");
            response.setRenderParameter("mvcPath", "/pharmacy-register.jsp");
            return;
        }

        String[] names = fullname.split(" ", 2);
        String prenom = names.length > 1 ? names[0] : fullname;
        String nom = names.length > 1 ? names[1] : "";

        // Generate a unique ID using Liferay's counter service
        long newUserId = CounterLocalServiceUtil.increment();

        // Create user with simple authentication
        Utilisateur utilisateur = UtilisateurLocalServiceUtil.createUtilisateur(newUserId);
        utilisateur.setEmail(email);
        // Use hashed password instead of plain text
        utilisateur.setMotDePasse(hashPassword(password));
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setRole("PHARMACIEN"); // Default role

        System.out.println("Creating user with ID: " + newUserId + ", Prenom: " + prenom + ", Nom: " + nom + ", Email: " + email);

        UtilisateurLocalServiceUtil.addUtilisateur(utilisateur);

        request.setAttribute("successMessage", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
        response.setRenderParameter("mvcPath", "/pharmacy-login.jsp");
    }

/*
    public void login(ActionRequest request, ActionResponse response) throws Exception {
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");

        System.out.println("Login attempt - Email: " + email + ", Password: " + password);


        // Super admin hardcoded (bypass hashing for demo) - CHECK THIS FIRST
        if ("admin@pharma.com".equals(email) && "12345".equals(password)) {
            PortletSession session = request.getPortletSession();
            session.setAttribute("authenticated", true, PortletSession.APPLICATION_SCOPE);
            session.setAttribute("userEmail", email, PortletSession.APPLICATION_SCOPE);
            session.setAttribute("userRole", "SUPER_ADMIN", PortletSession.APPLICATION_SCOPE);
            session.setAttribute("currentUser", null, PortletSession.APPLICATION_SCOPE); // no DB entry

            response.sendRedirect("/web/guest/dashboard"); // Super Admin dashboard
            return;  // IMPORTANT: Exit the method after handling admin login
        }

        // Normal users - compare hashed passwords
        Utilisateur utilisateur = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);

        if (utilisateur != null && utilisateur.getMotDePasse().equals(hashPassword(password))) {
            PortletSession session = request.getPortletSession();
            session.setAttribute("authenticated", true, PortletSession.APPLICATION_SCOPE);
            session.setAttribute("userEmail", utilisateur.getEmail(), PortletSession.APPLICATION_SCOPE);
            session.setAttribute("userRole", utilisateur.getRole(), PortletSession.APPLICATION_SCOPE);
            session.setAttribute("currentUser", utilisateur, PortletSession.APPLICATION_SCOPE);

            response.sendRedirect("/web/guest/dashboard"); // Normal dashboard
        } else {
            request.setAttribute("loginError", "Email ou mot de passe incorrect !");
            response.setRenderParameter("mvcPath", "/pharmacy-login.jsp");
        }
    }
*/
// Action method for login
    public void login(ActionRequest request, ActionResponse response) throws Exception {
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");

        System.out.println("Login attempt - Email: " + email + ", Password: " + password);

        // Super admin hardcoded (bypass hashing for demo)
        if ("admin@pharma.com".equals(email) && "12345".equals(password)) {
            System.out.println("Super admin login successful");
            PortletSession session = request.getPortletSession();
            session.setAttribute("authenticated", true, PortletSession.APPLICATION_SCOPE);
            session.setAttribute("userEmail", email, PortletSession.APPLICATION_SCOPE);
            session.setAttribute("userRole", "SUPER_ADMIN", PortletSession.APPLICATION_SCOPE);
            session.setAttribute("currentUser", null, PortletSession.APPLICATION_SCOPE);

            // Use render parameter instead of redirect
            response.setRenderParameter("mvcPath", "/dashboard.jsp");
            return;
        }

        // Normal users - compare hashed passwords
        Utilisateur utilisateur = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);

        if (utilisateur != null && utilisateur.getMotDePasse().equals(hashPassword(password))) {
            PortletSession session = request.getPortletSession();
            session.setAttribute("authenticated", true, PortletSession.APPLICATION_SCOPE);
            session.setAttribute("userEmail", utilisateur.getEmail(), PortletSession.APPLICATION_SCOPE);
            session.setAttribute("userRole", utilisateur.getRole(), PortletSession.APPLICATION_SCOPE);
            session.setAttribute("currentUser", utilisateur, PortletSession.APPLICATION_SCOPE);

            // Use render parameter instead of redirect
            response.setRenderParameter("mvcPath", "/dashboard.jsp");
        } else {
            request.setAttribute("loginError", "Email ou mot de passe incorrect !");
            response.setRenderParameter("mvcPath", "/pharmacy-login.jsp");
        }
    }
    // Action method for logout
    public void logout(ActionRequest request, ActionResponse response) throws IOException {
        PortletSession session = request.getPortletSession();
        session.removeAttribute("authenticated", PortletSession.APPLICATION_SCOPE);
        session.removeAttribute("userEmail", PortletSession.APPLICATION_SCOPE);
        session.removeAttribute("userRole", PortletSession.APPLICATION_SCOPE);
        session.removeAttribute("currentUser", PortletSession.APPLICATION_SCOPE);
        session.invalidate();

        // Set success message and show login page
        request.setAttribute("successMessage", "Vous avez été déconnecté avec succès.");
        response.setRenderParameter("mvcPath", "/pharmacy-login.jsp");
    }

    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        PortletSession session = renderRequest.getPortletSession();
        Boolean authenticated = (Boolean) session.getAttribute("authenticated", PortletSession.APPLICATION_SCOPE);

        // Check if we should show the test page
        String showTest = ParamUtil.getString(renderRequest, "showTest");

        if ("true".equals(showTest)) {
            // Show test page
            include("/test.jsp", renderRequest, renderResponse);
            return;
        }

        if (Boolean.TRUE.equals(authenticated)) {
            // User is logged in, show dashboard
            include("/dashboard.jsp", renderRequest, renderResponse);
        } else {
            // Not logged in → check action (login/register)
            String action = ParamUtil.getString(renderRequest, "action");
            if ("register".equals(action)) {
                include("/pharmacy-register.jsp", renderRequest, renderResponse);
            } else {
                include("/pharmacy-login.jsp", renderRequest, renderResponse);
            }
        }
    }

    // Helper method to check if user is authenticated
    public static boolean isAuthenticated(PortletRequest request) {
        return request.getPortletSession().getAttribute("currentUser", PortletSession.APPLICATION_SCOPE) != null;
    }

    // Helper method to get current user
    public static Utilisateur getCurrentUser(PortletRequest request) {
        return (Utilisateur) request.getPortletSession().getAttribute("currentUser", PortletSession.APPLICATION_SCOPE);
    }

    // Password hashing method
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
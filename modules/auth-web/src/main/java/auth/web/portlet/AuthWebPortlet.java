package auth.web.portlet;

import auth.web.constants.AuthWebPortletKeys;
import auth.web.constants.PortalSessionKeys;
import auth.web.util.PortalSessionUtil; // <-- make sure this exists in auth-web (same code as in dashboard)
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.UtilisateurLocalServiceUtil;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

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

    // REGISTER
    public void register(ActionRequest request, ActionResponse response) throws Exception {
        String fullname = ParamUtil.getString(request, "fullname");
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");
        String confirmPassword = ParamUtil.getString(request, "confirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Les mots de passe ne correspondent pas !");
            response.setRenderParameter("mvcPath", "/pharmacy-register.jsp");
            return;
        }
        if (UtilisateurLocalServiceUtil.getUtilisateurByEmail(email) != null) {
            request.setAttribute("errorMessage", "Cet email est déjà utilisé !");
            response.setRenderParameter("mvcPath", "/pharmacy-register.jsp");
            return;
        }

        String[] names = fullname.split(" ", 2);
        String prenom = names.length > 1 ? names[0] : fullname;
        String nom    = names.length > 1 ? names[1] : "";

        long newUserId = CounterLocalServiceUtil.increment();
        Utilisateur u = UtilisateurLocalServiceUtil.createUtilisateur(newUserId);
        u.setEmail(email);
        u.setMotDePasse(hashPassword(password));
        u.setPrenom(prenom);
        u.setNom(nom);
        u.setRole("PHARMACIEN");
        u.setDateCreation(new Date());

        UtilisateurLocalServiceUtil.addUtilisateur(u);

        request.setAttribute("successMessage", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
        response.setRenderParameter("mvcPath", "/pharmacy-login.jsp");
    }

    // LOGIN (HttpSession)
    public void login(ActionRequest request, ActionResponse response) throws Exception {
        String email = ParamUtil.getString(request, "email");
        String password = ParamUtil.getString(request, "password");

        // Demo SUPER_ADMIN
        if ("admin@pharma.com".equals(email) && "12345".equals(password)) {
            HttpSession hs = PortalSessionUtil.httpSession(request);
            hs.setAttribute(PortalSessionKeys.AUTHENTICATED, Boolean.TRUE);
            hs.setAttribute(PortalSessionKeys.USER_EMAIL, email);
            hs.setAttribute(PortalSessionKeys.USER_ROLE, "SUPER_ADMIN");
            response.sendRedirect("/web/guest/dashboard"); // page hosting dashboard-web
            return;
        }

        // Normal users
        Utilisateur u = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);
        if (u != null && u.getMotDePasse().equals(hashPassword(password))) {
            u.setLastLogin(new Date());
            UtilisateurLocalServiceUtil.updateUtilisateur(u);

            HttpSession hs = PortalSessionUtil.httpSession(request);
            hs.setAttribute(PortalSessionKeys.AUTHENTICATED, Boolean.TRUE);
            hs.setAttribute(PortalSessionKeys.USER_EMAIL, u.getEmail());
            hs.setAttribute(PortalSessionKeys.USER_ROLE, u.getRole());

            // DEBUG: prove it's written
            System.out.println("[AUTH] set HS id=" + hs.getId()
                    + " email=" + hs.getAttribute(PortalSessionKeys.USER_EMAIL)
                    + " role="  + hs.getAttribute(PortalSessionKeys.USER_ROLE));

            response.sendRedirect("/web/guest/dashboard");
        } else {
            request.setAttribute("loginError", "Email ou mot de passe incorrect !");
            response.setRenderParameter("mvcPath", "/pharmacy-login.jsp");
        }
    }

    // LOGOUT (HttpSession)
    @ProcessAction(name = "logout")
    public void logout(ActionRequest request, ActionResponse response) throws IOException {
        HttpSession hs = PortalSessionUtil.httpSession(request);
        hs.removeAttribute(PortalSessionKeys.AUTHENTICATED);
        hs.removeAttribute(PortalSessionKeys.USER_EMAIL);
        hs.removeAttribute(PortalSessionKeys.USER_ROLE);
        // hs.invalidate(); // optional

        // Redirect to the login page (portal URL that hosts AuthWeb)
        response.sendRedirect("/login");
    }

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        HttpSession hs = PortalSessionUtil.httpSession(req);
        Boolean authenticated = (Boolean) hs.getAttribute(PortalSessionKeys.AUTHENTICATED);

        // Optional: if already logged in, send them to dashboard page
        // if (Boolean.TRUE.equals(authenticated)) {
        //     res.createRenderURL(); // no-op; better to use a page redirect
        // }

        String action = ParamUtil.getString(req, "action");
        if ("register".equals(action)) {
            include("/pharmacy-register.jsp", req, res);
        } else {
            include("/pharmacy-login.jsp", req, res);
        }
    }

    // (If you keep these helpers, switch them to HttpSession too)
    public static boolean isAuthenticated(PortletRequest request) {
        HttpSession hs = PortalSessionUtil.httpSession((RenderRequest) request);
        Boolean auth = (Boolean) hs.getAttribute(PortalSessionKeys.AUTHENTICATED);
        return Boolean.TRUE.equals(auth);
    }
    public static Utilisateur getCurrentUser(PortletRequest request) {
        // You don't store the full Utilisateur in session; fetch by email if needed
        HttpSession hs = PortalSessionUtil.httpSession((RenderRequest) request);
        String email = (String) hs.getAttribute(PortalSessionKeys.USER_EMAIL);
        try {
            return UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}

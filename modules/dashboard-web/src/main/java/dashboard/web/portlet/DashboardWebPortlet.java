package dashboard.web.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import dashboard.web.constants.DashboardWebPortletKeys;
import dashboard.web.constants.PortalSessionKeys;
import dashboard.web.util.PortalSessionUtil;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.UtilisateurLocalServiceUtil;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component(
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=DashboardWeb",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/Super Admin/dashboard.jsp",
                "javax.portlet.name=" + DashboardWebPortletKeys.DASHBOARDWEB,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DashboardWebPortlet extends MVCPortlet {

    @ProcessAction(name = "addAdmin")
    public void addAdmin(ActionRequest request, ActionResponse response) throws Exception {
        String email = ParamUtil.getString(request, "email");
        String nom = ParamUtil.getString(request, "nom");
        String prenom = ParamUtil.getString(request, "prenom");
        String motDePasse = ParamUtil.getString(request, "motDePasse");

        Utilisateur utilisateur = UtilisateurLocalServiceUtil.createUtilisateur(
                CounterLocalServiceUtil.increment(Utilisateur.class.getName())
        );
        utilisateur.setEmail(email);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setMotDePasse(hashPassword(motDePasse));
        utilisateur.setRole("ADMIN");
        utilisateur.setDateCreation(new Date());

        UtilisateurLocalServiceUtil.addUtilisateur(utilisateur);

        request.setAttribute("successMessage", "Nouvel admin ajouté: " + email);
// addAdmin (example)
        response.setRenderParameter("successMsg", "Nouvel admin ajouté: " + email);
        response.setRenderParameter("mvcPath", "/Super Admin/dashboard.jsp");
        response.setRenderParameter("section", "admins");


    }

    @ProcessAction(name = "deleteAdmin")
    public void deleteAdmin(ActionRequest request, ActionResponse response) throws Exception {
        String email = ParamUtil.getString(request, "email");
        try {
            Utilisateur utilisateur = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);
            if (utilisateur != null) {
                String role = utilisateur.getRole();
                if ("ADMIN".equals(role) || "SUPER_ADMIN".equals(role)) {
                    HttpSession hs = PortalSessionUtil.httpSession(request);
                    String currentUserEmail = (String) hs.getAttribute(PortalSessionKeys.USER_EMAIL);

                    if (currentUserEmail != null && currentUserEmail.equals(email)) {
                        request.setAttribute("deleteErrorMessage", "Vous ne pouvez pas supprimer votre propre compte.");
                    } else {
                        UtilisateurLocalServiceUtil.deleteUtilisateur(utilisateur.getIdUtilisateur());
                        request.setAttribute("deleteSuccessMessage", "Admin " + email + " supprimé avec succès.");
                    }
                } else {
                    request.setAttribute("deleteErrorMessage", "L'utilisateur " + email + " n'est pas un administrateur.");
                }
            } else {
                request.setAttribute("deleteErrorMessage", "Aucun utilisateur trouvé avec l'email: " + email);
            }
        } catch (Exception e) {
            request.setAttribute("deleteErrorMessage", "Erreur lors de la suppression: " + e.getMessage());
            e.printStackTrace();
        }
        response.setRenderParameter("mvcPath", "/Super Admin/dashboard.jsp");
        response.setRenderParameter("section", "admins");
    }

    @ProcessAction(name = "changeRole")
    public void changeRole(ActionRequest request, ActionResponse response) throws Exception {
        long idUtilisateur = ParamUtil.getLong(request, "idUtilisateur");
        String newRole = ParamUtil.getString(request, "newRole");

        Utilisateur utilisateur = UtilisateurLocalServiceUtil.getUtilisateur(idUtilisateur);
        utilisateur.setRole(newRole);
        UtilisateurLocalServiceUtil.updateUtilisateur(utilisateur);

        response.setRenderParameter("mvcPath", "/Super Admin/dashboard.jsp");
        response.setRenderParameter("section", "admins");
    }

    /*@Override
    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        HttpSession hs = PortalSessionUtil.httpSession(request);
        String userRole  = (String) hs.getAttribute(PortalSessionKeys.USER_ROLE);
        String userEmail = (String) hs.getAttribute(PortalSessionKeys.USER_EMAIL);
// DEBUG: must match the [AUTH] line
        System.out.println("[DASH] get HS id=" + hs.getId()
                + " email=" + userEmail
                + " role="  + userRole);
        request.setAttribute("userRole",  userRole);   // request-scope for JSP
        request.setAttribute("userEmail", userEmail);

        System.out.println("[DBG] HS userRole=" + hs.getAttribute(PortalSessionKeys.USER_ROLE)
                + ", userEmail=" + hs.getAttribute(PortalSessionKeys.USER_EMAIL));


        if ("SUPER_ADMIN".equals(userRole) || "ADMIN".equals(userRole)) {
            try {
                List<Utilisateur> allUsers = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
                List<Utilisateur> employees = allUsers.stream()
                        .filter(u -> !"SUPER_ADMIN".equals(u.getRole()))
                        .filter(u -> !u.getEmail().equals(userEmail))
                        .collect(Collectors.toList());
                request.setAttribute("employees", employees);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.render(request, response);
    }*/
    /*@Override
    public void render(RenderRequest request, RenderResponse response)
            throws IOException, PortletException {

        HttpSession hs = PortalSessionUtil.httpSession(request);
        String userRole  = (String) hs.getAttribute(PortalSessionKeys.USER_ROLE);
        String userEmail = (String) hs.getAttribute(PortalSessionKeys.USER_EMAIL);

        request.setAttribute("userRole",  userRole);
        request.setAttribute("userEmail", userEmail);

        // which sub-view?
        String section = ParamUtil.getString(request, "section", "overview");
        request.setAttribute("section", section);

        // load employees only when needed
        if (("users".equals(section) || "admins".equals(section)) &&
                ("SUPER_ADMIN".equals(userRole) || "ADMIN".equals(userRole))) {
            try {
                List<Utilisateur> all = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
                request.setAttribute("employees", all);
            } catch (Exception e) { e.printStackTrace(); }
        }

        super.render(request, response);
    }*/

    @Override
    public void render(RenderRequest request, RenderResponse response)
            throws IOException, PortletException {

        HttpSession hs = PortalSessionUtil.httpSession(request);
        String userEmail = (String) hs.getAttribute(PortalSessionKeys.USER_EMAIL);
        String userRole  = (String) hs.getAttribute(PortalSessionKeys.USER_ROLE);

        // Fallback to Liferay user if custom session is empty (after redeploy, page editor, etc.)
        ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        if ((userEmail == null || userEmail.isEmpty()) && td != null && td.isSignedIn()) {
            try {
                userEmail = td.getUser().getEmailAddress();
                try {
                    // map to your domain user to get the app role
                    gestion_de_pharmacie.model.Utilisateur u =
                            UtilisateurLocalServiceUtil.getUtilisateurByEmail(userEmail);
                    if (u != null) {
                        userRole = u.getRole();
                    }
                } catch (Exception ignore) {
                    // no domain user yet – leave role null/blank
                }
            } catch (Exception ignore) { }
        }

        // NEW: If still blank, map Liferay permissions to your app roles
        if ((userRole == null || userRole.isEmpty()) && td != null && td.isSignedIn()) {
            try {
                boolean isSuperAdmin   = td.getPermissionChecker() != null
                        && td.getPermissionChecker().isOmniadmin();
                boolean isCompanyAdmin = td.getPermissionChecker() != null
                        && td.getPermissionChecker().isCompanyAdmin(td.getCompanyId());

                if (isSuperAdmin) {
                    userRole = "SUPER_ADMIN";
                } else if (isCompanyAdmin) {
                    userRole = "ADMIN";
                } else {
                    userRole = "UTILISATEUR"; // or PHARMACIEN, pick your default
                }
            } catch (Exception ignore) { /* keep blank if anything goes wrong */ }
        }

        // NEW: persist resolved values back to session so next renders are filled
        if (userEmail != null && !userEmail.isEmpty()) {
            hs.setAttribute(PortalSessionKeys.USER_EMAIL, userEmail);
        }
        if (userRole != null && !userRole.isEmpty()) {
            hs.setAttribute(PortalSessionKeys.USER_ROLE, userRole);
        }

        request.setAttribute("userEmail", userEmail != null ? userEmail : "");
        request.setAttribute("userRole",  userRole  != null ? userRole  : "");

        // selected section
        String section = ParamUtil.getString(request, "section", "overview");
        request.setAttribute("section", section);

        // Always provide a total count so the header/debug can show something
        try {
            int employeesTotal = UtilisateurLocalServiceUtil.getUtilisateursCount();
            request.setAttribute("employeesTotal", employeesTotal);
        } catch (Exception e) {
            request.setAttribute("employeesTotal", 0);
        }

        // Load the list only where it’s needed
        if (("users".equals(section) || "admins".equals(section))
                && (userRole != null && !userRole.isEmpty())) {
            try {
                List<gestion_de_pharmacie.model.Utilisateur> all =
                        UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
                request.setAttribute("employees", all);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.render(request, response);
    }




    @ProcessAction(name = "switchRole")
    public void switchRole(ActionRequest request, ActionResponse response) throws Exception {
        String targetUserEmail = ParamUtil.getString(request, "targetUserEmail");
        String newRole = ParamUtil.getString(request, "newRole");

        Utilisateur utilisateur = UtilisateurLocalServiceUtil.getUtilisateurByEmail(targetUserEmail);
        if (utilisateur != null) {
            HttpSession hs = PortalSessionUtil.httpSession(request);
            String currentUserRole  = (String) hs.getAttribute(PortalSessionKeys.USER_ROLE);
            String currentUserEmail = (String) hs.getAttribute(PortalSessionKeys.USER_EMAIL);

            if ("ADMIN".equals(newRole) && !"SUPER_ADMIN".equals(currentUserRole)) {
                request.setAttribute("errorMessage", "Seul le Super Admin peut promouvoir un Admin.");
                response.setRenderParameter("mvcPath", "/Super Admin/dashboard.jsp");
                return;
            }

            utilisateur.setRole(newRole);
            UtilisateurLocalServiceUtil.updateUtilisateur(utilisateur);

            // If you changed your own role, reflect it in session
            if (targetUserEmail.equals(currentUserEmail)) {
                hs.setAttribute(PortalSessionKeys.USER_ROLE, newRole);
            }

            request.setAttribute("successMessage", "Rôle de " + targetUserEmail + " mis à jour avec succès!");
        } else {
            request.setAttribute("errorMessage", "Utilisateur non trouvé: " + targetUserEmail);
        }

        response.setRenderParameter("mvcPath", "/Super Admin/dashboard.jsp");
        response.setRenderParameter("section", "admins");
    }

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

    @ProcessAction(name = "logout")
    public void logout(ActionRequest request, ActionResponse response) throws IOException {
        HttpSession hs = PortalSessionUtil.httpSession(request);
        hs.removeAttribute(PortalSessionKeys.AUTHENTICATED);
        hs.removeAttribute(PortalSessionKeys.USER_EMAIL);
        hs.removeAttribute(PortalSessionKeys.USER_ROLE);
        // Optional: hs.invalidate();

        // Redirect to the login PAGE (friendly URL), not a JSP in this portlet
        // Adjust the URL to your portal page that shows AuthWeb:
        // e.g. "/login" OR "/web/guest/login"
        ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        String loginPage = td.getPortalURL() + "/web/guest/login"; // or simply "/login" if that’s your page
        response.sendRedirect(loginPage);
    }
}

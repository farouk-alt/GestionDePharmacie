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
                "javax.portlet.init-param.view-template=/common/dashboard.jsp",
                "javax.portlet.name=" + DashboardWebPortletKeys.DASHBOARDWEB,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class DashboardWebPortlet extends MVCPortlet {
    @ProcessAction(name = "addAdmin")
    public void addAdmin(ActionRequest request, ActionResponse response) throws Exception {
        HttpSession hs = PortalSessionUtil.httpSession(request);
        String currentRole = (String) hs.getAttribute(PortalSessionKeys.USER_ROLE);

        if (!"SUPER_ADMIN".equals(currentRole)) {
            response.setRenderParameter("errorMsg", "Seul le Super Admin peut ajouter un administrateur.");
            response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
            response.setRenderParameter("section", "admins");
            return;
        }

        String email = ParamUtil.getString(request, "email");
        String nom = ParamUtil.getString(request, "nom");
        String prenom = ParamUtil.getString(request, "prenom");
        String motDePasse = ParamUtil.getString(request, "motDePasse");

        // refuse if user already exists (ask to use switchRole instead)
        gestion_de_pharmacie.model.Utilisateur existing = null;
        try { existing = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email); } catch (Exception ignore) {}
        if (existing != null) {
            response.setRenderParameter("errorMsg", "Un utilisateur avec cet email existe déjà. Modifiez son rôle depuis la liste.");
            response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
            response.setRenderParameter("section", "admins");
            return;
        }

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

        response.setRenderParameter("successMsg", "Nouvel admin ajouté: " + email);
        response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
        response.setRenderParameter("section", "admins");
    }

    @ProcessAction(name = "deleteAdmin")
    public void deleteAdmin(ActionRequest request, ActionResponse response) throws Exception {
        HttpSession hs = PortalSessionUtil.httpSession(request);
        String currentRole = (String) hs.getAttribute(PortalSessionKeys.USER_ROLE);
        String currentEmail = (String) hs.getAttribute(PortalSessionKeys.USER_EMAIL);

        if (!"SUPER_ADMIN".equals(currentRole)) {
            response.setRenderParameter("errorMsg", "Seul le Super Admin peut supprimer un administrateur.");
            response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
            response.setRenderParameter("section", "admins");
            return;
        }

        String email = ParamUtil.getString(request, "email");
        try {
            Utilisateur target = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);
            if (target == null) {
                response.setRenderParameter("errorMsg", "Aucun utilisateur trouvé avec l'email: " + email);
            } else if ("SUPER_ADMIN".equals(target.getRole())) {
                response.setRenderParameter("errorMsg", "Impossible de supprimer un SUPER_ADMIN.");
            } else if (email.equals(currentEmail)) {
                response.setRenderParameter("errorMsg", "Vous ne pouvez pas supprimer votre propre compte.");
            } else if (!"ADMIN".equals(target.getRole())) {
                response.setRenderParameter("errorMsg", "L'utilisateur " + email + " n'est pas un administrateur.");
            } else {
                UtilisateurLocalServiceUtil.deleteUtilisateur(target.getIdUtilisateur());
                response.setRenderParameter("successMsg", "Admin " + email + " supprimé avec succès.");
            }
        } catch (Exception e) {
            response.setRenderParameter("errorMsg", "Erreur lors de la suppression: " + e.getMessage());
        }

        response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
        response.setRenderParameter("section", "admins");
    }

    @ProcessAction(name = "switchRole")
    public void switchRole(ActionRequest request, ActionResponse response) throws Exception {
        HttpSession hs = PortalSessionUtil.httpSession(request);
        String currentUserRole  = (String) hs.getAttribute(PortalSessionKeys.USER_ROLE);
        String currentUserEmail = (String) hs.getAttribute(PortalSessionKeys.USER_EMAIL);

        String targetEmail = ParamUtil.getString(request, "targetUserEmail");
        String newRole     = ParamUtil.getString(request, "newRole");

        try {
            Utilisateur target = UtilisateurLocalServiceUtil.getUtilisateurByEmail(targetEmail);
            if (target == null) {
                response.setRenderParameter("errorMsg", "Utilisateur non trouvé: " + targetEmail);
            } else {
                String targetCurrent = target.getRole();

                // ADMIN can only toggle between PHARMACIEN and FOURNISSEUR (and only for such users)
                if ("ADMIN".equals(currentUserRole)) {
                    boolean allowedNew = "PHARMACIEN".equals(newRole) || "FOURNISSEUR".equals(newRole);
                    boolean targetEligible = "PHARMACIEN".equals(targetCurrent) || "FOURNISSEUR".equals(targetCurrent);

                    if (!allowedNew || !targetEligible) {
                        response.setRenderParameter("errorMsg", "Autorisation refusée. Un Admin ne peut changer les rôles qu'entre Pharmacien et Fournisseur.");
                    } else {
                        target.setRole(newRole);
                        UtilisateurLocalServiceUtil.updateUtilisateur(target);

                        // if you changed your own role (rare for ADMIN here), reflect it
                        if (targetEmail.equals(currentUserEmail)) {
                            hs.setAttribute(PortalSessionKeys.USER_ROLE, newRole);
                        }
                        response.setRenderParameter("successMsg", "Rôle de " + targetEmail + " mis à jour avec succès!");
                    }
                }
                // SUPER_ADMIN: can set to ADMIN/PHARMACIEN/FOURNISSEUR (but not promote/demote SUPER_ADMIN for safety)
                else if ("SUPER_ADMIN".equals(currentUserRole)) {
                    if ("SUPER_ADMIN".equals(newRole)) {
                        response.setRenderParameter("errorMsg", "La gestion du rôle SUPER_ADMIN est bloquée pour éviter un verrouillage.");
                    } else {
                        if ("SUPER_ADMIN".equals(targetCurrent)) {
                            response.setRenderParameter("errorMsg", "Vous ne pouvez pas modifier un SUPER_ADMIN.");
                        } else if (!"ADMIN".equals(newRole) && !"PHARMACIEN".equals(newRole) && !"FOURNISSEUR".equals(newRole)) {
                            response.setRenderParameter("errorMsg", "Rôle cible invalide.");
                        } else {
                            target.setRole(newRole);
                            UtilisateurLocalServiceUtil.updateUtilisateur(target);
                            if (targetEmail.equals(currentUserEmail)) {
                                hs.setAttribute(PortalSessionKeys.USER_ROLE, newRole);
                            }
                            response.setRenderParameter("successMsg", "Rôle de " + targetEmail + " mis à jour avec succès!");
                        }
                    }
                }
                // others: forbidden
                else {
                    response.setRenderParameter("errorMsg", "Autorisation refusée.");
                }
            }
        } catch (Exception e) {
            response.setRenderParameter("errorMsg", "Erreur lors de la mise à jour du rôle: " + e.getMessage());
        }

        response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
        response.setRenderParameter("section", "admins");
    }

    @ProcessAction(name = "changeRole")
    public void changeRole(ActionRequest request, ActionResponse response) throws Exception {
        long idUtilisateur = ParamUtil.getLong(request, "idUtilisateur");
        String newRole = ParamUtil.getString(request, "newRole");

        Utilisateur utilisateur = UtilisateurLocalServiceUtil.getUtilisateur(idUtilisateur);
        utilisateur.setRole(newRole);
        UtilisateurLocalServiceUtil.updateUtilisateur(utilisateur);

        response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
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
    @Override
    public void render(RenderRequest request, RenderResponse response)
            throws IOException, PortletException {

        PortletSession ps = request.getPortletSession();
        String userEmail = null;
        String userRole = null;

        // Debug: List all PortletSession attributes
        System.out.println("[DEBUG] === PortletSession Attributes ===");
        java.util.Enumeration<String> appAttrNames = ps.getAttributeNames(PortletSession.APPLICATION_SCOPE);
        while (appAttrNames.hasMoreElements()) {
            String name = appAttrNames.nextElement();
            Object value = ps.getAttribute(name, PortletSession.APPLICATION_SCOPE);
            System.out.println("  APPLICATION_SCOPE - " + name + " = " + value);
        }

        java.util.Enumeration<String> portletAttrNames = ps.getAttributeNames(PortletSession.PORTLET_SCOPE);
        while (portletAttrNames.hasMoreElements()) {
            String name = portletAttrNames.nextElement();
            Object value = ps.getAttribute(name, PortletSession.PORTLET_SCOPE);
            System.out.println("  PORTLET_SCOPE - " + name + " = " + value);
        }

        // Get from PortletSession (same as login portlet)
        userEmail = (String) ps.getAttribute("USER_EMAIL", PortletSession.APPLICATION_SCOPE);
        userRole = (String) ps.getAttribute("USER_ROLE", PortletSession.APPLICATION_SCOPE);

        System.out.println("[DEBUG] Retrieved - Email: " + userEmail + ", Role: " + userRole);

        // If still null, try ThemeDisplay
        if (userEmail == null || userRole == null) {
            ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
            if (td != null && td.isSignedIn()) {
                userEmail = td.getUser().getEmailAddress();
                System.out.println("[DEBUG] From ThemeDisplay: " + userEmail);

                try {
                    Utilisateur u = UtilisateurLocalServiceUtil.getUtilisateurByEmail(userEmail);
                    if (u != null) {
                        userRole = u.getRole();
                        // Save to PortletSession
                        ps.setAttribute("USER_EMAIL", userEmail, PortletSession.APPLICATION_SCOPE);
                        ps.setAttribute("USER_ROLE", userRole, PortletSession.APPLICATION_SCOPE);
                        ps.setAttribute("AUTHENTICATED", Boolean.TRUE, PortletSession.APPLICATION_SCOPE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Set request attributes
        request.setAttribute("userEmail", userEmail != null ? userEmail : "");
        request.setAttribute("userRole", userRole != null ? userRole : "");

        // Rest of your method remains the same...
        String section = ParamUtil.getString(request, "section", "overview");
        request.setAttribute("section", section);

        if (("users".equals(section) || "admins".equals(section)) &&
                ("SUPER_ADMIN".equals(userRole) || "ADMIN".equals(userRole))) {
            try {
                List<Utilisateur> all = UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1);
                request.setAttribute("employees", all);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.render(request, response);
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

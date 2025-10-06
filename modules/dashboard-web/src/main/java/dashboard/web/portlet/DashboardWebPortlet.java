package dashboard.web.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import dashboard.web.constants.DashboardWebPortletKeys;
import dashboard.web.constants.PortalSessionKeys;
import dashboard.web.util.PortalSessionUtil;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.UtilisateurLocalServiceUtil;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    @ProcessAction(name = "deleteUser")
    public void deleteUser(ActionRequest request, ActionResponse response) throws Exception {
        String actorRole = getEffectiveRole(request);
        String email = ParamUtil.getString(request, "email");

        try {
            Utilisateur target = UtilisateurLocalServiceUtil.getUtilisateurByEmail(email);
            if (target == null) {
                response.setRenderParameter("errorMsg", "Aucun utilisateur trouvé avec l'email: " + email);
            } else if ("SUPER_ADMIN".equals(target.getRole())) {
                response.setRenderParameter("errorMsg", "Impossible de supprimer un SUPER_ADMIN.");
            } else if ("ADMIN".equals(target.getRole())) {
                // only SUPER_ADMIN may delete ADMIN
                if (!"SUPER_ADMIN".equals(actorRole)) {
                    response.setRenderParameter("errorMsg", "Seul le Super Admin peut supprimer un administrateur.");
                } else {
                    UtilisateurLocalServiceUtil.deleteUtilisateur(target.getIdUtilisateur());
                    response.setRenderParameter("successMsg", "Admin " + email + " supprimé avec succès.");
                }
            } else {
                // PHARMACIEN / FOURNISSEUR
                if (!"ADMIN".equals(actorRole) && !"SUPER_ADMIN".equals(actorRole)) {
                    response.setRenderParameter("errorMsg", "Autorisation refusée.");
                } else {
                    UtilisateurLocalServiceUtil.deleteUtilisateur(target.getIdUtilisateur());
                    response.setRenderParameter("successMsg", "Utilisateur " + email + " supprimé avec succès.");
                }
            }
        } catch (Exception e) {
            response.setRenderParameter("errorMsg", "Erreur lors de la suppression: " + e.getMessage());
        }

        response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
        response.setRenderParameter("section", "utilisateurs");
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

   /* @ProcessAction(name = "changeRole")
    public void changeRole(ActionRequest request, ActionResponse response) throws Exception {
        long idUtilisateur = ParamUtil.getLong(request, "idUtilisateur");
        String newRole = ParamUtil.getString(request, "newRole");

        Utilisateur utilisateur = UtilisateurLocalServiceUtil.getUtilisateur(idUtilisateur);
        utilisateur.setRole(newRole);
        UtilisateurLocalServiceUtil.updateUtilisateur(utilisateur);

        response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
        response.setRenderParameter("section", "admins");
    }*/
   @ProcessAction(name = "changeRole")
   public void changeRole(ActionRequest request, ActionResponse response) throws Exception {
       HttpSession hs = resolveHttpSession(request);
       String actorRole = getEffectiveRole(request);
       String actorEmail = getEffectiveEmail(request);

       long idUtilisateur = ParamUtil.getLong(request, "idUtilisateur");
       String newRole = ParamUtil.getString(request, "newRole", "").trim().toUpperCase(Locale.ROOT);

       System.out.println("=== CHANGE ROLE DEBUG ===");
       System.out.println("Actor role: " + actorRole);
       System.out.println("Actor email: " + actorEmail);
       System.out.println("Target ID: " + idUtilisateur);
       System.out.println("New role requested: " + newRole);

       if (newRole.isEmpty()) {
           response.setRenderParameter("errorMsg", "Choisissez un rôle valide.");
           response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
           response.setRenderParameter("section", "admins");
           return;
       }

       if (actorRole == null || actorRole.isEmpty()) {
           response.setRenderParameter("errorMsg", "Session invalide. Reconnectez-vous.");
           response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
           response.setRenderParameter("section", "admins");
           return;
       }

       try {
           Utilisateur target = UtilisateurLocalServiceUtil.getUtilisateur(idUtilisateur);
           if (target == null) {
               System.out.println("ERROR: Target user not found");
               response.setRenderParameter("errorMsg", "Utilisateur introuvable.");
           } else {
               String targetRole = target.getRole();
               System.out.println("Target current role: " + targetRole);

               if ("ADMIN".equals(actorRole)) {
                   boolean allowed = ("PHARMACIEN".equals(newRole) || "FOURNISSEUR".equals(newRole)) &&
                           ("PHARMACIEN".equals(targetRole) || "FOURNISSEUR".equals(targetRole));

                   if (!allowed) {
                       System.out.println("DENIED: Admin can only change Pharmacien/Fournisseur");
                       response.setRenderParameter("errorMsg", "Autorisation refusée.");
                   } else {
                       target.setRole(newRole);
                       UtilisateurLocalServiceUtil.updateUtilisateur(target);
                       System.out.println("SUCCESS: Updated to " + newRole);

                       if (actorEmail != null && actorEmail.equals(target.getEmail())) {
                           if (hs != null) {
                               hs.setAttribute(PortalSessionKeys.USER_ROLE, newRole);
                               hs.setAttribute("USER_ROLE", newRole);
                           }
                           request.getPortletSession().setAttribute("USER_ROLE", newRole, PortletSession.APPLICATION_SCOPE);
                       }
                       response.setRenderParameter("successMsg", "Rôle mis à jour: " + newRole);
                   }
               } else if ("SUPER_ADMIN".equals(actorRole)) {
                   if ("SUPER_ADMIN".equals(newRole) || "SUPER_ADMIN".equals(targetRole)) {
                       System.out.println("DENIED: Cannot modify SUPER_ADMIN");
                       response.setRenderParameter("errorMsg", "Impossible de modifier SUPER_ADMIN.");
                   } else if ("ADMIN".equals(newRole) || "PHARMACIEN".equals(newRole) || "FOURNISSEUR".equals(newRole)) {
                       target.setRole(newRole);
                       UtilisateurLocalServiceUtil.updateUtilisateur(target);
                       System.out.println("SUCCESS: Updated to " + newRole);

                       if (actorEmail != null && actorEmail.equals(target.getEmail())) {
                           if (hs != null) {
                               hs.setAttribute(PortalSessionKeys.USER_ROLE, newRole);
                               hs.setAttribute("USER_ROLE", newRole);
                           }
                           request.getPortletSession().setAttribute("USER_ROLE", newRole, PortletSession.APPLICATION_SCOPE);
                       }
                       response.setRenderParameter("successMsg", "Rôle mis à jour: " + newRole);
                   } else {
                       response.setRenderParameter("errorMsg", "Rôle invalide.");
                   }
               } else {
                   System.out.println("DENIED: No permission");
                   response.setRenderParameter("errorMsg", "Autorisation refusée.");
               }
           }
       } catch (Exception e) {
           System.out.println("EXCEPTION: " + e.getMessage());
           e.printStackTrace();
           response.setRenderParameter("errorMsg", "Erreur: " + e.getMessage());
       }

       response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
       response.setRenderParameter("section", "admins");
       System.out.println("=== END DEBUG ===");
   }




    @Override
    public void render(RenderRequest request, RenderResponse response)
            throws IOException, PortletException {

        PortletSession ps = request.getPortletSession();

        // 1) Try PortletSession first
        String userEmail = (String) ps.getAttribute("USER_EMAIL", PortletSession.APPLICATION_SCOPE);
        String userRole  = (String) ps.getAttribute("USER_ROLE",  PortletSession.APPLICATION_SCOPE);

        // 2) If missing, try Liferay user
        if (userEmail == null || userRole == null) {
            ThemeDisplay td = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
            if (td != null && td.isSignedIn()) {
                userEmail = td.getUser().getEmailAddress();
                try {
                    Utilisateur u = UtilisateurLocalServiceUtil.getUtilisateurByEmail(userEmail);
                    if (u != null) {
                        userRole = u.getRole();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 3) Store in all session types
        if (userEmail != null) {
            ps.setAttribute("USER_EMAIL", userEmail, PortletSession.APPLICATION_SCOPE);
        }
        if (userRole != null) {
            ps.setAttribute("USER_ROLE", userRole, PortletSession.APPLICATION_SCOPE);
        }

        HttpSession hs = resolveHttpSession(request);
        if (hs != null) {
            if (userEmail != null) {
                hs.setAttribute(PortalSessionKeys.USER_EMAIL, userEmail);
                hs.setAttribute("USER_EMAIL", userEmail);  // ADD THIS LINE
            }
            if (userRole != null) {
                hs.setAttribute(PortalSessionKeys.USER_ROLE, userRole);
                hs.setAttribute("USER_ROLE", userRole);    // ADD THIS LINE
            }
            if (userEmail != null && userRole != null) {
                hs.setAttribute(PortalSessionKeys.AUTHENTICATED, Boolean.TRUE);
            }
        }

        // Expose to JSP
        request.setAttribute("userEmail", userEmail != null ? userEmail : "");
        request.setAttribute("userRole",  userRole  != null ? userRole  : "");

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
    @ProcessAction(name = "bulkChangeRoles")
    public void bulkChangeRoles(ActionRequest request, ActionResponse response) throws Exception {
        String actorRole = getEffectiveRole(request);
        String idsCsv    = ParamUtil.getString(request, "ids");
        String newRole   = ParamUtil.getString(request, "bulkRole");
        newRole = (newRole == null ? "" : newRole.trim().toUpperCase(java.util.Locale.ROOT));

        System.out.println("[bulkChangeRoles] actorRole=" + actorRole + ", ids=" + idsCsv + ", newRole=" + newRole);

        if (idsCsv == null || idsCsv.trim().isEmpty()) {
            response.setRenderParameter("errorMsg", "Aucune sélection.");
            response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
            response.setRenderParameter("section", "admins");
            return;
        }

        String[] parts = idsCsv.split(",");
        int ok = 0, ko = 0;

        for (String p : parts) {
            long id;
            try { id = Long.parseLong(p.trim()); } catch (NumberFormatException nfe) { ko++; continue; }

            try {
                Utilisateur u = UtilisateurLocalServiceUtil.getUtilisateur(id);
                if (u == null) { ko++; continue; }

                if ("ADMIN".equals(actorRole)) {
                    boolean allowedNew = "PHARMACIEN".equals(newRole) || "FOURNISSEUR".equals(newRole);
                    boolean targetEligible = "PHARMACIEN".equals(u.getRole()) || "FOURNISSEUR".equals(u.getRole());
                    if (!allowedNew || !targetEligible) { ko++; continue; }
                } else if ("SUPER_ADMIN".equals(actorRole)) {
                    if ("SUPER_ADMIN".equals(newRole) || "SUPER_ADMIN".equals(u.getRole())) { ko++; continue; }
                    if (!"ADMIN".equals(newRole) && !"PHARMACIEN".equals(newRole) && !"FOURNISSEUR".equals(newRole)) { ko++; continue; }
                } else { ko++; continue; }

                u.setRole(newRole);
                UtilisateurLocalServiceUtil.updateUtilisateur(u);
                ok++;
            } catch (Exception ex) { ko++; }
        }

        if (ok > 0 && ko == 0) {
            response.setRenderParameter("successMsg", ok + " utilisateur(s) mis à jour.");
        } else if (ok > 0) {
            response.setRenderParameter("successMsg", ok + " succès, " + ko + " échec(s).");
        } else {
            response.setRenderParameter("errorMsg", "Aucune mise à jour (contrôle d’accès ou données invalides).");
        }

        response.setRenderParameter("mvcPath", "/common/dashboard.jsp");
        response.setRenderParameter("section", "admins");
    }
    private HttpSession resolveHttpSession(PortletRequest req) {
        HttpSession hs = null;

        // Use your PortalSessionUtil where possible
        try {
            if (req instanceof ActionRequest) {
                hs = PortalSessionUtil.httpSession((ActionRequest) req);
            } else if (req instanceof RenderRequest) {
                hs = PortalSessionUtil.httpSession((RenderRequest) req);
            }
        } catch (Throwable ignore) {}

        // Fallback via PortalUtil
        if (hs == null) {
            HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(req);
            if (httpReq != null) {
                hs = httpReq.getSession(false); // don't create if absent
            }
        }
        return hs;
    }

    private String getEffectiveRole(PortletRequest req) {
        HttpSession hs = resolveHttpSession(req);
        Object v = (hs != null) ? hs.getAttribute(PortalSessionKeys.USER_ROLE) : null;
        String role = (v != null) ? String.valueOf(v) : null;

        if (role == null || role.isEmpty()) {
            PortletSession ps = req.getPortletSession();
            Object vv = (ps != null) ? ps.getAttribute("USER_ROLE", PortletSession.APPLICATION_SCOPE) : null;
            role = (vv != null) ? String.valueOf(vv) : null;
        }

        role = (role == null) ? null : role.trim().toUpperCase(java.util.Locale.ROOT);
        System.out.println("[getEffectiveRole] -> " + role);
        return role;
    }

    private String getEffectiveEmail(PortletRequest req) {
        HttpSession hs = resolveHttpSession(req);
        Object v = (hs != null) ? hs.getAttribute(PortalSessionKeys.USER_EMAIL) : null;
        String email = (v != null) ? String.valueOf(v) : null;

        if (email == null || email.isEmpty()) {
            PortletSession ps = req.getPortletSession();
            Object vv = (ps != null) ? ps.getAttribute("USER_EMAIL", PortletSession.APPLICATION_SCOPE) : null;
            email = (vv != null) ? String.valueOf(vv) : null;
        }

        System.out.println("[getEffectiveEmail] -> " + email);
        return email;
    }

}

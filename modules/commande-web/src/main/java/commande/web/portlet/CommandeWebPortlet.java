package commande.web.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import gestion_de_pharmacie.model.*;
import gestion_de_pharmacie.service.*;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;


@Component(
		property = {
				"com.liferay.portlet.display-category=category.sample",
				"com.liferay.portlet.header-portlet-css=/css/main.css",
				"com.liferay.portlet.instanceable=true",
				"javax.portlet.display-name=CommandeWeb",
				"javax.portlet.init-param.template-path=/",
				"javax.portlet.init-param.view-template=/view.jsp",
				"javax.portlet.name=commande_web",
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
)
public class CommandeWebPortlet extends MVCPortlet {

private static final Log _log = LogFactoryUtil.getLog(CommandeWebPortlet.class);

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws PortletException, IOException {
        try {
            System.out.println("---- doView called ----");

            // 1. Get session info (from login)
            PortletSession ps = req.getPortletSession();
            String userEmail = (String) ps.getAttribute("USER_EMAIL", PortletSession.APPLICATION_SCOPE);
            String userRole  = (String) ps.getAttribute("USER_ROLE", PortletSession.APPLICATION_SCOPE);

            // 2. Common data
            List<Utilisateur> fournisseurs = UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR");
            List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);
            List<Commande> commandes = new ArrayList<>();

            // 3. Filter commandes
            if ("FOURNISSEUR".equals(userRole) && userEmail != null) {
                Utilisateur fournisseur = UtilisateurLocalServiceUtil.getUtilisateurByEmail(userEmail);
                if (fournisseur != null) {
                    commandes = CommandeLocalServiceUtil.getCommandesByUtilisateurId(fournisseur.getIdUtilisateur());
                    System.out.println("✅ Fournisseur commandes fetched for: " + userEmail);
                } else {
                    System.out.println("⚠ No fournisseur found for email: " + userEmail);
                }
            } else if ("SUPER_ADMIN".equals(userRole) || "PHARMACIEN".equals(userRole)) {
                commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
                System.out.println("✅ Admin/Pharmacien sees all commandes");
            } else {
                System.out.println("⛔ Role not authorized: " + userRole);
            }

            // 4. Set attributes for JSP
            req.setAttribute("fournisseurs", fournisseurs);
            req.setAttribute("medicaments", medicaments);
            req.setAttribute("commandes", commandes);
            req.setAttribute("userRole", userRole);

        } catch (Exception e) {
            System.out.println("💥 doView failed: " + e.getMessage());
            e.printStackTrace();
        }
        super.doView(req, res);
    }


    @ProcessAction(name = "createCommande")
    public void createCommande(ActionRequest request, ActionResponse response) {
        try {
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                   CREATE COMMANDE DEBUG START                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("📅 Timestamp: " + new Date());
            System.out.println("🌐 Remote Address: " + request.getRemoteUser());
            System.out.println("🔗 Content Type: " + request.getContentType());

            // Wrap request to handle multipart/form-data
            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);

            // Retrieve fournisseurId
            long fournisseurId = ParamUtil.getLong(uploadRequest, "fournisseurId");
            String fournisseurIdParam = uploadRequest.getParameter("fournisseurId");
            System.out.println("👤 Fournisseur ID: " + fournisseurId + " | Raw param: " + fournisseurIdParam);

            // Retrieve medicament IDs
            String[] medicamentIds = uploadRequest.getParameterValues("medicamentId");
            System.out.println("💊 Medicament IDs: " + (medicamentIds != null ? java.util.Arrays.toString(medicamentIds) : "NONE"));

            if (fournisseurId <= 0 || medicamentIds == null || medicamentIds.length == 0) {
                System.out.println("❌ Missing fournisseurId or medicamentIds. Aborting...");
                SessionMessages.add(request, "commande-no-items");
                response.setRenderParameter("mvcPath", "/view.jsp");
                return;
            }

            // Verify fournisseur exists
            Utilisateur fournisseur = UtilisateurLocalServiceUtil.getUtilisateur(fournisseurId);
            System.out.println("✅ Fournisseur found: " + fournisseur.getNom() + " " + fournisseur.getPrenom());

            // Create Commande
            long commandeId = CounterLocalServiceUtil.increment(Commande.class.getName());
            Commande commande = CommandeLocalServiceUtil.createCommande(commandeId);
            commande.setIdUtilisateur(fournisseurId);
            commande.setDateCommande(new Date());
            commande.setStatut("CREATED");
            commande.setMontantTotal(0.0);

            double total = 0.0;
            List<CommandeDetail> details = new ArrayList<>();

            // Process each medicament
            for (String medIdStr : medicamentIds) {
                if (medIdStr == null || medIdStr.trim().isEmpty()) continue;

                long medId = Long.parseLong(medIdStr);
                System.out.println("💊 Processing medicament ID: " + medId);

                // Get quantity
                int quantity = ParamUtil.getInteger(uploadRequest, "quantite_" + medId, 1);
                if (quantity <= 0) quantity = 1;
                System.out.println("   📦 Quantity: " + quantity);

                // Fetch medicament
                Medicament medicament = MedicamentLocalServiceUtil.fetchMedicament(medId);
                if (medicament == null) {
                    System.out.println("   ❌ Medicament not found: " + medId);
                    continue;
                }

                double prixUnitaire = medicament.getPrixUnitaire();
                double subtotal = prixUnitaire * quantity;
                total += subtotal;

                System.out.println("   💰 Prix Unitaire: " + prixUnitaire);
                System.out.println("   📊 Subtotal: " + subtotal);
                System.out.println("   ✅ Medicament Name: " + medicament.getNom());

                // Create detail
                long detailId = CounterLocalServiceUtil.increment(CommandeDetail.class.getName());
                CommandeDetail detail = CommandeDetailLocalServiceUtil.createCommandeDetail(detailId);
                detail.setIdCommande(commandeId);
                detail.setIdMedicament(medId);
                detail.setQuantite(quantity);
                detail.setPrixUnitaire(prixUnitaire);
                details.add(detail);
            }

            // Finalize commande
            commande.setMontantTotal(total);
            CommandeLocalServiceUtil.addCommande(commande);
            System.out.println("✅ Commande saved: ID=" + commande.getIdCommande() + ", Total=" + total);

            // Save details
            for (CommandeDetail detail : details) {
                CommandeDetailLocalServiceUtil.addCommandeDetail(detail);
                System.out.println("✅ Detail saved for medicament ID: " + detail.getIdMedicament());
            }
            List<Commande> commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
            List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);
            List<Fournisseur> fournisseurs = FournisseurLocalServiceUtil.getFournisseurs(-1, -1);

            request.setAttribute("commandes", commandes);
            request.setAttribute("medicaments", medicaments);
            request.setAttribute("fournisseurs", fournisseurs);


            System.out.println("📦 TOTAL ITEMS: " + details.size());
            SessionMessages.add(request, "commande-created-success");
            response.setRenderParameter("mvcPath", "/view.jsp");

            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                   CREATE COMMANDE DEBUG END                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

        } catch (Exception e) {
            System.out.println("💥 CRITICAL ERROR IN CREATE COMMANDE:");
            e.printStackTrace();
            SessionMessages.add(request, "commande-create-error");
            response.setRenderParameter("mvcPath", "/view.jsp");
        }
    }

}

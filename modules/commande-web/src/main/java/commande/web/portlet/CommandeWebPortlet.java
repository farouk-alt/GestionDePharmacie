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

/*	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		try {
			// List all fournisseurs (role = FOURNISSEUR)
			List<Utilisateur> fournisseurs =
					UtilisateurLocalServiceUtil.getUtilisateurs(-1, -1)
							.stream()
							.filter(u -> "FOURNISSEUR".equalsIgnoreCase(u.getRole()))
							.toList();

			// List all medicaments
			List<Medicament> medicaments =
					MedicamentLocalServiceUtil.getMedicaments(-1, -1);

			// List all commandes
			List<Commande> commandes =
					CommandeLocalServiceUtil.getCommandes(-1, -1);

			renderRequest.setAttribute("fournisseurs", fournisseurs);
			renderRequest.setAttribute("medicaments", medicaments);
			renderRequest.setAttribute("commandes", commandes);

		} catch (Exception e) {
			e.printStackTrace();
		}

		super.doView(renderRequest, renderResponse);
	}


	@ProcessAction(name = "createCommande")
	public void createCommande(ActionRequest request, ActionResponse response) {
		try {
			long fournisseurId = ParamUtil.getLong(request, "fournisseurId");

			// Create Commande
			long cmdId = CounterLocalServiceUtil.increment();
			Commande cmd = CommandeLocalServiceUtil.createCommande(cmdId);
			cmd.setIdFournisseur(fournisseurId);
			cmd.setDateCommande(new Date());
			cmd.setStatut("EN_ATTENTE");
			cmd.setMontantTotal(0.0);
			CommandeLocalServiceUtil.addCommande(cmd);

			double montantTotal = 0.0;

			// Get selected medicaments
			String[] medicamentIds = request.getParameterValues("medicamentId");
			if (medicamentIds != null) {
				for (String medIdStr : medicamentIds) {
					long medId = Long.parseLong(medIdStr);

					// fetch quantity by dynamic name
					int qte = ParamUtil.getInteger(request, "quantite_" + medId, 1);

					Medicament m = MedicamentLocalServiceUtil.getMedicament(medId);

					long detailId = CounterLocalServiceUtil.increment();
					CommandeDetail detail = CommandeDetailLocalServiceUtil.createCommandeDetail(detailId);
					detail.setIdCommande(cmdId);
					detail.setIdMedicament(medId);
					detail.setQuantite(qte);
					detail.setPrixUnitaire(m.getPrixUnitaire());

					double sousTotal = qte * m.getPrixUnitaire();
					montantTotal += sousTotal;

					CommandeDetailLocalServiceUtil.addCommandeDetail(detail);
				}
			}

			// Update total
			cmd.setMontantTotal(montantTotal);
			CommandeLocalServiceUtil.updateCommande(cmd);

			response.setRenderParameter("mvcPath", "/view.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
private static final Log _log = LogFactoryUtil.getLog(CommandeWebPortlet.class);

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws PortletException, IOException {
        try {
            System.out.println("---- doView called ----");
            // fournisseurs: users having role "FOURNISSEUR" (adjust how you store roles)
            List<Utilisateur> fournisseurs = UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR");
            List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);

            // commandes: show all for admins, or only created by this user for pharmacists (optional)
            List<Commande> commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);
            System.out.println("Fournisseurs fetched: " + (fournisseurs != null ? fournisseurs.size() : 0));
            System.out.println("Medicaments fetched: " + (medicaments != null ? medicaments.size() : 0));


            req.setAttribute("fournisseurs", fournisseurs);
            req.setAttribute("medicaments", medicaments);
            req.setAttribute("commandes", commandes);
        } catch (Exception e) {
            System.out.println("doView failed: " + e.getMessage());
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
            commande.setIdFournisseur(fournisseurId);
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

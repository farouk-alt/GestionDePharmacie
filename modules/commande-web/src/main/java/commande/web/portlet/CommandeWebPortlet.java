package commande.web.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import gestion_de_pharmacie.model.Commande;
import gestion_de_pharmacie.model.CommandeDetail;
import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.CommandeDetailLocalServiceUtil;
import gestion_de_pharmacie.service.CommandeLocalServiceUtil;
import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import gestion_de_pharmacie.service.UtilisateurLocalServiceUtil;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            // fournisseurs: users having role "FOURNISSEUR" (adjust how you store roles)
            List<Utilisateur> fournisseurs = UtilisateurLocalServiceUtil.getUtilisateurByRole("FOURNISSEUR");
            List<Medicament> medicaments = MedicamentLocalServiceUtil.getMedicaments(-1, -1);

            // commandes: show all for admins, or only created by this user for pharmacists (optional)
            List<Commande> commandes = CommandeLocalServiceUtil.getCommandes(-1, -1);

            req.setAttribute("fournisseurs", fournisseurs);
            req.setAttribute("medicaments", medicaments);
            req.setAttribute("commandes", commandes);
        } catch (Exception e) {
            _log.error("doView fetch error", e);
        }
        super.doView(req, res);
    }

    @ProcessAction(name = "createCommande")
    public void createCommande(ActionRequest request, ActionResponse response) {
        try {
            long fournisseurId = ParamUtil.getLong(request, "fournisseurId");

            // medicamentId[] are checkboxes — ParamUtil.getParameterValues(...) returns String[] in Liferay
            String[] medIds = ParamUtil.getParameterValues(request, "medicamentId");
            if (medIds == null || medIds.length == 0) {
                SessionMessages.add(request, "commande-no-items");
                response.setRenderParameter("mvcPath", "/view.jsp");
                return;
            }

            // Create commande
            Commande commande = CommandeLocalServiceUtil.createCommande(
                    CounterLocalServiceUtil.increment(Commande.class.getName())
            );
            commande.setIdFournisseur(fournisseurId);
            commande.setDateCommande(new Date());
            commande.setStatut("CREATED");

            // we'll accumulate total
            double total = 0.0;
            List<CommandeDetail> details = new ArrayList<>();

            for (String idStr : medIds) {
                long medId = Long.parseLong(idStr);
                // quantity input name pattern in your JSP: quantite_<medId>
                int q = ParamUtil.getInteger(request, "quantite_" + medId, 1);
                Medicament m = MedicamentLocalServiceUtil.fetchMedicament(medId);
                double pu = (m != null) ? m.getPrixUnitaire() : 0.0;
                double subtotal = pu * q;
                total += subtotal;

                CommandeDetail d = CommandeDetailLocalServiceUtil.createCommandeDetail(
                        CounterLocalServiceUtil.increment(CommandeDetail.class.getName())
                );
                d.setIdMedicament(medId);
                d.setQuantite(q);
                d.setPrixUnitaire(pu);
                // link later after commande is persisted
                details.add(d);
            }

            commande.setMontantTotal(total);

            // persist
            CommandeLocalServiceUtil.addCommande(commande);

            // set details' idCommande and persist
            for (CommandeDetail d : details) {
                d.setIdCommande(commande.getIdCommande());
                CommandeDetailLocalServiceUtil.addCommandeDetail(d);
            }

            SessionMessages.add(request, "commande-created-success");
            response.setRenderParameter("mvcPath", "/view.jsp");
        } catch (Exception e) {
            _log.error("createCommande failed", e);
            SessionMessages.add(request, "commande-create-error");
            response.setRenderParameter("mvcPath", "/view.jsp");
        }
    }
}

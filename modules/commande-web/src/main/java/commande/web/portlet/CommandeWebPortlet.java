package commande.web.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
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

	@Override
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
	}

}

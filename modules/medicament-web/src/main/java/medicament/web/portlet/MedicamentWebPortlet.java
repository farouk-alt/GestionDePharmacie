/*
package medicament.web.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;


import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import medicament.web.constants.MedicamentWebPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

import java.util.Date;

*/
/**
 * @author farou
 *//*

*/
/*@Component(
	property = {
		"com.liferay.portlet.display-category=category.tools",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=MedicamentWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + MedicamentWebPortletKeys.MEDICAMENTWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)*//*

@Component(
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=MedicamentWeb",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + MedicamentWebPortletKeys.MEDICAMENTWEB,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class MedicamentWebPortlet extends MVCPortlet {
    public void ajouterMedicament(ActionRequest request, ActionResponse response) {
        String nom = ParamUtil.getString(request, "nom");
        double prix = ParamUtil.getDouble(request, "prix");

        Medicament medicament = MedicamentLocalServiceUtil.createMedicament(CounterLocalServiceUtil.increment());
        medicament.setNom(nom);
        medicament.setPrixUnitaire(prix);
        medicament.setDateAjout(new Date());

        MedicamentLocalServiceUtil.addMedicament(medicament);
    }
}*/
package medicament.web.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import gestion_de_pharmacie.service.StockLocalServiceUtil;
import medicament.web.constants.MedicamentWebPortletKeys;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author farou
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=MedicamentWeb",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + MedicamentWebPortletKeys.MEDICAMENTWEB,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class MedicamentWebPortlet extends MVCPortlet {

    private static final Log _log = LogFactoryUtil.getLog(MedicamentWebPortlet.class);

    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
            throws IOException, PortletException {

        String cmd = ParamUtil.getString(actionRequest, "cmd");

        if ("ajouterMedicament".equals(cmd)) {
            ajouterMedicament(actionRequest, actionResponse);
        }

        super.processAction(actionRequest, actionResponse);
    }

    @ProcessAction(name = "ajouterMedicament")
    public void ajouterMedicament(ActionRequest request, ActionResponse response) {
        try {
            String nom = ParamUtil.getString(request, "nom");
            double prix = ParamUtil.getDouble(request, "prix");
            String description = ParamUtil.getString(request, "description");
            String categorie = ParamUtil.getString(request, "categorie");
            int seuilMinimum = ParamUtil.getInteger(request, "seuilMinimum");

            // Check if medicament already exists
            List<Medicament> existing = findByNom(nom);
            if (!existing.isEmpty()) {
                SessionErrors.add(request, "medicament-already-exists");
                return; // stop adding
            }

            // Create new Medicament
            Medicament medicament = MedicamentLocalServiceUtil.createMedicament(
                    CounterLocalServiceUtil.increment()
            );

            medicament.setNom(nom);
            medicament.setPrixUnitaire(prix);
            medicament.setDescription(description);
            medicament.setCategorie(categorie);
            medicament.setSeuilMinimum(seuilMinimum);
            medicament.setDateAjout(new Date());

            MedicamentLocalServiceUtil.addMedicament(medicament);

            Stock stock = StockLocalServiceUtil.createStock(CounterLocalServiceUtil.increment());
            stock.setIdMedicament(medicament.getIdMedicament());
            stock.setQuantiteDisponible(0);
            stock.setDateDerniereMaj(new Date());


            stock = StockLocalServiceUtil.addStock(stock);
            _log.info("Stock added: " + stock.getIdStock() + " for medicament " + medicament.getNom());


            StockLocalServiceUtil.addStock(stock);

            SessionMessages.add(request, "medicament-added-successfully");

        } catch (Exception e) {
            _log.error("Error adding medicament: " + e.getMessage(), e);
        }
    }



    @ProcessAction(name = "updateMedicament")
    public void updateMedicament(ActionRequest request, ActionResponse response) throws Exception {
        long medicamentId = ParamUtil.getLong(request, "medicamentId");
        String nom = ParamUtil.getString(request, "nom");
        double prix = ParamUtil.getDouble(request, "prix");
        String description = ParamUtil.getString(request, "description");
        String categorie = ParamUtil.getString(request, "categorie");
        int seuilMinimum = ParamUtil.getInteger(request, "seuilMinimum");

        Medicament medicament = MedicamentLocalServiceUtil.getMedicament(medicamentId);

        medicament.setNom(nom);
        medicament.setPrixUnitaire(prix);
        medicament.setDescription(description);
        medicament.setCategorie(categorie);
        medicament.setSeuilMinimum(seuilMinimum);

        MedicamentLocalServiceUtil.updateMedicament(medicament);

        SessionMessages.add(request, "medicament-updated-successfully");
        response.setRenderParameter("mvcPath", "/view.jsp");
    }


    @ProcessAction(name = "deleteMedicament")
    public void deleteMedicament(ActionRequest request, ActionResponse response) {
        try {
            long medicamentId = ParamUtil.getLong(request, "medicamentId");

            _log.info("Deleting medicament with ID: " + medicamentId);

            MedicamentLocalServiceUtil.deleteMedicament(medicamentId);

            SessionMessages.add(request, "medicament-deleted-successfully");

            _log.info("Medicament deleted successfully: ID " + medicamentId);

        } catch (Exception e) {
            _log.error("Error deleting medicament: " + e.getMessage(), e);
        }
    }


    private List<Medicament> findByNom(String nom) {
        try {
            DynamicQuery dq = MedicamentLocalServiceUtil.dynamicQuery();

            // Exact match, ignoring case
            dq.add(RestrictionsFactoryUtil.eq("nom", nom));

            List<Object> results = MedicamentLocalServiceUtil.dynamicQuery(dq);
            List<Medicament> medicaments = new ArrayList<>();
            for (Object obj : results) {
                medicaments.add((Medicament) obj);
            }
            return medicaments;
        } catch (Exception e) {
            _log.error("Error finding medicament by name: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }



}
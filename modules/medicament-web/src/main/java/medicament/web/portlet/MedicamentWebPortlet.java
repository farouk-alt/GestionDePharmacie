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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import medicament.web.constants.MedicamentWebPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.util.Date;

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

    public void ajouterMedicament(ActionRequest request, ActionResponse response) {
        try {
            String nom = ParamUtil.getString(request, "nom");
            double prix = ParamUtil.getDouble(request, "prix");

            _log.info("Creating medicament: " + nom + " with price: " + prix);

            Medicament medicament = MedicamentLocalServiceUtil.createMedicament(
                    CounterLocalServiceUtil.increment()
            );
            medicament.setNom(nom);
            medicament.setPrixUnitaire(prix);
            medicament.setDateAjout(new Date());

            MedicamentLocalServiceUtil.addMedicament(medicament);

            SessionMessages.add(request, "medicament-added-successfully");


            _log.info("Medicament added successfully: " + medicament.getNom());

        } catch (Exception e) {
            _log.error("Error adding medicament: " + e.getMessage(), e);
        }
    }
}
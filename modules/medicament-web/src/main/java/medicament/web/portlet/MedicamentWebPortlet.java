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

import com.liferay.portal.kernel.util.Validator;
import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import gestion_de_pharmacie.service.NotificationLocalService;
import gestion_de_pharmacie.service.StockLocalServiceUtil;
import gestion_de_pharmacie.service.UtilisateurLocalService;
import medicament.web.constants.MedicamentWebPortletKeys;

import javax.portlet.*;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
    @Reference
    private NotificationLocalService _notifLocalService;

    @Reference
    private UtilisateurLocalService _userLocalService;

   @ProcessAction(name = "ajouterMedicament")
   public void ajouterMedicament(ActionRequest request, ActionResponse response) {
       try {
           String code       = ParamUtil.getString(request, "code");
           String codeBarre  = ParamUtil.getString(request, "codeBarre");
           String nom        = ParamUtil.getString(request, "nom");
           double prix       = ParamUtil.getDouble(request, "prix");
           String description= ParamUtil.getString(request, "description");
           String categorie  = ParamUtil.getString(request, "categorie");
           int seuilMinimum  = ParamUtil.getInteger(request, "seuilMinimum");

           if (Validator.isNull(nom) || Validator.isNull(code)) {
               SessionErrors.add(request, "medicament-required");
               return;
           }
           if (Validator.isNotNull(codeBarre) && !isValidEAN13(codeBarre)) {
               SessionErrors.add(request, "medicament-barcode-invalid");
               return;
           }
           if (!findByCode(code).isEmpty()) {
               SessionErrors.add(request, "medicament-code-exists");
               return;
           }
           if (Validator.isNotNull(codeBarre) && !findByBarcode(codeBarre).isEmpty()) {
               SessionErrors.add(request, "medicament-barcode-exists");
               return;
           }

           Medicament medicament = MedicamentLocalServiceUtil.createMedicament(
                   CounterLocalServiceUtil.increment()
           );
           medicament.setCode(code);
           medicament.setCodeBarre(codeBarre);
           medicament.setNom(nom);
           medicament.setPrixUnitaire(prix);
           medicament.setDescription(description);
           medicament.setCategorie(categorie);
           medicament.setSeuilMinimum(seuilMinimum);
           medicament.setDateAjout(new Date());

           MedicamentLocalServiceUtil.addMedicament(medicament);

           // Stock init
           Stock stock = StockLocalServiceUtil.createStock(CounterLocalServiceUtil.increment());
           stock.setIdMedicament(medicament.getIdMedicament());
           stock.setQuantiteDisponible(0);
           stock.setDateDerniereMaj(new Date());
           StockLocalServiceUtil.addStock(stock);

           _notifLocalService.createLowStockAlertsForMed(medicament.getIdMedicament());

           SessionMessages.add(request, "medicament-added-successfully");
           response.setRenderParameter("mvcPath", "/view.jsp");
       } catch (Exception e) {
           _log.error("Error adding medicament: " + e.getMessage(), e);
           SessionErrors.add(request, "medicament-add-error");
       }
   }

   /* @ProcessAction(name = "updateMedicament")
    public void updateMedicament(ActionRequest request, ActionResponse response) throws Exception {
        long medicamentId   = ParamUtil.getLong(request, "medicamentId");
        String code         = ParamUtil.getString(request, "code");
        String codeBarre    = ParamUtil.getString(request, "codeBarre");
        String nom          = ParamUtil.getString(request, "nom");
        double prix         = ParamUtil.getDouble(request, "prix");
        String description  = ParamUtil.getString(request, "description");
        String categorie    = ParamUtil.getString(request, "categorie");
        int seuilMinimum    = ParamUtil.getInteger(request, "seuilMinimum");

        Medicament m = MedicamentLocalServiceUtil.getMedicament(medicamentId);

        // Simple conflicts check (ignore current record)
        if (!code.equals(m.getCode()) && !findByCode(code).isEmpty()) {
            SessionErrors.add(request, "medicament-code-exists"); return;
        }
        if (Validator.isNotNull(codeBarre) && !isValidEAN13(codeBarre)) {
            SessionErrors.add(request, "medicament-barcode-invalid"); return;
        }
        if (Validator.isNotNull(codeBarre)
                && !codeBarre.equals(m.getCodeBarre())
                && !findByBarcode(codeBarre).isEmpty()) {
            SessionErrors.add(request, "medicament-barcode-exists"); return;
        }

        m.setCode(code);
        m.setCodeBarre(codeBarre);
        m.setNom(nom);
        m.setPrixUnitaire(prix);
        m.setDescription(description);
        m.setCategorie(categorie);
        m.setSeuilMinimum(seuilMinimum);

        MedicamentLocalServiceUtil.updateMedicament(m);

        SessionMessages.add(request, "medicament-updated-successfully");
        response.setRenderParameter("mvcPath", "/view.jsp");
    }*/
   @ProcessAction(name = "updateMedicament")
   public void updateMedicament(ActionRequest request, ActionResponse response) throws Exception {
       long medicamentId = ParamUtil.getLong(request, "medicamentId");
       String code         = ParamUtil.getString(request, "code");
       String codeBarre    = ParamUtil.getString(request, "codeBarre");
       String nom          = ParamUtil.getString(request, "nom");
       double prix         = ParamUtil.getDouble(request, "prix");
       String description  = ParamUtil.getString(request, "description");
       String categorie    = ParamUtil.getString(request, "categorie");
       int seuilMinimum    = ParamUtil.getInteger(request, "seuilMinimum");

       Medicament m = MedicamentLocalServiceUtil.getMedicament(medicamentId);

       // Simple conflicts check (ignore current record)
       if (!code.equals(m.getCode()) && !findByCode(code).isEmpty()) {
           SessionErrors.add(request, "medicament-code-exists"); return;
       }
       if (Validator.isNotNull(codeBarre) && !isValidEAN13(codeBarre)) {
           SessionErrors.add(request, "medicament-barcode-invalid"); return;
       }
       if (Validator.isNotNull(codeBarre)
               && !codeBarre.equals(m.getCodeBarre())
               && !findByBarcode(codeBarre).isEmpty()) {
           SessionErrors.add(request, "medicament-barcode-exists"); return;
       }

       m.setCode(code);
       m.setCodeBarre(codeBarre);
       m.setNom(nom);
       m.setPrixUnitaire(prix);
       m.setDescription(description);
       m.setCategorie(categorie);
       m.setSeuilMinimum(seuilMinimum);

       MedicamentLocalServiceUtil.updateMedicament(m);
       SessionMessages.add(request, "medicament-updated-successfully");

       boolean fromModal = ParamUtil.getBoolean(request, "fromModal");

       if (fromModal) {
           // Re-render edit_medicament.jsp inside the iframe with a close instruction
           response.setRenderParameter("mvcPath", "/edit_medicament.jsp");
           response.setRenderParameter("medicamentId", String.valueOf(medicamentId));
           response.setRenderParameter("modal", "1");
           response.setRenderParameter("close", "1");
       } else {
           response.setRenderParameter("mvcPath", "/view.jsp");
       }
   }


    // --- helpers ---
    private boolean isValidEAN13(String s) {
        if (Validator.isNull(s) || !s.matches("\\d{13}")) return false;
        int sum=0;
        for (int i=0;i<12;i++){ int n=s.charAt(i)-'0'; sum += (i%2==0)? n : n*3; }
        int check = (10 - (sum % 10)) % 10;
        return check == (s.charAt(12)-'0');
    }

    private List<Medicament> findByCode(String code) {
        try {
            DynamicQuery dq = MedicamentLocalServiceUtil.dynamicQuery();
            dq.add(RestrictionsFactoryUtil.eq("code", code));
            List<Object> res = MedicamentLocalServiceUtil.dynamicQuery(dq);
            List<Medicament> out = new ArrayList<>();
            for (Object o: res) out.add((Medicament)o);
            return out;
        } catch (Exception e) { return Collections.emptyList(); }
    }

    private List<Medicament> findByBarcode(String codeBarre) {
        try {
            DynamicQuery dq = MedicamentLocalServiceUtil.dynamicQuery();
            dq.add(RestrictionsFactoryUtil.eq("codeBarre", codeBarre));
            List<Object> res = MedicamentLocalServiceUtil.dynamicQuery(dq);
            List<Medicament> out = new ArrayList<>();
            for (Object o: res) out.add((Medicament)o);
            return out;
        } catch (Exception e) { return Collections.emptyList(); }
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

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {
        int unread = 0;
        try {
            long uid = resolveUid(req);
            if (uid > 0) {
                // Use your finder-backed service method (see #4)
                unread = _notifLocalService.countUnreadByUser(uid);
            }
        } catch (Exception ignore) {}
        req.setAttribute("unreadCount", unread);
        super.doView(req, res);
    }

    private long resolveUid(PortletRequest req) {
        try {
            PortletSession s = req.getPortletSession();
            String email = (String) s.getAttribute("userEmail", PortletSession.APPLICATION_SCOPE);
            if (email == null) return 0;
            gestion_de_pharmacie.model.Utilisateur u = _userLocalService.getUtilisateurByEmail(email);
            return (u != null) ? u.getIdUtilisateur() : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
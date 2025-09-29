package stock.web.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import gestion_de_pharmacie.service.StockLocalServiceUtil;

import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import java.io.IOException;
import java.util.*;

@Component(
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "com.liferay.portlet.add-default-resource=true",   // <-- add this
                "javax.portlet.display-name=StockWeb",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=stock_web",
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)

public class StockWebPortlet extends MVCPortlet {

	private static final Log _log = LogFactoryUtil.getLog(StockWebPortlet.class);

    @Override
    public void doView(RenderRequest req, RenderResponse res) throws IOException, PortletException {

        System.out.println("[STOCK] doView called");
        try {
            // Load all stocks, join friendly Medicament info for display
            List<Stock> stocks = StockLocalServiceUtil.getStocks(-1, -1);

            // Build a light DTO list for the JSP
            List<Map<String,Object>> rows = new ArrayList<>();
            for (Stock s : stocks) {
                Medicament m = null;
                try { m = MedicamentLocalServiceUtil.fetchMedicament(s.getIdMedicament()); } catch (Exception ignore){}
                Map<String,Object> r = new HashMap<>();
                r.put("idStock", s.getIdStock());
                r.put("idMedicament", s.getIdMedicament());
                r.put("nomMedicament", (m!=null ? m.getNom() : ("#"+s.getIdMedicament())));
                r.put("prixUnitaire", (m!=null ? m.getPrixUnitaire() : 0.0));
                r.put("quantite", s.getQuantiteDisponible());
                r.put("dateMaj", s.getDateDerniereMaj());
                rows.add(r);
            }
            req.setAttribute("stockRows", rows);

            // Also provide medicaments (for adding a missing stock row)
            req.setAttribute("medicaments", MedicamentLocalServiceUtil.getMedicaments(-1, -1));
        } catch (Exception e) {
            req.setAttribute("stockRows", Collections.emptyList());
            req.setAttribute("medicaments", Collections.emptyList());
        }
        super.doView(req, res);
    }

    @ProcessAction(name = "adjustStock")
    public void adjustStock(ActionRequest request, ActionResponse response) {
        UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
        try {
            long idMedicament = ParamUtil.getLong(upload, "idMedicament");
            int delta = ParamUtil.getInteger(upload, "delta");  // can be + or -
            if (idMedicament <= 0 || delta == 0) {
                SessionMessages.add(request, "stock-error");
                return;
            }

            // fetch or create Stock row
            Stock s;
            try {
                // With finder name="Med" return-type="Entity", service builder usually generates fetchByMed
                s = StockLocalServiceUtil.fetchStockByMedicamentId(idMedicament);
            } catch (Throwable t) {
                s = null;
            }
            if (s == null) {
                long id = com.liferay.counter.kernel.service.CounterLocalServiceUtil.increment(Stock.class.getName());
                s = StockLocalServiceUtil.createStock(id);
                s.setIdMedicament(idMedicament);
                s.setQuantiteDisponible(0);
            }

            int newQty = Math.max(0, s.getQuantiteDisponible() + delta);
            s.setQuantiteDisponible(newQty);
            s.setDateDerniereMaj(new Date());
            StockLocalServiceUtil.updateStock(s);

            // (Optional) also write a MouvementStock row here if you want every manual adjust tracked

            SessionMessages.add(request, "stock-updated");
        } catch (Exception e) {
            SessionMessages.add(request, "stock-error");
        }

        response.setRenderParameter("mvcPath", "/view.jsp");
    }

    @ProcessAction(name = "setStock")
    public void setStock(ActionRequest request, ActionResponse response) {
        UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
        try {
            long idMedicament = ParamUtil.getLong(upload, "idMedicament");
            int qty = ParamUtil.getInteger(upload, "qty", -1);
            if (idMedicament <= 0 || qty < 0) {
                SessionMessages.add(request, "stock-error");
                return;
            }
            Stock s = StockLocalServiceUtil.fetchStockByMedicamentId(idMedicament);
            if (s == null) {
                long id = com.liferay.counter.kernel.service.CounterLocalServiceUtil.increment(Stock.class.getName());
                s = StockLocalServiceUtil.createStock(id);
                s.setIdMedicament(idMedicament);
            }
            s.setQuantiteDisponible(qty);
            s.setDateDerniereMaj(new Date());
            StockLocalServiceUtil.updateStock(s);

            SessionMessages.add(request, "stock-updated");
        } catch (Exception e) {
            SessionMessages.add(request, "stock-error");
        }
        response.setRenderParameter("mvcPath", "/view.jsp");
    }
}

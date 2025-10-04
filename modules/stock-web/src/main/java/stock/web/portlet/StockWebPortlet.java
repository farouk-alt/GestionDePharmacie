package stock.web.portlet;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Component(
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "com.liferay.portlet.add-default-resource=true",
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
            // Provide all medicaments for dropdowns
            req.setAttribute("medicaments", MedicamentLocalServiceUtil.getMedicaments(-1, -1));
        } catch (Exception e) {
            req.setAttribute("medicaments", Collections.emptyList());
        }
        super.doView(req, res);
    }

    @Override
    public void serveResource(ResourceRequest req, ResourceResponse res) throws IOException, PortletException {
        res.setCharacterEncoding("UTF-8");

        String rid = req.getResourceID();
        String cmd = ParamUtil.getString(req, "cmd");
        String op = (rid != null && !rid.isEmpty()) ? rid : cmd;

        if ("list".equals(op)) {
            res.setContentType("application/json; charset=UTF-8");

            String ns = PortalUtil.getPortletNamespace(PortalUtil.getPortletId(req));
            int page = ParamUtil.getInteger(req, ns + "page", ParamUtil.getInteger(req, "page", 1));
            int size = ParamUtil.getInteger(req, ns + "size", ParamUtil.getInteger(req, "size", 10));

            // Filter parameters
            String search = ParamUtil.getString(req, ns + "search", ParamUtil.getString(req, "search", ""));
            String stockLevel = ParamUtil.getString(req, ns + "stockLevel", ParamUtil.getString(req, "stockLevel", ""));
            String dateFrom = ParamUtil.getString(req, ns + "dateFrom", ParamUtil.getString(req, "dateFrom", ""));
            String dateTo = ParamUtil.getString(req, ns + "dateTo", ParamUtil.getString(req, "dateTo", ""));

            System.out.println("[STOCK list] filters - search=" + search + " stockLevel=" + stockLevel +
                    " dateFrom=" + dateFrom + " dateTo=" + dateTo);

            page = (page <= 0) ? 1 : page;
            size = (size <= 0) ? 10 : size;
            int start = (page - 1) * size;
            int end = start + size;

            try {
                // Build filtered query
                DynamicQuery dqCount = buildFilteredQuery(search, stockLevel, dateFrom, dateTo);
                int total = (int) StockLocalServiceUtil.dynamicQueryCount(dqCount);

                // Build query with ordering
                DynamicQuery dqPage = buildFilteredQuery(search, stockLevel, dateFrom, dateTo);
                dqPage.addOrder(OrderFactoryUtil.desc("dateDerniereMaj"));

                @SuppressWarnings("unchecked")
                List<Stock> rows = (List<Stock>) (List<?>) StockLocalServiceUtil.dynamicQuery(dqPage, start, end);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                List<Map<String, Object>> items = new ArrayList<>();

                for (Stock s : rows) {
                    Medicament m = null;
                    try {
                        m = MedicamentLocalServiceUtil.fetchMedicament(s.getIdMedicament());
                    } catch (Exception ignore) {}

                    Map<String, Object> r = new LinkedHashMap<>();
                    r.put("idStock", s.getIdStock());
                    r.put("idMedicament", s.getIdMedicament());
                    r.put("nomMedicament", (m != null ? m.getNom() : "#" + s.getIdMedicament()));
                    r.put("prixUnitaire", (m != null ? m.getPrixUnitaire() : 0.0));
                    r.put("quantite", s.getQuantiteDisponible());
                    r.put("dateMaj", s.getDateDerniereMaj() != null ? sdf.format(s.getDateDerniereMaj()) : "");
                    items.add(r);
                }

                int finalPage = page;
                int finalSize = size;
                String json = com.liferay.portal.kernel.json.JSONFactoryUtil.getJSONFactory()
                        .looseSerializeDeep(new LinkedHashMap<String, Object>() {{
                            put("items", items);
                            put("total", total);
                            put("page", finalPage);
                            put("size", finalSize);
                        }});
                res.getWriter().write(json);
                return;

            } catch (Exception e) {
                System.out.println("[STOCK list] ERROR: " + e);
                e.printStackTrace();
                res.getWriter().write("{\"items\":[],\"total\":0,\"page\":1,\"size\":10}");
                return;
            }
        }
    }

    /**
     * Build filtered query for stock with medicament join
     */
    private DynamicQuery buildFilteredQuery(String search, String stockLevel, String dateFrom, String dateTo) {
        DynamicQuery dq = StockLocalServiceUtil.dynamicQuery();

        // Stock level filter
        if (stockLevel != null && !stockLevel.trim().isEmpty()) {
            String level = stockLevel.trim().toUpperCase();
            if ("LOW".equals(level)) {
                // Stock <= 10
                dq.add(RestrictionsFactoryUtil.le("quantiteDisponible", 10));
            } else if ("MEDIUM".equals(level)) {
                // Stock between 11 and 50
                dq.add(RestrictionsFactoryUtil.gt("quantiteDisponible", 10));
                dq.add(RestrictionsFactoryUtil.le("quantiteDisponible", 50));
            } else if ("HIGH".equals(level)) {
                // Stock > 50
                dq.add(RestrictionsFactoryUtil.gt("quantiteDisponible", 50));
            } else if ("OUT".equals(level)) {
                // Stock = 0
                dq.add(RestrictionsFactoryUtil.eq("quantiteDisponible", 0));
            }
            System.out.println("[buildFilteredQuery] stockLevel filter: " + level);
        }

        // Date range filter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (dateFrom != null && !dateFrom.trim().isEmpty()) {
            try {
                Date fromDate = sdf.parse(dateFrom.trim());
                Calendar cal = Calendar.getInstance();
                cal.setTime(fromDate);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                dq.add(RestrictionsFactoryUtil.ge("dateDerniereMaj", cal.getTime()));
                System.out.println("[buildFilteredQuery] dateFrom filter: " + cal.getTime());
            } catch (Exception e) {
                System.out.println("[buildFilteredQuery] Invalid dateFrom: " + dateFrom);
            }
        }

        if (dateTo != null && !dateTo.trim().isEmpty()) {
            try {
                Date toDate = sdf.parse(dateTo.trim());
                Calendar cal = Calendar.getInstance();
                cal.setTime(toDate);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                dq.add(RestrictionsFactoryUtil.le("dateDerniereMaj", cal.getTime()));
                System.out.println("[buildFilteredQuery] dateTo filter: " + cal.getTime());
            } catch (Exception e) {
                System.out.println("[buildFilteredQuery] Invalid dateTo: " + dateTo);
            }
        }

        // Note: Search by medicament name requires a join or post-filtering
        // For now, we'll handle search client-side or you need to implement a custom query

        return dq;
    }

    @ProcessAction(name = "adjustStock")
    public void adjustStock(ActionRequest request, ActionResponse response) {
        UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
        try {
            long idMedicament = ParamUtil.getLong(upload, "idMedicament");
            int delta = ParamUtil.getInteger(upload, "delta");
            if (idMedicament <= 0 || delta == 0) {
                SessionMessages.add(request, "stock-error");
                return;
            }

            Stock s;
            try {
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
            System.out.println("[adjustStock] idMed=" + idMedicament + " delta=" + delta);

            StockLocalServiceUtil.adjustStockDelta(idMedicament, delta);

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
                s.setQuantiteDisponible(0);
            }

            int newQty = Math.max(0, qty);
            s.setQuantiteDisponible(newQty);
            s.setDateDerniereMaj(new Date());
            StockLocalServiceUtil.updateStock(s);
            System.out.println("[setStock] idMed=" + idMedicament + " qty=" + qty);

            StockLocalServiceUtil.setStockQty(idMedicament, qty);

            SessionMessages.add(request, "stock-updated");
        } catch (Exception e) {
            SessionMessages.add(request, "stock-error");
        }
        response.setRenderParameter("mvcPath", "/view.jsp");
    }
}
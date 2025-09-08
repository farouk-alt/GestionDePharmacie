package stock.web.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import gestion_de_pharmacie.service.StockLocalServiceUtil;

import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component(
		property = {
				"com.liferay.portlet.display-category=category.sample",
				"com.liferay.portlet.instanceable=true",
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
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		try {
			// Get all stocks
			List<Stock> stocks = StockLocalServiceUtil.getStocks(-1, -1);

			// Build a list of maps to pass to the view
			List<Map<String, Object>> stockList = new ArrayList<>();

			for (Stock s : stocks) {
				// Use fetchMedicament to avoid exceptions when the medicament was deleted
				Medicament m = MedicamentLocalServiceUtil.fetchMedicament(s.getIdMedicament());

				Map<String, Object> map = new HashMap<>();

				if (m != null) {
					map.put("medicament", m);
				} else {
					// keep the medicamentId so view can show a placeholder
					map.put("medicament", null);
					map.put("medicamentId", s.getIdMedicament());
					_log.warn("Medicament not found for stock id " + s.getIdStock()
							+ " (referenced medicamentId=" + s.getIdMedicament() + ")");
				}

				map.put("quantite", s.getQuantiteDisponible());
				map.put("stockId", s.getIdStock());

				stockList.add(map);
			}

			renderRequest.setAttribute("stockList", stockList);

		} catch (Exception e) {
			_log.error("Error loading stock list: " + e.getMessage(), e);
		}

		super.doView(renderRequest, renderResponse);
	}
}

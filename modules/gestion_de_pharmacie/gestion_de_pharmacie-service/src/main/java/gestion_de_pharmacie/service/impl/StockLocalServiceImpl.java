package gestion_de_pharmacie.service.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.MedicamentLocalService;
import gestion_de_pharmacie.service.NotificationLocalService;
import gestion_de_pharmacie.service.UtilisateurLocalService;
import gestion_de_pharmacie.service.base.StockLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Date;

@Component(
        property = "model.class.name=gestion_de_pharmacie.model.Stock",
        service = AopService.class
)
public class StockLocalServiceImpl extends StockLocalServiceBaseImpl {

    @Reference private MedicamentLocalService medicamentLocalService;
    @Reference private NotificationLocalService notificationLocalService;
    @Reference private UtilisateurLocalService utilisateurLocalService;

    public Stock fetchStockByMedicamentId(long medicamentId) {
        return stockPersistence.fetchByMed(medicamentId);
    }

    /** Public API used by Vente + Portlet: adjust by delta (can be negative). */
    public Stock adjustStockDelta(long idMedicament, int delta) {
        Stock s = fetchStockByMedicamentId(idMedicament);
        if (s == null) {
            long id = CounterLocalServiceUtil.increment(Stock.class.getName());
            s = createStock(id);
            s.setIdMedicament(idMedicament);
            s.setQuantiteDisponible(0);
        }

        int previousQty = s.getQuantiteDisponible();
        int newQty = Math.max(0, previousQty + delta);

        s.setQuantiteDisponible(newQty);
        s.setDateDerniereMaj(new Date());

        s = updateStock(s);

        checkAndNotifyThreshold(idMedicament, previousQty, newQty);
        return s;
    }

    /** Public API: set absolute qty. */
    public Stock setStockQty(long idMedicament, int qty) {
        Stock s = fetchStockByMedicamentId(idMedicament);
        int prev = (s == null) ? 0 : s.getQuantiteDisponible();
        if (s == null) {
            long id = CounterLocalServiceUtil.increment(Stock.class.getName());
            s = createStock(id);
            s.setIdMedicament(idMedicament);
        }
        int newQty = Math.max(0, qty);
        s.setQuantiteDisponible(newQty);
        s.setDateDerniereMaj(new Date());
        s = updateStock(s);

        checkAndNotifyThreshold(idMedicament, prev, newQty);
        return s;
    }

    /** Centralized threshold logic. */
    protected void checkAndNotifyThreshold(long idMedicament, int previousQty, int newQty) {
        try {
            Medicament med = medicamentLocalService.fetchMedicament(idMedicament);
            if (med == null) return;

            int seuil = med.getSeuilMinimum();
            boolean crossedDown       = (previousQty >= seuil) && (newQty < seuil);
            boolean rupture           = (previousQty >  0)     && (newQty == 0);
            boolean firstCreationBelow = (previousQty == 0) && (newQty > 0) && (newQty <= seuil);

            if (!(crossedDown || rupture || firstCreationBelow)) return;

            String message = rupture
                    ? "Rupture de stock pour " + med.getNom() + " (0 ≤ seuil " + seuil + ")"
                    : "Seuil minimum atteint pour " + med.getNom() + " (" + newQty + " ≤ " + seuil + ")";

            // Notify roles in your custom table
            notificationLocalService.addNotificationForRole("ADMIN",      "STOCK_LOW", message);
            notificationLocalService.addNotificationForRole("PHARMACIEN", "STOCK_LOW", message);

            // Also notify the actor if we can resolve them
            ServiceContext sc = ServiceContextThreadLocal.getServiceContext();
            if (sc != null && sc.getUserId() > 0) {
                com.liferay.portal.kernel.model.User pu =
                        com.liferay.portal.kernel.service.UserLocalServiceUtil.fetchUser(sc.getUserId());
                if (pu != null) {
                    Utilisateur u = utilisateurLocalService.getUtilisateurByEmail(pu.getEmailAddress());
                    if (u != null) {
                        notificationLocalService.addNotification(u.getIdUtilisateur(), "STOCK_LOW", message);
                    }
                }
            }

            System.out.printf("[StockService][notify] med=%d prev=%d new=%d seuil=%d%n",
                    idMedicament, previousQty, newQty, seuil);
        } catch (Throwable t) {
            System.out.println("[StockService][notify] ERROR " + t);
            t.printStackTrace();
        }
    }
}

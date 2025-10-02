/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import gestion_de_pharmacie.model.*;
import gestion_de_pharmacie.service.*;
import gestion_de_pharmacie.service.base.VenteLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Date;

/**
 * @author Farouk
 */
@Component(
	property = "model.class.name=gestion_de_pharmacie.model.Vente",
	service = AopService.class
)
public class VenteLocalServiceImpl extends VenteLocalServiceBaseImpl {

    @Reference
    private MedicamentLocalService medicamentLocalService;

    @Reference
    private StockLocalService stockLocalService;

    @Reference
    private MouvementStockLocalService mouvementStockLocalService;

    @Reference
    private VenteDetailLocalService venteDetailLocalService;

    @Reference
    private NotificationLocalService notificationLocalService;


    @Override
    public Vente createVente(long idUtilisateur, long[] medicamentIds, int[] quantities)
            throws PortalException {

        if (medicamentIds == null || quantities == null || medicamentIds.length != quantities.length) {
            throw new PortalException("Invalid cart arrays");
        }

        Date now = new Date();
        double total = 0.0;

        // Validate / compute total
        for (int i = 0; i < medicamentIds.length; i++) {
            long mid = medicamentIds[i];
            int qty = Math.max(0, quantities[i]);
            if (qty == 0) continue;

            Medicament m = medicamentLocalService.fetchMedicament(mid);
            if (m == null) throw new PortalException("Medicament not found: " + mid);

            // If your finder in service.xml is <finder name="Med">,
            // Service Builder generates fetchByMed(long idMedicament)
            Stock stock = stockLocalService.fetchStockByMedicamentId(mid); // <-- IMPORTANT: method name matches your finder
            int available = (stock == null) ? 0 : stock.getQuantiteDisponible();
            if (available < qty) {
                throw new PortalException("Insufficient stock for medicament " + mid + " (need " + qty + ", have " + available + ")");
            }

            total += m.getPrixUnitaire() * qty;
        }

        // Create Vente (use add*)
        long idVente = CounterLocalServiceUtil.increment(Vente.class.getName());
        Vente v = ventePersistence.create(idVente);
        v.setIdUtilisateur(idUtilisateur);
        v.setDateVente(now);
        v.setMontantTotal(total);
        v = addVente(v); // <-- add*, not update*

        // Details + stock
        for (int i = 0; i < medicamentIds.length; i++) {
            long mid = medicamentIds[i];
            int qty = Math.max(0, quantities[i]);
            if (qty == 0) continue;

            Medicament m = medicamentLocalService.getMedicament(mid);
            double pu = m.getPrixUnitaire();
            double st = pu * qty;

            long idDetail = CounterLocalServiceUtil.increment(VenteDetail.class.getName());
            VenteDetail d = venteDetailPersistence.create(idDetail);
            d.setIdVente(idVente);
            d.setIdMedicament(mid);
            d.setQuantite(qty);
            d.setPrixUnitaire(pu);
            d.setSousTotal(st);
            venteDetailLocalService.addVenteDetail(d); // <-- add*, not update*

            // stock -
            Stock stock = stockLocalService.fetchStockByMedicamentId(mid);
            if (stock == null) {
                throw new PortalException("No stock row for medicament " + mid);
            }
            stock.setQuantiteDisponible(Math.max(0, stock.getQuantiteDisponible() - qty));
            stock.setDateDerniereMaj(now);
            stockLocalService.updateStock(stock); // update existing row

            // mouvement
            long idMv = CounterLocalServiceUtil.increment(MouvementStock.class.getName());
            MouvementStock mv = mouvementStockPersistence.create(idMv);
            mv.setIdStock(stock.getIdStock());
            mv.setTypeMouvement("VENTE");
            mv.setQuantite(qty);
            mv.setDateMouvement(now);
            mouvementStockLocalService.addMouvementStock(mv); // add*, not update*
        }
        try {
            int itemCount = 0;
            for (int i = 0; i < medicamentIds.length; i++) {
                if (quantities[i] > 0) itemCount += 1; // (# of distinct lines)
            }
            String msg = "Vente #" + v.getIdVente() + " enregistrée (" + itemCount + " article(s), total " +
                    String.format(java.util.Locale.FRANCE, "%.2f", v.getMontantTotal()) + " €).";

            Notification notif = notificationLocalService.addNotification(
                    idUtilisateur,
                    "VENTE",
                    msg,
                    v.getDateVente()
            );

            System.out.println("[vente] Notification created for user " + v.getIdUtilisateur()
                    + " id=" + notif.getIdNotification() + " message=" + msg);

            // fan-out to other ADMINs (seller must be ADMIN/PHARMACIEN)
            try {
                notificationLocalService.notifyAdminsOfSale(idUtilisateur, v);

                System.out.println("[vente] createVente sellerUid=" + idUtilisateur + " total=" + total);
                System.out.println("[vente] saved vente id=" + v.getIdVente());

            } catch (Exception e) {
                System.out.println("[vente][notifyAdminsOfSale] failed for vente #" + v.getIdVente() + " err=" + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("[vente][notif] failed (won’t block sale): " + e);
            e.printStackTrace();
        }


        return v;
    }
    public java.util.List<Vente> findByDateRange(java.util.Date from, java.util.Date to, int start, int end) {
        DynamicQuery dq = dynamicQuery();
        if (from != null) dq.add(RestrictionsFactoryUtil.ge("dateVente", from));
        if (to   != null) dq.add(RestrictionsFactoryUtil.le("dateVente", to));
        dq.addOrder(OrderFactoryUtil.desc("dateVente"));
        return ventePersistence.findWithDynamicQuery(dq, start, end);
    }

    public int countByDateRange(java.util.Date from, java.util.Date to) {
        DynamicQuery dq = dynamicQuery();
        if (from != null) dq.add(RestrictionsFactoryUtil.ge("dateVente", from));
        if (to   != null) dq.add(RestrictionsFactoryUtil.le("dateVente", to));
        return (int) dynamicQueryCount(dq);
    }

    public java.util.List<Vente> findByUserAndDate(long idUtilisateur, java.util.Date from, java.util.Date to, int start, int end) {
        DynamicQuery dq = dynamicQuery()
                .add(RestrictionsFactoryUtil.eq("idUtilisateur", idUtilisateur));
        if (from != null) dq.add(RestrictionsFactoryUtil.ge("dateVente", from));
        if (to   != null) dq.add(RestrictionsFactoryUtil.le("dateVente", to));
        dq.addOrder(OrderFactoryUtil.desc("dateVente"));
        return ventePersistence.findWithDynamicQuery(dq, start, end);
    }

    @Override
    public java.util.List<gestion_de_pharmacie.model.VenteDetail> getDetails(long idVente) {
        // generated by <finder name="Vente"> on VenteDetail
        return venteDetailPersistence.findByVente(idVente);
    }


}
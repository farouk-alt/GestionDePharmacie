/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Transactional;
import gestion_de_pharmacie.constants.CommandeStatus;
import gestion_de_pharmacie.model.Commande;
import gestion_de_pharmacie.model.CommandeDetail;
import gestion_de_pharmacie.model.MouvementStock;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.service.CommandeDetailLocalService;
import gestion_de_pharmacie.service.FournisseurLocalService;
import gestion_de_pharmacie.service.StockLocalService;
import gestion_de_pharmacie.service.base.CommandeLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Date;
import java.util.List;

import static gestion_de_pharmacie.service.CommandeLocalServiceUtil.deleteCommande;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=gestion_de_pharmacie.model.Commande",
	service = AopService.class
)
public class CommandeLocalServiceImpl extends CommandeLocalServiceBaseImpl {

    @Reference
    private FournisseurLocalService fournisseurLocalService;

    @Reference
    private CommandeDetailLocalService commandeDetailLocalService;

    @Reference
    private StockLocalService stockLocalService;

    @Reference
    private gestion_de_pharmacie.service.MouvementStockLocalService mouvementStockLocalService;

    @Override
    public List<Commande> getCommandesByUtilisateurId(long idUtilisateur) {
        return commandePersistence.findByIdFournisseur(idUtilisateur);
    }

    public String getFournisseurName(long commandeId) {
        try {
            long fournisseurId = commandePersistence.findByPrimaryKey(commandeId).getIdUtilisateur();
            return fournisseurLocalService.fetchFournisseur(fournisseurId).getNom();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCommandeWithDetails(long commandeId) throws PortalException {
        // Delete children first
        List<CommandeDetail> details = commandeDetailPersistence.findByIdCommande(commandeId);
        for (CommandeDetail d : details) {
            commandeDetailLocalService.deleteCommandeDetail(d);
        }

        // Delete parent
        deleteCommande(commandeId); // baseImpl method removes the entity
    }
    /**
     * Mark an order as RECEIVED, increment stocks per detail, and log IN movements.
     */
    public void receiveCommande(long commandeId) throws Exception {
        // 1) Load & validate order
        Commande cmd = fetchCommande(commandeId);
        if (cmd == null) throw new IllegalArgumentException("Commande introuvable: " + commandeId);

        String st = (cmd.getStatut() == null) ? "" : cmd.getStatut().trim().toUpperCase();
        if (!CommandeStatus.ST_ACCEPTED.equals(st)) {
            // You can relax this if you want to allow PENDING -> RECEIVED directly
            throw new IllegalStateException("La commande doit être ACCEPTED pour être réceptionnée.");
        }

        // 2) Get details
        List<CommandeDetail> details;
        try {
            details = commandeDetailLocalService.findByCommandeId(commandeId); // method name matches your finder
        } catch (Throwable t) {
            details = commandeDetailLocalService.findByCommandeId(commandeId, -1, -1);
        }

        // 3) For each line: update stock (create if missing) + mouvement IN
        Date now = new Date();
        for (CommandeDetail d : details) {
            long medId = d.getIdMedicament();
            int qte = d.getQuantite();

            // Try to fetch the Stock row for this med
            Stock stock = null;
            try {
                // Because finder name="Med" return-type="Entity"
                // Service Builder usually generates fetchStockByMed / getStockByMed or persistence.findByMed
                stock = stockPersistence.fetchByMed(medId); // if generated
                if (stock == null) {
                    // fallback via persistence finders if only findByMed exists
                    // stock = stockPersistence.findByMed(medId);
                }
            } catch (Exception ignore) {}

            if (stock == null) {
                long stockId = counterLocalService.increment(Stock.class.getName());
                stock = stockLocalService.createStock(stockId);
                stock.setIdMedicament(medId);
                stock.setQuantiteDisponible(0);
            }

            // increment
            stock.setQuantiteDisponible(Math.max(0, stock.getQuantiteDisponible()) + qte);
            stock.setDateDerniereMaj(now);
            stockLocalService.updateStock(stock);

            // mouvement IN
            long mouvId = counterLocalService.increment(MouvementStock.class.getName());
            MouvementStock mv = mouvementStockLocalService.createMouvementStock(mouvId);
            mv.setIdStock(stock.getIdStock());
            mv.setTypeMouvement("IN"); // or "ENTREE"
            mv.setQuantite(qte);
            mv.setDateMouvement(now);
            mouvementStockLocalService.addMouvementStock(mv);
        }

        // 4) Set order as RECEIVED
        cmd.setStatut(CommandeStatus.ST_RECEIVED);
        // (optional) add a dedicated dateReception column later if you need history
        updateCommande(cmd);
    }
}
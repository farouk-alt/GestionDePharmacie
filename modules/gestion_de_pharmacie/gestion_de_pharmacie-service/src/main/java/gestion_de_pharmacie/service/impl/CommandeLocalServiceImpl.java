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

import java.text.MessageFormat;
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

    @Reference
    private gestion_de_pharmacie.service.NotificationLocalService notificationLocalService;

    // ----- Domain status (use ONE source of truth; if you already have the constants in
    // gestion_de_pharmacie.constants.CommandeStatus, use those and delete these)
    public static final String ST_CREATED  = "CREATED";
    public static final String ST_PENDING  = "PENDING";
    public static final String ST_ACCEPTED = "ACCEPTED";
    public static final String ST_REFUSED  = "REFUSED";
    public static final String ST_CANCELED = "CANCELED";
    public static final String ST_RECEIVED = "RECEIVED";

    // ----- Notification types (distinct from statuses)
    private static final String NT_CMD_SENT      = "CMD_SENT";
    private static final String NT_CMD_RECEIVED  = "CMD_RECEIVED";
    private static final String NT_CMD_REASSIGN  = "CMD_REASSIGNED";
    private static final String NT_CMD_CANCELED  = "CMD_CANCELED";
    private static final String NT_CMD_UPDATED   = "CMD_UPDATED";
    private static final String NT_CMD_ACCEPTED  = "CMD_ACCEPTED";
    private static final String NT_CMD_REFUSED   = "CMD_REFUSED";


    private static final java.util.Set<String> SUPPLIER_VISIBLE =
            new java.util.HashSet<>(java.util.Arrays.asList(
                    NT_CMD_SENT, NT_CMD_ACCEPTED, NT_CMD_RECEIVED
            ));

    private boolean shouldNotifyFournisseur(String type) {
        return SUPPLIER_VISIBLE.contains(type);
    }

    private void notifyCommandeAction(long actorId, Commande cmd, String type, String message) {
        try {
            // 1) actor (optional – keep if you want a “confirmation” to the clicker)
            if (actorId > 0) {
                notificationLocalService.addNotification(actorId, type, message);
            }

            // 2) fournisseur (see section #2 for filtering)
            long fid = (cmd != null) ? cmd.getIdUtilisateur() : 0;

            if (shouldNotifyFournisseur(type) && fid > 0 && fid != actorId) {
                notificationLocalService.addNotification(fid, type, message);
            }

            // 3) admin fan-out, but exclude actor to avoid duplicates
            notificationLocalService.addNotificationForRoleExceptUser("ADMIN", type, message, actorId);

        } catch (Exception e) {
            System.out.println("[notifyCommandeAction] failed: " + e);
        }
    }

    public void sendCommande(long actorId, long commandeId) throws PortalException {
        Commande cmd = fetchCommande(commandeId);
        if (cmd == null) throw new PortalException("Commande introuvable: " + commandeId);
        String st = (cmd.getStatut() == null) ? "" : cmd.getStatut().trim().toUpperCase();
        if (!ST_CREATED.equals(st)) throw new PortalException("Seules les commandes CREATED peuvent être envoyées.");

        cmd.setStatut(ST_PENDING);
        updateCommande(cmd);

        notifyCommandeAction(actorId, cmd, NT_CMD_SENT,
                MessageFormat.format("Commande #{0} envoyée au fournisseur.", cmd.getIdCommande()));
    }

    public void cancelCommande(long actorId, long commandeId) throws PortalException {
        Commande cmd = fetchCommande(commandeId);
        if (cmd == null) throw new PortalException("Commande introuvable: " + commandeId);
        String st = (cmd.getStatut() == null) ? "" : cmd.getStatut().trim().toUpperCase();
        if (!CommandeStatus.isCancelable(st)) throw new PortalException("Statut non annulable: " + st);

        cmd.setStatut(ST_CANCELED);
        updateCommande(cmd);

        notifyCommandeAction(actorId, cmd, NT_CMD_CANCELED,
                MessageFormat.format("Commande #{0} annulée.", cmd.getIdCommande()));
    }
    public void reassignCommande(long actorId, long commandeId, long newFournisseurId, boolean sendNow) throws PortalException {
        Commande cmd = fetchCommande(commandeId);
        if (cmd == null) throw new PortalException("Commande introuvable: " + commandeId);
        String st = (cmd.getStatut() == null) ? "" : cmd.getStatut().trim().toUpperCase();
        if (!ST_REFUSED.equals(st)) throw new PortalException("Seules les commandes REFUSED peuvent être réassignées.");

        cmd.setIdUtilisateur(newFournisseurId);
        cmd.setDateCommande(new Date());
        cmd.setStatut(sendNow ? ST_PENDING : ST_CREATED);
        updateCommande(cmd);

        notifyCommandeAction(actorId, cmd, NT_CMD_REASSIGN,
                MessageFormat.format("Commande #{0} réassignée (nouveau fournisseur {1}).", cmd.getIdCommande(), newFournisseurId));
    }


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
    private void receiveCommandeCore(long commandeId) throws Exception {
        Commande cmd = fetchCommande(commandeId);
        if (cmd == null) throw new IllegalArgumentException("Commande introuvable: " + commandeId);

        String st = (cmd.getStatut() == null) ? "" : cmd.getStatut().trim().toUpperCase();
        if (!ST_ACCEPTED.equals(st)) {
            throw new IllegalStateException("La commande doit être ACCEPTED pour être réceptionnée.");
        }

        // details
        List<CommandeDetail> details;
        try { details = commandeDetailLocalService.findByCommandeId(commandeId); }
        catch (Throwable t) { details = commandeDetailLocalService.findByCommandeId(commandeId, -1, -1); }

        Date now = new Date();
        for (CommandeDetail d : details) {
            long medId = d.getIdMedicament();
            int qte   = d.getQuantite();

            Stock stock = null;
            try { stock = stockPersistence.fetchByMed(medId); } catch (Exception ignore) {}

            if (stock == null) {
                long stockId = counterLocalService.increment(Stock.class.getName());
                stock = stockLocalService.createStock(stockId);
                stock.setIdMedicament(medId);
                stock.setQuantiteDisponible(0);
            }

            stock.setQuantiteDisponible(Math.max(0, stock.getQuantiteDisponible()) + qte);
            stock.setDateDerniereMaj(now);
            stockLocalService.updateStock(stock);

            long mouvId = counterLocalService.increment(MouvementStock.class.getName());
            MouvementStock mv = mouvementStockLocalService.createMouvementStock(mouvId);
            mv.setIdStock(stock.getIdStock());
            mv.setTypeMouvement("IN");
            mv.setQuantite(qte);
            mv.setDateMouvement(now);
            mouvementStockLocalService.addMouvementStock(mv);
        }

        cmd.setStatut(ST_RECEIVED);
        updateCommande(cmd);
    }

    public void receiveCommande(long commandeId) throws Exception {
        receiveCommandeCore(commandeId);
    }

    public void receiveCommande(long actorId, long commandeId) throws Exception {
        receiveCommandeCore(commandeId);
        Commande cmd = fetchCommande(commandeId);
        notifyCommandeAction(actorId, cmd, NT_CMD_RECEIVED,
                MessageFormat.format("Commande #{0} réceptionnée.", cmd.getIdCommande()));
    }


    public void acceptCommande(long actorId, long commandeId) throws PortalException {
        Commande cmd = fetchCommande(commandeId);
        if (cmd == null) throw new PortalException("Commande introuvable: " + commandeId);
        String st = (cmd.getStatut() == null) ? "" : cmd.getStatut().trim().toUpperCase();
        if (!ST_PENDING.equals(st)) throw new PortalException("La commande doit être PENDING pour être acceptée.");

        cmd.setStatut(ST_ACCEPTED);
        updateCommande(cmd);

        notifyCommandeAction(actorId, cmd, NT_CMD_ACCEPTED,
                MessageFormat.format("Commande #{0} acceptée par le fournisseur.", cmd.getIdCommande()));
    }

    public void rejectCommande(long actorId, long commandeId) throws PortalException {
        Commande cmd = fetchCommande(commandeId);
        if (cmd == null) throw new PortalException("Commande introuvable: " + commandeId);
        String st = (cmd.getStatut() == null) ? "" : cmd.getStatut().trim().toUpperCase();
        if (!ST_PENDING.equals(st)) throw new PortalException("La commande doit être PENDING pour être refusée.");

        cmd.setStatut(ST_REFUSED);
        updateCommande(cmd);

        notifyCommandeAction(actorId, cmd, NT_CMD_REFUSED,
                MessageFormat.format("Commande #{0} refusée par le fournisseur.", cmd.getIdCommande()));
    }
    public void notifyCommandeEdited(long actorId, long commandeId) throws PortalException {
        Commande cmd = fetchCommande(commandeId);
        if (cmd == null) throw new PortalException("Commande introuvable: " + commandeId);
        notifyCommandeAction(actorId, cmd, NT_CMD_UPDATED,
                MessageFormat.format("Commande #{0} modifiée.", cmd.getIdCommande()));
    }

}
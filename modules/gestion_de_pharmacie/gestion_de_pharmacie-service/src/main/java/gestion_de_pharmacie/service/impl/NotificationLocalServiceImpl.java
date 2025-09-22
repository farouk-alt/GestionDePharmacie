/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.util.OrderByComparator;
import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Notification;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.service.base.NotificationLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Farouk
 */
@Component(
	property = "model.class.name=gestion_de_pharmacie.model.Notification",
	service = AopService.class
)
public class NotificationLocalServiceImpl
	extends NotificationLocalServiceBaseImpl {
    // Create for ONE user
    public Notification addNotification(
            long userId, String type, String message) {

        long id = counterLocalService.increment(Notification.class.getName());
        Notification n = notificationPersistence.create(id);

        n.setIdUtilisateur(userId);
        n.setType(type);
        n.setMessage(message);
        n.setStatut("UNREAD");
        n.setDateCreation(new Date());

        return notificationPersistence.update(n);
    }

    // Create for everyone in a role
    public void addNotificationForRole(
            String role, String type, String message) {

        List<Utilisateur> users = utilisateurPersistence.findByRole(role);
        for (Utilisateur u : users) {
            addNotification(u.getIdUtilisateur(), type, message);
        }
    }

    // Mark one as READ
    public Notification markAsRead(long idNotification){
        Notification n = notificationPersistence.fetchByPrimaryKey(idNotification);
        if (n != null && !"READ".equals(n.getStatut())) {
            n.setStatut("READ");
            n = notificationPersistence.update(n);
        }
        return n;
    }

    // Mark all for a user
    public int markAllAsRead(long idUtilisateur){
        List<Notification> list = notificationPersistence.findByUser_Status(idUtilisateur, "UNREAD");
        for (Notification n : list) {
            n.setStatut("READ");
            notificationPersistence.update(n);
        }
        return list.size();
    }

    // Count unread for badge
    public int countUnread(long idUtilisateur){
        return notificationPersistence.findByUser_Status(idUtilisateur, "UNREAD").size();
    }
    public List<Notification> getByUserStatus(
            long userId, String statut, int start, int end,
            OrderByComparator<Notification> obc) {
        return notificationPersistence.findByUser_Status(userId, statut, start, end, obc);
    }

    // Call this after you add/update a medicament (or when stock changes later)
    public void createLowStockAlertsForMed(long idMedicament) {
        Medicament med = medicamentPersistence.fetchByPrimaryKey(idMedicament);
        if (med == null) return;

        // current qty (if no stock row yet, treat as 0)
        Stock st = null;
        try {
            st = stockPersistence.fetchByMed(idMedicament);  // needs the Stock finder above
        } catch (Exception ignore) {}
        int qte = (st != null) ? st.getQuantiteDisponible() : 0;

        if (med.getSeuilMinimum() <= 0 || qte >= med.getSeuilMinimum()) return;

        // choose recipients — for MVP send only to current user or all admins
        // (simplest without adding a finder on role: filter in memory)
        List<Utilisateur> allUsers = utilisateurPersistence.findAll();
        List<Utilisateur> recipients = new ArrayList<>();
        for (Utilisateur u : allUsers) {
            String r = (u.getRole() == null) ? "" : u.getRole();
            if ("ADMIN".equals(r) || "SUPER_ADMIN".equals(r)) {
                recipients.add(u);
            }
        }

        final String type = "LOW_STOCK";
        final String msg = String.format(
                "Stock bas pour %s (%s) : %d < seuil %d",
                med.getNom(), med.getCode(), qte, med.getSeuilMinimum()
        );

        Date now = new Date();
        for (Utilisateur u : recipients) {
            // de-dupe: skip if an UNREAD LOW_STOCK with same message already exists
            List<Notification> existing = notificationPersistence.findByUser_Status(u.getIdUtilisateur(), "UNREAD");
            boolean dup = false;
            for (Notification n : existing) {
                if (type.equals(n.getType()) && msg.equals(n.getMessage())) { dup = true; break; }
            }
            if (dup) continue;

            long pk = counterLocalService.increment(Notification.class.getName());
            Notification n = notificationPersistence.create(pk);
            n.setIdUtilisateur(u.getIdUtilisateur());
            n.setType(type);
            n.setMessage(msg);
            n.setStatut("UNREAD");
            n.setDateCreation(now);
            notificationPersistence.update(n);
        }
    }
    // in NotificationLocalServiceImpl
    public int countUnreadByUser(long userId) {
        return notificationPersistence.countByUser_Status(userId, "UNREAD");
    }

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package gestion_de_pharmacie.service.impl;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;

import com.liferay.portal.kernel.util.OrderByComparator;
import gestion_de_pharmacie.model.Notification;
import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.model.Vente;

import gestion_de_pharmacie.service.UtilisateurLocalService;
import gestion_de_pharmacie.service.base.NotificationLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Notification business logic.
 * - On sale creation by ADMIN/PHARMACIEN -> notify all other ADMINs.
 * - Basic CRUD helpers (mark read, counts, listings).
 * - Optional low-stock alert helper.
 */
@Component(
        property = "model.class.name=gestion_de_pharmacie.model.Notification",
        service = AopService.class
)
public class NotificationLocalServiceImpl extends NotificationLocalServiceBaseImpl {

    // Adjust if your DB uses other names (e.g., profil / ADMINISTRATEUR)
    private static final String ROLE_ADMIN       = "ADMIN";
    private static final String ROLE_PHARMACIEN  = "PHARMACIEN";
    private static final String UTILISATEUR_ROLE_COL = "role";

    @Reference
    private UtilisateurLocalService utilisateurLocalService;

    /* ================================================================
     * Convenience creators
     * ================================================================ */

    /** Custom helper: create one UNREAD notification now. */
    public Notification addNotification(long idUtilisateur, String type, String message) {
        return addNotification(idUtilisateur, type, message, new Date());
    }

    /** Custom helper: create one UNREAD notification at a given time. */
    public Notification addNotification(long idUtilisateur, String type, String message, Date when) {
        System.out.println("[notif][addNotification] uid=" + idUtilisateur + " type=" + type + " msg=" + message);
        long id = CounterLocalServiceUtil.increment(Notification.class.getName());
        Notification n = notificationPersistence.create(id);
        n.setIdUtilisateur(idUtilisateur);
        n.setType(type);
        n.setMessage(message);
        n.setStatut("UNREAD");
        n.setDateCreation(when != null ? when : new Date());
        n = super.addNotification(n);
        System.out.println("[notif][addNotification] +row id=" + n.getIdNotification());
        return n;
    }

    /** Create the same notification for all users having a role. */
    public void addNotificationForRole(String role, String type, String message) {
        System.out.println("[notif][addNotificationForRole] role=" + role);
        List<Utilisateur> users = utilisateurPersistence.findByRole(role);
        for (Utilisateur u : users) {
            addNotification(u.getIdUtilisateur(), type, message);
        }
    }

    /* ================================================================
     * Read / list / counters
     * ================================================================ */

    /** Count unread for badge (dynamicQuery variant, with debugs). */
    public int countUnreadByUser(long userId) {
        try {
            DynamicQuery dq = dynamicQuery()
                    .add(RestrictionsFactoryUtil.eq("idUtilisateur", userId))
                    .add(RestrictionsFactoryUtil.eq("statut", "UNREAD"));
            int c = (int) dynamicQueryCount(dq);
            System.out.println("[notif][countUnreadByUser] uid=" + userId + " -> " + c);
            return c;
        } catch (Exception e) {
            System.out.println("[notif][countUnreadByUser] ERROR " + e);
            return 0;
        }
    }

    /** Legacy convenience: count unread via finder (if you prefer). */
    public int countUnread(long idUtilisateur) {
        int c = notificationPersistence.findByUser_Status(idUtilisateur, "UNREAD").size();
        System.out.println("[notif][countUnread] uid=" + idUtilisateur + " -> " + c);
        return c;
    }

    /** List unread (paged). */
    public List<Notification> getUnreadByUser(long idUtilisateur, int start, int end) {
        List<Notification> list = notificationPersistence.findByUser_Status(idUtilisateur, "UNREAD", start, end);
        System.out.println("[notif][getUnreadByUser] uid=" + idUtilisateur + " size=" + list.size());
        return list;
    }

    /** List latest (any status) ordered by dateCreation desc. */
    public List<Notification> getLatestByUser(long userId, int start, int end) {
        try {
            DynamicQuery dq = dynamicQuery()
                    .add(RestrictionsFactoryUtil.eq("idUtilisateur", userId))
                    .addOrder(OrderFactoryUtil.desc("dateCreation"));
            @SuppressWarnings("unchecked")
            List<Notification> list = (List<Notification>)(List<?>)
                    notificationPersistence.findWithDynamicQuery(dq, start, end);
            System.out.println("[notif][getLatestByUser] uid=" + userId + " size=" + list.size());
            return list;
        } catch (Exception e) {
            System.out.println("[notif][getLatestByUser] ERROR " + e);
            return new ArrayList<>();
        }
    }

    /* ================================================================
     * Mark read
     * ================================================================ */

    public Notification markAsRead(long idNotification) {
        Notification n = notificationPersistence.fetchByPrimaryKey(idNotification);
        if (n != null && !"READ".equals(n.getStatut())) {
            n.setStatut("READ");
            n = notificationPersistence.update(n);
            System.out.println("[notif][markAsRead] id=" + idNotification + " -> READ");
        } else {
            System.out.println("[notif][markAsRead] id=" + idNotification + " not found or already READ");
        }
        return n;
    }

    public int markAllAsRead(long idUtilisateur) {
        List<Notification> list = notificationPersistence.findByUser_Status(
                idUtilisateur, "UNREAD", QueryUtil.ALL_POS, QueryUtil.ALL_POS
        );
        for (Notification n : list) {
            n.setStatut("READ");
            notificationPersistence.update(n);
        }
        System.out.println("[notif][markAllAsRead] uid=" + idUtilisateur + " changed=" + list.size());
        return list.size();
    }

    @Override
    public int markAllRead(long idUtilisateur) {
        return 0;
    }

    /* ================================================================
     * Business rule: Vente -> notify admins (exclude seller)
     * ================================================================ */

    /**
     * Triggered by VenteLocalServiceImpl after a sale is saved.
     * If seller is ADMIN or PHARMACIEN, notify all other ADMINs.
     */
    public void notifyAdminsOfSale(long sellerUtilisateurId, Vente vente) {
        System.out.println("[notif][notifyAdminsOfSale] sellerUid=" + sellerUtilisateurId
                + " venteId=" + (vente != null ? vente.getIdVente() : 0));

        try {
            if (vente == null) {
                System.out.println("[notif] vente=null -> abort");
                return;
            }

            Utilisateur seller = utilisateurLocalService.fetchUtilisateur(sellerUtilisateurId);
            if (seller == null) {
                System.out.println("[notif] seller Utilisateur not found -> abort");
                return;
            }

            String sellerRole = readRole(seller);
            System.out.println("[notif] sellerRole=" + sellerRole);

            boolean sellerCanTrigger =
                    ROLE_ADMIN.equalsIgnoreCase(sellerRole) || ROLE_PHARMACIEN.equalsIgnoreCase(sellerRole);

            if (!sellerCanTrigger) {
                System.out.println("[notif] seller role not allowed to trigger -> skip");
                return;
            }

            // Recipient = all ADMINs except the seller
            DynamicQuery dqAdmins = utilisateurLocalService.dynamicQuery()
                    .add(RestrictionsFactoryUtil.eq(UTILISATEUR_ROLE_COL, ROLE_ADMIN))
                    .add(RestrictionsFactoryUtil.ne("idUtilisateur", sellerUtilisateurId));

            @SuppressWarnings("unchecked")
            List<Utilisateur> admins = (List<Utilisateur>)(List<?>)
                    utilisateurLocalService.dynamicQuery(dqAdmins);

            System.out.println("[notif] adminRecipients(size)=" + admins.size());
            if (admins.isEmpty()) return;

            String sellerDisplay = buildSellerDisplay(seller);
            String message = "Nouvelle vente #" + vente.getIdVente()
                    + " par " + sellerDisplay
                    + " – Montant: " + String.format("%.2f", vente.getMontantTotal());

            Date now = new Date();
            for (Utilisateur u : admins) {
                try {
                    long id = CounterLocalServiceUtil.increment(Notification.class.getName());
                    Notification n = notificationPersistence.create(id);
                    n.setIdUtilisateur(u.getIdUtilisateur());
                    n.setType("VENTE");
                    n.setMessage(message);
                    n.setStatut("UNREAD");
                    n.setDateCreation(now);
                    addNotification(n);
                    System.out.println("[notif] +row id=" + id + " -> uid=" + u.getIdUtilisateur());
                } catch (Exception ex) {
                    System.out.println("[notif] create failed for uid=" + u.getIdUtilisateur() + " err=" + ex);
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("[notif][notifyAdminsOfSale] ERROR " + e);
            e.printStackTrace();
        }
    }

    /* ================================================================
     * Optional: low stock alert helper (uses generated *Persistence)
     * ================================================================ */

    public void createLowStockAlertsForMed(long idMedicament) {
        Medicament med = medicamentPersistence.fetchByPrimaryKey(idMedicament);
        if (med == null) return;

        Stock st = null;
        try {
            st = stockPersistence.fetchByMed(idMedicament);  // requires a <finder name="Med"> on Stock
        } catch (Exception ignore) {}
        int qte = (st != null) ? st.getQuantiteDisponible() : 0;

        if (med.getSeuilMinimum() <= 0 || qte >= med.getSeuilMinimum()) return;

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
            // de-dupe on unread same message
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
            System.out.println("[notif][lowStock] +row id=" + pk + " -> uid=" + u.getIdUtilisateur());
        }
    }

    @Override
    public int deleteAllNotifications() {
        return 0;
    }

    @Override
    public List<Notification> getByUserStatus(long userId, String statut, int start, int end, OrderByComparator<Notification> obc) {
        return List.of();
    }

    /* ================================================================
     * helpers
     * ================================================================ */

    private String readRole(Utilisateur u) {
        // If your model method is getProfil(), this will fall back to it
        try {
            return (String) Utilisateur.class.getMethod("getRole").invoke(u);
        } catch (NoSuchMethodException nsme) {
            try { return (String) Utilisateur.class.getMethod("getProfil").invoke(u); }
            catch (Exception e) { return ""; }
        } catch (Exception e) {
            return "";
        }
    }

    private String buildSellerDisplay(Utilisateur u) {
        try {
            String full = ((nullSafe(u.getPrenom()) + " " + nullSafe(u.getNom())).trim());
            if (!full.isBlank()) return full;
        } catch (Exception ignore) {}
        try {
            // adjust to getAdresseEmail() if your field is different
            String email = (String) Utilisateur.class.getMethod("getEmail").invoke(u);
            if (email != null) return email;
        } catch (Exception ignore) {}
        return "Utilisateur#" + u.getIdUtilisateur();
    }

    private static String nullSafe(String s) { return (s == null) ? "" : s; }

    // NotificationLocalServiceImpl.java
    public int deleteAllForUser(long idUtilisateur) {
        try {
            System.out.println("[notif][deleteAllForUser] uid=" + idUtilisateur);
            DynamicQuery dq = dynamicQuery()
                    .add(RestrictionsFactoryUtil.eq("idUtilisateur", idUtilisateur));
            @SuppressWarnings("unchecked")
            List<Notification> rows = (List<Notification>)(List<?>)
                    notificationPersistence.findWithDynamicQuery(dq);

            int n = 0;
            for (Notification r : rows) {
                notificationPersistence.remove(r);
                n++;
            }
            System.out.println("[notif][deleteAllForUser] removed=" + n);
            return n;
        } catch (Exception e) {
            System.out.println("[notif][deleteAllForUser] ERROR " + e);
            e.printStackTrace();
            return 0;
        }
    }

    // In NotificationLocalServiceImpl (impl)
    public void addNotificationForRoleExceptUser(String role, String type, String message, long excludeUserId) {
        List<Utilisateur> users = utilisateurPersistence.findByRole(role); // or your existing finder
        Date now = new Date();
        for (Utilisateur u : users) {
            if (u.getIdUtilisateur() == excludeUserId) continue;
            addNotification(u.getIdUtilisateur(), type, message, now);
        }
    }


}

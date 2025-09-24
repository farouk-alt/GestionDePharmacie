/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import gestion_de_pharmacie.exception.NoSuchNotificationException;

import gestion_de_pharmacie.model.Notification;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the notification service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see NotificationUtil
 * @generated
 */
@ProviderType
public interface NotificationPersistence extends BasePersistence<Notification> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link NotificationUtil} to access the notification persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the notifications where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the matching notifications
	 */
	public java.util.List<Notification> findByUser(long idUtilisateur);

	/**
	 * Returns a range of all the notifications where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @return the range of matching notifications
	 */
	public java.util.List<Notification> findByUser(
		long idUtilisateur, int start, int end);

	/**
	 * Returns an ordered range of all the notifications where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications
	 */
	public java.util.List<Notification> findByUser(
		long idUtilisateur, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notifications where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications
	 */
	public java.util.List<Notification> findByUser(
		long idUtilisateur, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	public Notification findByUser_First(
			long idUtilisateur,
			com.liferay.portal.kernel.util.OrderByComparator<Notification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification, or <code>null</code> if a matching notification could not be found
	 */
	public Notification fetchByUser_First(
		long idUtilisateur,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator);

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	public Notification findByUser_Last(
			long idUtilisateur,
			com.liferay.portal.kernel.util.OrderByComparator<Notification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification, or <code>null</code> if a matching notification could not be found
	 */
	public Notification fetchByUser_Last(
		long idUtilisateur,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator);

	/**
	 * Returns the notifications before and after the current notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idNotification the primary key of the current notification
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	public Notification[] findByUser_PrevAndNext(
			long idNotification, long idUtilisateur,
			com.liferay.portal.kernel.util.OrderByComparator<Notification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Removes all the notifications where idUtilisateur = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 */
	public void removeByUser(long idUtilisateur);

	/**
	 * Returns the number of notifications where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the number of matching notifications
	 */
	public int countByUser(long idUtilisateur);

	/**
	 * Returns all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @return the matching notifications
	 */
	public java.util.List<Notification> findByUser_Status(
		long idUtilisateur, String statut);

	/**
	 * Returns a range of all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @return the range of matching notifications
	 */
	public java.util.List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end);

	/**
	 * Returns an ordered range of all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications
	 */
	public java.util.List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications
	 */
	public java.util.List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	public Notification findByUser_Status_First(
			long idUtilisateur, String statut,
			com.liferay.portal.kernel.util.OrderByComparator<Notification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification, or <code>null</code> if a matching notification could not be found
	 */
	public Notification fetchByUser_Status_First(
		long idUtilisateur, String statut,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator);

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	public Notification findByUser_Status_Last(
			long idUtilisateur, String statut,
			com.liferay.portal.kernel.util.OrderByComparator<Notification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification, or <code>null</code> if a matching notification could not be found
	 */
	public Notification fetchByUser_Status_Last(
		long idUtilisateur, String statut,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator);

	/**
	 * Returns the notifications before and after the current notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idNotification the primary key of the current notification
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	public Notification[] findByUser_Status_PrevAndNext(
			long idNotification, long idUtilisateur, String statut,
			com.liferay.portal.kernel.util.OrderByComparator<Notification>
				orderByComparator)
		throws NoSuchNotificationException;

	/**
	 * Removes all the notifications where idUtilisateur = &#63; and statut = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 */
	public void removeByUser_Status(long idUtilisateur, String statut);

	/**
	 * Returns the number of notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @return the number of matching notifications
	 */
	public int countByUser_Status(long idUtilisateur, String statut);

	/**
	 * Caches the notification in the entity cache if it is enabled.
	 *
	 * @param notification the notification
	 */
	public void cacheResult(Notification notification);

	/**
	 * Caches the notifications in the entity cache if it is enabled.
	 *
	 * @param notifications the notifications
	 */
	public void cacheResult(java.util.List<Notification> notifications);

	/**
	 * Creates a new notification with the primary key. Does not add the notification to the database.
	 *
	 * @param idNotification the primary key for the new notification
	 * @return the new notification
	 */
	public Notification create(long idNotification);

	/**
	 * Removes the notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification that was removed
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	public Notification remove(long idNotification)
		throws NoSuchNotificationException;

	public Notification updateImpl(Notification notification);

	/**
	 * Returns the notification with the primary key or throws a <code>NoSuchNotificationException</code> if it could not be found.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	public Notification findByPrimaryKey(long idNotification)
		throws NoSuchNotificationException;

	/**
	 * Returns the notification with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification, or <code>null</code> if a notification with the primary key could not be found
	 */
	public Notification fetchByPrimaryKey(long idNotification);

	/**
	 * Returns all the notifications.
	 *
	 * @return the notifications
	 */
	public java.util.List<Notification> findAll();

	/**
	 * Returns a range of all the notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @return the range of notifications
	 */
	public java.util.List<Notification> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notifications
	 */
	public java.util.List<Notification> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notifications
	 */
	public java.util.List<Notification> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Notification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the notifications from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of notifications.
	 *
	 * @return the number of notifications
	 */
	public int countAll();

}
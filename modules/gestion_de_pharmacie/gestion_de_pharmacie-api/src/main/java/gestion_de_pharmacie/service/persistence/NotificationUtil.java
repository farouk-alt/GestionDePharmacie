/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.Notification;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the notification service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.NotificationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see NotificationPersistence
 * @generated
 */
public class NotificationUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Notification notification) {
		getPersistence().clearCache(notification);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Notification> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Notification> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Notification> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Notification> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Notification> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Notification update(Notification notification) {
		return getPersistence().update(notification);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Notification update(
		Notification notification, ServiceContext serviceContext) {

		return getPersistence().update(notification, serviceContext);
	}

	/**
	 * Returns all the notifications where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the matching notifications
	 */
	public static List<Notification> findByUser(long idUtilisateur) {
		return getPersistence().findByUser(idUtilisateur);
	}

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
	public static List<Notification> findByUser(
		long idUtilisateur, int start, int end) {

		return getPersistence().findByUser(idUtilisateur, start, end);
	}

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
	public static List<Notification> findByUser(
		long idUtilisateur, int start, int end,
		OrderByComparator<Notification> orderByComparator) {

		return getPersistence().findByUser(
			idUtilisateur, start, end, orderByComparator);
	}

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
	public static List<Notification> findByUser(
		long idUtilisateur, int start, int end,
		OrderByComparator<Notification> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUser(
			idUtilisateur, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	public static Notification findByUser_First(
			long idUtilisateur,
			OrderByComparator<Notification> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchNotificationException {

		return getPersistence().findByUser_First(
			idUtilisateur, orderByComparator);
	}

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification, or <code>null</code> if a matching notification could not be found
	 */
	public static Notification fetchByUser_First(
		long idUtilisateur, OrderByComparator<Notification> orderByComparator) {

		return getPersistence().fetchByUser_First(
			idUtilisateur, orderByComparator);
	}

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	public static Notification findByUser_Last(
			long idUtilisateur,
			OrderByComparator<Notification> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchNotificationException {

		return getPersistence().findByUser_Last(
			idUtilisateur, orderByComparator);
	}

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification, or <code>null</code> if a matching notification could not be found
	 */
	public static Notification fetchByUser_Last(
		long idUtilisateur, OrderByComparator<Notification> orderByComparator) {

		return getPersistence().fetchByUser_Last(
			idUtilisateur, orderByComparator);
	}

	/**
	 * Returns the notifications before and after the current notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idNotification the primary key of the current notification
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	public static Notification[] findByUser_PrevAndNext(
			long idNotification, long idUtilisateur,
			OrderByComparator<Notification> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchNotificationException {

		return getPersistence().findByUser_PrevAndNext(
			idNotification, idUtilisateur, orderByComparator);
	}

	/**
	 * Removes all the notifications where idUtilisateur = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 */
	public static void removeByUser(long idUtilisateur) {
		getPersistence().removeByUser(idUtilisateur);
	}

	/**
	 * Returns the number of notifications where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the number of matching notifications
	 */
	public static int countByUser(long idUtilisateur) {
		return getPersistence().countByUser(idUtilisateur);
	}

	/**
	 * Returns all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @return the matching notifications
	 */
	public static List<Notification> findByUser_Status(
		long idUtilisateur, String statut) {

		return getPersistence().findByUser_Status(idUtilisateur, statut);
	}

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
	public static List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end) {

		return getPersistence().findByUser_Status(
			idUtilisateur, statut, start, end);
	}

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
	public static List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end,
		OrderByComparator<Notification> orderByComparator) {

		return getPersistence().findByUser_Status(
			idUtilisateur, statut, start, end, orderByComparator);
	}

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
	public static List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end,
		OrderByComparator<Notification> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUser_Status(
			idUtilisateur, statut, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	public static Notification findByUser_Status_First(
			long idUtilisateur, String statut,
			OrderByComparator<Notification> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchNotificationException {

		return getPersistence().findByUser_Status_First(
			idUtilisateur, statut, orderByComparator);
	}

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification, or <code>null</code> if a matching notification could not be found
	 */
	public static Notification fetchByUser_Status_First(
		long idUtilisateur, String statut,
		OrderByComparator<Notification> orderByComparator) {

		return getPersistence().fetchByUser_Status_First(
			idUtilisateur, statut, orderByComparator);
	}

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	public static Notification findByUser_Status_Last(
			long idUtilisateur, String statut,
			OrderByComparator<Notification> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchNotificationException {

		return getPersistence().findByUser_Status_Last(
			idUtilisateur, statut, orderByComparator);
	}

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification, or <code>null</code> if a matching notification could not be found
	 */
	public static Notification fetchByUser_Status_Last(
		long idUtilisateur, String statut,
		OrderByComparator<Notification> orderByComparator) {

		return getPersistence().fetchByUser_Status_Last(
			idUtilisateur, statut, orderByComparator);
	}

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
	public static Notification[] findByUser_Status_PrevAndNext(
			long idNotification, long idUtilisateur, String statut,
			OrderByComparator<Notification> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchNotificationException {

		return getPersistence().findByUser_Status_PrevAndNext(
			idNotification, idUtilisateur, statut, orderByComparator);
	}

	/**
	 * Removes all the notifications where idUtilisateur = &#63; and statut = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 */
	public static void removeByUser_Status(long idUtilisateur, String statut) {
		getPersistence().removeByUser_Status(idUtilisateur, statut);
	}

	/**
	 * Returns the number of notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @return the number of matching notifications
	 */
	public static int countByUser_Status(long idUtilisateur, String statut) {
		return getPersistence().countByUser_Status(idUtilisateur, statut);
	}

	/**
	 * Caches the notification in the entity cache if it is enabled.
	 *
	 * @param notification the notification
	 */
	public static void cacheResult(Notification notification) {
		getPersistence().cacheResult(notification);
	}

	/**
	 * Caches the notifications in the entity cache if it is enabled.
	 *
	 * @param notifications the notifications
	 */
	public static void cacheResult(List<Notification> notifications) {
		getPersistence().cacheResult(notifications);
	}

	/**
	 * Creates a new notification with the primary key. Does not add the notification to the database.
	 *
	 * @param idNotification the primary key for the new notification
	 * @return the new notification
	 */
	public static Notification create(long idNotification) {
		return getPersistence().create(idNotification);
	}

	/**
	 * Removes the notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification that was removed
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	public static Notification remove(long idNotification)
		throws gestion_de_pharmacie.exception.NoSuchNotificationException {

		return getPersistence().remove(idNotification);
	}

	public static Notification updateImpl(Notification notification) {
		return getPersistence().updateImpl(notification);
	}

	/**
	 * Returns the notification with the primary key or throws a <code>NoSuchNotificationException</code> if it could not be found.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	public static Notification findByPrimaryKey(long idNotification)
		throws gestion_de_pharmacie.exception.NoSuchNotificationException {

		return getPersistence().findByPrimaryKey(idNotification);
	}

	/**
	 * Returns the notification with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification, or <code>null</code> if a notification with the primary key could not be found
	 */
	public static Notification fetchByPrimaryKey(long idNotification) {
		return getPersistence().fetchByPrimaryKey(idNotification);
	}

	/**
	 * Returns all the notifications.
	 *
	 * @return the notifications
	 */
	public static List<Notification> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<Notification> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<Notification> findAll(
		int start, int end, OrderByComparator<Notification> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<Notification> findAll(
		int start, int end, OrderByComparator<Notification> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the notifications from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of notifications.
	 *
	 * @return the number of notifications
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static NotificationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(NotificationPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile NotificationPersistence _persistence;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link NotificationLocalService}.
 *
 * @author Farouk
 * @see NotificationLocalService
 * @generated
 */
public class NotificationLocalServiceWrapper
	implements NotificationLocalService,
			   ServiceWrapper<NotificationLocalService> {

	public NotificationLocalServiceWrapper() {
		this(null);
	}

	public NotificationLocalServiceWrapper(
		NotificationLocalService notificationLocalService) {

		_notificationLocalService = notificationLocalService;
	}

	/**
	 * Custom helper: create one UNREAD notification now.
	 */
	@Override
	public gestion_de_pharmacie.model.Notification addNotification(
		long idUtilisateur, String type, String message) {

		return _notificationLocalService.addNotification(
			idUtilisateur, type, message);
	}

	/**
	 * Custom helper: create one UNREAD notification at a given time.
	 */
	@Override
	public gestion_de_pharmacie.model.Notification addNotification(
		long idUtilisateur, String type, String message, java.util.Date when) {

		return _notificationLocalService.addNotification(
			idUtilisateur, type, message, when);
	}

	/**
	 * Adds the notification to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notification the notification
	 * @return the notification that was added
	 */
	@Override
	public gestion_de_pharmacie.model.Notification addNotification(
		gestion_de_pharmacie.model.Notification notification) {

		return _notificationLocalService.addNotification(notification);
	}

	/**
	 * Create the same notification for all users having a role.
	 */
	@Override
	public void addNotificationForRole(
		String role, String type, String message) {

		_notificationLocalService.addNotificationForRole(role, type, message);
	}

	@Override
	public void addNotificationForRoleExceptUser(
		String role, String type, String message, long excludeUserId) {

		_notificationLocalService.addNotificationForRoleExceptUser(
			role, type, message, excludeUserId);
	}

	/**
	 * Legacy convenience: count unread via finder (if you prefer).
	 */
	@Override
	public int countUnread(long idUtilisateur) {
		return _notificationLocalService.countUnread(idUtilisateur);
	}

	/**
	 * Count unread for badge (dynamicQuery variant, with debugs).
	 */
	@Override
	public int countUnreadByUser(long userId) {
		return _notificationLocalService.countUnreadByUser(userId);
	}

	@Override
	public void createLowStockAlertsForMed(long idMedicament) {
		_notificationLocalService.createLowStockAlertsForMed(idMedicament);
	}

	/**
	 * Creates a new notification with the primary key. Does not add the notification to the database.
	 *
	 * @param idNotification the primary key for the new notification
	 * @return the new notification
	 */
	@Override
	public gestion_de_pharmacie.model.Notification createNotification(
		long idNotification) {

		return _notificationLocalService.createNotification(idNotification);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public int deleteAllForUser(long idUtilisateur) {
		return _notificationLocalService.deleteAllForUser(idUtilisateur);
	}

	@Override
	public int deleteAllNotifications() {
		return _notificationLocalService.deleteAllNotifications();
	}

	/**
	 * Deletes the notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification that was removed
	 * @throws PortalException if a notification with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Notification deleteNotification(
			long idNotification)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationLocalService.deleteNotification(idNotification);
	}

	/**
	 * Deletes the notification from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notification the notification
	 * @return the notification that was removed
	 */
	@Override
	public gestion_de_pharmacie.model.Notification deleteNotification(
		gestion_de_pharmacie.model.Notification notification) {

		return _notificationLocalService.deleteNotification(notification);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _notificationLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _notificationLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _notificationLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _notificationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _notificationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _notificationLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _notificationLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _notificationLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public gestion_de_pharmacie.model.Notification fetchNotification(
		long idNotification) {

		return _notificationLocalService.fetchNotification(idNotification);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _notificationLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<gestion_de_pharmacie.model.Notification>
		getByUserStatus(
			long userId, String statut, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<gestion_de_pharmacie.model.Notification> obc) {

		return _notificationLocalService.getByUserStatus(
			userId, statut, start, end, obc);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _notificationLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * List latest (any status) ordered by dateCreation desc.
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.Notification>
		getLatestByUser(long userId, int start, int end) {

		return _notificationLocalService.getLatestByUser(userId, start, end);
	}

	/**
	 * Returns the notification with the primary key.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification
	 * @throws PortalException if a notification with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Notification getNotification(
			long idNotification)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationLocalService.getNotification(idNotification);
	}

	/**
	 * Returns a range of all the notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @return the range of notifications
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.Notification>
		getNotifications(int start, int end) {

		return _notificationLocalService.getNotifications(start, end);
	}

	/**
	 * Returns the number of notifications.
	 *
	 * @return the number of notifications
	 */
	@Override
	public int getNotificationsCount() {
		return _notificationLocalService.getNotificationsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * List unread (paged).
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.Notification>
		getUnreadByUser(long idUtilisateur, int start, int end) {

		return _notificationLocalService.getUnreadByUser(
			idUtilisateur, start, end);
	}

	@Override
	public int markAllAsRead(long idUtilisateur) {
		return _notificationLocalService.markAllAsRead(idUtilisateur);
	}

	@Override
	public int markAllRead(long idUtilisateur) {
		return _notificationLocalService.markAllRead(idUtilisateur);
	}

	@Override
	public gestion_de_pharmacie.model.Notification markAsRead(
		long idNotification) {

		return _notificationLocalService.markAsRead(idNotification);
	}

	/**
	 * Triggered by VenteLocalServiceImpl after a sale is saved.
	 * If seller is ADMIN or PHARMACIEN, notify all other ADMINs.
	 */
	@Override
	public void notifyAdminsOfSale(
		long sellerUtilisateurId, gestion_de_pharmacie.model.Vente vente) {

		_notificationLocalService.notifyAdminsOfSale(
			sellerUtilisateurId, vente);
	}

	/**
	 * Updates the notification in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param notification the notification
	 * @return the notification that was updated
	 */
	@Override
	public gestion_de_pharmacie.model.Notification updateNotification(
		gestion_de_pharmacie.model.Notification notification) {

		return _notificationLocalService.updateNotification(notification);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _notificationLocalService.getBasePersistence();
	}

	@Override
	public NotificationLocalService getWrappedService() {
		return _notificationLocalService;
	}

	@Override
	public void setWrappedService(
		NotificationLocalService notificationLocalService) {

		_notificationLocalService = notificationLocalService;
	}

	private NotificationLocalService _notificationLocalService;

}
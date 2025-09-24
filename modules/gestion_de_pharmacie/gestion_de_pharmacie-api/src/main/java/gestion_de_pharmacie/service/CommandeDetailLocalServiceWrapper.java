/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link CommandeDetailLocalService}.
 *
 * @author Farouk
 * @see CommandeDetailLocalService
 * @generated
 */
public class CommandeDetailLocalServiceWrapper
	implements CommandeDetailLocalService,
			   ServiceWrapper<CommandeDetailLocalService> {

	public CommandeDetailLocalServiceWrapper() {
		this(null);
	}

	public CommandeDetailLocalServiceWrapper(
		CommandeDetailLocalService commandeDetailLocalService) {

		_commandeDetailLocalService = commandeDetailLocalService;
	}

	/**
	 * Adds the commande detail to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommandeDetailLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commandeDetail the commande detail
	 * @return the commande detail that was added
	 */
	@Override
	public gestion_de_pharmacie.model.CommandeDetail addCommandeDetail(
		gestion_de_pharmacie.model.CommandeDetail commandeDetail) {

		return _commandeDetailLocalService.addCommandeDetail(commandeDetail);
	}

	/**
	 * Creates a new commande detail with the primary key. Does not add the commande detail to the database.
	 *
	 * @param idDetail the primary key for the new commande detail
	 * @return the new commande detail
	 */
	@Override
	public gestion_de_pharmacie.model.CommandeDetail createCommandeDetail(
		long idDetail) {

		return _commandeDetailLocalService.createCommandeDetail(idDetail);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeDetailLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commande detail from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommandeDetailLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commandeDetail the commande detail
	 * @return the commande detail that was removed
	 */
	@Override
	public gestion_de_pharmacie.model.CommandeDetail deleteCommandeDetail(
		gestion_de_pharmacie.model.CommandeDetail commandeDetail) {

		return _commandeDetailLocalService.deleteCommandeDetail(commandeDetail);
	}

	/**
	 * Deletes the commande detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommandeDetailLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail that was removed
	 * @throws PortalException if a commande detail with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.CommandeDetail deleteCommandeDetail(
			long idDetail)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeDetailLocalService.deleteCommandeDetail(idDetail);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeDetailLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commandeDetailLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commandeDetailLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commandeDetailLocalService.dynamicQuery();
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

		return _commandeDetailLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.CommandeDetailModelImpl</code>.
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

		return _commandeDetailLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.CommandeDetailModelImpl</code>.
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

		return _commandeDetailLocalService.dynamicQuery(
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

		return _commandeDetailLocalService.dynamicQueryCount(dynamicQuery);
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

		return _commandeDetailLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public gestion_de_pharmacie.model.CommandeDetail fetchCommandeDetail(
		long idDetail) {

		return _commandeDetailLocalService.fetchCommandeDetail(idDetail);
	}

	@Override
	public java.util.List<gestion_de_pharmacie.model.CommandeDetail>
		findByCommandeId(long commandeId) {

		return _commandeDetailLocalService.findByCommandeId(commandeId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commandeDetailLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the commande detail with the primary key.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail
	 * @throws PortalException if a commande detail with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.CommandeDetail getCommandeDetail(
			long idDetail)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeDetailLocalService.getCommandeDetail(idDetail);
	}

	/**
	 * Returns a range of all the commande details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @return the range of commande details
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.CommandeDetail>
		getCommandeDetails(int start, int end) {

		return _commandeDetailLocalService.getCommandeDetails(start, end);
	}

	/**
	 * Returns the number of commande details.
	 *
	 * @return the number of commande details
	 */
	@Override
	public int getCommandeDetailsCount() {
		return _commandeDetailLocalService.getCommandeDetailsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commandeDetailLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public gestion_de_pharmacie.model.Medicament getMedicament(
		gestion_de_pharmacie.model.CommandeDetail commandeDetail) {

		return _commandeDetailLocalService.getMedicament(commandeDetail);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commandeDetailLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeDetailLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the commande detail in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommandeDetailLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commandeDetail the commande detail
	 * @return the commande detail that was updated
	 */
	@Override
	public gestion_de_pharmacie.model.CommandeDetail updateCommandeDetail(
		gestion_de_pharmacie.model.CommandeDetail commandeDetail) {

		return _commandeDetailLocalService.updateCommandeDetail(commandeDetail);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commandeDetailLocalService.getBasePersistence();
	}

	@Override
	public CommandeDetailLocalService getWrappedService() {
		return _commandeDetailLocalService;
	}

	@Override
	public void setWrappedService(
		CommandeDetailLocalService commandeDetailLocalService) {

		_commandeDetailLocalService = commandeDetailLocalService;
	}

	private CommandeDetailLocalService _commandeDetailLocalService;

}
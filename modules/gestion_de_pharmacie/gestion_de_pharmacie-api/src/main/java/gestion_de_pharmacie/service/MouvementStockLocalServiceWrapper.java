/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link MouvementStockLocalService}.
 *
 * @author Farouk
 * @see MouvementStockLocalService
 * @generated
 */
public class MouvementStockLocalServiceWrapper
	implements MouvementStockLocalService,
			   ServiceWrapper<MouvementStockLocalService> {

	public MouvementStockLocalServiceWrapper() {
		this(null);
	}

	public MouvementStockLocalServiceWrapper(
		MouvementStockLocalService mouvementStockLocalService) {

		_mouvementStockLocalService = mouvementStockLocalService;
	}

	/**
	 * Adds the mouvement stock to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MouvementStockLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mouvementStock the mouvement stock
	 * @return the mouvement stock that was added
	 */
	@Override
	public gestion_de_pharmacie.model.MouvementStock addMouvementStock(
		gestion_de_pharmacie.model.MouvementStock mouvementStock) {

		return _mouvementStockLocalService.addMouvementStock(mouvementStock);
	}

	/**
	 * Creates a new mouvement stock with the primary key. Does not add the mouvement stock to the database.
	 *
	 * @param idMouvement the primary key for the new mouvement stock
	 * @return the new mouvement stock
	 */
	@Override
	public gestion_de_pharmacie.model.MouvementStock createMouvementStock(
		long idMouvement) {

		return _mouvementStockLocalService.createMouvementStock(idMouvement);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mouvementStockLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the mouvement stock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MouvementStockLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock that was removed
	 * @throws PortalException if a mouvement stock with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.MouvementStock deleteMouvementStock(
			long idMouvement)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mouvementStockLocalService.deleteMouvementStock(idMouvement);
	}

	/**
	 * Deletes the mouvement stock from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MouvementStockLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mouvementStock the mouvement stock
	 * @return the mouvement stock that was removed
	 */
	@Override
	public gestion_de_pharmacie.model.MouvementStock deleteMouvementStock(
		gestion_de_pharmacie.model.MouvementStock mouvementStock) {

		return _mouvementStockLocalService.deleteMouvementStock(mouvementStock);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mouvementStockLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _mouvementStockLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _mouvementStockLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mouvementStockLocalService.dynamicQuery();
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

		return _mouvementStockLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.MouvementStockModelImpl</code>.
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

		return _mouvementStockLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.MouvementStockModelImpl</code>.
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

		return _mouvementStockLocalService.dynamicQuery(
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

		return _mouvementStockLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mouvementStockLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public gestion_de_pharmacie.model.MouvementStock fetchMouvementStock(
		long idMouvement) {

		return _mouvementStockLocalService.fetchMouvementStock(idMouvement);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mouvementStockLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mouvementStockLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the mouvement stock with the primary key.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock
	 * @throws PortalException if a mouvement stock with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.MouvementStock getMouvementStock(
			long idMouvement)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mouvementStockLocalService.getMouvementStock(idMouvement);
	}

	/**
	 * Returns a range of all the mouvement stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.MouvementStockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mouvement stocks
	 * @param end the upper bound of the range of mouvement stocks (not inclusive)
	 * @return the range of mouvement stocks
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.MouvementStock>
		getMouvementStocks(int start, int end) {

		return _mouvementStockLocalService.getMouvementStocks(start, end);
	}

	/**
	 * Returns the number of mouvement stocks.
	 *
	 * @return the number of mouvement stocks
	 */
	@Override
	public int getMouvementStocksCount() {
		return _mouvementStockLocalService.getMouvementStocksCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mouvementStockLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mouvementStockLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the mouvement stock in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MouvementStockLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mouvementStock the mouvement stock
	 * @return the mouvement stock that was updated
	 */
	@Override
	public gestion_de_pharmacie.model.MouvementStock updateMouvementStock(
		gestion_de_pharmacie.model.MouvementStock mouvementStock) {

		return _mouvementStockLocalService.updateMouvementStock(mouvementStock);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mouvementStockLocalService.getBasePersistence();
	}

	@Override
	public MouvementStockLocalService getWrappedService() {
		return _mouvementStockLocalService;
	}

	@Override
	public void setWrappedService(
		MouvementStockLocalService mouvementStockLocalService) {

		_mouvementStockLocalService = mouvementStockLocalService;
	}

	private MouvementStockLocalService _mouvementStockLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link VenteLocalService}.
 *
 * @author Farouk
 * @see VenteLocalService
 * @generated
 */
public class VenteLocalServiceWrapper
	implements ServiceWrapper<VenteLocalService>, VenteLocalService {

	public VenteLocalServiceWrapper() {
		this(null);
	}

	public VenteLocalServiceWrapper(VenteLocalService venteLocalService) {
		_venteLocalService = venteLocalService;
	}

	/**
	 * Adds the vente to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect VenteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param vente the vente
	 * @return the vente that was added
	 */
	@Override
	public gestion_de_pharmacie.model.Vente addVente(
		gestion_de_pharmacie.model.Vente vente) {

		return _venteLocalService.addVente(vente);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _venteLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new vente with the primary key. Does not add the vente to the database.
	 *
	 * @param idVente the primary key for the new vente
	 * @return the new vente
	 */
	@Override
	public gestion_de_pharmacie.model.Vente createVente(long idVente) {
		return _venteLocalService.createVente(idVente);
	}

	@Override
	public gestion_de_pharmacie.model.Vente createVente(
			long idUtilisateur, long[] medicamentIds, int[] quantities)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _venteLocalService.createVente(
			idUtilisateur, medicamentIds, quantities);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _venteLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the vente with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect VenteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente that was removed
	 * @throws PortalException if a vente with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Vente deleteVente(long idVente)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _venteLocalService.deleteVente(idVente);
	}

	/**
	 * Deletes the vente from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect VenteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param vente the vente
	 * @return the vente that was removed
	 */
	@Override
	public gestion_de_pharmacie.model.Vente deleteVente(
		gestion_de_pharmacie.model.Vente vente) {

		return _venteLocalService.deleteVente(vente);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _venteLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _venteLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _venteLocalService.dynamicQuery();
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

		return _venteLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.VenteModelImpl</code>.
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

		return _venteLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.VenteModelImpl</code>.
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

		return _venteLocalService.dynamicQuery(
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

		return _venteLocalService.dynamicQueryCount(dynamicQuery);
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

		return _venteLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public gestion_de_pharmacie.model.Vente fetchVente(long idVente) {
		return _venteLocalService.fetchVente(idVente);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _venteLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _venteLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _venteLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _venteLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the vente with the primary key.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente
	 * @throws PortalException if a vente with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Vente getVente(long idVente)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _venteLocalService.getVente(idVente);
	}

	/**
	 * Returns a range of all the ventes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.VenteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @return the range of ventes
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.Vente> getVentes(
		int start, int end) {

		return _venteLocalService.getVentes(start, end);
	}

	/**
	 * Returns the number of ventes.
	 *
	 * @return the number of ventes
	 */
	@Override
	public int getVentesCount() {
		return _venteLocalService.getVentesCount();
	}

	/**
	 * Updates the vente in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect VenteLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param vente the vente
	 * @return the vente that was updated
	 */
	@Override
	public gestion_de_pharmacie.model.Vente updateVente(
		gestion_de_pharmacie.model.Vente vente) {

		return _venteLocalService.updateVente(vente);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _venteLocalService.getBasePersistence();
	}

	@Override
	public VenteLocalService getWrappedService() {
		return _venteLocalService;
	}

	@Override
	public void setWrappedService(VenteLocalService venteLocalService) {
		_venteLocalService = venteLocalService;
	}

	private VenteLocalService _venteLocalService;

}
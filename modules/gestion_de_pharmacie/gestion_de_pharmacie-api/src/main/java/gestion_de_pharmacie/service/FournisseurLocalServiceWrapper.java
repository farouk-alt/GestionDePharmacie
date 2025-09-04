/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FournisseurLocalService}.
 *
 * @author Farouk
 * @see FournisseurLocalService
 * @generated
 */
public class FournisseurLocalServiceWrapper
	implements FournisseurLocalService,
			   ServiceWrapper<FournisseurLocalService> {

	public FournisseurLocalServiceWrapper() {
		this(null);
	}

	public FournisseurLocalServiceWrapper(
		FournisseurLocalService fournisseurLocalService) {

		_fournisseurLocalService = fournisseurLocalService;
	}

	/**
	 * Adds the fournisseur to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FournisseurLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fournisseur the fournisseur
	 * @return the fournisseur that was added
	 */
	@Override
	public gestion_de_pharmacie.model.Fournisseur addFournisseur(
		gestion_de_pharmacie.model.Fournisseur fournisseur) {

		return _fournisseurLocalService.addFournisseur(fournisseur);
	}

	/**
	 * Creates a new fournisseur with the primary key. Does not add the fournisseur to the database.
	 *
	 * @param idFournisseur the primary key for the new fournisseur
	 * @return the new fournisseur
	 */
	@Override
	public gestion_de_pharmacie.model.Fournisseur createFournisseur(
		long idFournisseur) {

		return _fournisseurLocalService.createFournisseur(idFournisseur);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fournisseurLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the fournisseur from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FournisseurLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fournisseur the fournisseur
	 * @return the fournisseur that was removed
	 */
	@Override
	public gestion_de_pharmacie.model.Fournisseur deleteFournisseur(
		gestion_de_pharmacie.model.Fournisseur fournisseur) {

		return _fournisseurLocalService.deleteFournisseur(fournisseur);
	}

	/**
	 * Deletes the fournisseur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FournisseurLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param idFournisseur the primary key of the fournisseur
	 * @return the fournisseur that was removed
	 * @throws PortalException if a fournisseur with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Fournisseur deleteFournisseur(
			long idFournisseur)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fournisseurLocalService.deleteFournisseur(idFournisseur);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fournisseurLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _fournisseurLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _fournisseurLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fournisseurLocalService.dynamicQuery();
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

		return _fournisseurLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.FournisseurModelImpl</code>.
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

		return _fournisseurLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.FournisseurModelImpl</code>.
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

		return _fournisseurLocalService.dynamicQuery(
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

		return _fournisseurLocalService.dynamicQueryCount(dynamicQuery);
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

		return _fournisseurLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public gestion_de_pharmacie.model.Fournisseur fetchFournisseur(
		long idFournisseur) {

		return _fournisseurLocalService.fetchFournisseur(idFournisseur);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _fournisseurLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the fournisseur with the primary key.
	 *
	 * @param idFournisseur the primary key of the fournisseur
	 * @return the fournisseur
	 * @throws PortalException if a fournisseur with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Fournisseur getFournisseur(
			long idFournisseur)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fournisseurLocalService.getFournisseur(idFournisseur);
	}

	/**
	 * Returns a range of all the fournisseurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.FournisseurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fournisseurs
	 * @param end the upper bound of the range of fournisseurs (not inclusive)
	 * @return the range of fournisseurs
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.Fournisseur>
		getFournisseurs(int start, int end) {

		return _fournisseurLocalService.getFournisseurs(start, end);
	}

	/**
	 * Returns the number of fournisseurs.
	 *
	 * @return the number of fournisseurs
	 */
	@Override
	public int getFournisseursCount() {
		return _fournisseurLocalService.getFournisseursCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _fournisseurLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fournisseurLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _fournisseurLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the fournisseur in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FournisseurLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param fournisseur the fournisseur
	 * @return the fournisseur that was updated
	 */
	@Override
	public gestion_de_pharmacie.model.Fournisseur updateFournisseur(
		gestion_de_pharmacie.model.Fournisseur fournisseur) {

		return _fournisseurLocalService.updateFournisseur(fournisseur);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _fournisseurLocalService.getBasePersistence();
	}

	@Override
	public FournisseurLocalService getWrappedService() {
		return _fournisseurLocalService;
	}

	@Override
	public void setWrappedService(
		FournisseurLocalService fournisseurLocalService) {

		_fournisseurLocalService = fournisseurLocalService;
	}

	private FournisseurLocalService _fournisseurLocalService;

}
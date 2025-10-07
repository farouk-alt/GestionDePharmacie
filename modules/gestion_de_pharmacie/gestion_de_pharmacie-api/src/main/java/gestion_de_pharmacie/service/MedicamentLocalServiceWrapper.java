/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link MedicamentLocalService}.
 *
 * @author Farouk
 * @see MedicamentLocalService
 * @generated
 */
public class MedicamentLocalServiceWrapper
	implements MedicamentLocalService, ServiceWrapper<MedicamentLocalService> {

	public MedicamentLocalServiceWrapper() {
		this(null);
	}

	public MedicamentLocalServiceWrapper(
		MedicamentLocalService medicamentLocalService) {

		_medicamentLocalService = medicamentLocalService;
	}

	/**
	 * Adds the medicament to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MedicamentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param medicament the medicament
	 * @return the medicament that was added
	 */
	@Override
	public gestion_de_pharmacie.model.Medicament addMedicament(
		gestion_de_pharmacie.model.Medicament medicament) {

		return _medicamentLocalService.addMedicament(medicament);
	}

	/**
	 * Creates a new medicament with the primary key. Does not add the medicament to the database.
	 *
	 * @param idMedicament the primary key for the new medicament
	 * @return the new medicament
	 */
	@Override
	public gestion_de_pharmacie.model.Medicament createMedicament(
		long idMedicament) {

		return _medicamentLocalService.createMedicament(idMedicament);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _medicamentLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the medicament with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MedicamentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament that was removed
	 * @throws PortalException if a medicament with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Medicament deleteMedicament(
			long idMedicament)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _medicamentLocalService.deleteMedicament(idMedicament);
	}

	/**
	 * Deletes the medicament from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MedicamentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param medicament the medicament
	 * @return the medicament that was removed
	 */
	@Override
	public gestion_de_pharmacie.model.Medicament deleteMedicament(
		gestion_de_pharmacie.model.Medicament medicament) {

		return _medicamentLocalService.deleteMedicament(medicament);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _medicamentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _medicamentLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _medicamentLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _medicamentLocalService.dynamicQuery();
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

		return _medicamentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.MedicamentModelImpl</code>.
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

		return _medicamentLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.MedicamentModelImpl</code>.
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

		return _medicamentLocalService.dynamicQuery(
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

		return _medicamentLocalService.dynamicQueryCount(dynamicQuery);
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

		return _medicamentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public gestion_de_pharmacie.model.Medicament fetchMedicament(
		long idMedicament) {

		return _medicamentLocalService.fetchMedicament(idMedicament);
	}

	@Override
	public java.util.List<gestion_de_pharmacie.model.Medicament> findByCode(
		String codeBarre) {

		return _medicamentLocalService.findByCode(codeBarre);
	}

	@Override
	public java.util.List<gestion_de_pharmacie.model.Medicament>
		findByCodeBarre(String codeBarre) {

		return _medicamentLocalService.findByCodeBarre(codeBarre);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _medicamentLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _medicamentLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the medicament with the primary key.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament
	 * @throws PortalException if a medicament with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Medicament getMedicament(
			long idMedicament)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _medicamentLocalService.getMedicament(idMedicament);
	}

	/**
	 * Returns a range of all the medicaments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of medicaments
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.Medicament> getMedicaments(
		int start, int end) {

		return _medicamentLocalService.getMedicaments(start, end);
	}

	/**
	 * Returns the number of medicaments.
	 *
	 * @return the number of medicaments
	 */
	@Override
	public int getMedicamentsCount() {
		return _medicamentLocalService.getMedicamentsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _medicamentLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _medicamentLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the medicament in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MedicamentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param medicament the medicament
	 * @return the medicament that was updated
	 */
	@Override
	public gestion_de_pharmacie.model.Medicament updateMedicament(
		gestion_de_pharmacie.model.Medicament medicament) {

		return _medicamentLocalService.updateMedicament(medicament);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _medicamentLocalService.getBasePersistence();
	}

	@Override
	public MedicamentLocalService getWrappedService() {
		return _medicamentLocalService;
	}

	@Override
	public void setWrappedService(
		MedicamentLocalService medicamentLocalService) {

		_medicamentLocalService = medicamentLocalService;
	}

	private MedicamentLocalService _medicamentLocalService;

}
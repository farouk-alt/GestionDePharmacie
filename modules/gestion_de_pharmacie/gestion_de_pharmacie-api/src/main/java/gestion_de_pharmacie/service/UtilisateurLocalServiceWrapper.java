/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link UtilisateurLocalService}.
 *
 * @author Farouk
 * @see UtilisateurLocalService
 * @generated
 */
public class UtilisateurLocalServiceWrapper
	implements ServiceWrapper<UtilisateurLocalService>,
			   UtilisateurLocalService {

	public UtilisateurLocalServiceWrapper() {
		this(null);
	}

	public UtilisateurLocalServiceWrapper(
		UtilisateurLocalService utilisateurLocalService) {

		_utilisateurLocalService = utilisateurLocalService;
	}

	@Override
	public gestion_de_pharmacie.model.Utilisateur addUtilisateur(
			long creatorUserId, long companyId, String email, String motDePasse,
			String prenom, String nom, String role,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _utilisateurLocalService.addUtilisateur(
			creatorUserId, companyId, email, motDePasse, prenom, nom, role,
			serviceContext);
	}

	/**
	 * Adds the utilisateur to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UtilisateurLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param utilisateur the utilisateur
	 * @return the utilisateur that was added
	 */
	@Override
	public gestion_de_pharmacie.model.Utilisateur addUtilisateur(
		gestion_de_pharmacie.model.Utilisateur utilisateur) {

		return _utilisateurLocalService.addUtilisateur(utilisateur);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _utilisateurLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new utilisateur with the primary key. Does not add the utilisateur to the database.
	 *
	 * @param idUtilisateur the primary key for the new utilisateur
	 * @return the new utilisateur
	 */
	@Override
	public gestion_de_pharmacie.model.Utilisateur createUtilisateur(
		long idUtilisateur) {

		return _utilisateurLocalService.createUtilisateur(idUtilisateur);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _utilisateurLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the utilisateur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UtilisateurLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur that was removed
	 * @throws PortalException if a utilisateur with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Utilisateur deleteUtilisateur(
			long idUtilisateur)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _utilisateurLocalService.deleteUtilisateur(idUtilisateur);
	}

	/**
	 * Deletes the utilisateur from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UtilisateurLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param utilisateur the utilisateur
	 * @return the utilisateur that was removed
	 */
	@Override
	public gestion_de_pharmacie.model.Utilisateur deleteUtilisateur(
		gestion_de_pharmacie.model.Utilisateur utilisateur) {

		return _utilisateurLocalService.deleteUtilisateur(utilisateur);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _utilisateurLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _utilisateurLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _utilisateurLocalService.dynamicQuery();
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

		return _utilisateurLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.UtilisateurModelImpl</code>.
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

		return _utilisateurLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.UtilisateurModelImpl</code>.
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

		return _utilisateurLocalService.dynamicQuery(
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

		return _utilisateurLocalService.dynamicQueryCount(dynamicQuery);
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

		return _utilisateurLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public gestion_de_pharmacie.model.Utilisateur fetchUtilisateur(
		long idUtilisateur) {

		return _utilisateurLocalService.fetchUtilisateur(idUtilisateur);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _utilisateurLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _utilisateurLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _utilisateurLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _utilisateurLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the utilisateur with the primary key.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur
	 * @throws PortalException if a utilisateur with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Utilisateur getUtilisateur(
			long idUtilisateur)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _utilisateurLocalService.getUtilisateur(idUtilisateur);
	}

	@Override
	public gestion_de_pharmacie.model.Utilisateur getUtilisateurByEmail(
		String email) {

		return _utilisateurLocalService.getUtilisateurByEmail(email);
	}

	/**
	 * Returns a range of all the utilisateurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @return the range of utilisateurs
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.Utilisateur>
		getUtilisateurs(int start, int end) {

		return _utilisateurLocalService.getUtilisateurs(start, end);
	}

	/**
	 * Returns the number of utilisateurs.
	 *
	 * @return the number of utilisateurs
	 */
	@Override
	public int getUtilisateursCount() {
		return _utilisateurLocalService.getUtilisateursCount();
	}

	/**
	 * Updates the utilisateur in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect UtilisateurLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param utilisateur the utilisateur
	 * @return the utilisateur that was updated
	 */
	@Override
	public gestion_de_pharmacie.model.Utilisateur updateUtilisateur(
		gestion_de_pharmacie.model.Utilisateur utilisateur) {

		return _utilisateurLocalService.updateUtilisateur(utilisateur);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _utilisateurLocalService.getBasePersistence();
	}

	@Override
	public UtilisateurLocalService getWrappedService() {
		return _utilisateurLocalService;
	}

	@Override
	public void setWrappedService(
		UtilisateurLocalService utilisateurLocalService) {

		_utilisateurLocalService = utilisateurLocalService;
	}

	private UtilisateurLocalService _utilisateurLocalService;

}
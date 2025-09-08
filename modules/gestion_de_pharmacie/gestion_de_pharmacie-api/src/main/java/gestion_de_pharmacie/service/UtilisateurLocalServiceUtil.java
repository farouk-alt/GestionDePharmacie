/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.Utilisateur;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for Utilisateur. This utility wraps
 * <code>gestion_de_pharmacie.service.impl.UtilisateurLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Farouk
 * @see UtilisateurLocalService
 * @generated
 */
public class UtilisateurLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>gestion_de_pharmacie.service.impl.UtilisateurLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Utilisateur addUtilisateur(
			long creatorUserId, long companyId, String email, String motDePasse,
			String prenom, String nom, String role,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addUtilisateur(
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
	public static Utilisateur addUtilisateur(Utilisateur utilisateur) {
		return getService().addUtilisateur(utilisateur);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new utilisateur with the primary key. Does not add the utilisateur to the database.
	 *
	 * @param idUtilisateur the primary key for the new utilisateur
	 * @return the new utilisateur
	 */
	public static Utilisateur createUtilisateur(long idUtilisateur) {
		return getService().createUtilisateur(idUtilisateur);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	public static Utilisateur deleteUtilisateur(long idUtilisateur)
		throws PortalException {

		return getService().deleteUtilisateur(idUtilisateur);
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
	public static Utilisateur deleteUtilisateur(Utilisateur utilisateur) {
		return getService().deleteUtilisateur(utilisateur);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static Utilisateur fetchUtilisateur(long idUtilisateur) {
		return getService().fetchUtilisateur(idUtilisateur);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the utilisateur with the primary key.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur
	 * @throws PortalException if a utilisateur with the primary key could not be found
	 */
	public static Utilisateur getUtilisateur(long idUtilisateur)
		throws PortalException {

		return getService().getUtilisateur(idUtilisateur);
	}

	public static Utilisateur getUtilisateurByEmail(String email) {
		return getService().getUtilisateurByEmail(email);
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
	public static List<Utilisateur> getUtilisateurs(int start, int end) {
		return getService().getUtilisateurs(start, end);
	}

	/**
	 * Returns the number of utilisateurs.
	 *
	 * @return the number of utilisateurs
	 */
	public static int getUtilisateursCount() {
		return getService().getUtilisateursCount();
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
	public static Utilisateur updateUtilisateur(Utilisateur utilisateur) {
		return getService().updateUtilisateur(utilisateur);
	}

	public static UtilisateurLocalService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<UtilisateurLocalService> _serviceSnapshot =
		new Snapshot<>(
			UtilisateurLocalServiceUtil.class, UtilisateurLocalService.class);

}
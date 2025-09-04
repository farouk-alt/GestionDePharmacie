/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.Utilisateur;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the utilisateur service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.UtilisateurPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see UtilisateurPersistence
 * @generated
 */
public class UtilisateurUtil {

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
	public static void clearCache(Utilisateur utilisateur) {
		getPersistence().clearCache(utilisateur);
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
	public static Map<Serializable, Utilisateur> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Utilisateur> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Utilisateur> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Utilisateur> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Utilisateur> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Utilisateur update(Utilisateur utilisateur) {
		return getPersistence().update(utilisateur);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Utilisateur update(
		Utilisateur utilisateur, ServiceContext serviceContext) {

		return getPersistence().update(utilisateur, serviceContext);
	}

	/**
	 * Returns the utilisateur where email = &#63; or throws a <code>NoSuchUtilisateurException</code> if it could not be found.
	 *
	 * @param email the email
	 * @return the matching utilisateur
	 * @throws NoSuchUtilisateurException if a matching utilisateur could not be found
	 */
	public static Utilisateur findByEmail(String email)
		throws gestion_de_pharmacie.exception.NoSuchUtilisateurException {

		return getPersistence().findByEmail(email);
	}

	/**
	 * Returns the utilisateur where email = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param email the email
	 * @return the matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	public static Utilisateur fetchByEmail(String email) {
		return getPersistence().fetchByEmail(email);
	}

	/**
	 * Returns the utilisateur where email = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param email the email
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	public static Utilisateur fetchByEmail(
		String email, boolean useFinderCache) {

		return getPersistence().fetchByEmail(email, useFinderCache);
	}

	/**
	 * Removes the utilisateur where email = &#63; from the database.
	 *
	 * @param email the email
	 * @return the utilisateur that was removed
	 */
	public static Utilisateur removeByEmail(String email)
		throws gestion_de_pharmacie.exception.NoSuchUtilisateurException {

		return getPersistence().removeByEmail(email);
	}

	/**
	 * Returns the number of utilisateurs where email = &#63;.
	 *
	 * @param email the email
	 * @return the number of matching utilisateurs
	 */
	public static int countByEmail(String email) {
		return getPersistence().countByEmail(email);
	}

	/**
	 * Caches the utilisateur in the entity cache if it is enabled.
	 *
	 * @param utilisateur the utilisateur
	 */
	public static void cacheResult(Utilisateur utilisateur) {
		getPersistence().cacheResult(utilisateur);
	}

	/**
	 * Caches the utilisateurs in the entity cache if it is enabled.
	 *
	 * @param utilisateurs the utilisateurs
	 */
	public static void cacheResult(List<Utilisateur> utilisateurs) {
		getPersistence().cacheResult(utilisateurs);
	}

	/**
	 * Creates a new utilisateur with the primary key. Does not add the utilisateur to the database.
	 *
	 * @param idUtilisateur the primary key for the new utilisateur
	 * @return the new utilisateur
	 */
	public static Utilisateur create(long idUtilisateur) {
		return getPersistence().create(idUtilisateur);
	}

	/**
	 * Removes the utilisateur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur that was removed
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	public static Utilisateur remove(long idUtilisateur)
		throws gestion_de_pharmacie.exception.NoSuchUtilisateurException {

		return getPersistence().remove(idUtilisateur);
	}

	public static Utilisateur updateImpl(Utilisateur utilisateur) {
		return getPersistence().updateImpl(utilisateur);
	}

	/**
	 * Returns the utilisateur with the primary key or throws a <code>NoSuchUtilisateurException</code> if it could not be found.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	public static Utilisateur findByPrimaryKey(long idUtilisateur)
		throws gestion_de_pharmacie.exception.NoSuchUtilisateurException {

		return getPersistence().findByPrimaryKey(idUtilisateur);
	}

	/**
	 * Returns the utilisateur with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur, or <code>null</code> if a utilisateur with the primary key could not be found
	 */
	public static Utilisateur fetchByPrimaryKey(long idUtilisateur) {
		return getPersistence().fetchByPrimaryKey(idUtilisateur);
	}

	/**
	 * Returns all the utilisateurs.
	 *
	 * @return the utilisateurs
	 */
	public static List<Utilisateur> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the utilisateurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @return the range of utilisateurs
	 */
	public static List<Utilisateur> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the utilisateurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of utilisateurs
	 */
	public static List<Utilisateur> findAll(
		int start, int end, OrderByComparator<Utilisateur> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the utilisateurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of utilisateurs
	 */
	public static List<Utilisateur> findAll(
		int start, int end, OrderByComparator<Utilisateur> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the utilisateurs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of utilisateurs.
	 *
	 * @return the number of utilisateurs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static UtilisateurPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(UtilisateurPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile UtilisateurPersistence _persistence;

}
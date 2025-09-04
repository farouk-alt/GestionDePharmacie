/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.Fournisseur;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the fournisseur service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.FournisseurPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see FournisseurPersistence
 * @generated
 */
public class FournisseurUtil {

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
	public static void clearCache(Fournisseur fournisseur) {
		getPersistence().clearCache(fournisseur);
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
	public static Map<Serializable, Fournisseur> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Fournisseur> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Fournisseur> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Fournisseur> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Fournisseur> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Fournisseur update(Fournisseur fournisseur) {
		return getPersistence().update(fournisseur);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Fournisseur update(
		Fournisseur fournisseur, ServiceContext serviceContext) {

		return getPersistence().update(fournisseur, serviceContext);
	}

	/**
	 * Caches the fournisseur in the entity cache if it is enabled.
	 *
	 * @param fournisseur the fournisseur
	 */
	public static void cacheResult(Fournisseur fournisseur) {
		getPersistence().cacheResult(fournisseur);
	}

	/**
	 * Caches the fournisseurs in the entity cache if it is enabled.
	 *
	 * @param fournisseurs the fournisseurs
	 */
	public static void cacheResult(List<Fournisseur> fournisseurs) {
		getPersistence().cacheResult(fournisseurs);
	}

	/**
	 * Creates a new fournisseur with the primary key. Does not add the fournisseur to the database.
	 *
	 * @param idFournisseur the primary key for the new fournisseur
	 * @return the new fournisseur
	 */
	public static Fournisseur create(long idFournisseur) {
		return getPersistence().create(idFournisseur);
	}

	/**
	 * Removes the fournisseur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idFournisseur the primary key of the fournisseur
	 * @return the fournisseur that was removed
	 * @throws NoSuchFournisseurException if a fournisseur with the primary key could not be found
	 */
	public static Fournisseur remove(long idFournisseur)
		throws gestion_de_pharmacie.exception.NoSuchFournisseurException {

		return getPersistence().remove(idFournisseur);
	}

	public static Fournisseur updateImpl(Fournisseur fournisseur) {
		return getPersistence().updateImpl(fournisseur);
	}

	/**
	 * Returns the fournisseur with the primary key or throws a <code>NoSuchFournisseurException</code> if it could not be found.
	 *
	 * @param idFournisseur the primary key of the fournisseur
	 * @return the fournisseur
	 * @throws NoSuchFournisseurException if a fournisseur with the primary key could not be found
	 */
	public static Fournisseur findByPrimaryKey(long idFournisseur)
		throws gestion_de_pharmacie.exception.NoSuchFournisseurException {

		return getPersistence().findByPrimaryKey(idFournisseur);
	}

	/**
	 * Returns the fournisseur with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idFournisseur the primary key of the fournisseur
	 * @return the fournisseur, or <code>null</code> if a fournisseur with the primary key could not be found
	 */
	public static Fournisseur fetchByPrimaryKey(long idFournisseur) {
		return getPersistence().fetchByPrimaryKey(idFournisseur);
	}

	/**
	 * Returns all the fournisseurs.
	 *
	 * @return the fournisseurs
	 */
	public static List<Fournisseur> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the fournisseurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FournisseurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fournisseurs
	 * @param end the upper bound of the range of fournisseurs (not inclusive)
	 * @return the range of fournisseurs
	 */
	public static List<Fournisseur> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the fournisseurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FournisseurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fournisseurs
	 * @param end the upper bound of the range of fournisseurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fournisseurs
	 */
	public static List<Fournisseur> findAll(
		int start, int end, OrderByComparator<Fournisseur> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fournisseurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FournisseurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fournisseurs
	 * @param end the upper bound of the range of fournisseurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fournisseurs
	 */
	public static List<Fournisseur> findAll(
		int start, int end, OrderByComparator<Fournisseur> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the fournisseurs from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of fournisseurs.
	 *
	 * @return the number of fournisseurs
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FournisseurPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(FournisseurPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile FournisseurPersistence _persistence;

}
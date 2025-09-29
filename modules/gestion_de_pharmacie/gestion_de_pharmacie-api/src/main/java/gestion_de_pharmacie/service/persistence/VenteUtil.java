/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.Vente;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the vente service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.VentePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see VentePersistence
 * @generated
 */
public class VenteUtil {

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
	public static void clearCache(Vente vente) {
		getPersistence().clearCache(vente);
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
	public static Map<Serializable, Vente> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Vente> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Vente> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Vente> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Vente> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Vente update(Vente vente) {
		return getPersistence().update(vente);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Vente update(Vente vente, ServiceContext serviceContext) {
		return getPersistence().update(vente, serviceContext);
	}

	/**
	 * Returns all the ventes where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the matching ventes
	 */
	public static List<Vente> findByUser(long idUtilisateur) {
		return getPersistence().findByUser(idUtilisateur);
	}

	/**
	 * Returns a range of all the ventes where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @return the range of matching ventes
	 */
	public static List<Vente> findByUser(
		long idUtilisateur, int start, int end) {

		return getPersistence().findByUser(idUtilisateur, start, end);
	}

	/**
	 * Returns an ordered range of all the ventes where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ventes
	 */
	public static List<Vente> findByUser(
		long idUtilisateur, int start, int end,
		OrderByComparator<Vente> orderByComparator) {

		return getPersistence().findByUser(
			idUtilisateur, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ventes where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ventes
	 */
	public static List<Vente> findByUser(
		long idUtilisateur, int start, int end,
		OrderByComparator<Vente> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUser(
			idUtilisateur, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	public static Vente findByUser_First(
			long idUtilisateur, OrderByComparator<Vente> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteException {

		return getPersistence().findByUser_First(
			idUtilisateur, orderByComparator);
	}

	/**
	 * Returns the first vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente, or <code>null</code> if a matching vente could not be found
	 */
	public static Vente fetchByUser_First(
		long idUtilisateur, OrderByComparator<Vente> orderByComparator) {

		return getPersistence().fetchByUser_First(
			idUtilisateur, orderByComparator);
	}

	/**
	 * Returns the last vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	public static Vente findByUser_Last(
			long idUtilisateur, OrderByComparator<Vente> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteException {

		return getPersistence().findByUser_Last(
			idUtilisateur, orderByComparator);
	}

	/**
	 * Returns the last vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente, or <code>null</code> if a matching vente could not be found
	 */
	public static Vente fetchByUser_Last(
		long idUtilisateur, OrderByComparator<Vente> orderByComparator) {

		return getPersistence().fetchByUser_Last(
			idUtilisateur, orderByComparator);
	}

	/**
	 * Returns the ventes before and after the current vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idVente the primary key of the current vente
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	public static Vente[] findByUser_PrevAndNext(
			long idVente, long idUtilisateur,
			OrderByComparator<Vente> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteException {

		return getPersistence().findByUser_PrevAndNext(
			idVente, idUtilisateur, orderByComparator);
	}

	/**
	 * Removes all the ventes where idUtilisateur = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 */
	public static void removeByUser(long idUtilisateur) {
		getPersistence().removeByUser(idUtilisateur);
	}

	/**
	 * Returns the number of ventes where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the number of matching ventes
	 */
	public static int countByUser(long idUtilisateur) {
		return getPersistence().countByUser(idUtilisateur);
	}

	/**
	 * Returns all the ventes where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @return the matching ventes
	 */
	public static List<Vente> findByDate(Date dateVente) {
		return getPersistence().findByDate(dateVente);
	}

	/**
	 * Returns a range of all the ventes where dateVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param dateVente the date vente
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @return the range of matching ventes
	 */
	public static List<Vente> findByDate(Date dateVente, int start, int end) {
		return getPersistence().findByDate(dateVente, start, end);
	}

	/**
	 * Returns an ordered range of all the ventes where dateVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param dateVente the date vente
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ventes
	 */
	public static List<Vente> findByDate(
		Date dateVente, int start, int end,
		OrderByComparator<Vente> orderByComparator) {

		return getPersistence().findByDate(
			dateVente, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ventes where dateVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param dateVente the date vente
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ventes
	 */
	public static List<Vente> findByDate(
		Date dateVente, int start, int end,
		OrderByComparator<Vente> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByDate(
			dateVente, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	public static Vente findByDate_First(
			Date dateVente, OrderByComparator<Vente> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteException {

		return getPersistence().findByDate_First(dateVente, orderByComparator);
	}

	/**
	 * Returns the first vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente, or <code>null</code> if a matching vente could not be found
	 */
	public static Vente fetchByDate_First(
		Date dateVente, OrderByComparator<Vente> orderByComparator) {

		return getPersistence().fetchByDate_First(dateVente, orderByComparator);
	}

	/**
	 * Returns the last vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	public static Vente findByDate_Last(
			Date dateVente, OrderByComparator<Vente> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteException {

		return getPersistence().findByDate_Last(dateVente, orderByComparator);
	}

	/**
	 * Returns the last vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente, or <code>null</code> if a matching vente could not be found
	 */
	public static Vente fetchByDate_Last(
		Date dateVente, OrderByComparator<Vente> orderByComparator) {

		return getPersistence().fetchByDate_Last(dateVente, orderByComparator);
	}

	/**
	 * Returns the ventes before and after the current vente in the ordered set where dateVente = &#63;.
	 *
	 * @param idVente the primary key of the current vente
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	public static Vente[] findByDate_PrevAndNext(
			long idVente, Date dateVente,
			OrderByComparator<Vente> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteException {

		return getPersistence().findByDate_PrevAndNext(
			idVente, dateVente, orderByComparator);
	}

	/**
	 * Removes all the ventes where dateVente = &#63; from the database.
	 *
	 * @param dateVente the date vente
	 */
	public static void removeByDate(Date dateVente) {
		getPersistence().removeByDate(dateVente);
	}

	/**
	 * Returns the number of ventes where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @return the number of matching ventes
	 */
	public static int countByDate(Date dateVente) {
		return getPersistence().countByDate(dateVente);
	}

	/**
	 * Caches the vente in the entity cache if it is enabled.
	 *
	 * @param vente the vente
	 */
	public static void cacheResult(Vente vente) {
		getPersistence().cacheResult(vente);
	}

	/**
	 * Caches the ventes in the entity cache if it is enabled.
	 *
	 * @param ventes the ventes
	 */
	public static void cacheResult(List<Vente> ventes) {
		getPersistence().cacheResult(ventes);
	}

	/**
	 * Creates a new vente with the primary key. Does not add the vente to the database.
	 *
	 * @param idVente the primary key for the new vente
	 * @return the new vente
	 */
	public static Vente create(long idVente) {
		return getPersistence().create(idVente);
	}

	/**
	 * Removes the vente with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente that was removed
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	public static Vente remove(long idVente)
		throws gestion_de_pharmacie.exception.NoSuchVenteException {

		return getPersistence().remove(idVente);
	}

	public static Vente updateImpl(Vente vente) {
		return getPersistence().updateImpl(vente);
	}

	/**
	 * Returns the vente with the primary key or throws a <code>NoSuchVenteException</code> if it could not be found.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	public static Vente findByPrimaryKey(long idVente)
		throws gestion_de_pharmacie.exception.NoSuchVenteException {

		return getPersistence().findByPrimaryKey(idVente);
	}

	/**
	 * Returns the vente with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente, or <code>null</code> if a vente with the primary key could not be found
	 */
	public static Vente fetchByPrimaryKey(long idVente) {
		return getPersistence().fetchByPrimaryKey(idVente);
	}

	/**
	 * Returns all the ventes.
	 *
	 * @return the ventes
	 */
	public static List<Vente> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ventes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @return the range of ventes
	 */
	public static List<Vente> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ventes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ventes
	 */
	public static List<Vente> findAll(
		int start, int end, OrderByComparator<Vente> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ventes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ventes
	 */
	public static List<Vente> findAll(
		int start, int end, OrderByComparator<Vente> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ventes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ventes.
	 *
	 * @return the number of ventes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static VentePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(VentePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile VentePersistence _persistence;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.VenteDetail;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the vente detail service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.VenteDetailPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see VenteDetailPersistence
 * @generated
 */
public class VenteDetailUtil {

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
	public static void clearCache(VenteDetail venteDetail) {
		getPersistence().clearCache(venteDetail);
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
	public static Map<Serializable, VenteDetail> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<VenteDetail> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<VenteDetail> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<VenteDetail> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<VenteDetail> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static VenteDetail update(VenteDetail venteDetail) {
		return getPersistence().update(venteDetail);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static VenteDetail update(
		VenteDetail venteDetail, ServiceContext serviceContext) {

		return getPersistence().update(venteDetail, serviceContext);
	}

	/**
	 * Returns all the vente details where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @return the matching vente details
	 */
	public static List<VenteDetail> findByVente(long idVente) {
		return getPersistence().findByVente(idVente);
	}

	/**
	 * Returns a range of all the vente details where idVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idVente the id vente
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @return the range of matching vente details
	 */
	public static List<VenteDetail> findByVente(
		long idVente, int start, int end) {

		return getPersistence().findByVente(idVente, start, end);
	}

	/**
	 * Returns an ordered range of all the vente details where idVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idVente the id vente
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching vente details
	 */
	public static List<VenteDetail> findByVente(
		long idVente, int start, int end,
		OrderByComparator<VenteDetail> orderByComparator) {

		return getPersistence().findByVente(
			idVente, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the vente details where idVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idVente the id vente
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching vente details
	 */
	public static List<VenteDetail> findByVente(
		long idVente, int start, int end,
		OrderByComparator<VenteDetail> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByVente(
			idVente, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente detail
	 * @throws NoSuchVenteDetailException if a matching vente detail could not be found
	 */
	public static VenteDetail findByVente_First(
			long idVente, OrderByComparator<VenteDetail> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteDetailException {

		return getPersistence().findByVente_First(idVente, orderByComparator);
	}

	/**
	 * Returns the first vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente detail, or <code>null</code> if a matching vente detail could not be found
	 */
	public static VenteDetail fetchByVente_First(
		long idVente, OrderByComparator<VenteDetail> orderByComparator) {

		return getPersistence().fetchByVente_First(idVente, orderByComparator);
	}

	/**
	 * Returns the last vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente detail
	 * @throws NoSuchVenteDetailException if a matching vente detail could not be found
	 */
	public static VenteDetail findByVente_Last(
			long idVente, OrderByComparator<VenteDetail> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteDetailException {

		return getPersistence().findByVente_Last(idVente, orderByComparator);
	}

	/**
	 * Returns the last vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente detail, or <code>null</code> if a matching vente detail could not be found
	 */
	public static VenteDetail fetchByVente_Last(
		long idVente, OrderByComparator<VenteDetail> orderByComparator) {

		return getPersistence().fetchByVente_Last(idVente, orderByComparator);
	}

	/**
	 * Returns the vente details before and after the current vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idDetail the primary key of the current vente detail
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente detail
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	public static VenteDetail[] findByVente_PrevAndNext(
			long idDetail, long idVente,
			OrderByComparator<VenteDetail> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteDetailException {

		return getPersistence().findByVente_PrevAndNext(
			idDetail, idVente, orderByComparator);
	}

	/**
	 * Removes all the vente details where idVente = &#63; from the database.
	 *
	 * @param idVente the id vente
	 */
	public static void removeByVente(long idVente) {
		getPersistence().removeByVente(idVente);
	}

	/**
	 * Returns the number of vente details where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @return the number of matching vente details
	 */
	public static int countByVente(long idVente) {
		return getPersistence().countByVente(idVente);
	}

	/**
	 * Returns all the vente details where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the matching vente details
	 */
	public static List<VenteDetail> findByMedicament(long idMedicament) {
		return getPersistence().findByMedicament(idMedicament);
	}

	/**
	 * Returns a range of all the vente details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @return the range of matching vente details
	 */
	public static List<VenteDetail> findByMedicament(
		long idMedicament, int start, int end) {

		return getPersistence().findByMedicament(idMedicament, start, end);
	}

	/**
	 * Returns an ordered range of all the vente details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching vente details
	 */
	public static List<VenteDetail> findByMedicament(
		long idMedicament, int start, int end,
		OrderByComparator<VenteDetail> orderByComparator) {

		return getPersistence().findByMedicament(
			idMedicament, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the vente details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching vente details
	 */
	public static List<VenteDetail> findByMedicament(
		long idMedicament, int start, int end,
		OrderByComparator<VenteDetail> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByMedicament(
			idMedicament, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente detail
	 * @throws NoSuchVenteDetailException if a matching vente detail could not be found
	 */
	public static VenteDetail findByMedicament_First(
			long idMedicament, OrderByComparator<VenteDetail> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteDetailException {

		return getPersistence().findByMedicament_First(
			idMedicament, orderByComparator);
	}

	/**
	 * Returns the first vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente detail, or <code>null</code> if a matching vente detail could not be found
	 */
	public static VenteDetail fetchByMedicament_First(
		long idMedicament, OrderByComparator<VenteDetail> orderByComparator) {

		return getPersistence().fetchByMedicament_First(
			idMedicament, orderByComparator);
	}

	/**
	 * Returns the last vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente detail
	 * @throws NoSuchVenteDetailException if a matching vente detail could not be found
	 */
	public static VenteDetail findByMedicament_Last(
			long idMedicament, OrderByComparator<VenteDetail> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteDetailException {

		return getPersistence().findByMedicament_Last(
			idMedicament, orderByComparator);
	}

	/**
	 * Returns the last vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente detail, or <code>null</code> if a matching vente detail could not be found
	 */
	public static VenteDetail fetchByMedicament_Last(
		long idMedicament, OrderByComparator<VenteDetail> orderByComparator) {

		return getPersistence().fetchByMedicament_Last(
			idMedicament, orderByComparator);
	}

	/**
	 * Returns the vente details before and after the current vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idDetail the primary key of the current vente detail
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente detail
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	public static VenteDetail[] findByMedicament_PrevAndNext(
			long idDetail, long idMedicament,
			OrderByComparator<VenteDetail> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchVenteDetailException {

		return getPersistence().findByMedicament_PrevAndNext(
			idDetail, idMedicament, orderByComparator);
	}

	/**
	 * Removes all the vente details where idMedicament = &#63; from the database.
	 *
	 * @param idMedicament the id medicament
	 */
	public static void removeByMedicament(long idMedicament) {
		getPersistence().removeByMedicament(idMedicament);
	}

	/**
	 * Returns the number of vente details where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the number of matching vente details
	 */
	public static int countByMedicament(long idMedicament) {
		return getPersistence().countByMedicament(idMedicament);
	}

	/**
	 * Caches the vente detail in the entity cache if it is enabled.
	 *
	 * @param venteDetail the vente detail
	 */
	public static void cacheResult(VenteDetail venteDetail) {
		getPersistence().cacheResult(venteDetail);
	}

	/**
	 * Caches the vente details in the entity cache if it is enabled.
	 *
	 * @param venteDetails the vente details
	 */
	public static void cacheResult(List<VenteDetail> venteDetails) {
		getPersistence().cacheResult(venteDetails);
	}

	/**
	 * Creates a new vente detail with the primary key. Does not add the vente detail to the database.
	 *
	 * @param idDetail the primary key for the new vente detail
	 * @return the new vente detail
	 */
	public static VenteDetail create(long idDetail) {
		return getPersistence().create(idDetail);
	}

	/**
	 * Removes the vente detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail that was removed
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	public static VenteDetail remove(long idDetail)
		throws gestion_de_pharmacie.exception.NoSuchVenteDetailException {

		return getPersistence().remove(idDetail);
	}

	public static VenteDetail updateImpl(VenteDetail venteDetail) {
		return getPersistence().updateImpl(venteDetail);
	}

	/**
	 * Returns the vente detail with the primary key or throws a <code>NoSuchVenteDetailException</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	public static VenteDetail findByPrimaryKey(long idDetail)
		throws gestion_de_pharmacie.exception.NoSuchVenteDetailException {

		return getPersistence().findByPrimaryKey(idDetail);
	}

	/**
	 * Returns the vente detail with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail, or <code>null</code> if a vente detail with the primary key could not be found
	 */
	public static VenteDetail fetchByPrimaryKey(long idDetail) {
		return getPersistence().fetchByPrimaryKey(idDetail);
	}

	/**
	 * Returns all the vente details.
	 *
	 * @return the vente details
	 */
	public static List<VenteDetail> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the vente details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @return the range of vente details
	 */
	public static List<VenteDetail> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the vente details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of vente details
	 */
	public static List<VenteDetail> findAll(
		int start, int end, OrderByComparator<VenteDetail> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the vente details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of vente details
	 */
	public static List<VenteDetail> findAll(
		int start, int end, OrderByComparator<VenteDetail> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the vente details from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of vente details.
	 *
	 * @return the number of vente details
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static VenteDetailPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(VenteDetailPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile VenteDetailPersistence _persistence;

}
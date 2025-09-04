/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.Medicament;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the medicament service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.MedicamentPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see MedicamentPersistence
 * @generated
 */
public class MedicamentUtil {

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
	public static void clearCache(Medicament medicament) {
		getPersistence().clearCache(medicament);
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
	public static Map<Serializable, Medicament> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Medicament> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Medicament> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Medicament> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Medicament> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Medicament update(Medicament medicament) {
		return getPersistence().update(medicament);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Medicament update(
		Medicament medicament, ServiceContext serviceContext) {

		return getPersistence().update(medicament, serviceContext);
	}

	/**
	 * Returns all the medicaments where nom = &#63;.
	 *
	 * @param nom the nom
	 * @return the matching medicaments
	 */
	public static List<Medicament> findByNom(String nom) {
		return getPersistence().findByNom(nom);
	}

	/**
	 * Returns a range of all the medicaments where nom = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param nom the nom
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of matching medicaments
	 */
	public static List<Medicament> findByNom(String nom, int start, int end) {
		return getPersistence().findByNom(nom, start, end);
	}

	/**
	 * Returns an ordered range of all the medicaments where nom = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param nom the nom
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching medicaments
	 */
	public static List<Medicament> findByNom(
		String nom, int start, int end,
		OrderByComparator<Medicament> orderByComparator) {

		return getPersistence().findByNom(nom, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the medicaments where nom = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param nom the nom
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching medicaments
	 */
	public static List<Medicament> findByNom(
		String nom, int start, int end,
		OrderByComparator<Medicament> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByNom(
			nom, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	public static Medicament findByNom_First(
			String nom, OrderByComparator<Medicament> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchMedicamentException {

		return getPersistence().findByNom_First(nom, orderByComparator);
	}

	/**
	 * Returns the first medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	public static Medicament fetchByNom_First(
		String nom, OrderByComparator<Medicament> orderByComparator) {

		return getPersistence().fetchByNom_First(nom, orderByComparator);
	}

	/**
	 * Returns the last medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	public static Medicament findByNom_Last(
			String nom, OrderByComparator<Medicament> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchMedicamentException {

		return getPersistence().findByNom_Last(nom, orderByComparator);
	}

	/**
	 * Returns the last medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	public static Medicament fetchByNom_Last(
		String nom, OrderByComparator<Medicament> orderByComparator) {

		return getPersistence().fetchByNom_Last(nom, orderByComparator);
	}

	/**
	 * Returns the medicaments before and after the current medicament in the ordered set where nom = &#63;.
	 *
	 * @param idMedicament the primary key of the current medicament
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	public static Medicament[] findByNom_PrevAndNext(
			long idMedicament, String nom,
			OrderByComparator<Medicament> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchMedicamentException {

		return getPersistence().findByNom_PrevAndNext(
			idMedicament, nom, orderByComparator);
	}

	/**
	 * Removes all the medicaments where nom = &#63; from the database.
	 *
	 * @param nom the nom
	 */
	public static void removeByNom(String nom) {
		getPersistence().removeByNom(nom);
	}

	/**
	 * Returns the number of medicaments where nom = &#63;.
	 *
	 * @param nom the nom
	 * @return the number of matching medicaments
	 */
	public static int countByNom(String nom) {
		return getPersistence().countByNom(nom);
	}

	/**
	 * Returns all the medicaments where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @return the matching medicaments
	 */
	public static List<Medicament> findByCategorie(String categorie) {
		return getPersistence().findByCategorie(categorie);
	}

	/**
	 * Returns a range of all the medicaments where categorie = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param categorie the categorie
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of matching medicaments
	 */
	public static List<Medicament> findByCategorie(
		String categorie, int start, int end) {

		return getPersistence().findByCategorie(categorie, start, end);
	}

	/**
	 * Returns an ordered range of all the medicaments where categorie = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param categorie the categorie
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching medicaments
	 */
	public static List<Medicament> findByCategorie(
		String categorie, int start, int end,
		OrderByComparator<Medicament> orderByComparator) {

		return getPersistence().findByCategorie(
			categorie, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the medicaments where categorie = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param categorie the categorie
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching medicaments
	 */
	public static List<Medicament> findByCategorie(
		String categorie, int start, int end,
		OrderByComparator<Medicament> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCategorie(
			categorie, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	public static Medicament findByCategorie_First(
			String categorie, OrderByComparator<Medicament> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchMedicamentException {

		return getPersistence().findByCategorie_First(
			categorie, orderByComparator);
	}

	/**
	 * Returns the first medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	public static Medicament fetchByCategorie_First(
		String categorie, OrderByComparator<Medicament> orderByComparator) {

		return getPersistence().fetchByCategorie_First(
			categorie, orderByComparator);
	}

	/**
	 * Returns the last medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	public static Medicament findByCategorie_Last(
			String categorie, OrderByComparator<Medicament> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchMedicamentException {

		return getPersistence().findByCategorie_Last(
			categorie, orderByComparator);
	}

	/**
	 * Returns the last medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	public static Medicament fetchByCategorie_Last(
		String categorie, OrderByComparator<Medicament> orderByComparator) {

		return getPersistence().fetchByCategorie_Last(
			categorie, orderByComparator);
	}

	/**
	 * Returns the medicaments before and after the current medicament in the ordered set where categorie = &#63;.
	 *
	 * @param idMedicament the primary key of the current medicament
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	public static Medicament[] findByCategorie_PrevAndNext(
			long idMedicament, String categorie,
			OrderByComparator<Medicament> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchMedicamentException {

		return getPersistence().findByCategorie_PrevAndNext(
			idMedicament, categorie, orderByComparator);
	}

	/**
	 * Removes all the medicaments where categorie = &#63; from the database.
	 *
	 * @param categorie the categorie
	 */
	public static void removeByCategorie(String categorie) {
		getPersistence().removeByCategorie(categorie);
	}

	/**
	 * Returns the number of medicaments where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @return the number of matching medicaments
	 */
	public static int countByCategorie(String categorie) {
		return getPersistence().countByCategorie(categorie);
	}

	/**
	 * Caches the medicament in the entity cache if it is enabled.
	 *
	 * @param medicament the medicament
	 */
	public static void cacheResult(Medicament medicament) {
		getPersistence().cacheResult(medicament);
	}

	/**
	 * Caches the medicaments in the entity cache if it is enabled.
	 *
	 * @param medicaments the medicaments
	 */
	public static void cacheResult(List<Medicament> medicaments) {
		getPersistence().cacheResult(medicaments);
	}

	/**
	 * Creates a new medicament with the primary key. Does not add the medicament to the database.
	 *
	 * @param idMedicament the primary key for the new medicament
	 * @return the new medicament
	 */
	public static Medicament create(long idMedicament) {
		return getPersistence().create(idMedicament);
	}

	/**
	 * Removes the medicament with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament that was removed
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	public static Medicament remove(long idMedicament)
		throws gestion_de_pharmacie.exception.NoSuchMedicamentException {

		return getPersistence().remove(idMedicament);
	}

	public static Medicament updateImpl(Medicament medicament) {
		return getPersistence().updateImpl(medicament);
	}

	/**
	 * Returns the medicament with the primary key or throws a <code>NoSuchMedicamentException</code> if it could not be found.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	public static Medicament findByPrimaryKey(long idMedicament)
		throws gestion_de_pharmacie.exception.NoSuchMedicamentException {

		return getPersistence().findByPrimaryKey(idMedicament);
	}

	/**
	 * Returns the medicament with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament, or <code>null</code> if a medicament with the primary key could not be found
	 */
	public static Medicament fetchByPrimaryKey(long idMedicament) {
		return getPersistence().fetchByPrimaryKey(idMedicament);
	}

	/**
	 * Returns all the medicaments.
	 *
	 * @return the medicaments
	 */
	public static List<Medicament> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the medicaments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of medicaments
	 */
	public static List<Medicament> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the medicaments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of medicaments
	 */
	public static List<Medicament> findAll(
		int start, int end, OrderByComparator<Medicament> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the medicaments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of medicaments
	 */
	public static List<Medicament> findAll(
		int start, int end, OrderByComparator<Medicament> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the medicaments from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of medicaments.
	 *
	 * @return the number of medicaments
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MedicamentPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(MedicamentPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile MedicamentPersistence _persistence;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.Commande;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commande service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.CommandePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see CommandePersistence
 * @generated
 */
public class CommandeUtil {

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
	public static void clearCache(Commande commande) {
		getPersistence().clearCache(commande);
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
	public static Map<Serializable, Commande> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Commande> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Commande> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Commande> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Commande> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Commande update(Commande commande) {
		return getPersistence().update(commande);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Commande update(
		Commande commande, ServiceContext serviceContext) {

		return getPersistence().update(commande, serviceContext);
	}

	/**
	 * Returns all the commandes where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @return the matching commandes
	 */
	public static List<Commande> findByFournisseur(long idFournisseur) {
		return getPersistence().findByFournisseur(idFournisseur);
	}

	/**
	 * Returns a range of all the commandes where idFournisseur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param idFournisseur the id fournisseur
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @return the range of matching commandes
	 */
	public static List<Commande> findByFournisseur(
		long idFournisseur, int start, int end) {

		return getPersistence().findByFournisseur(idFournisseur, start, end);
	}

	/**
	 * Returns an ordered range of all the commandes where idFournisseur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param idFournisseur the id fournisseur
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commandes
	 */
	public static List<Commande> findByFournisseur(
		long idFournisseur, int start, int end,
		OrderByComparator<Commande> orderByComparator) {

		return getPersistence().findByFournisseur(
			idFournisseur, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commandes where idFournisseur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param idFournisseur the id fournisseur
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commandes
	 */
	public static List<Commande> findByFournisseur(
		long idFournisseur, int start, int end,
		OrderByComparator<Commande> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByFournisseur(
			idFournisseur, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande
	 * @throws NoSuchCommandeException if a matching commande could not be found
	 */
	public static Commande findByFournisseur_First(
			long idFournisseur, OrderByComparator<Commande> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchCommandeException {

		return getPersistence().findByFournisseur_First(
			idFournisseur, orderByComparator);
	}

	/**
	 * Returns the first commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande, or <code>null</code> if a matching commande could not be found
	 */
	public static Commande fetchByFournisseur_First(
		long idFournisseur, OrderByComparator<Commande> orderByComparator) {

		return getPersistence().fetchByFournisseur_First(
			idFournisseur, orderByComparator);
	}

	/**
	 * Returns the last commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande
	 * @throws NoSuchCommandeException if a matching commande could not be found
	 */
	public static Commande findByFournisseur_Last(
			long idFournisseur, OrderByComparator<Commande> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchCommandeException {

		return getPersistence().findByFournisseur_Last(
			idFournisseur, orderByComparator);
	}

	/**
	 * Returns the last commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande, or <code>null</code> if a matching commande could not be found
	 */
	public static Commande fetchByFournisseur_Last(
		long idFournisseur, OrderByComparator<Commande> orderByComparator) {

		return getPersistence().fetchByFournisseur_Last(
			idFournisseur, orderByComparator);
	}

	/**
	 * Returns the commandes before and after the current commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idCommande the primary key of the current commande
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commande
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	public static Commande[] findByFournisseur_PrevAndNext(
			long idCommande, long idFournisseur,
			OrderByComparator<Commande> orderByComparator)
		throws gestion_de_pharmacie.exception.NoSuchCommandeException {

		return getPersistence().findByFournisseur_PrevAndNext(
			idCommande, idFournisseur, orderByComparator);
	}

	/**
	 * Removes all the commandes where idFournisseur = &#63; from the database.
	 *
	 * @param idFournisseur the id fournisseur
	 */
	public static void removeByFournisseur(long idFournisseur) {
		getPersistence().removeByFournisseur(idFournisseur);
	}

	/**
	 * Returns the number of commandes where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @return the number of matching commandes
	 */
	public static int countByFournisseur(long idFournisseur) {
		return getPersistence().countByFournisseur(idFournisseur);
	}

	/**
	 * Caches the commande in the entity cache if it is enabled.
	 *
	 * @param commande the commande
	 */
	public static void cacheResult(Commande commande) {
		getPersistence().cacheResult(commande);
	}

	/**
	 * Caches the commandes in the entity cache if it is enabled.
	 *
	 * @param commandes the commandes
	 */
	public static void cacheResult(List<Commande> commandes) {
		getPersistence().cacheResult(commandes);
	}

	/**
	 * Creates a new commande with the primary key. Does not add the commande to the database.
	 *
	 * @param idCommande the primary key for the new commande
	 * @return the new commande
	 */
	public static Commande create(long idCommande) {
		return getPersistence().create(idCommande);
	}

	/**
	 * Removes the commande with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande that was removed
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	public static Commande remove(long idCommande)
		throws gestion_de_pharmacie.exception.NoSuchCommandeException {

		return getPersistence().remove(idCommande);
	}

	public static Commande updateImpl(Commande commande) {
		return getPersistence().updateImpl(commande);
	}

	/**
	 * Returns the commande with the primary key or throws a <code>NoSuchCommandeException</code> if it could not be found.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	public static Commande findByPrimaryKey(long idCommande)
		throws gestion_de_pharmacie.exception.NoSuchCommandeException {

		return getPersistence().findByPrimaryKey(idCommande);
	}

	/**
	 * Returns the commande with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande, or <code>null</code> if a commande with the primary key could not be found
	 */
	public static Commande fetchByPrimaryKey(long idCommande) {
		return getPersistence().fetchByPrimaryKey(idCommande);
	}

	/**
	 * Returns all the commandes.
	 *
	 * @return the commandes
	 */
	public static List<Commande> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commandes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @return the range of commandes
	 */
	public static List<Commande> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commandes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commandes
	 */
	public static List<Commande> findAll(
		int start, int end, OrderByComparator<Commande> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commandes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commandes
	 */
	public static List<Commande> findAll(
		int start, int end, OrderByComparator<Commande> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commandes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commandes.
	 *
	 * @return the number of commandes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommandePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CommandePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CommandePersistence _persistence;

}
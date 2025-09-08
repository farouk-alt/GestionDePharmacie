/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.CommandeDetail;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commande detail service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.CommandeDetailPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see CommandeDetailPersistence
 * @generated
 */
public class CommandeDetailUtil {

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
	public static void clearCache(CommandeDetail commandeDetail) {
		getPersistence().clearCache(commandeDetail);
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
	public static Map<Serializable, CommandeDetail> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommandeDetail> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommandeDetail> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommandeDetail> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommandeDetail> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommandeDetail update(CommandeDetail commandeDetail) {
		return getPersistence().update(commandeDetail);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommandeDetail update(
		CommandeDetail commandeDetail, ServiceContext serviceContext) {

		return getPersistence().update(commandeDetail, serviceContext);
	}

	/**
	 * Caches the commande detail in the entity cache if it is enabled.
	 *
	 * @param commandeDetail the commande detail
	 */
	public static void cacheResult(CommandeDetail commandeDetail) {
		getPersistence().cacheResult(commandeDetail);
	}

	/**
	 * Caches the commande details in the entity cache if it is enabled.
	 *
	 * @param commandeDetails the commande details
	 */
	public static void cacheResult(List<CommandeDetail> commandeDetails) {
		getPersistence().cacheResult(commandeDetails);
	}

	/**
	 * Creates a new commande detail with the primary key. Does not add the commande detail to the database.
	 *
	 * @param idDetail the primary key for the new commande detail
	 * @return the new commande detail
	 */
	public static CommandeDetail create(long idDetail) {
		return getPersistence().create(idDetail);
	}

	/**
	 * Removes the commande detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail that was removed
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	public static CommandeDetail remove(long idDetail)
		throws gestion_de_pharmacie.exception.NoSuchCommandeDetailException {

		return getPersistence().remove(idDetail);
	}

	public static CommandeDetail updateImpl(CommandeDetail commandeDetail) {
		return getPersistence().updateImpl(commandeDetail);
	}

	/**
	 * Returns the commande detail with the primary key or throws a <code>NoSuchCommandeDetailException</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	public static CommandeDetail findByPrimaryKey(long idDetail)
		throws gestion_de_pharmacie.exception.NoSuchCommandeDetailException {

		return getPersistence().findByPrimaryKey(idDetail);
	}

	/**
	 * Returns the commande detail with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail, or <code>null</code> if a commande detail with the primary key could not be found
	 */
	public static CommandeDetail fetchByPrimaryKey(long idDetail) {
		return getPersistence().fetchByPrimaryKey(idDetail);
	}

	/**
	 * Returns all the commande details.
	 *
	 * @return the commande details
	 */
	public static List<CommandeDetail> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commande details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @return the range of commande details
	 */
	public static List<CommandeDetail> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commande details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commande details
	 */
	public static List<CommandeDetail> findAll(
		int start, int end,
		OrderByComparator<CommandeDetail> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commande details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commande details
	 */
	public static List<CommandeDetail> findAll(
		int start, int end, OrderByComparator<CommandeDetail> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commande details from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commande details.
	 *
	 * @return the number of commande details
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommandeDetailPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CommandeDetailPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CommandeDetailPersistence _persistence;

}
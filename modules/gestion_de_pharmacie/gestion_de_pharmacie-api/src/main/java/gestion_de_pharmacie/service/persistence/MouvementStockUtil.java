/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.MouvementStock;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the mouvement stock service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.MouvementStockPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see MouvementStockPersistence
 * @generated
 */
public class MouvementStockUtil {

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
	public static void clearCache(MouvementStock mouvementStock) {
		getPersistence().clearCache(mouvementStock);
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
	public static Map<Serializable, MouvementStock> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MouvementStock> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MouvementStock> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MouvementStock> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<MouvementStock> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static MouvementStock update(MouvementStock mouvementStock) {
		return getPersistence().update(mouvementStock);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static MouvementStock update(
		MouvementStock mouvementStock, ServiceContext serviceContext) {

		return getPersistence().update(mouvementStock, serviceContext);
	}

	/**
	 * Caches the mouvement stock in the entity cache if it is enabled.
	 *
	 * @param mouvementStock the mouvement stock
	 */
	public static void cacheResult(MouvementStock mouvementStock) {
		getPersistence().cacheResult(mouvementStock);
	}

	/**
	 * Caches the mouvement stocks in the entity cache if it is enabled.
	 *
	 * @param mouvementStocks the mouvement stocks
	 */
	public static void cacheResult(List<MouvementStock> mouvementStocks) {
		getPersistence().cacheResult(mouvementStocks);
	}

	/**
	 * Creates a new mouvement stock with the primary key. Does not add the mouvement stock to the database.
	 *
	 * @param idMouvement the primary key for the new mouvement stock
	 * @return the new mouvement stock
	 */
	public static MouvementStock create(long idMouvement) {
		return getPersistence().create(idMouvement);
	}

	/**
	 * Removes the mouvement stock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock that was removed
	 * @throws NoSuchMouvementStockException if a mouvement stock with the primary key could not be found
	 */
	public static MouvementStock remove(long idMouvement)
		throws gestion_de_pharmacie.exception.NoSuchMouvementStockException {

		return getPersistence().remove(idMouvement);
	}

	public static MouvementStock updateImpl(MouvementStock mouvementStock) {
		return getPersistence().updateImpl(mouvementStock);
	}

	/**
	 * Returns the mouvement stock with the primary key or throws a <code>NoSuchMouvementStockException</code> if it could not be found.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock
	 * @throws NoSuchMouvementStockException if a mouvement stock with the primary key could not be found
	 */
	public static MouvementStock findByPrimaryKey(long idMouvement)
		throws gestion_de_pharmacie.exception.NoSuchMouvementStockException {

		return getPersistence().findByPrimaryKey(idMouvement);
	}

	/**
	 * Returns the mouvement stock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock, or <code>null</code> if a mouvement stock with the primary key could not be found
	 */
	public static MouvementStock fetchByPrimaryKey(long idMouvement) {
		return getPersistence().fetchByPrimaryKey(idMouvement);
	}

	/**
	 * Returns all the mouvement stocks.
	 *
	 * @return the mouvement stocks
	 */
	public static List<MouvementStock> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the mouvement stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MouvementStockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mouvement stocks
	 * @param end the upper bound of the range of mouvement stocks (not inclusive)
	 * @return the range of mouvement stocks
	 */
	public static List<MouvementStock> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the mouvement stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MouvementStockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mouvement stocks
	 * @param end the upper bound of the range of mouvement stocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mouvement stocks
	 */
	public static List<MouvementStock> findAll(
		int start, int end,
		OrderByComparator<MouvementStock> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the mouvement stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MouvementStockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mouvement stocks
	 * @param end the upper bound of the range of mouvement stocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mouvement stocks
	 */
	public static List<MouvementStock> findAll(
		int start, int end, OrderByComparator<MouvementStock> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the mouvement stocks from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of mouvement stocks.
	 *
	 * @return the number of mouvement stocks
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static MouvementStockPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(MouvementStockPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile MouvementStockPersistence _persistence;

}
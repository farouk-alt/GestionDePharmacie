/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import gestion_de_pharmacie.model.Stock;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the stock service. This utility wraps <code>gestion_de_pharmacie.service.persistence.impl.StockPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see StockPersistence
 * @generated
 */
public class StockUtil {

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
	public static void clearCache(Stock stock) {
		getPersistence().clearCache(stock);
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
	public static Map<Serializable, Stock> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Stock> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Stock> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Stock> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Stock> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Stock update(Stock stock) {
		return getPersistence().update(stock);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Stock update(Stock stock, ServiceContext serviceContext) {
		return getPersistence().update(stock, serviceContext);
	}

	/**
	 * Caches the stock in the entity cache if it is enabled.
	 *
	 * @param stock the stock
	 */
	public static void cacheResult(Stock stock) {
		getPersistence().cacheResult(stock);
	}

	/**
	 * Caches the stocks in the entity cache if it is enabled.
	 *
	 * @param stocks the stocks
	 */
	public static void cacheResult(List<Stock> stocks) {
		getPersistence().cacheResult(stocks);
	}

	/**
	 * Creates a new stock with the primary key. Does not add the stock to the database.
	 *
	 * @param idStock the primary key for the new stock
	 * @return the new stock
	 */
	public static Stock create(long idStock) {
		return getPersistence().create(idStock);
	}

	/**
	 * Removes the stock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idStock the primary key of the stock
	 * @return the stock that was removed
	 * @throws NoSuchStockException if a stock with the primary key could not be found
	 */
	public static Stock remove(long idStock)
		throws gestion_de_pharmacie.exception.NoSuchStockException {

		return getPersistence().remove(idStock);
	}

	public static Stock updateImpl(Stock stock) {
		return getPersistence().updateImpl(stock);
	}

	/**
	 * Returns the stock with the primary key or throws a <code>NoSuchStockException</code> if it could not be found.
	 *
	 * @param idStock the primary key of the stock
	 * @return the stock
	 * @throws NoSuchStockException if a stock with the primary key could not be found
	 */
	public static Stock findByPrimaryKey(long idStock)
		throws gestion_de_pharmacie.exception.NoSuchStockException {

		return getPersistence().findByPrimaryKey(idStock);
	}

	/**
	 * Returns the stock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idStock the primary key of the stock
	 * @return the stock, or <code>null</code> if a stock with the primary key could not be found
	 */
	public static Stock fetchByPrimaryKey(long idStock) {
		return getPersistence().fetchByPrimaryKey(idStock);
	}

	/**
	 * Returns all the stocks.
	 *
	 * @return the stocks
	 */
	public static List<Stock> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of stocks
	 * @param end the upper bound of the range of stocks (not inclusive)
	 * @return the range of stocks
	 */
	public static List<Stock> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of stocks
	 * @param end the upper bound of the range of stocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of stocks
	 */
	public static List<Stock> findAll(
		int start, int end, OrderByComparator<Stock> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of stocks
	 * @param end the upper bound of the range of stocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of stocks
	 */
	public static List<Stock> findAll(
		int start, int end, OrderByComparator<Stock> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the stocks from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of stocks.
	 *
	 * @return the number of stocks
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static StockPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(StockPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile StockPersistence _persistence;

}
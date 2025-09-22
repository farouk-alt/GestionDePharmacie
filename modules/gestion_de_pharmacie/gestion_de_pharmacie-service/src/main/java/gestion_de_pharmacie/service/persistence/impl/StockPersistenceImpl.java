/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import gestion_de_pharmacie.exception.NoSuchStockException;

import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.model.StockTable;
import gestion_de_pharmacie.model.impl.StockImpl;
import gestion_de_pharmacie.model.impl.StockModelImpl;

import gestion_de_pharmacie.service.persistence.StockPersistence;
import gestion_de_pharmacie.service.persistence.StockUtil;
import gestion_de_pharmacie.service.persistence.impl.constants.PharmaPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the stock service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = StockPersistence.class)
public class StockPersistenceImpl
	extends BasePersistenceImpl<Stock> implements StockPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>StockUtil</code> to access the stock persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		StockImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByMed;

	/**
	 * Returns the stock where idMedicament = &#63; or throws a <code>NoSuchStockException</code> if it could not be found.
	 *
	 * @param idMedicament the id medicament
	 * @return the matching stock
	 * @throws NoSuchStockException if a matching stock could not be found
	 */
	@Override
	public Stock findByMed(long idMedicament) throws NoSuchStockException {
		Stock stock = fetchByMed(idMedicament);

		if (stock == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("idMedicament=");
			sb.append(idMedicament);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchStockException(sb.toString());
		}

		return stock;
	}

	/**
	 * Returns the stock where idMedicament = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param idMedicament the id medicament
	 * @return the matching stock, or <code>null</code> if a matching stock could not be found
	 */
	@Override
	public Stock fetchByMed(long idMedicament) {
		return fetchByMed(idMedicament, true);
	}

	/**
	 * Returns the stock where idMedicament = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param idMedicament the id medicament
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching stock, or <code>null</code> if a matching stock could not be found
	 */
	@Override
	public Stock fetchByMed(long idMedicament, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {idMedicament};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByMed, finderArgs, this);
		}

		if (result instanceof Stock) {
			Stock stock = (Stock)result;

			if (idMedicament != stock.getIdMedicament()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_STOCK_WHERE);

			sb.append(_FINDER_COLUMN_MED_IDMEDICAMENT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idMedicament);

				List<Stock> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByMed, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {idMedicament};
							}

							_log.warn(
								"StockPersistenceImpl.fetchByMed(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Stock stock = list.get(0);

					result = stock;

					cacheResult(stock);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Stock)result;
		}
	}

	/**
	 * Removes the stock where idMedicament = &#63; from the database.
	 *
	 * @param idMedicament the id medicament
	 * @return the stock that was removed
	 */
	@Override
	public Stock removeByMed(long idMedicament) throws NoSuchStockException {
		Stock stock = findByMed(idMedicament);

		return remove(stock);
	}

	/**
	 * Returns the number of stocks where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the number of matching stocks
	 */
	@Override
	public int countByMed(long idMedicament) {
		Stock stock = fetchByMed(idMedicament);

		if (stock == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_MED_IDMEDICAMENT_2 =
		"stock.idMedicament = ?";

	public StockPersistenceImpl() {
		setModelClass(Stock.class);

		setModelImplClass(StockImpl.class);
		setModelPKClass(long.class);

		setTable(StockTable.INSTANCE);
	}

	/**
	 * Caches the stock in the entity cache if it is enabled.
	 *
	 * @param stock the stock
	 */
	@Override
	public void cacheResult(Stock stock) {
		entityCache.putResult(StockImpl.class, stock.getPrimaryKey(), stock);

		finderCache.putResult(
			_finderPathFetchByMed, new Object[] {stock.getIdMedicament()},
			stock);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the stocks in the entity cache if it is enabled.
	 *
	 * @param stocks the stocks
	 */
	@Override
	public void cacheResult(List<Stock> stocks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (stocks.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Stock stock : stocks) {
			if (entityCache.getResult(StockImpl.class, stock.getPrimaryKey()) ==
					null) {

				cacheResult(stock);
			}
		}
	}

	/**
	 * Clears the cache for all stocks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(StockImpl.class);

		finderCache.clearCache(StockImpl.class);
	}

	/**
	 * Clears the cache for the stock.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Stock stock) {
		entityCache.removeResult(StockImpl.class, stock);
	}

	@Override
	public void clearCache(List<Stock> stocks) {
		for (Stock stock : stocks) {
			entityCache.removeResult(StockImpl.class, stock);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(StockImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(StockImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(StockModelImpl stockModelImpl) {
		Object[] args = new Object[] {stockModelImpl.getIdMedicament()};

		finderCache.putResult(_finderPathFetchByMed, args, stockModelImpl);
	}

	/**
	 * Creates a new stock with the primary key. Does not add the stock to the database.
	 *
	 * @param idStock the primary key for the new stock
	 * @return the new stock
	 */
	@Override
	public Stock create(long idStock) {
		Stock stock = new StockImpl();

		stock.setNew(true);
		stock.setPrimaryKey(idStock);

		return stock;
	}

	/**
	 * Removes the stock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idStock the primary key of the stock
	 * @return the stock that was removed
	 * @throws NoSuchStockException if a stock with the primary key could not be found
	 */
	@Override
	public Stock remove(long idStock) throws NoSuchStockException {
		return remove((Serializable)idStock);
	}

	/**
	 * Removes the stock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the stock
	 * @return the stock that was removed
	 * @throws NoSuchStockException if a stock with the primary key could not be found
	 */
	@Override
	public Stock remove(Serializable primaryKey) throws NoSuchStockException {
		Session session = null;

		try {
			session = openSession();

			Stock stock = (Stock)session.get(StockImpl.class, primaryKey);

			if (stock == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStockException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(stock);
		}
		catch (NoSuchStockException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Stock removeImpl(Stock stock) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(stock)) {
				stock = (Stock)session.get(
					StockImpl.class, stock.getPrimaryKeyObj());
			}

			if (stock != null) {
				session.delete(stock);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (stock != null) {
			clearCache(stock);
		}

		return stock;
	}

	@Override
	public Stock updateImpl(Stock stock) {
		boolean isNew = stock.isNew();

		if (!(stock instanceof StockModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(stock.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(stock);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in stock proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Stock implementation " +
					stock.getClass());
		}

		StockModelImpl stockModelImpl = (StockModelImpl)stock;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(stock);
			}
			else {
				stock = (Stock)session.merge(stock);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(StockImpl.class, stockModelImpl, false, true);

		cacheUniqueFindersCache(stockModelImpl);

		if (isNew) {
			stock.setNew(false);
		}

		stock.resetOriginalValues();

		return stock;
	}

	/**
	 * Returns the stock with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the stock
	 * @return the stock
	 * @throws NoSuchStockException if a stock with the primary key could not be found
	 */
	@Override
	public Stock findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStockException {

		Stock stock = fetchByPrimaryKey(primaryKey);

		if (stock == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStockException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return stock;
	}

	/**
	 * Returns the stock with the primary key or throws a <code>NoSuchStockException</code> if it could not be found.
	 *
	 * @param idStock the primary key of the stock
	 * @return the stock
	 * @throws NoSuchStockException if a stock with the primary key could not be found
	 */
	@Override
	public Stock findByPrimaryKey(long idStock) throws NoSuchStockException {
		return findByPrimaryKey((Serializable)idStock);
	}

	/**
	 * Returns the stock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idStock the primary key of the stock
	 * @return the stock, or <code>null</code> if a stock with the primary key could not be found
	 */
	@Override
	public Stock fetchByPrimaryKey(long idStock) {
		return fetchByPrimaryKey((Serializable)idStock);
	}

	/**
	 * Returns all the stocks.
	 *
	 * @return the stocks
	 */
	@Override
	public List<Stock> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<Stock> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<Stock> findAll(
		int start, int end, OrderByComparator<Stock> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<Stock> findAll(
		int start, int end, OrderByComparator<Stock> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<Stock> list = null;

		if (useFinderCache) {
			list = (List<Stock>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_STOCK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_STOCK;

				sql = sql.concat(StockModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Stock>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the stocks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Stock stock : findAll()) {
			remove(stock);
		}
	}

	/**
	 * Returns the number of stocks.
	 *
	 * @return the number of stocks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_STOCK);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "idStock";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_STOCK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return StockModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the stock persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByMed = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByMed",
			new String[] {Long.class.getName()}, new String[] {"idMedicament"},
			true);

		StockUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		StockUtil.setPersistence(null);

		entityCache.removeCache(StockImpl.class.getName());
	}

	@Override
	@Reference(
		target = PharmaPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = PharmaPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = PharmaPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_STOCK =
		"SELECT stock FROM Stock stock";

	private static final String _SQL_SELECT_STOCK_WHERE =
		"SELECT stock FROM Stock stock WHERE ";

	private static final String _SQL_COUNT_STOCK =
		"SELECT COUNT(stock) FROM Stock stock";

	private static final String _SQL_COUNT_STOCK_WHERE =
		"SELECT COUNT(stock) FROM Stock stock WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "stock.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Stock exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Stock exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		StockPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
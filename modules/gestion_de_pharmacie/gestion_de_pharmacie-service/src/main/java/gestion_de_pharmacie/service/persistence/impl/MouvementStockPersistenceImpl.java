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

import gestion_de_pharmacie.exception.NoSuchMouvementStockException;

import gestion_de_pharmacie.model.MouvementStock;
import gestion_de_pharmacie.model.MouvementStockTable;
import gestion_de_pharmacie.model.impl.MouvementStockImpl;
import gestion_de_pharmacie.model.impl.MouvementStockModelImpl;

import gestion_de_pharmacie.service.persistence.MouvementStockPersistence;
import gestion_de_pharmacie.service.persistence.MouvementStockUtil;
import gestion_de_pharmacie.service.persistence.impl.constants.PharmaPersistenceConstants;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the mouvement stock service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = MouvementStockPersistence.class)
public class MouvementStockPersistenceImpl
	extends BasePersistenceImpl<MouvementStock>
	implements MouvementStockPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MouvementStockUtil</code> to access the mouvement stock persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MouvementStockImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public MouvementStockPersistenceImpl() {
		setModelClass(MouvementStock.class);

		setModelImplClass(MouvementStockImpl.class);
		setModelPKClass(long.class);

		setTable(MouvementStockTable.INSTANCE);
	}

	/**
	 * Caches the mouvement stock in the entity cache if it is enabled.
	 *
	 * @param mouvementStock the mouvement stock
	 */
	@Override
	public void cacheResult(MouvementStock mouvementStock) {
		entityCache.putResult(
			MouvementStockImpl.class, mouvementStock.getPrimaryKey(),
			mouvementStock);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the mouvement stocks in the entity cache if it is enabled.
	 *
	 * @param mouvementStocks the mouvement stocks
	 */
	@Override
	public void cacheResult(List<MouvementStock> mouvementStocks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (mouvementStocks.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MouvementStock mouvementStock : mouvementStocks) {
			if (entityCache.getResult(
					MouvementStockImpl.class, mouvementStock.getPrimaryKey()) ==
						null) {

				cacheResult(mouvementStock);
			}
		}
	}

	/**
	 * Clears the cache for all mouvement stocks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MouvementStockImpl.class);

		finderCache.clearCache(MouvementStockImpl.class);
	}

	/**
	 * Clears the cache for the mouvement stock.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MouvementStock mouvementStock) {
		entityCache.removeResult(MouvementStockImpl.class, mouvementStock);
	}

	@Override
	public void clearCache(List<MouvementStock> mouvementStocks) {
		for (MouvementStock mouvementStock : mouvementStocks) {
			entityCache.removeResult(MouvementStockImpl.class, mouvementStock);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MouvementStockImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MouvementStockImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new mouvement stock with the primary key. Does not add the mouvement stock to the database.
	 *
	 * @param idMouvement the primary key for the new mouvement stock
	 * @return the new mouvement stock
	 */
	@Override
	public MouvementStock create(long idMouvement) {
		MouvementStock mouvementStock = new MouvementStockImpl();

		mouvementStock.setNew(true);
		mouvementStock.setPrimaryKey(idMouvement);

		return mouvementStock;
	}

	/**
	 * Removes the mouvement stock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock that was removed
	 * @throws NoSuchMouvementStockException if a mouvement stock with the primary key could not be found
	 */
	@Override
	public MouvementStock remove(long idMouvement)
		throws NoSuchMouvementStockException {

		return remove((Serializable)idMouvement);
	}

	/**
	 * Removes the mouvement stock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the mouvement stock
	 * @return the mouvement stock that was removed
	 * @throws NoSuchMouvementStockException if a mouvement stock with the primary key could not be found
	 */
	@Override
	public MouvementStock remove(Serializable primaryKey)
		throws NoSuchMouvementStockException {

		Session session = null;

		try {
			session = openSession();

			MouvementStock mouvementStock = (MouvementStock)session.get(
				MouvementStockImpl.class, primaryKey);

			if (mouvementStock == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMouvementStockException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mouvementStock);
		}
		catch (NoSuchMouvementStockException noSuchEntityException) {
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
	protected MouvementStock removeImpl(MouvementStock mouvementStock) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mouvementStock)) {
				mouvementStock = (MouvementStock)session.get(
					MouvementStockImpl.class,
					mouvementStock.getPrimaryKeyObj());
			}

			if (mouvementStock != null) {
				session.delete(mouvementStock);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mouvementStock != null) {
			clearCache(mouvementStock);
		}

		return mouvementStock;
	}

	@Override
	public MouvementStock updateImpl(MouvementStock mouvementStock) {
		boolean isNew = mouvementStock.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(mouvementStock);
			}
			else {
				mouvementStock = (MouvementStock)session.merge(mouvementStock);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MouvementStockImpl.class, mouvementStock, false, true);

		if (isNew) {
			mouvementStock.setNew(false);
		}

		mouvementStock.resetOriginalValues();

		return mouvementStock;
	}

	/**
	 * Returns the mouvement stock with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the mouvement stock
	 * @return the mouvement stock
	 * @throws NoSuchMouvementStockException if a mouvement stock with the primary key could not be found
	 */
	@Override
	public MouvementStock findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMouvementStockException {

		MouvementStock mouvementStock = fetchByPrimaryKey(primaryKey);

		if (mouvementStock == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMouvementStockException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mouvementStock;
	}

	/**
	 * Returns the mouvement stock with the primary key or throws a <code>NoSuchMouvementStockException</code> if it could not be found.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock
	 * @throws NoSuchMouvementStockException if a mouvement stock with the primary key could not be found
	 */
	@Override
	public MouvementStock findByPrimaryKey(long idMouvement)
		throws NoSuchMouvementStockException {

		return findByPrimaryKey((Serializable)idMouvement);
	}

	/**
	 * Returns the mouvement stock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock, or <code>null</code> if a mouvement stock with the primary key could not be found
	 */
	@Override
	public MouvementStock fetchByPrimaryKey(long idMouvement) {
		return fetchByPrimaryKey((Serializable)idMouvement);
	}

	/**
	 * Returns all the mouvement stocks.
	 *
	 * @return the mouvement stocks
	 */
	@Override
	public List<MouvementStock> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<MouvementStock> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<MouvementStock> findAll(
		int start, int end,
		OrderByComparator<MouvementStock> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<MouvementStock> findAll(
		int start, int end, OrderByComparator<MouvementStock> orderByComparator,
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

		List<MouvementStock> list = null;

		if (useFinderCache) {
			list = (List<MouvementStock>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MOUVEMENTSTOCK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MOUVEMENTSTOCK;

				sql = sql.concat(MouvementStockModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MouvementStock>)QueryUtil.list(
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
	 * Removes all the mouvement stocks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MouvementStock mouvementStock : findAll()) {
			remove(mouvementStock);
		}
	}

	/**
	 * Returns the number of mouvement stocks.
	 *
	 * @return the number of mouvement stocks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MOUVEMENTSTOCK);

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
		return "idMouvement";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MOUVEMENTSTOCK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MouvementStockModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the mouvement stock persistence.
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

		MouvementStockUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		MouvementStockUtil.setPersistence(null);

		entityCache.removeCache(MouvementStockImpl.class.getName());
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

	private static final String _SQL_SELECT_MOUVEMENTSTOCK =
		"SELECT mouvementStock FROM MouvementStock mouvementStock";

	private static final String _SQL_COUNT_MOUVEMENTSTOCK =
		"SELECT COUNT(mouvementStock) FROM MouvementStock mouvementStock";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mouvementStock.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MouvementStock exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		MouvementStockPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
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

import gestion_de_pharmacie.exception.NoSuchCommandeDetailException;

import gestion_de_pharmacie.model.CommandeDetail;
import gestion_de_pharmacie.model.CommandeDetailTable;
import gestion_de_pharmacie.model.impl.CommandeDetailImpl;
import gestion_de_pharmacie.model.impl.CommandeDetailModelImpl;

import gestion_de_pharmacie.service.persistence.CommandeDetailPersistence;
import gestion_de_pharmacie.service.persistence.CommandeDetailUtil;
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
 * The persistence implementation for the commande detail service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = CommandeDetailPersistence.class)
public class CommandeDetailPersistenceImpl
	extends BasePersistenceImpl<CommandeDetail>
	implements CommandeDetailPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommandeDetailUtil</code> to access the commande detail persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommandeDetailImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public CommandeDetailPersistenceImpl() {
		setModelClass(CommandeDetail.class);

		setModelImplClass(CommandeDetailImpl.class);
		setModelPKClass(long.class);

		setTable(CommandeDetailTable.INSTANCE);
	}

	/**
	 * Caches the commande detail in the entity cache if it is enabled.
	 *
	 * @param commandeDetail the commande detail
	 */
	@Override
	public void cacheResult(CommandeDetail commandeDetail) {
		entityCache.putResult(
			CommandeDetailImpl.class, commandeDetail.getPrimaryKey(),
			commandeDetail);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commande details in the entity cache if it is enabled.
	 *
	 * @param commandeDetails the commande details
	 */
	@Override
	public void cacheResult(List<CommandeDetail> commandeDetails) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commandeDetails.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommandeDetail commandeDetail : commandeDetails) {
			if (entityCache.getResult(
					CommandeDetailImpl.class, commandeDetail.getPrimaryKey()) ==
						null) {

				cacheResult(commandeDetail);
			}
		}
	}

	/**
	 * Clears the cache for all commande details.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommandeDetailImpl.class);

		finderCache.clearCache(CommandeDetailImpl.class);
	}

	/**
	 * Clears the cache for the commande detail.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommandeDetail commandeDetail) {
		entityCache.removeResult(CommandeDetailImpl.class, commandeDetail);
	}

	@Override
	public void clearCache(List<CommandeDetail> commandeDetails) {
		for (CommandeDetail commandeDetail : commandeDetails) {
			entityCache.removeResult(CommandeDetailImpl.class, commandeDetail);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommandeDetailImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommandeDetailImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commande detail with the primary key. Does not add the commande detail to the database.
	 *
	 * @param idDetail the primary key for the new commande detail
	 * @return the new commande detail
	 */
	@Override
	public CommandeDetail create(long idDetail) {
		CommandeDetail commandeDetail = new CommandeDetailImpl();

		commandeDetail.setNew(true);
		commandeDetail.setPrimaryKey(idDetail);

		return commandeDetail;
	}

	/**
	 * Removes the commande detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail that was removed
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	@Override
	public CommandeDetail remove(long idDetail)
		throws NoSuchCommandeDetailException {

		return remove((Serializable)idDetail);
	}

	/**
	 * Removes the commande detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commande detail
	 * @return the commande detail that was removed
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	@Override
	public CommandeDetail remove(Serializable primaryKey)
		throws NoSuchCommandeDetailException {

		Session session = null;

		try {
			session = openSession();

			CommandeDetail commandeDetail = (CommandeDetail)session.get(
				CommandeDetailImpl.class, primaryKey);

			if (commandeDetail == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCommandeDetailException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commandeDetail);
		}
		catch (NoSuchCommandeDetailException noSuchEntityException) {
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
	protected CommandeDetail removeImpl(CommandeDetail commandeDetail) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commandeDetail)) {
				commandeDetail = (CommandeDetail)session.get(
					CommandeDetailImpl.class,
					commandeDetail.getPrimaryKeyObj());
			}

			if (commandeDetail != null) {
				session.delete(commandeDetail);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commandeDetail != null) {
			clearCache(commandeDetail);
		}

		return commandeDetail;
	}

	@Override
	public CommandeDetail updateImpl(CommandeDetail commandeDetail) {
		boolean isNew = commandeDetail.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commandeDetail);
			}
			else {
				commandeDetail = (CommandeDetail)session.merge(commandeDetail);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommandeDetailImpl.class, commandeDetail, false, true);

		if (isNew) {
			commandeDetail.setNew(false);
		}

		commandeDetail.resetOriginalValues();

		return commandeDetail;
	}

	/**
	 * Returns the commande detail with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commande detail
	 * @return the commande detail
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	@Override
	public CommandeDetail findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCommandeDetailException {

		CommandeDetail commandeDetail = fetchByPrimaryKey(primaryKey);

		if (commandeDetail == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCommandeDetailException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commandeDetail;
	}

	/**
	 * Returns the commande detail with the primary key or throws a <code>NoSuchCommandeDetailException</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	@Override
	public CommandeDetail findByPrimaryKey(long idDetail)
		throws NoSuchCommandeDetailException {

		return findByPrimaryKey((Serializable)idDetail);
	}

	/**
	 * Returns the commande detail with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail, or <code>null</code> if a commande detail with the primary key could not be found
	 */
	@Override
	public CommandeDetail fetchByPrimaryKey(long idDetail) {
		return fetchByPrimaryKey((Serializable)idDetail);
	}

	/**
	 * Returns all the commande details.
	 *
	 * @return the commande details
	 */
	@Override
	public List<CommandeDetail> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CommandeDetail> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<CommandeDetail> findAll(
		int start, int end,
		OrderByComparator<CommandeDetail> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<CommandeDetail> findAll(
		int start, int end, OrderByComparator<CommandeDetail> orderByComparator,
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

		List<CommandeDetail> list = null;

		if (useFinderCache) {
			list = (List<CommandeDetail>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMANDEDETAIL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMANDEDETAIL;

				sql = sql.concat(CommandeDetailModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommandeDetail>)QueryUtil.list(
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
	 * Removes all the commande details from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommandeDetail commandeDetail : findAll()) {
			remove(commandeDetail);
		}
	}

	/**
	 * Returns the number of commande details.
	 *
	 * @return the number of commande details
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMANDEDETAIL);

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
		return "idDetail";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMANDEDETAIL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommandeDetailModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commande detail persistence.
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

		CommandeDetailUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommandeDetailUtil.setPersistence(null);

		entityCache.removeCache(CommandeDetailImpl.class.getName());
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

	private static final String _SQL_SELECT_COMMANDEDETAIL =
		"SELECT commandeDetail FROM CommandeDetail commandeDetail";

	private static final String _SQL_COUNT_COMMANDEDETAIL =
		"SELECT COUNT(commandeDetail) FROM CommandeDetail commandeDetail";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commandeDetail.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommandeDetail exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		CommandeDetailPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
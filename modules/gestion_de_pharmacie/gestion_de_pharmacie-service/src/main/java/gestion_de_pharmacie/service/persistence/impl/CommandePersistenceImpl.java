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

import gestion_de_pharmacie.exception.NoSuchCommandeException;

import gestion_de_pharmacie.model.Commande;
import gestion_de_pharmacie.model.CommandeTable;
import gestion_de_pharmacie.model.impl.CommandeImpl;
import gestion_de_pharmacie.model.impl.CommandeModelImpl;

import gestion_de_pharmacie.service.persistence.CommandePersistence;
import gestion_de_pharmacie.service.persistence.CommandeUtil;
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
 * The persistence implementation for the commande service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = CommandePersistence.class)
public class CommandePersistenceImpl
	extends BasePersistenceImpl<Commande> implements CommandePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommandeUtil</code> to access the commande persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommandeImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public CommandePersistenceImpl() {
		setModelClass(Commande.class);

		setModelImplClass(CommandeImpl.class);
		setModelPKClass(long.class);

		setTable(CommandeTable.INSTANCE);
	}

	/**
	 * Caches the commande in the entity cache if it is enabled.
	 *
	 * @param commande the commande
	 */
	@Override
	public void cacheResult(Commande commande) {
		entityCache.putResult(
			CommandeImpl.class, commande.getPrimaryKey(), commande);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commandes in the entity cache if it is enabled.
	 *
	 * @param commandes the commandes
	 */
	@Override
	public void cacheResult(List<Commande> commandes) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commandes.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Commande commande : commandes) {
			if (entityCache.getResult(
					CommandeImpl.class, commande.getPrimaryKey()) == null) {

				cacheResult(commande);
			}
		}
	}

	/**
	 * Clears the cache for all commandes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommandeImpl.class);

		finderCache.clearCache(CommandeImpl.class);
	}

	/**
	 * Clears the cache for the commande.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Commande commande) {
		entityCache.removeResult(CommandeImpl.class, commande);
	}

	@Override
	public void clearCache(List<Commande> commandes) {
		for (Commande commande : commandes) {
			entityCache.removeResult(CommandeImpl.class, commande);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommandeImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommandeImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commande with the primary key. Does not add the commande to the database.
	 *
	 * @param idCommande the primary key for the new commande
	 * @return the new commande
	 */
	@Override
	public Commande create(long idCommande) {
		Commande commande = new CommandeImpl();

		commande.setNew(true);
		commande.setPrimaryKey(idCommande);

		return commande;
	}

	/**
	 * Removes the commande with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande that was removed
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	@Override
	public Commande remove(long idCommande) throws NoSuchCommandeException {
		return remove((Serializable)idCommande);
	}

	/**
	 * Removes the commande with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commande
	 * @return the commande that was removed
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	@Override
	public Commande remove(Serializable primaryKey)
		throws NoSuchCommandeException {

		Session session = null;

		try {
			session = openSession();

			Commande commande = (Commande)session.get(
				CommandeImpl.class, primaryKey);

			if (commande == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCommandeException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commande);
		}
		catch (NoSuchCommandeException noSuchEntityException) {
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
	protected Commande removeImpl(Commande commande) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commande)) {
				commande = (Commande)session.get(
					CommandeImpl.class, commande.getPrimaryKeyObj());
			}

			if (commande != null) {
				session.delete(commande);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commande != null) {
			clearCache(commande);
		}

		return commande;
	}

	@Override
	public Commande updateImpl(Commande commande) {
		boolean isNew = commande.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commande);
			}
			else {
				commande = (Commande)session.merge(commande);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(CommandeImpl.class, commande, false, true);

		if (isNew) {
			commande.setNew(false);
		}

		commande.resetOriginalValues();

		return commande;
	}

	/**
	 * Returns the commande with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commande
	 * @return the commande
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	@Override
	public Commande findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCommandeException {

		Commande commande = fetchByPrimaryKey(primaryKey);

		if (commande == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCommandeException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commande;
	}

	/**
	 * Returns the commande with the primary key or throws a <code>NoSuchCommandeException</code> if it could not be found.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	@Override
	public Commande findByPrimaryKey(long idCommande)
		throws NoSuchCommandeException {

		return findByPrimaryKey((Serializable)idCommande);
	}

	/**
	 * Returns the commande with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande, or <code>null</code> if a commande with the primary key could not be found
	 */
	@Override
	public Commande fetchByPrimaryKey(long idCommande) {
		return fetchByPrimaryKey((Serializable)idCommande);
	}

	/**
	 * Returns all the commandes.
	 *
	 * @return the commandes
	 */
	@Override
	public List<Commande> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<Commande> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<Commande> findAll(
		int start, int end, OrderByComparator<Commande> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<Commande> findAll(
		int start, int end, OrderByComparator<Commande> orderByComparator,
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

		List<Commande> list = null;

		if (useFinderCache) {
			list = (List<Commande>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMANDE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMANDE;

				sql = sql.concat(CommandeModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Commande>)QueryUtil.list(
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
	 * Removes all the commandes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Commande commande : findAll()) {
			remove(commande);
		}
	}

	/**
	 * Returns the number of commandes.
	 *
	 * @return the number of commandes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMANDE);

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
		return "idCommande";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMANDE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommandeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commande persistence.
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

		CommandeUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommandeUtil.setPersistence(null);

		entityCache.removeCache(CommandeImpl.class.getName());
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

	private static final String _SQL_SELECT_COMMANDE =
		"SELECT commande FROM Commande commande";

	private static final String _SQL_COUNT_COMMANDE =
		"SELECT COUNT(commande) FROM Commande commande";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commande.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Commande exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		CommandePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
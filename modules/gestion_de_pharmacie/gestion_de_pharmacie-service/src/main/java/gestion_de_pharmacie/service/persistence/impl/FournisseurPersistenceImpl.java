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

import gestion_de_pharmacie.exception.NoSuchFournisseurException;

import gestion_de_pharmacie.model.Fournisseur;
import gestion_de_pharmacie.model.FournisseurTable;
import gestion_de_pharmacie.model.impl.FournisseurImpl;
import gestion_de_pharmacie.model.impl.FournisseurModelImpl;

import gestion_de_pharmacie.service.persistence.FournisseurPersistence;
import gestion_de_pharmacie.service.persistence.FournisseurUtil;
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
 * The persistence implementation for the fournisseur service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = FournisseurPersistence.class)
public class FournisseurPersistenceImpl
	extends BasePersistenceImpl<Fournisseur> implements FournisseurPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FournisseurUtil</code> to access the fournisseur persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FournisseurImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public FournisseurPersistenceImpl() {
		setModelClass(Fournisseur.class);

		setModelImplClass(FournisseurImpl.class);
		setModelPKClass(long.class);

		setTable(FournisseurTable.INSTANCE);
	}

	/**
	 * Caches the fournisseur in the entity cache if it is enabled.
	 *
	 * @param fournisseur the fournisseur
	 */
	@Override
	public void cacheResult(Fournisseur fournisseur) {
		entityCache.putResult(
			FournisseurImpl.class, fournisseur.getPrimaryKey(), fournisseur);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fournisseurs in the entity cache if it is enabled.
	 *
	 * @param fournisseurs the fournisseurs
	 */
	@Override
	public void cacheResult(List<Fournisseur> fournisseurs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fournisseurs.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Fournisseur fournisseur : fournisseurs) {
			if (entityCache.getResult(
					FournisseurImpl.class, fournisseur.getPrimaryKey()) ==
						null) {

				cacheResult(fournisseur);
			}
		}
	}

	/**
	 * Clears the cache for all fournisseurs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FournisseurImpl.class);

		finderCache.clearCache(FournisseurImpl.class);
	}

	/**
	 * Clears the cache for the fournisseur.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Fournisseur fournisseur) {
		entityCache.removeResult(FournisseurImpl.class, fournisseur);
	}

	@Override
	public void clearCache(List<Fournisseur> fournisseurs) {
		for (Fournisseur fournisseur : fournisseurs) {
			entityCache.removeResult(FournisseurImpl.class, fournisseur);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FournisseurImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FournisseurImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new fournisseur with the primary key. Does not add the fournisseur to the database.
	 *
	 * @param idFournisseur the primary key for the new fournisseur
	 * @return the new fournisseur
	 */
	@Override
	public Fournisseur create(long idFournisseur) {
		Fournisseur fournisseur = new FournisseurImpl();

		fournisseur.setNew(true);
		fournisseur.setPrimaryKey(idFournisseur);

		return fournisseur;
	}

	/**
	 * Removes the fournisseur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idFournisseur the primary key of the fournisseur
	 * @return the fournisseur that was removed
	 * @throws NoSuchFournisseurException if a fournisseur with the primary key could not be found
	 */
	@Override
	public Fournisseur remove(long idFournisseur)
		throws NoSuchFournisseurException {

		return remove((Serializable)idFournisseur);
	}

	/**
	 * Removes the fournisseur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fournisseur
	 * @return the fournisseur that was removed
	 * @throws NoSuchFournisseurException if a fournisseur with the primary key could not be found
	 */
	@Override
	public Fournisseur remove(Serializable primaryKey)
		throws NoSuchFournisseurException {

		Session session = null;

		try {
			session = openSession();

			Fournisseur fournisseur = (Fournisseur)session.get(
				FournisseurImpl.class, primaryKey);

			if (fournisseur == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFournisseurException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fournisseur);
		}
		catch (NoSuchFournisseurException noSuchEntityException) {
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
	protected Fournisseur removeImpl(Fournisseur fournisseur) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fournisseur)) {
				fournisseur = (Fournisseur)session.get(
					FournisseurImpl.class, fournisseur.getPrimaryKeyObj());
			}

			if (fournisseur != null) {
				session.delete(fournisseur);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fournisseur != null) {
			clearCache(fournisseur);
		}

		return fournisseur;
	}

	@Override
	public Fournisseur updateImpl(Fournisseur fournisseur) {
		boolean isNew = fournisseur.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(fournisseur);
			}
			else {
				fournisseur = (Fournisseur)session.merge(fournisseur);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(FournisseurImpl.class, fournisseur, false, true);

		if (isNew) {
			fournisseur.setNew(false);
		}

		fournisseur.resetOriginalValues();

		return fournisseur;
	}

	/**
	 * Returns the fournisseur with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fournisseur
	 * @return the fournisseur
	 * @throws NoSuchFournisseurException if a fournisseur with the primary key could not be found
	 */
	@Override
	public Fournisseur findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFournisseurException {

		Fournisseur fournisseur = fetchByPrimaryKey(primaryKey);

		if (fournisseur == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFournisseurException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fournisseur;
	}

	/**
	 * Returns the fournisseur with the primary key or throws a <code>NoSuchFournisseurException</code> if it could not be found.
	 *
	 * @param idFournisseur the primary key of the fournisseur
	 * @return the fournisseur
	 * @throws NoSuchFournisseurException if a fournisseur with the primary key could not be found
	 */
	@Override
	public Fournisseur findByPrimaryKey(long idFournisseur)
		throws NoSuchFournisseurException {

		return findByPrimaryKey((Serializable)idFournisseur);
	}

	/**
	 * Returns the fournisseur with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idFournisseur the primary key of the fournisseur
	 * @return the fournisseur, or <code>null</code> if a fournisseur with the primary key could not be found
	 */
	@Override
	public Fournisseur fetchByPrimaryKey(long idFournisseur) {
		return fetchByPrimaryKey((Serializable)idFournisseur);
	}

	/**
	 * Returns all the fournisseurs.
	 *
	 * @return the fournisseurs
	 */
	@Override
	public List<Fournisseur> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fournisseurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FournisseurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fournisseurs
	 * @param end the upper bound of the range of fournisseurs (not inclusive)
	 * @return the range of fournisseurs
	 */
	@Override
	public List<Fournisseur> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fournisseurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FournisseurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fournisseurs
	 * @param end the upper bound of the range of fournisseurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fournisseurs
	 */
	@Override
	public List<Fournisseur> findAll(
		int start, int end, OrderByComparator<Fournisseur> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fournisseurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FournisseurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fournisseurs
	 * @param end the upper bound of the range of fournisseurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fournisseurs
	 */
	@Override
	public List<Fournisseur> findAll(
		int start, int end, OrderByComparator<Fournisseur> orderByComparator,
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

		List<Fournisseur> list = null;

		if (useFinderCache) {
			list = (List<Fournisseur>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FOURNISSEUR);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FOURNISSEUR;

				sql = sql.concat(FournisseurModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Fournisseur>)QueryUtil.list(
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
	 * Removes all the fournisseurs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Fournisseur fournisseur : findAll()) {
			remove(fournisseur);
		}
	}

	/**
	 * Returns the number of fournisseurs.
	 *
	 * @return the number of fournisseurs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FOURNISSEUR);

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
		return "idFournisseur";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FOURNISSEUR;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FournisseurModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the fournisseur persistence.
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

		FournisseurUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FournisseurUtil.setPersistence(null);

		entityCache.removeCache(FournisseurImpl.class.getName());
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

	private static final String _SQL_SELECT_FOURNISSEUR =
		"SELECT fournisseur FROM Fournisseur fournisseur";

	private static final String _SQL_COUNT_FOURNISSEUR =
		"SELECT COUNT(fournisseur) FROM Fournisseur fournisseur";

	private static final String _ORDER_BY_ENTITY_ALIAS = "fournisseur.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Fournisseur exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		FournisseurPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
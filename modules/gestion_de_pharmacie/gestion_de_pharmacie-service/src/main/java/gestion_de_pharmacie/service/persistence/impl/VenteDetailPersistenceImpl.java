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

import gestion_de_pharmacie.exception.NoSuchVenteDetailException;

import gestion_de_pharmacie.model.VenteDetail;
import gestion_de_pharmacie.model.VenteDetailTable;
import gestion_de_pharmacie.model.impl.VenteDetailImpl;
import gestion_de_pharmacie.model.impl.VenteDetailModelImpl;

import gestion_de_pharmacie.service.persistence.VenteDetailPersistence;
import gestion_de_pharmacie.service.persistence.VenteDetailUtil;
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
 * The persistence implementation for the vente detail service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = VenteDetailPersistence.class)
public class VenteDetailPersistenceImpl
	extends BasePersistenceImpl<VenteDetail> implements VenteDetailPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>VenteDetailUtil</code> to access the vente detail persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		VenteDetailImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;

	public VenteDetailPersistenceImpl() {
		setModelClass(VenteDetail.class);

		setModelImplClass(VenteDetailImpl.class);
		setModelPKClass(long.class);

		setTable(VenteDetailTable.INSTANCE);
	}

	/**
	 * Caches the vente detail in the entity cache if it is enabled.
	 *
	 * @param venteDetail the vente detail
	 */
	@Override
	public void cacheResult(VenteDetail venteDetail) {
		entityCache.putResult(
			VenteDetailImpl.class, venteDetail.getPrimaryKey(), venteDetail);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the vente details in the entity cache if it is enabled.
	 *
	 * @param venteDetails the vente details
	 */
	@Override
	public void cacheResult(List<VenteDetail> venteDetails) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (venteDetails.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (VenteDetail venteDetail : venteDetails) {
			if (entityCache.getResult(
					VenteDetailImpl.class, venteDetail.getPrimaryKey()) ==
						null) {

				cacheResult(venteDetail);
			}
		}
	}

	/**
	 * Clears the cache for all vente details.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(VenteDetailImpl.class);

		finderCache.clearCache(VenteDetailImpl.class);
	}

	/**
	 * Clears the cache for the vente detail.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(VenteDetail venteDetail) {
		entityCache.removeResult(VenteDetailImpl.class, venteDetail);
	}

	@Override
	public void clearCache(List<VenteDetail> venteDetails) {
		for (VenteDetail venteDetail : venteDetails) {
			entityCache.removeResult(VenteDetailImpl.class, venteDetail);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(VenteDetailImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(VenteDetailImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new vente detail with the primary key. Does not add the vente detail to the database.
	 *
	 * @param idDetail the primary key for the new vente detail
	 * @return the new vente detail
	 */
	@Override
	public VenteDetail create(long idDetail) {
		VenteDetail venteDetail = new VenteDetailImpl();

		venteDetail.setNew(true);
		venteDetail.setPrimaryKey(idDetail);

		return venteDetail;
	}

	/**
	 * Removes the vente detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail that was removed
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	@Override
	public VenteDetail remove(long idDetail) throws NoSuchVenteDetailException {
		return remove((Serializable)idDetail);
	}

	/**
	 * Removes the vente detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the vente detail
	 * @return the vente detail that was removed
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	@Override
	public VenteDetail remove(Serializable primaryKey)
		throws NoSuchVenteDetailException {

		Session session = null;

		try {
			session = openSession();

			VenteDetail venteDetail = (VenteDetail)session.get(
				VenteDetailImpl.class, primaryKey);

			if (venteDetail == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVenteDetailException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(venteDetail);
		}
		catch (NoSuchVenteDetailException noSuchEntityException) {
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
	protected VenteDetail removeImpl(VenteDetail venteDetail) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(venteDetail)) {
				venteDetail = (VenteDetail)session.get(
					VenteDetailImpl.class, venteDetail.getPrimaryKeyObj());
			}

			if (venteDetail != null) {
				session.delete(venteDetail);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (venteDetail != null) {
			clearCache(venteDetail);
		}

		return venteDetail;
	}

	@Override
	public VenteDetail updateImpl(VenteDetail venteDetail) {
		boolean isNew = venteDetail.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(venteDetail);
			}
			else {
				venteDetail = (VenteDetail)session.merge(venteDetail);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(VenteDetailImpl.class, venteDetail, false, true);

		if (isNew) {
			venteDetail.setNew(false);
		}

		venteDetail.resetOriginalValues();

		return venteDetail;
	}

	/**
	 * Returns the vente detail with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the vente detail
	 * @return the vente detail
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	@Override
	public VenteDetail findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVenteDetailException {

		VenteDetail venteDetail = fetchByPrimaryKey(primaryKey);

		if (venteDetail == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVenteDetailException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return venteDetail;
	}

	/**
	 * Returns the vente detail with the primary key or throws a <code>NoSuchVenteDetailException</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	@Override
	public VenteDetail findByPrimaryKey(long idDetail)
		throws NoSuchVenteDetailException {

		return findByPrimaryKey((Serializable)idDetail);
	}

	/**
	 * Returns the vente detail with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail, or <code>null</code> if a vente detail with the primary key could not be found
	 */
	@Override
	public VenteDetail fetchByPrimaryKey(long idDetail) {
		return fetchByPrimaryKey((Serializable)idDetail);
	}

	/**
	 * Returns all the vente details.
	 *
	 * @return the vente details
	 */
	@Override
	public List<VenteDetail> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the vente details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @return the range of vente details
	 */
	@Override
	public List<VenteDetail> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the vente details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of vente details
	 */
	@Override
	public List<VenteDetail> findAll(
		int start, int end, OrderByComparator<VenteDetail> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the vente details.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of vente details
	 */
	@Override
	public List<VenteDetail> findAll(
		int start, int end, OrderByComparator<VenteDetail> orderByComparator,
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

		List<VenteDetail> list = null;

		if (useFinderCache) {
			list = (List<VenteDetail>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_VENTEDETAIL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_VENTEDETAIL;

				sql = sql.concat(VenteDetailModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<VenteDetail>)QueryUtil.list(
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
	 * Removes all the vente details from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (VenteDetail venteDetail : findAll()) {
			remove(venteDetail);
		}
	}

	/**
	 * Returns the number of vente details.
	 *
	 * @return the number of vente details
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_VENTEDETAIL);

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
		return _SQL_SELECT_VENTEDETAIL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return VenteDetailModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the vente detail persistence.
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

		VenteDetailUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		VenteDetailUtil.setPersistence(null);

		entityCache.removeCache(VenteDetailImpl.class.getName());
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

	private static final String _SQL_SELECT_VENTEDETAIL =
		"SELECT venteDetail FROM VenteDetail venteDetail";

	private static final String _SQL_COUNT_VENTEDETAIL =
		"SELECT COUNT(venteDetail) FROM VenteDetail venteDetail";

	private static final String _ORDER_BY_ENTITY_ALIAS = "venteDetail.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No VenteDetail exists with the primary key ";

	private static final Log _log = LogFactoryUtil.getLog(
		VenteDetailPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
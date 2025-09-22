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
import com.liferay.portal.kernel.util.SetUtil;

import gestion_de_pharmacie.exception.NoSuchUtilisateurException;

import gestion_de_pharmacie.model.Utilisateur;
import gestion_de_pharmacie.model.UtilisateurTable;
import gestion_de_pharmacie.model.impl.UtilisateurImpl;
import gestion_de_pharmacie.model.impl.UtilisateurModelImpl;

import gestion_de_pharmacie.service.persistence.UtilisateurPersistence;
import gestion_de_pharmacie.service.persistence.UtilisateurUtil;
import gestion_de_pharmacie.service.persistence.impl.constants.PharmaPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the utilisateur service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = UtilisateurPersistence.class)
public class UtilisateurPersistenceImpl
	extends BasePersistenceImpl<Utilisateur> implements UtilisateurPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UtilisateurUtil</code> to access the utilisateur persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UtilisateurImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByEmail;

	/**
	 * Returns the utilisateur where email = &#63; or throws a <code>NoSuchUtilisateurException</code> if it could not be found.
	 *
	 * @param email the email
	 * @return the matching utilisateur
	 * @throws NoSuchUtilisateurException if a matching utilisateur could not be found
	 */
	@Override
	public Utilisateur findByEmail(String email)
		throws NoSuchUtilisateurException {

		Utilisateur utilisateur = fetchByEmail(email);

		if (utilisateur == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("email=");
			sb.append(email);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUtilisateurException(sb.toString());
		}

		return utilisateur;
	}

	/**
	 * Returns the utilisateur where email = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param email the email
	 * @return the matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	@Override
	public Utilisateur fetchByEmail(String email) {
		return fetchByEmail(email, true);
	}

	/**
	 * Returns the utilisateur where email = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param email the email
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	@Override
	public Utilisateur fetchByEmail(String email, boolean useFinderCache) {
		email = Objects.toString(email, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {email};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByEmail, finderArgs, this);
		}

		if (result instanceof Utilisateur) {
			Utilisateur utilisateur = (Utilisateur)result;

			if (!Objects.equals(email, utilisateur.getEmail())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_UTILISATEUR_WHERE);

			boolean bindEmail = false;

			if (email.isEmpty()) {
				sb.append(_FINDER_COLUMN_EMAIL_EMAIL_3);
			}
			else {
				bindEmail = true;

				sb.append(_FINDER_COLUMN_EMAIL_EMAIL_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmail) {
					queryPos.add(email);
				}

				List<Utilisateur> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByEmail, finderArgs, list);
					}
				}
				else {
					Utilisateur utilisateur = list.get(0);

					result = utilisateur;

					cacheResult(utilisateur);
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
			return (Utilisateur)result;
		}
	}

	/**
	 * Removes the utilisateur where email = &#63; from the database.
	 *
	 * @param email the email
	 * @return the utilisateur that was removed
	 */
	@Override
	public Utilisateur removeByEmail(String email)
		throws NoSuchUtilisateurException {

		Utilisateur utilisateur = findByEmail(email);

		return remove(utilisateur);
	}

	/**
	 * Returns the number of utilisateurs where email = &#63;.
	 *
	 * @param email the email
	 * @return the number of matching utilisateurs
	 */
	@Override
	public int countByEmail(String email) {
		Utilisateur utilisateur = fetchByEmail(email);

		if (utilisateur == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_EMAIL_EMAIL_2 =
		"utilisateur.email = ?";

	private static final String _FINDER_COLUMN_EMAIL_EMAIL_3 =
		"(utilisateur.email IS NULL OR utilisateur.email = '')";

	private FinderPath _finderPathWithPaginationFindByRole;
	private FinderPath _finderPathWithoutPaginationFindByRole;
	private FinderPath _finderPathCountByRole;

	/**
	 * Returns all the utilisateurs where role = &#63;.
	 *
	 * @param role the role
	 * @return the matching utilisateurs
	 */
	@Override
	public List<Utilisateur> findByRole(String role) {
		return findByRole(role, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the utilisateurs where role = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param role the role
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @return the range of matching utilisateurs
	 */
	@Override
	public List<Utilisateur> findByRole(String role, int start, int end) {
		return findByRole(role, start, end, null);
	}

	/**
	 * Returns an ordered range of all the utilisateurs where role = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param role the role
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching utilisateurs
	 */
	@Override
	public List<Utilisateur> findByRole(
		String role, int start, int end,
		OrderByComparator<Utilisateur> orderByComparator) {

		return findByRole(role, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the utilisateurs where role = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param role the role
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching utilisateurs
	 */
	@Override
	public List<Utilisateur> findByRole(
		String role, int start, int end,
		OrderByComparator<Utilisateur> orderByComparator,
		boolean useFinderCache) {

		role = Objects.toString(role, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRole;
				finderArgs = new Object[] {role};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRole;
			finderArgs = new Object[] {role, start, end, orderByComparator};
		}

		List<Utilisateur> list = null;

		if (useFinderCache) {
			list = (List<Utilisateur>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Utilisateur utilisateur : list) {
					if (!role.equals(utilisateur.getRole())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_UTILISATEUR_WHERE);

			boolean bindRole = false;

			if (role.isEmpty()) {
				sb.append(_FINDER_COLUMN_ROLE_ROLE_3);
			}
			else {
				bindRole = true;

				sb.append(_FINDER_COLUMN_ROLE_ROLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UtilisateurModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindRole) {
					queryPos.add(role);
				}

				list = (List<Utilisateur>)QueryUtil.list(
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
	 * Returns the first utilisateur in the ordered set where role = &#63;.
	 *
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching utilisateur
	 * @throws NoSuchUtilisateurException if a matching utilisateur could not be found
	 */
	@Override
	public Utilisateur findByRole_First(
			String role, OrderByComparator<Utilisateur> orderByComparator)
		throws NoSuchUtilisateurException {

		Utilisateur utilisateur = fetchByRole_First(role, orderByComparator);

		if (utilisateur != null) {
			return utilisateur;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("role=");
		sb.append(role);

		sb.append("}");

		throw new NoSuchUtilisateurException(sb.toString());
	}

	/**
	 * Returns the first utilisateur in the ordered set where role = &#63;.
	 *
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	@Override
	public Utilisateur fetchByRole_First(
		String role, OrderByComparator<Utilisateur> orderByComparator) {

		List<Utilisateur> list = findByRole(role, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last utilisateur in the ordered set where role = &#63;.
	 *
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching utilisateur
	 * @throws NoSuchUtilisateurException if a matching utilisateur could not be found
	 */
	@Override
	public Utilisateur findByRole_Last(
			String role, OrderByComparator<Utilisateur> orderByComparator)
		throws NoSuchUtilisateurException {

		Utilisateur utilisateur = fetchByRole_Last(role, orderByComparator);

		if (utilisateur != null) {
			return utilisateur;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("role=");
		sb.append(role);

		sb.append("}");

		throw new NoSuchUtilisateurException(sb.toString());
	}

	/**
	 * Returns the last utilisateur in the ordered set where role = &#63;.
	 *
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	@Override
	public Utilisateur fetchByRole_Last(
		String role, OrderByComparator<Utilisateur> orderByComparator) {

		int count = countByRole(role);

		if (count == 0) {
			return null;
		}

		List<Utilisateur> list = findByRole(
			role, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the utilisateurs before and after the current utilisateur in the ordered set where role = &#63;.
	 *
	 * @param idUtilisateur the primary key of the current utilisateur
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next utilisateur
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	@Override
	public Utilisateur[] findByRole_PrevAndNext(
			long idUtilisateur, String role,
			OrderByComparator<Utilisateur> orderByComparator)
		throws NoSuchUtilisateurException {

		role = Objects.toString(role, "");

		Utilisateur utilisateur = findByPrimaryKey(idUtilisateur);

		Session session = null;

		try {
			session = openSession();

			Utilisateur[] array = new UtilisateurImpl[3];

			array[0] = getByRole_PrevAndNext(
				session, utilisateur, role, orderByComparator, true);

			array[1] = utilisateur;

			array[2] = getByRole_PrevAndNext(
				session, utilisateur, role, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Utilisateur getByRole_PrevAndNext(
		Session session, Utilisateur utilisateur, String role,
		OrderByComparator<Utilisateur> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_UTILISATEUR_WHERE);

		boolean bindRole = false;

		if (role.isEmpty()) {
			sb.append(_FINDER_COLUMN_ROLE_ROLE_3);
		}
		else {
			bindRole = true;

			sb.append(_FINDER_COLUMN_ROLE_ROLE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(UtilisateurModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindRole) {
			queryPos.add(role);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(utilisateur)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Utilisateur> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the utilisateurs where role = &#63; from the database.
	 *
	 * @param role the role
	 */
	@Override
	public void removeByRole(String role) {
		for (Utilisateur utilisateur :
				findByRole(role, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(utilisateur);
		}
	}

	/**
	 * Returns the number of utilisateurs where role = &#63;.
	 *
	 * @param role the role
	 * @return the number of matching utilisateurs
	 */
	@Override
	public int countByRole(String role) {
		role = Objects.toString(role, "");

		FinderPath finderPath = _finderPathCountByRole;

		Object[] finderArgs = new Object[] {role};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_UTILISATEUR_WHERE);

			boolean bindRole = false;

			if (role.isEmpty()) {
				sb.append(_FINDER_COLUMN_ROLE_ROLE_3);
			}
			else {
				bindRole = true;

				sb.append(_FINDER_COLUMN_ROLE_ROLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindRole) {
					queryPos.add(role);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_ROLE_ROLE_2 =
		"utilisateur.role = ?";

	private static final String _FINDER_COLUMN_ROLE_ROLE_3 =
		"(utilisateur.role IS NULL OR utilisateur.role = '')";

	public UtilisateurPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("role", "role_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Utilisateur.class);

		setModelImplClass(UtilisateurImpl.class);
		setModelPKClass(long.class);

		setTable(UtilisateurTable.INSTANCE);
	}

	/**
	 * Caches the utilisateur in the entity cache if it is enabled.
	 *
	 * @param utilisateur the utilisateur
	 */
	@Override
	public void cacheResult(Utilisateur utilisateur) {
		entityCache.putResult(
			UtilisateurImpl.class, utilisateur.getPrimaryKey(), utilisateur);

		finderCache.putResult(
			_finderPathFetchByEmail, new Object[] {utilisateur.getEmail()},
			utilisateur);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the utilisateurs in the entity cache if it is enabled.
	 *
	 * @param utilisateurs the utilisateurs
	 */
	@Override
	public void cacheResult(List<Utilisateur> utilisateurs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (utilisateurs.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Utilisateur utilisateur : utilisateurs) {
			if (entityCache.getResult(
					UtilisateurImpl.class, utilisateur.getPrimaryKey()) ==
						null) {

				cacheResult(utilisateur);
			}
		}
	}

	/**
	 * Clears the cache for all utilisateurs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(UtilisateurImpl.class);

		finderCache.clearCache(UtilisateurImpl.class);
	}

	/**
	 * Clears the cache for the utilisateur.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Utilisateur utilisateur) {
		entityCache.removeResult(UtilisateurImpl.class, utilisateur);
	}

	@Override
	public void clearCache(List<Utilisateur> utilisateurs) {
		for (Utilisateur utilisateur : utilisateurs) {
			entityCache.removeResult(UtilisateurImpl.class, utilisateur);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(UtilisateurImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(UtilisateurImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		UtilisateurModelImpl utilisateurModelImpl) {

		Object[] args = new Object[] {utilisateurModelImpl.getEmail()};

		finderCache.putResult(
			_finderPathFetchByEmail, args, utilisateurModelImpl);
	}

	/**
	 * Creates a new utilisateur with the primary key. Does not add the utilisateur to the database.
	 *
	 * @param idUtilisateur the primary key for the new utilisateur
	 * @return the new utilisateur
	 */
	@Override
	public Utilisateur create(long idUtilisateur) {
		Utilisateur utilisateur = new UtilisateurImpl();

		utilisateur.setNew(true);
		utilisateur.setPrimaryKey(idUtilisateur);

		return utilisateur;
	}

	/**
	 * Removes the utilisateur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur that was removed
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	@Override
	public Utilisateur remove(long idUtilisateur)
		throws NoSuchUtilisateurException {

		return remove((Serializable)idUtilisateur);
	}

	/**
	 * Removes the utilisateur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the utilisateur
	 * @return the utilisateur that was removed
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	@Override
	public Utilisateur remove(Serializable primaryKey)
		throws NoSuchUtilisateurException {

		Session session = null;

		try {
			session = openSession();

			Utilisateur utilisateur = (Utilisateur)session.get(
				UtilisateurImpl.class, primaryKey);

			if (utilisateur == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUtilisateurException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(utilisateur);
		}
		catch (NoSuchUtilisateurException noSuchEntityException) {
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
	protected Utilisateur removeImpl(Utilisateur utilisateur) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(utilisateur)) {
				utilisateur = (Utilisateur)session.get(
					UtilisateurImpl.class, utilisateur.getPrimaryKeyObj());
			}

			if (utilisateur != null) {
				session.delete(utilisateur);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (utilisateur != null) {
			clearCache(utilisateur);
		}

		return utilisateur;
	}

	@Override
	public Utilisateur updateImpl(Utilisateur utilisateur) {
		boolean isNew = utilisateur.isNew();

		if (!(utilisateur instanceof UtilisateurModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(utilisateur.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(utilisateur);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in utilisateur proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Utilisateur implementation " +
					utilisateur.getClass());
		}

		UtilisateurModelImpl utilisateurModelImpl =
			(UtilisateurModelImpl)utilisateur;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(utilisateur);
			}
			else {
				utilisateur = (Utilisateur)session.merge(utilisateur);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			UtilisateurImpl.class, utilisateurModelImpl, false, true);

		cacheUniqueFindersCache(utilisateurModelImpl);

		if (isNew) {
			utilisateur.setNew(false);
		}

		utilisateur.resetOriginalValues();

		return utilisateur;
	}

	/**
	 * Returns the utilisateur with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the utilisateur
	 * @return the utilisateur
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	@Override
	public Utilisateur findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUtilisateurException {

		Utilisateur utilisateur = fetchByPrimaryKey(primaryKey);

		if (utilisateur == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUtilisateurException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return utilisateur;
	}

	/**
	 * Returns the utilisateur with the primary key or throws a <code>NoSuchUtilisateurException</code> if it could not be found.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	@Override
	public Utilisateur findByPrimaryKey(long idUtilisateur)
		throws NoSuchUtilisateurException {

		return findByPrimaryKey((Serializable)idUtilisateur);
	}

	/**
	 * Returns the utilisateur with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur, or <code>null</code> if a utilisateur with the primary key could not be found
	 */
	@Override
	public Utilisateur fetchByPrimaryKey(long idUtilisateur) {
		return fetchByPrimaryKey((Serializable)idUtilisateur);
	}

	/**
	 * Returns all the utilisateurs.
	 *
	 * @return the utilisateurs
	 */
	@Override
	public List<Utilisateur> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the utilisateurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @return the range of utilisateurs
	 */
	@Override
	public List<Utilisateur> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the utilisateurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of utilisateurs
	 */
	@Override
	public List<Utilisateur> findAll(
		int start, int end, OrderByComparator<Utilisateur> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the utilisateurs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UtilisateurModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of utilisateurs
	 * @param end the upper bound of the range of utilisateurs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of utilisateurs
	 */
	@Override
	public List<Utilisateur> findAll(
		int start, int end, OrderByComparator<Utilisateur> orderByComparator,
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

		List<Utilisateur> list = null;

		if (useFinderCache) {
			list = (List<Utilisateur>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_UTILISATEUR);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_UTILISATEUR;

				sql = sql.concat(UtilisateurModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Utilisateur>)QueryUtil.list(
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
	 * Removes all the utilisateurs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Utilisateur utilisateur : findAll()) {
			remove(utilisateur);
		}
	}

	/**
	 * Returns the number of utilisateurs.
	 *
	 * @return the number of utilisateurs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_UTILISATEUR);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "idUtilisateur";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_UTILISATEUR;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UtilisateurModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the utilisateur persistence.
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

		_finderPathFetchByEmail = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByEmail",
			new String[] {String.class.getName()}, new String[] {"email"},
			true);

		_finderPathWithPaginationFindByRole = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRole",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"role_"}, true);

		_finderPathWithoutPaginationFindByRole = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRole",
			new String[] {String.class.getName()}, new String[] {"role_"},
			true);

		_finderPathCountByRole = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRole",
			new String[] {String.class.getName()}, new String[] {"role_"},
			false);

		UtilisateurUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		UtilisateurUtil.setPersistence(null);

		entityCache.removeCache(UtilisateurImpl.class.getName());
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

	private static final String _SQL_SELECT_UTILISATEUR =
		"SELECT utilisateur FROM Utilisateur utilisateur";

	private static final String _SQL_SELECT_UTILISATEUR_WHERE =
		"SELECT utilisateur FROM Utilisateur utilisateur WHERE ";

	private static final String _SQL_COUNT_UTILISATEUR =
		"SELECT COUNT(utilisateur) FROM Utilisateur utilisateur";

	private static final String _SQL_COUNT_UTILISATEUR_WHERE =
		"SELECT COUNT(utilisateur) FROM Utilisateur utilisateur WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "utilisateur.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Utilisateur exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Utilisateur exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UtilisateurPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"role"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
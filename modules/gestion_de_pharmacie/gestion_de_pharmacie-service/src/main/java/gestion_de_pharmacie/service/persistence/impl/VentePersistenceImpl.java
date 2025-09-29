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

import gestion_de_pharmacie.exception.NoSuchVenteException;

import gestion_de_pharmacie.model.Vente;
import gestion_de_pharmacie.model.VenteTable;
import gestion_de_pharmacie.model.impl.VenteImpl;
import gestion_de_pharmacie.model.impl.VenteModelImpl;

import gestion_de_pharmacie.service.persistence.VentePersistence;
import gestion_de_pharmacie.service.persistence.VenteUtil;
import gestion_de_pharmacie.service.persistence.impl.constants.PharmaPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Date;
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
 * The persistence implementation for the vente service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = VentePersistence.class)
public class VentePersistenceImpl
	extends BasePersistenceImpl<Vente> implements VentePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>VenteUtil</code> to access the vente persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		VenteImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUser;
	private FinderPath _finderPathWithoutPaginationFindByUser;
	private FinderPath _finderPathCountByUser;

	/**
	 * Returns all the ventes where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the matching ventes
	 */
	@Override
	public List<Vente> findByUser(long idUtilisateur) {
		return findByUser(
			idUtilisateur, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ventes where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @return the range of matching ventes
	 */
	@Override
	public List<Vente> findByUser(long idUtilisateur, int start, int end) {
		return findByUser(idUtilisateur, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ventes where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ventes
	 */
	@Override
	public List<Vente> findByUser(
		long idUtilisateur, int start, int end,
		OrderByComparator<Vente> orderByComparator) {

		return findByUser(idUtilisateur, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ventes where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ventes
	 */
	@Override
	public List<Vente> findByUser(
		long idUtilisateur, int start, int end,
		OrderByComparator<Vente> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUser;
				finderArgs = new Object[] {idUtilisateur};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUser;
			finderArgs = new Object[] {
				idUtilisateur, start, end, orderByComparator
			};
		}

		List<Vente> list = null;

		if (useFinderCache) {
			list = (List<Vente>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Vente vente : list) {
					if (idUtilisateur != vente.getIdUtilisateur()) {
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

			sb.append(_SQL_SELECT_VENTE_WHERE);

			sb.append(_FINDER_COLUMN_USER_IDUTILISATEUR_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(VenteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idUtilisateur);

				list = (List<Vente>)QueryUtil.list(
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
	 * Returns the first vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	@Override
	public Vente findByUser_First(
			long idUtilisateur, OrderByComparator<Vente> orderByComparator)
		throws NoSuchVenteException {

		Vente vente = fetchByUser_First(idUtilisateur, orderByComparator);

		if (vente != null) {
			return vente;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idUtilisateur=");
		sb.append(idUtilisateur);

		sb.append("}");

		throw new NoSuchVenteException(sb.toString());
	}

	/**
	 * Returns the first vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente, or <code>null</code> if a matching vente could not be found
	 */
	@Override
	public Vente fetchByUser_First(
		long idUtilisateur, OrderByComparator<Vente> orderByComparator) {

		List<Vente> list = findByUser(idUtilisateur, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	@Override
	public Vente findByUser_Last(
			long idUtilisateur, OrderByComparator<Vente> orderByComparator)
		throws NoSuchVenteException {

		Vente vente = fetchByUser_Last(idUtilisateur, orderByComparator);

		if (vente != null) {
			return vente;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idUtilisateur=");
		sb.append(idUtilisateur);

		sb.append("}");

		throw new NoSuchVenteException(sb.toString());
	}

	/**
	 * Returns the last vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente, or <code>null</code> if a matching vente could not be found
	 */
	@Override
	public Vente fetchByUser_Last(
		long idUtilisateur, OrderByComparator<Vente> orderByComparator) {

		int count = countByUser(idUtilisateur);

		if (count == 0) {
			return null;
		}

		List<Vente> list = findByUser(
			idUtilisateur, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ventes before and after the current vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idVente the primary key of the current vente
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	@Override
	public Vente[] findByUser_PrevAndNext(
			long idVente, long idUtilisateur,
			OrderByComparator<Vente> orderByComparator)
		throws NoSuchVenteException {

		Vente vente = findByPrimaryKey(idVente);

		Session session = null;

		try {
			session = openSession();

			Vente[] array = new VenteImpl[3];

			array[0] = getByUser_PrevAndNext(
				session, vente, idUtilisateur, orderByComparator, true);

			array[1] = vente;

			array[2] = getByUser_PrevAndNext(
				session, vente, idUtilisateur, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Vente getByUser_PrevAndNext(
		Session session, Vente vente, long idUtilisateur,
		OrderByComparator<Vente> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_VENTE_WHERE);

		sb.append(_FINDER_COLUMN_USER_IDUTILISATEUR_2);

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
			sb.append(VenteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(idUtilisateur);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(vente)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Vente> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ventes where idUtilisateur = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 */
	@Override
	public void removeByUser(long idUtilisateur) {
		for (Vente vente :
				findByUser(
					idUtilisateur, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(vente);
		}
	}

	/**
	 * Returns the number of ventes where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the number of matching ventes
	 */
	@Override
	public int countByUser(long idUtilisateur) {
		FinderPath finderPath = _finderPathCountByUser;

		Object[] finderArgs = new Object[] {idUtilisateur};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_VENTE_WHERE);

			sb.append(_FINDER_COLUMN_USER_IDUTILISATEUR_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idUtilisateur);

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

	private static final String _FINDER_COLUMN_USER_IDUTILISATEUR_2 =
		"vente.idUtilisateur = ?";

	private FinderPath _finderPathWithPaginationFindByDate;
	private FinderPath _finderPathWithoutPaginationFindByDate;
	private FinderPath _finderPathCountByDate;

	/**
	 * Returns all the ventes where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @return the matching ventes
	 */
	@Override
	public List<Vente> findByDate(Date dateVente) {
		return findByDate(
			dateVente, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ventes where dateVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param dateVente the date vente
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @return the range of matching ventes
	 */
	@Override
	public List<Vente> findByDate(Date dateVente, int start, int end) {
		return findByDate(dateVente, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ventes where dateVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param dateVente the date vente
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ventes
	 */
	@Override
	public List<Vente> findByDate(
		Date dateVente, int start, int end,
		OrderByComparator<Vente> orderByComparator) {

		return findByDate(dateVente, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ventes where dateVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param dateVente the date vente
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ventes
	 */
	@Override
	public List<Vente> findByDate(
		Date dateVente, int start, int end,
		OrderByComparator<Vente> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByDate;
				finderArgs = new Object[] {_getTime(dateVente)};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDate;
			finderArgs = new Object[] {
				_getTime(dateVente), start, end, orderByComparator
			};
		}

		List<Vente> list = null;

		if (useFinderCache) {
			list = (List<Vente>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Vente vente : list) {
					if (!Objects.equals(dateVente, vente.getDateVente())) {
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

			sb.append(_SQL_SELECT_VENTE_WHERE);

			boolean bindDateVente = false;

			if (dateVente == null) {
				sb.append(_FINDER_COLUMN_DATE_DATEVENTE_1);
			}
			else {
				bindDateVente = true;

				sb.append(_FINDER_COLUMN_DATE_DATEVENTE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(VenteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDateVente) {
					queryPos.add(new Timestamp(dateVente.getTime()));
				}

				list = (List<Vente>)QueryUtil.list(
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
	 * Returns the first vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	@Override
	public Vente findByDate_First(
			Date dateVente, OrderByComparator<Vente> orderByComparator)
		throws NoSuchVenteException {

		Vente vente = fetchByDate_First(dateVente, orderByComparator);

		if (vente != null) {
			return vente;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dateVente=");
		sb.append(dateVente);

		sb.append("}");

		throw new NoSuchVenteException(sb.toString());
	}

	/**
	 * Returns the first vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente, or <code>null</code> if a matching vente could not be found
	 */
	@Override
	public Vente fetchByDate_First(
		Date dateVente, OrderByComparator<Vente> orderByComparator) {

		List<Vente> list = findByDate(dateVente, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	@Override
	public Vente findByDate_Last(
			Date dateVente, OrderByComparator<Vente> orderByComparator)
		throws NoSuchVenteException {

		Vente vente = fetchByDate_Last(dateVente, orderByComparator);

		if (vente != null) {
			return vente;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dateVente=");
		sb.append(dateVente);

		sb.append("}");

		throw new NoSuchVenteException(sb.toString());
	}

	/**
	 * Returns the last vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente, or <code>null</code> if a matching vente could not be found
	 */
	@Override
	public Vente fetchByDate_Last(
		Date dateVente, OrderByComparator<Vente> orderByComparator) {

		int count = countByDate(dateVente);

		if (count == 0) {
			return null;
		}

		List<Vente> list = findByDate(
			dateVente, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ventes before and after the current vente in the ordered set where dateVente = &#63;.
	 *
	 * @param idVente the primary key of the current vente
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	@Override
	public Vente[] findByDate_PrevAndNext(
			long idVente, Date dateVente,
			OrderByComparator<Vente> orderByComparator)
		throws NoSuchVenteException {

		Vente vente = findByPrimaryKey(idVente);

		Session session = null;

		try {
			session = openSession();

			Vente[] array = new VenteImpl[3];

			array[0] = getByDate_PrevAndNext(
				session, vente, dateVente, orderByComparator, true);

			array[1] = vente;

			array[2] = getByDate_PrevAndNext(
				session, vente, dateVente, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Vente getByDate_PrevAndNext(
		Session session, Vente vente, Date dateVente,
		OrderByComparator<Vente> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_VENTE_WHERE);

		boolean bindDateVente = false;

		if (dateVente == null) {
			sb.append(_FINDER_COLUMN_DATE_DATEVENTE_1);
		}
		else {
			bindDateVente = true;

			sb.append(_FINDER_COLUMN_DATE_DATEVENTE_2);
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
			sb.append(VenteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindDateVente) {
			queryPos.add(new Timestamp(dateVente.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(vente)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Vente> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ventes where dateVente = &#63; from the database.
	 *
	 * @param dateVente the date vente
	 */
	@Override
	public void removeByDate(Date dateVente) {
		for (Vente vente :
				findByDate(
					dateVente, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(vente);
		}
	}

	/**
	 * Returns the number of ventes where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @return the number of matching ventes
	 */
	@Override
	public int countByDate(Date dateVente) {
		FinderPath finderPath = _finderPathCountByDate;

		Object[] finderArgs = new Object[] {_getTime(dateVente)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_VENTE_WHERE);

			boolean bindDateVente = false;

			if (dateVente == null) {
				sb.append(_FINDER_COLUMN_DATE_DATEVENTE_1);
			}
			else {
				bindDateVente = true;

				sb.append(_FINDER_COLUMN_DATE_DATEVENTE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDateVente) {
					queryPos.add(new Timestamp(dateVente.getTime()));
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

	private static final String _FINDER_COLUMN_DATE_DATEVENTE_1 =
		"vente.dateVente IS NULL";

	private static final String _FINDER_COLUMN_DATE_DATEVENTE_2 =
		"vente.dateVente = ?";

	public VentePersistenceImpl() {
		setModelClass(Vente.class);

		setModelImplClass(VenteImpl.class);
		setModelPKClass(long.class);

		setTable(VenteTable.INSTANCE);
	}

	/**
	 * Caches the vente in the entity cache if it is enabled.
	 *
	 * @param vente the vente
	 */
	@Override
	public void cacheResult(Vente vente) {
		entityCache.putResult(VenteImpl.class, vente.getPrimaryKey(), vente);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ventes in the entity cache if it is enabled.
	 *
	 * @param ventes the ventes
	 */
	@Override
	public void cacheResult(List<Vente> ventes) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ventes.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Vente vente : ventes) {
			if (entityCache.getResult(VenteImpl.class, vente.getPrimaryKey()) ==
					null) {

				cacheResult(vente);
			}
		}
	}

	/**
	 * Clears the cache for all ventes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(VenteImpl.class);

		finderCache.clearCache(VenteImpl.class);
	}

	/**
	 * Clears the cache for the vente.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Vente vente) {
		entityCache.removeResult(VenteImpl.class, vente);
	}

	@Override
	public void clearCache(List<Vente> ventes) {
		for (Vente vente : ventes) {
			entityCache.removeResult(VenteImpl.class, vente);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(VenteImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(VenteImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new vente with the primary key. Does not add the vente to the database.
	 *
	 * @param idVente the primary key for the new vente
	 * @return the new vente
	 */
	@Override
	public Vente create(long idVente) {
		Vente vente = new VenteImpl();

		vente.setNew(true);
		vente.setPrimaryKey(idVente);

		return vente;
	}

	/**
	 * Removes the vente with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente that was removed
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	@Override
	public Vente remove(long idVente) throws NoSuchVenteException {
		return remove((Serializable)idVente);
	}

	/**
	 * Removes the vente with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the vente
	 * @return the vente that was removed
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	@Override
	public Vente remove(Serializable primaryKey) throws NoSuchVenteException {
		Session session = null;

		try {
			session = openSession();

			Vente vente = (Vente)session.get(VenteImpl.class, primaryKey);

			if (vente == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVenteException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(vente);
		}
		catch (NoSuchVenteException noSuchEntityException) {
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
	protected Vente removeImpl(Vente vente) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(vente)) {
				vente = (Vente)session.get(
					VenteImpl.class, vente.getPrimaryKeyObj());
			}

			if (vente != null) {
				session.delete(vente);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (vente != null) {
			clearCache(vente);
		}

		return vente;
	}

	@Override
	public Vente updateImpl(Vente vente) {
		boolean isNew = vente.isNew();

		if (!(vente instanceof VenteModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(vente.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(vente);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in vente proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Vente implementation " +
					vente.getClass());
		}

		VenteModelImpl venteModelImpl = (VenteModelImpl)vente;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(vente);
			}
			else {
				vente = (Vente)session.merge(vente);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(VenteImpl.class, venteModelImpl, false, true);

		if (isNew) {
			vente.setNew(false);
		}

		vente.resetOriginalValues();

		return vente;
	}

	/**
	 * Returns the vente with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the vente
	 * @return the vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	@Override
	public Vente findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVenteException {

		Vente vente = fetchByPrimaryKey(primaryKey);

		if (vente == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVenteException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return vente;
	}

	/**
	 * Returns the vente with the primary key or throws a <code>NoSuchVenteException</code> if it could not be found.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	@Override
	public Vente findByPrimaryKey(long idVente) throws NoSuchVenteException {
		return findByPrimaryKey((Serializable)idVente);
	}

	/**
	 * Returns the vente with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente, or <code>null</code> if a vente with the primary key could not be found
	 */
	@Override
	public Vente fetchByPrimaryKey(long idVente) {
		return fetchByPrimaryKey((Serializable)idVente);
	}

	/**
	 * Returns all the ventes.
	 *
	 * @return the ventes
	 */
	@Override
	public List<Vente> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ventes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @return the range of ventes
	 */
	@Override
	public List<Vente> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ventes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ventes
	 */
	@Override
	public List<Vente> findAll(
		int start, int end, OrderByComparator<Vente> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ventes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ventes
	 * @param end the upper bound of the range of ventes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ventes
	 */
	@Override
	public List<Vente> findAll(
		int start, int end, OrderByComparator<Vente> orderByComparator,
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

		List<Vente> list = null;

		if (useFinderCache) {
			list = (List<Vente>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_VENTE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_VENTE;

				sql = sql.concat(VenteModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Vente>)QueryUtil.list(
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
	 * Removes all the ventes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Vente vente : findAll()) {
			remove(vente);
		}
	}

	/**
	 * Returns the number of ventes.
	 *
	 * @return the number of ventes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_VENTE);

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
		return "idVente";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_VENTE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return VenteModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the vente persistence.
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

		_finderPathWithPaginationFindByUser = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUser",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"idUtilisateur"}, true);

		_finderPathWithoutPaginationFindByUser = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUser",
			new String[] {Long.class.getName()}, new String[] {"idUtilisateur"},
			true);

		_finderPathCountByUser = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUser",
			new String[] {Long.class.getName()}, new String[] {"idUtilisateur"},
			false);

		_finderPathWithPaginationFindByDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"dateVente"}, true);

		_finderPathWithoutPaginationFindByDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDate",
			new String[] {Date.class.getName()}, new String[] {"dateVente"},
			true);

		_finderPathCountByDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDate",
			new String[] {Date.class.getName()}, new String[] {"dateVente"},
			false);

		VenteUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		VenteUtil.setPersistence(null);

		entityCache.removeCache(VenteImpl.class.getName());
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

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_VENTE =
		"SELECT vente FROM Vente vente";

	private static final String _SQL_SELECT_VENTE_WHERE =
		"SELECT vente FROM Vente vente WHERE ";

	private static final String _SQL_COUNT_VENTE =
		"SELECT COUNT(vente) FROM Vente vente";

	private static final String _SQL_COUNT_VENTE_WHERE =
		"SELECT COUNT(vente) FROM Vente vente WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "vente.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Vente exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Vente exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		VentePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
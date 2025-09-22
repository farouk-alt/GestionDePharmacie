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

import gestion_de_pharmacie.exception.NoSuchNotificationException;

import gestion_de_pharmacie.model.Notification;
import gestion_de_pharmacie.model.NotificationTable;
import gestion_de_pharmacie.model.impl.NotificationImpl;
import gestion_de_pharmacie.model.impl.NotificationModelImpl;

import gestion_de_pharmacie.service.persistence.NotificationPersistence;
import gestion_de_pharmacie.service.persistence.NotificationUtil;
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
 * The persistence implementation for the notification service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = NotificationPersistence.class)
public class NotificationPersistenceImpl
	extends BasePersistenceImpl<Notification>
	implements NotificationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>NotificationUtil</code> to access the notification persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		NotificationImpl.class.getName();

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
	 * Returns all the notifications where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the matching notifications
	 */
	@Override
	public List<Notification> findByUser(long idUtilisateur) {
		return findByUser(
			idUtilisateur, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @return the range of matching notifications
	 */
	@Override
	public List<Notification> findByUser(
		long idUtilisateur, int start, int end) {

		return findByUser(idUtilisateur, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications
	 */
	@Override
	public List<Notification> findByUser(
		long idUtilisateur, int start, int end,
		OrderByComparator<Notification> orderByComparator) {

		return findByUser(idUtilisateur, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications where idUtilisateur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications
	 */
	@Override
	public List<Notification> findByUser(
		long idUtilisateur, int start, int end,
		OrderByComparator<Notification> orderByComparator,
		boolean useFinderCache) {

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

		List<Notification> list = null;

		if (useFinderCache) {
			list = (List<Notification>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Notification notification : list) {
					if (idUtilisateur != notification.getIdUtilisateur()) {
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

			sb.append(_SQL_SELECT_NOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_USER_IDUTILISATEUR_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idUtilisateur);

				list = (List<Notification>)QueryUtil.list(
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
	 * Returns the first notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	@Override
	public Notification findByUser_First(
			long idUtilisateur,
			OrderByComparator<Notification> orderByComparator)
		throws NoSuchNotificationException {

		Notification notification = fetchByUser_First(
			idUtilisateur, orderByComparator);

		if (notification != null) {
			return notification;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idUtilisateur=");
		sb.append(idUtilisateur);

		sb.append("}");

		throw new NoSuchNotificationException(sb.toString());
	}

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification, or <code>null</code> if a matching notification could not be found
	 */
	@Override
	public Notification fetchByUser_First(
		long idUtilisateur, OrderByComparator<Notification> orderByComparator) {

		List<Notification> list = findByUser(
			idUtilisateur, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	@Override
	public Notification findByUser_Last(
			long idUtilisateur,
			OrderByComparator<Notification> orderByComparator)
		throws NoSuchNotificationException {

		Notification notification = fetchByUser_Last(
			idUtilisateur, orderByComparator);

		if (notification != null) {
			return notification;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idUtilisateur=");
		sb.append(idUtilisateur);

		sb.append("}");

		throw new NoSuchNotificationException(sb.toString());
	}

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification, or <code>null</code> if a matching notification could not be found
	 */
	@Override
	public Notification fetchByUser_Last(
		long idUtilisateur, OrderByComparator<Notification> orderByComparator) {

		int count = countByUser(idUtilisateur);

		if (count == 0) {
			return null;
		}

		List<Notification> list = findByUser(
			idUtilisateur, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications before and after the current notification in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idNotification the primary key of the current notification
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	@Override
	public Notification[] findByUser_PrevAndNext(
			long idNotification, long idUtilisateur,
			OrderByComparator<Notification> orderByComparator)
		throws NoSuchNotificationException {

		Notification notification = findByPrimaryKey(idNotification);

		Session session = null;

		try {
			session = openSession();

			Notification[] array = new NotificationImpl[3];

			array[0] = getByUser_PrevAndNext(
				session, notification, idUtilisateur, orderByComparator, true);

			array[1] = notification;

			array[2] = getByUser_PrevAndNext(
				session, notification, idUtilisateur, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Notification getByUser_PrevAndNext(
		Session session, Notification notification, long idUtilisateur,
		OrderByComparator<Notification> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_NOTIFICATION_WHERE);

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
			sb.append(NotificationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(idUtilisateur);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(notification)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Notification> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications where idUtilisateur = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 */
	@Override
	public void removeByUser(long idUtilisateur) {
		for (Notification notification :
				findByUser(
					idUtilisateur, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(notification);
		}
	}

	/**
	 * Returns the number of notifications where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the number of matching notifications
	 */
	@Override
	public int countByUser(long idUtilisateur) {
		FinderPath finderPath = _finderPathCountByUser;

		Object[] finderArgs = new Object[] {idUtilisateur};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_NOTIFICATION_WHERE);

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
		"notification.idUtilisateur = ?";

	private FinderPath _finderPathWithPaginationFindByUser_Status;
	private FinderPath _finderPathWithoutPaginationFindByUser_Status;
	private FinderPath _finderPathCountByUser_Status;

	/**
	 * Returns all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @return the matching notifications
	 */
	@Override
	public List<Notification> findByUser_Status(
		long idUtilisateur, String statut) {

		return findByUser_Status(
			idUtilisateur, statut, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @return the range of matching notifications
	 */
	@Override
	public List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end) {

		return findByUser_Status(idUtilisateur, statut, start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching notifications
	 */
	@Override
	public List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end,
		OrderByComparator<Notification> orderByComparator) {

		return findByUser_Status(
			idUtilisateur, statut, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching notifications
	 */
	@Override
	public List<Notification> findByUser_Status(
		long idUtilisateur, String statut, int start, int end,
		OrderByComparator<Notification> orderByComparator,
		boolean useFinderCache) {

		statut = Objects.toString(statut, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUser_Status;
				finderArgs = new Object[] {idUtilisateur, statut};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUser_Status;
			finderArgs = new Object[] {
				idUtilisateur, statut, start, end, orderByComparator
			};
		}

		List<Notification> list = null;

		if (useFinderCache) {
			list = (List<Notification>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Notification notification : list) {
					if ((idUtilisateur != notification.getIdUtilisateur()) ||
						!statut.equals(notification.getStatut())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_NOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_USER_STATUS_IDUTILISATEUR_2);

			boolean bindStatut = false;

			if (statut.isEmpty()) {
				sb.append(_FINDER_COLUMN_USER_STATUS_STATUT_3);
			}
			else {
				bindStatut = true;

				sb.append(_FINDER_COLUMN_USER_STATUS_STATUT_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(NotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idUtilisateur);

				if (bindStatut) {
					queryPos.add(statut);
				}

				list = (List<Notification>)QueryUtil.list(
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
	 * Returns the first notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	@Override
	public Notification findByUser_Status_First(
			long idUtilisateur, String statut,
			OrderByComparator<Notification> orderByComparator)
		throws NoSuchNotificationException {

		Notification notification = fetchByUser_Status_First(
			idUtilisateur, statut, orderByComparator);

		if (notification != null) {
			return notification;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idUtilisateur=");
		sb.append(idUtilisateur);

		sb.append(", statut=");
		sb.append(statut);

		sb.append("}");

		throw new NoSuchNotificationException(sb.toString());
	}

	/**
	 * Returns the first notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching notification, or <code>null</code> if a matching notification could not be found
	 */
	@Override
	public Notification fetchByUser_Status_First(
		long idUtilisateur, String statut,
		OrderByComparator<Notification> orderByComparator) {

		List<Notification> list = findByUser_Status(
			idUtilisateur, statut, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification
	 * @throws NoSuchNotificationException if a matching notification could not be found
	 */
	@Override
	public Notification findByUser_Status_Last(
			long idUtilisateur, String statut,
			OrderByComparator<Notification> orderByComparator)
		throws NoSuchNotificationException {

		Notification notification = fetchByUser_Status_Last(
			idUtilisateur, statut, orderByComparator);

		if (notification != null) {
			return notification;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idUtilisateur=");
		sb.append(idUtilisateur);

		sb.append(", statut=");
		sb.append(statut);

		sb.append("}");

		throw new NoSuchNotificationException(sb.toString());
	}

	/**
	 * Returns the last notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching notification, or <code>null</code> if a matching notification could not be found
	 */
	@Override
	public Notification fetchByUser_Status_Last(
		long idUtilisateur, String statut,
		OrderByComparator<Notification> orderByComparator) {

		int count = countByUser_Status(idUtilisateur, statut);

		if (count == 0) {
			return null;
		}

		List<Notification> list = findByUser_Status(
			idUtilisateur, statut, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the notifications before and after the current notification in the ordered set where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idNotification the primary key of the current notification
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	@Override
	public Notification[] findByUser_Status_PrevAndNext(
			long idNotification, long idUtilisateur, String statut,
			OrderByComparator<Notification> orderByComparator)
		throws NoSuchNotificationException {

		statut = Objects.toString(statut, "");

		Notification notification = findByPrimaryKey(idNotification);

		Session session = null;

		try {
			session = openSession();

			Notification[] array = new NotificationImpl[3];

			array[0] = getByUser_Status_PrevAndNext(
				session, notification, idUtilisateur, statut, orderByComparator,
				true);

			array[1] = notification;

			array[2] = getByUser_Status_PrevAndNext(
				session, notification, idUtilisateur, statut, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Notification getByUser_Status_PrevAndNext(
		Session session, Notification notification, long idUtilisateur,
		String statut, OrderByComparator<Notification> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_NOTIFICATION_WHERE);

		sb.append(_FINDER_COLUMN_USER_STATUS_IDUTILISATEUR_2);

		boolean bindStatut = false;

		if (statut.isEmpty()) {
			sb.append(_FINDER_COLUMN_USER_STATUS_STATUT_3);
		}
		else {
			bindStatut = true;

			sb.append(_FINDER_COLUMN_USER_STATUS_STATUT_2);
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
			sb.append(NotificationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(idUtilisateur);

		if (bindStatut) {
			queryPos.add(statut);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(notification)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Notification> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the notifications where idUtilisateur = &#63; and statut = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 */
	@Override
	public void removeByUser_Status(long idUtilisateur, String statut) {
		for (Notification notification :
				findByUser_Status(
					idUtilisateur, statut, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(notification);
		}
	}

	/**
	 * Returns the number of notifications where idUtilisateur = &#63; and statut = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param statut the statut
	 * @return the number of matching notifications
	 */
	@Override
	public int countByUser_Status(long idUtilisateur, String statut) {
		statut = Objects.toString(statut, "");

		FinderPath finderPath = _finderPathCountByUser_Status;

		Object[] finderArgs = new Object[] {idUtilisateur, statut};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_NOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_USER_STATUS_IDUTILISATEUR_2);

			boolean bindStatut = false;

			if (statut.isEmpty()) {
				sb.append(_FINDER_COLUMN_USER_STATUS_STATUT_3);
			}
			else {
				bindStatut = true;

				sb.append(_FINDER_COLUMN_USER_STATUS_STATUT_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idUtilisateur);

				if (bindStatut) {
					queryPos.add(statut);
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

	private static final String _FINDER_COLUMN_USER_STATUS_IDUTILISATEUR_2 =
		"notification.idUtilisateur = ? AND ";

	private static final String _FINDER_COLUMN_USER_STATUS_STATUT_2 =
		"notification.statut = ?";

	private static final String _FINDER_COLUMN_USER_STATUS_STATUT_3 =
		"(notification.statut IS NULL OR notification.statut = '')";

	public NotificationPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Notification.class);

		setModelImplClass(NotificationImpl.class);
		setModelPKClass(long.class);

		setTable(NotificationTable.INSTANCE);
	}

	/**
	 * Caches the notification in the entity cache if it is enabled.
	 *
	 * @param notification the notification
	 */
	@Override
	public void cacheResult(Notification notification) {
		entityCache.putResult(
			NotificationImpl.class, notification.getPrimaryKey(), notification);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the notifications in the entity cache if it is enabled.
	 *
	 * @param notifications the notifications
	 */
	@Override
	public void cacheResult(List<Notification> notifications) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (notifications.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Notification notification : notifications) {
			if (entityCache.getResult(
					NotificationImpl.class, notification.getPrimaryKey()) ==
						null) {

				cacheResult(notification);
			}
		}
	}

	/**
	 * Clears the cache for all notifications.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(NotificationImpl.class);

		finderCache.clearCache(NotificationImpl.class);
	}

	/**
	 * Clears the cache for the notification.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Notification notification) {
		entityCache.removeResult(NotificationImpl.class, notification);
	}

	@Override
	public void clearCache(List<Notification> notifications) {
		for (Notification notification : notifications) {
			entityCache.removeResult(NotificationImpl.class, notification);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(NotificationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(NotificationImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new notification with the primary key. Does not add the notification to the database.
	 *
	 * @param idNotification the primary key for the new notification
	 * @return the new notification
	 */
	@Override
	public Notification create(long idNotification) {
		Notification notification = new NotificationImpl();

		notification.setNew(true);
		notification.setPrimaryKey(idNotification);

		return notification;
	}

	/**
	 * Removes the notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification that was removed
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	@Override
	public Notification remove(long idNotification)
		throws NoSuchNotificationException {

		return remove((Serializable)idNotification);
	}

	/**
	 * Removes the notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the notification
	 * @return the notification that was removed
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	@Override
	public Notification remove(Serializable primaryKey)
		throws NoSuchNotificationException {

		Session session = null;

		try {
			session = openSession();

			Notification notification = (Notification)session.get(
				NotificationImpl.class, primaryKey);

			if (notification == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(notification);
		}
		catch (NoSuchNotificationException noSuchEntityException) {
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
	protected Notification removeImpl(Notification notification) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(notification)) {
				notification = (Notification)session.get(
					NotificationImpl.class, notification.getPrimaryKeyObj());
			}

			if (notification != null) {
				session.delete(notification);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (notification != null) {
			clearCache(notification);
		}

		return notification;
	}

	@Override
	public Notification updateImpl(Notification notification) {
		boolean isNew = notification.isNew();

		if (!(notification instanceof NotificationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(notification.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					notification);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in notification proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Notification implementation " +
					notification.getClass());
		}

		NotificationModelImpl notificationModelImpl =
			(NotificationModelImpl)notification;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(notification);
			}
			else {
				notification = (Notification)session.merge(notification);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			NotificationImpl.class, notificationModelImpl, false, true);

		if (isNew) {
			notification.setNew(false);
		}

		notification.resetOriginalValues();

		return notification;
	}

	/**
	 * Returns the notification with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the notification
	 * @return the notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	@Override
	public Notification findByPrimaryKey(Serializable primaryKey)
		throws NoSuchNotificationException {

		Notification notification = fetchByPrimaryKey(primaryKey);

		if (notification == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return notification;
	}

	/**
	 * Returns the notification with the primary key or throws a <code>NoSuchNotificationException</code> if it could not be found.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification
	 * @throws NoSuchNotificationException if a notification with the primary key could not be found
	 */
	@Override
	public Notification findByPrimaryKey(long idNotification)
		throws NoSuchNotificationException {

		return findByPrimaryKey((Serializable)idNotification);
	}

	/**
	 * Returns the notification with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idNotification the primary key of the notification
	 * @return the notification, or <code>null</code> if a notification with the primary key could not be found
	 */
	@Override
	public Notification fetchByPrimaryKey(long idNotification) {
		return fetchByPrimaryKey((Serializable)idNotification);
	}

	/**
	 * Returns all the notifications.
	 *
	 * @return the notifications
	 */
	@Override
	public List<Notification> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @return the range of notifications
	 */
	@Override
	public List<Notification> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of notifications
	 */
	@Override
	public List<Notification> findAll(
		int start, int end, OrderByComparator<Notification> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>NotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of notifications
	 * @param end the upper bound of the range of notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of notifications
	 */
	@Override
	public List<Notification> findAll(
		int start, int end, OrderByComparator<Notification> orderByComparator,
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

		List<Notification> list = null;

		if (useFinderCache) {
			list = (List<Notification>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_NOTIFICATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_NOTIFICATION;

				sql = sql.concat(NotificationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Notification>)QueryUtil.list(
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
	 * Removes all the notifications from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Notification notification : findAll()) {
			remove(notification);
		}
	}

	/**
	 * Returns the number of notifications.
	 *
	 * @return the number of notifications
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_NOTIFICATION);

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
		return "idNotification";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_NOTIFICATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return NotificationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the notification persistence.
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

		_finderPathWithPaginationFindByUser_Status = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUser_Status",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"idUtilisateur", "statut"}, true);

		_finderPathWithoutPaginationFindByUser_Status = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUser_Status",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"idUtilisateur", "statut"}, true);

		_finderPathCountByUser_Status = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUser_Status",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"idUtilisateur", "statut"}, false);

		NotificationUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		NotificationUtil.setPersistence(null);

		entityCache.removeCache(NotificationImpl.class.getName());
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

	private static final String _SQL_SELECT_NOTIFICATION =
		"SELECT notification FROM Notification notification";

	private static final String _SQL_SELECT_NOTIFICATION_WHERE =
		"SELECT notification FROM Notification notification WHERE ";

	private static final String _SQL_COUNT_NOTIFICATION =
		"SELECT COUNT(notification) FROM Notification notification";

	private static final String _SQL_COUNT_NOTIFICATION_WHERE =
		"SELECT COUNT(notification) FROM Notification notification WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "notification.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Notification exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Notification exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
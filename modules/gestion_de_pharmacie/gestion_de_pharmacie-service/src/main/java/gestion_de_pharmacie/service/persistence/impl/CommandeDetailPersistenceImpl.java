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

import gestion_de_pharmacie.exception.NoSuchCommandeDetailException;

import gestion_de_pharmacie.model.CommandeDetail;
import gestion_de_pharmacie.model.CommandeDetailTable;
import gestion_de_pharmacie.model.impl.CommandeDetailImpl;
import gestion_de_pharmacie.model.impl.CommandeDetailModelImpl;

import gestion_de_pharmacie.service.persistence.CommandeDetailPersistence;
import gestion_de_pharmacie.service.persistence.CommandeDetailUtil;
import gestion_de_pharmacie.service.persistence.impl.constants.PharmaPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
	private FinderPath _finderPathWithPaginationFindByIdCommande;
	private FinderPath _finderPathWithoutPaginationFindByIdCommande;
	private FinderPath _finderPathCountByIdCommande;

	/**
	 * Returns all the commande details where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @return the matching commande details
	 */
	@Override
	public List<CommandeDetail> findByIdCommande(long idCommande) {
		return findByIdCommande(
			idCommande, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commande details where idCommande = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idCommande the id commande
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @return the range of matching commande details
	 */
	@Override
	public List<CommandeDetail> findByIdCommande(
		long idCommande, int start, int end) {

		return findByIdCommande(idCommande, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commande details where idCommande = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idCommande the id commande
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commande details
	 */
	@Override
	public List<CommandeDetail> findByIdCommande(
		long idCommande, int start, int end,
		OrderByComparator<CommandeDetail> orderByComparator) {

		return findByIdCommande(
			idCommande, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commande details where idCommande = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idCommande the id commande
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commande details
	 */
	@Override
	public List<CommandeDetail> findByIdCommande(
		long idCommande, int start, int end,
		OrderByComparator<CommandeDetail> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByIdCommande;
				finderArgs = new Object[] {idCommande};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByIdCommande;
			finderArgs = new Object[] {
				idCommande, start, end, orderByComparator
			};
		}

		List<CommandeDetail> list = null;

		if (useFinderCache) {
			list = (List<CommandeDetail>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommandeDetail commandeDetail : list) {
					if (idCommande != commandeDetail.getIdCommande()) {
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

			sb.append(_SQL_SELECT_COMMANDEDETAIL_WHERE);

			sb.append(_FINDER_COLUMN_IDCOMMANDE_IDCOMMANDE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommandeDetailModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idCommande);

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
	 * Returns the first commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande detail
	 * @throws NoSuchCommandeDetailException if a matching commande detail could not be found
	 */
	@Override
	public CommandeDetail findByIdCommande_First(
			long idCommande,
			OrderByComparator<CommandeDetail> orderByComparator)
		throws NoSuchCommandeDetailException {

		CommandeDetail commandeDetail = fetchByIdCommande_First(
			idCommande, orderByComparator);

		if (commandeDetail != null) {
			return commandeDetail;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idCommande=");
		sb.append(idCommande);

		sb.append("}");

		throw new NoSuchCommandeDetailException(sb.toString());
	}

	/**
	 * Returns the first commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande detail, or <code>null</code> if a matching commande detail could not be found
	 */
	@Override
	public CommandeDetail fetchByIdCommande_First(
		long idCommande, OrderByComparator<CommandeDetail> orderByComparator) {

		List<CommandeDetail> list = findByIdCommande(
			idCommande, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande detail
	 * @throws NoSuchCommandeDetailException if a matching commande detail could not be found
	 */
	@Override
	public CommandeDetail findByIdCommande_Last(
			long idCommande,
			OrderByComparator<CommandeDetail> orderByComparator)
		throws NoSuchCommandeDetailException {

		CommandeDetail commandeDetail = fetchByIdCommande_Last(
			idCommande, orderByComparator);

		if (commandeDetail != null) {
			return commandeDetail;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idCommande=");
		sb.append(idCommande);

		sb.append("}");

		throw new NoSuchCommandeDetailException(sb.toString());
	}

	/**
	 * Returns the last commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande detail, or <code>null</code> if a matching commande detail could not be found
	 */
	@Override
	public CommandeDetail fetchByIdCommande_Last(
		long idCommande, OrderByComparator<CommandeDetail> orderByComparator) {

		int count = countByIdCommande(idCommande);

		if (count == 0) {
			return null;
		}

		List<CommandeDetail> list = findByIdCommande(
			idCommande, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commande details before and after the current commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idDetail the primary key of the current commande detail
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commande detail
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	@Override
	public CommandeDetail[] findByIdCommande_PrevAndNext(
			long idDetail, long idCommande,
			OrderByComparator<CommandeDetail> orderByComparator)
		throws NoSuchCommandeDetailException {

		CommandeDetail commandeDetail = findByPrimaryKey(idDetail);

		Session session = null;

		try {
			session = openSession();

			CommandeDetail[] array = new CommandeDetailImpl[3];

			array[0] = getByIdCommande_PrevAndNext(
				session, commandeDetail, idCommande, orderByComparator, true);

			array[1] = commandeDetail;

			array[2] = getByIdCommande_PrevAndNext(
				session, commandeDetail, idCommande, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommandeDetail getByIdCommande_PrevAndNext(
		Session session, CommandeDetail commandeDetail, long idCommande,
		OrderByComparator<CommandeDetail> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMANDEDETAIL_WHERE);

		sb.append(_FINDER_COLUMN_IDCOMMANDE_IDCOMMANDE_2);

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
			sb.append(CommandeDetailModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(idCommande);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commandeDetail)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommandeDetail> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commande details where idCommande = &#63; from the database.
	 *
	 * @param idCommande the id commande
	 */
	@Override
	public void removeByIdCommande(long idCommande) {
		for (CommandeDetail commandeDetail :
				findByIdCommande(
					idCommande, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commandeDetail);
		}
	}

	/**
	 * Returns the number of commande details where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @return the number of matching commande details
	 */
	@Override
	public int countByIdCommande(long idCommande) {
		FinderPath finderPath = _finderPathCountByIdCommande;

		Object[] finderArgs = new Object[] {idCommande};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMANDEDETAIL_WHERE);

			sb.append(_FINDER_COLUMN_IDCOMMANDE_IDCOMMANDE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idCommande);

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

	private static final String _FINDER_COLUMN_IDCOMMANDE_IDCOMMANDE_2 =
		"commandeDetail.idCommande = ?";

	private FinderPath _finderPathWithPaginationFindByIdMedicament;
	private FinderPath _finderPathWithoutPaginationFindByIdMedicament;
	private FinderPath _finderPathCountByIdMedicament;

	/**
	 * Returns all the commande details where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the matching commande details
	 */
	@Override
	public List<CommandeDetail> findByIdMedicament(long idMedicament) {
		return findByIdMedicament(
			idMedicament, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commande details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @return the range of matching commande details
	 */
	@Override
	public List<CommandeDetail> findByIdMedicament(
		long idMedicament, int start, int end) {

		return findByIdMedicament(idMedicament, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commande details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commande details
	 */
	@Override
	public List<CommandeDetail> findByIdMedicament(
		long idMedicament, int start, int end,
		OrderByComparator<CommandeDetail> orderByComparator) {

		return findByIdMedicament(
			idMedicament, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commande details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of commande details
	 * @param end the upper bound of the range of commande details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commande details
	 */
	@Override
	public List<CommandeDetail> findByIdMedicament(
		long idMedicament, int start, int end,
		OrderByComparator<CommandeDetail> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByIdMedicament;
				finderArgs = new Object[] {idMedicament};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByIdMedicament;
			finderArgs = new Object[] {
				idMedicament, start, end, orderByComparator
			};
		}

		List<CommandeDetail> list = null;

		if (useFinderCache) {
			list = (List<CommandeDetail>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommandeDetail commandeDetail : list) {
					if (idMedicament != commandeDetail.getIdMedicament()) {
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

			sb.append(_SQL_SELECT_COMMANDEDETAIL_WHERE);

			sb.append(_FINDER_COLUMN_IDMEDICAMENT_IDMEDICAMENT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommandeDetailModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idMedicament);

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
	 * Returns the first commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande detail
	 * @throws NoSuchCommandeDetailException if a matching commande detail could not be found
	 */
	@Override
	public CommandeDetail findByIdMedicament_First(
			long idMedicament,
			OrderByComparator<CommandeDetail> orderByComparator)
		throws NoSuchCommandeDetailException {

		CommandeDetail commandeDetail = fetchByIdMedicament_First(
			idMedicament, orderByComparator);

		if (commandeDetail != null) {
			return commandeDetail;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idMedicament=");
		sb.append(idMedicament);

		sb.append("}");

		throw new NoSuchCommandeDetailException(sb.toString());
	}

	/**
	 * Returns the first commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande detail, or <code>null</code> if a matching commande detail could not be found
	 */
	@Override
	public CommandeDetail fetchByIdMedicament_First(
		long idMedicament,
		OrderByComparator<CommandeDetail> orderByComparator) {

		List<CommandeDetail> list = findByIdMedicament(
			idMedicament, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande detail
	 * @throws NoSuchCommandeDetailException if a matching commande detail could not be found
	 */
	@Override
	public CommandeDetail findByIdMedicament_Last(
			long idMedicament,
			OrderByComparator<CommandeDetail> orderByComparator)
		throws NoSuchCommandeDetailException {

		CommandeDetail commandeDetail = fetchByIdMedicament_Last(
			idMedicament, orderByComparator);

		if (commandeDetail != null) {
			return commandeDetail;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("idMedicament=");
		sb.append(idMedicament);

		sb.append("}");

		throw new NoSuchCommandeDetailException(sb.toString());
	}

	/**
	 * Returns the last commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande detail, or <code>null</code> if a matching commande detail could not be found
	 */
	@Override
	public CommandeDetail fetchByIdMedicament_Last(
		long idMedicament,
		OrderByComparator<CommandeDetail> orderByComparator) {

		int count = countByIdMedicament(idMedicament);

		if (count == 0) {
			return null;
		}

		List<CommandeDetail> list = findByIdMedicament(
			idMedicament, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commande details before and after the current commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idDetail the primary key of the current commande detail
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commande detail
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	@Override
	public CommandeDetail[] findByIdMedicament_PrevAndNext(
			long idDetail, long idMedicament,
			OrderByComparator<CommandeDetail> orderByComparator)
		throws NoSuchCommandeDetailException {

		CommandeDetail commandeDetail = findByPrimaryKey(idDetail);

		Session session = null;

		try {
			session = openSession();

			CommandeDetail[] array = new CommandeDetailImpl[3];

			array[0] = getByIdMedicament_PrevAndNext(
				session, commandeDetail, idMedicament, orderByComparator, true);

			array[1] = commandeDetail;

			array[2] = getByIdMedicament_PrevAndNext(
				session, commandeDetail, idMedicament, orderByComparator,
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

	protected CommandeDetail getByIdMedicament_PrevAndNext(
		Session session, CommandeDetail commandeDetail, long idMedicament,
		OrderByComparator<CommandeDetail> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMANDEDETAIL_WHERE);

		sb.append(_FINDER_COLUMN_IDMEDICAMENT_IDMEDICAMENT_2);

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
			sb.append(CommandeDetailModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(idMedicament);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commandeDetail)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommandeDetail> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commande details where idMedicament = &#63; from the database.
	 *
	 * @param idMedicament the id medicament
	 */
	@Override
	public void removeByIdMedicament(long idMedicament) {
		for (CommandeDetail commandeDetail :
				findByIdMedicament(
					idMedicament, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commandeDetail);
		}
	}

	/**
	 * Returns the number of commande details where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the number of matching commande details
	 */
	@Override
	public int countByIdMedicament(long idMedicament) {
		FinderPath finderPath = _finderPathCountByIdMedicament;

		Object[] finderArgs = new Object[] {idMedicament};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMANDEDETAIL_WHERE);

			sb.append(_FINDER_COLUMN_IDMEDICAMENT_IDMEDICAMENT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(idMedicament);

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

	private static final String _FINDER_COLUMN_IDMEDICAMENT_IDMEDICAMENT_2 =
		"commandeDetail.idMedicament = ?";

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

		if (!(commandeDetail instanceof CommandeDetailModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commandeDetail.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commandeDetail);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commandeDetail proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommandeDetail implementation " +
					commandeDetail.getClass());
		}

		CommandeDetailModelImpl commandeDetailModelImpl =
			(CommandeDetailModelImpl)commandeDetail;

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
			CommandeDetailImpl.class, commandeDetailModelImpl, false, true);

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

		_finderPathWithPaginationFindByIdCommande = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByIdCommande",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"idCommande"}, true);

		_finderPathWithoutPaginationFindByIdCommande = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByIdCommande",
			new String[] {Long.class.getName()}, new String[] {"idCommande"},
			true);

		_finderPathCountByIdCommande = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByIdCommande",
			new String[] {Long.class.getName()}, new String[] {"idCommande"},
			false);

		_finderPathWithPaginationFindByIdMedicament = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByIdMedicament",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"idMedicament"}, true);

		_finderPathWithoutPaginationFindByIdMedicament = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByIdMedicament",
			new String[] {Long.class.getName()}, new String[] {"idMedicament"},
			true);

		_finderPathCountByIdMedicament = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByIdMedicament",
			new String[] {Long.class.getName()}, new String[] {"idMedicament"},
			false);

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

	private static final String _SQL_SELECT_COMMANDEDETAIL_WHERE =
		"SELECT commandeDetail FROM CommandeDetail commandeDetail WHERE ";

	private static final String _SQL_COUNT_COMMANDEDETAIL =
		"SELECT COUNT(commandeDetail) FROM CommandeDetail commandeDetail";

	private static final String _SQL_COUNT_COMMANDEDETAIL_WHERE =
		"SELECT COUNT(commandeDetail) FROM CommandeDetail commandeDetail WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commandeDetail.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommandeDetail exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommandeDetail exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommandeDetailPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
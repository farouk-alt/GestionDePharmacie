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

import gestion_de_pharmacie.exception.NoSuchMedicamentException;

import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.model.MedicamentTable;
import gestion_de_pharmacie.model.impl.MedicamentImpl;
import gestion_de_pharmacie.model.impl.MedicamentModelImpl;

import gestion_de_pharmacie.service.persistence.MedicamentPersistence;
import gestion_de_pharmacie.service.persistence.MedicamentUtil;
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
 * The persistence implementation for the medicament service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @generated
 */
@Component(service = MedicamentPersistence.class)
public class MedicamentPersistenceImpl
	extends BasePersistenceImpl<Medicament> implements MedicamentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MedicamentUtil</code> to access the medicament persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MedicamentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCode;
	private FinderPath _finderPathWithoutPaginationFindByCode;
	private FinderPath _finderPathCountByCode;

	/**
	 * Returns all the medicaments where code = &#63;.
	 *
	 * @param code the code
	 * @return the matching medicaments
	 */
	@Override
	public List<Medicament> findByCode(String code) {
		return findByCode(code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the medicaments where code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param code the code
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCode(String code, int start, int end) {
		return findByCode(code, start, end, null);
	}

	/**
	 * Returns an ordered range of all the medicaments where code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param code the code
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCode(
		String code, int start, int end,
		OrderByComparator<Medicament> orderByComparator) {

		return findByCode(code, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the medicaments where code = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param code the code
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCode(
		String code, int start, int end,
		OrderByComparator<Medicament> orderByComparator,
		boolean useFinderCache) {

		code = Objects.toString(code, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCode;
				finderArgs = new Object[] {code};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCode;
			finderArgs = new Object[] {code, start, end, orderByComparator};
		}

		List<Medicament> list = null;

		if (useFinderCache) {
			list = (List<Medicament>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Medicament medicament : list) {
					if (!code.equals(medicament.getCode())) {
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

			sb.append(_SQL_SELECT_MEDICAMENT_WHERE);

			boolean bindCode = false;

			if (code.isEmpty()) {
				sb.append(_FINDER_COLUMN_CODE_CODE_3);
			}
			else {
				bindCode = true;

				sb.append(_FINDER_COLUMN_CODE_CODE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MedicamentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCode) {
					queryPos.add(code);
				}

				list = (List<Medicament>)QueryUtil.list(
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
	 * Returns the first medicament in the ordered set where code = &#63;.
	 *
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	@Override
	public Medicament findByCode_First(
			String code, OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByCode_First(code, orderByComparator);

		if (medicament != null) {
			return medicament;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("code=");
		sb.append(code);

		sb.append("}");

		throw new NoSuchMedicamentException(sb.toString());
	}

	/**
	 * Returns the first medicament in the ordered set where code = &#63;.
	 *
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	@Override
	public Medicament fetchByCode_First(
		String code, OrderByComparator<Medicament> orderByComparator) {

		List<Medicament> list = findByCode(code, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last medicament in the ordered set where code = &#63;.
	 *
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	@Override
	public Medicament findByCode_Last(
			String code, OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByCode_Last(code, orderByComparator);

		if (medicament != null) {
			return medicament;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("code=");
		sb.append(code);

		sb.append("}");

		throw new NoSuchMedicamentException(sb.toString());
	}

	/**
	 * Returns the last medicament in the ordered set where code = &#63;.
	 *
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	@Override
	public Medicament fetchByCode_Last(
		String code, OrderByComparator<Medicament> orderByComparator) {

		int count = countByCode(code);

		if (count == 0) {
			return null;
		}

		List<Medicament> list = findByCode(
			code, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the medicaments before and after the current medicament in the ordered set where code = &#63;.
	 *
	 * @param idMedicament the primary key of the current medicament
	 * @param code the code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament[] findByCode_PrevAndNext(
			long idMedicament, String code,
			OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		code = Objects.toString(code, "");

		Medicament medicament = findByPrimaryKey(idMedicament);

		Session session = null;

		try {
			session = openSession();

			Medicament[] array = new MedicamentImpl[3];

			array[0] = getByCode_PrevAndNext(
				session, medicament, code, orderByComparator, true);

			array[1] = medicament;

			array[2] = getByCode_PrevAndNext(
				session, medicament, code, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Medicament getByCode_PrevAndNext(
		Session session, Medicament medicament, String code,
		OrderByComparator<Medicament> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MEDICAMENT_WHERE);

		boolean bindCode = false;

		if (code.isEmpty()) {
			sb.append(_FINDER_COLUMN_CODE_CODE_3);
		}
		else {
			bindCode = true;

			sb.append(_FINDER_COLUMN_CODE_CODE_2);
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
			sb.append(MedicamentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCode) {
			queryPos.add(code);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(medicament)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Medicament> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the medicaments where code = &#63; from the database.
	 *
	 * @param code the code
	 */
	@Override
	public void removeByCode(String code) {
		for (Medicament medicament :
				findByCode(code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(medicament);
		}
	}

	/**
	 * Returns the number of medicaments where code = &#63;.
	 *
	 * @param code the code
	 * @return the number of matching medicaments
	 */
	@Override
	public int countByCode(String code) {
		code = Objects.toString(code, "");

		FinderPath finderPath = _finderPathCountByCode;

		Object[] finderArgs = new Object[] {code};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MEDICAMENT_WHERE);

			boolean bindCode = false;

			if (code.isEmpty()) {
				sb.append(_FINDER_COLUMN_CODE_CODE_3);
			}
			else {
				bindCode = true;

				sb.append(_FINDER_COLUMN_CODE_CODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCode) {
					queryPos.add(code);
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

	private static final String _FINDER_COLUMN_CODE_CODE_2 =
		"medicament.code = ?";

	private static final String _FINDER_COLUMN_CODE_CODE_3 =
		"(medicament.code IS NULL OR medicament.code = '')";

	private FinderPath _finderPathWithPaginationFindByCodeBarre;
	private FinderPath _finderPathWithoutPaginationFindByCodeBarre;
	private FinderPath _finderPathCountByCodeBarre;

	/**
	 * Returns all the medicaments where codeBarre = &#63;.
	 *
	 * @param codeBarre the code barre
	 * @return the matching medicaments
	 */
	@Override
	public List<Medicament> findByCodeBarre(String codeBarre) {
		return findByCodeBarre(
			codeBarre, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the medicaments where codeBarre = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param codeBarre the code barre
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCodeBarre(
		String codeBarre, int start, int end) {

		return findByCodeBarre(codeBarre, start, end, null);
	}

	/**
	 * Returns an ordered range of all the medicaments where codeBarre = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param codeBarre the code barre
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCodeBarre(
		String codeBarre, int start, int end,
		OrderByComparator<Medicament> orderByComparator) {

		return findByCodeBarre(codeBarre, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the medicaments where codeBarre = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param codeBarre the code barre
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCodeBarre(
		String codeBarre, int start, int end,
		OrderByComparator<Medicament> orderByComparator,
		boolean useFinderCache) {

		codeBarre = Objects.toString(codeBarre, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCodeBarre;
				finderArgs = new Object[] {codeBarre};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCodeBarre;
			finderArgs = new Object[] {
				codeBarre, start, end, orderByComparator
			};
		}

		List<Medicament> list = null;

		if (useFinderCache) {
			list = (List<Medicament>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Medicament medicament : list) {
					if (!codeBarre.equals(medicament.getCodeBarre())) {
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

			sb.append(_SQL_SELECT_MEDICAMENT_WHERE);

			boolean bindCodeBarre = false;

			if (codeBarre.isEmpty()) {
				sb.append(_FINDER_COLUMN_CODEBARRE_CODEBARRE_3);
			}
			else {
				bindCodeBarre = true;

				sb.append(_FINDER_COLUMN_CODEBARRE_CODEBARRE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MedicamentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCodeBarre) {
					queryPos.add(codeBarre);
				}

				list = (List<Medicament>)QueryUtil.list(
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
	 * Returns the first medicament in the ordered set where codeBarre = &#63;.
	 *
	 * @param codeBarre the code barre
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	@Override
	public Medicament findByCodeBarre_First(
			String codeBarre, OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByCodeBarre_First(
			codeBarre, orderByComparator);

		if (medicament != null) {
			return medicament;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("codeBarre=");
		sb.append(codeBarre);

		sb.append("}");

		throw new NoSuchMedicamentException(sb.toString());
	}

	/**
	 * Returns the first medicament in the ordered set where codeBarre = &#63;.
	 *
	 * @param codeBarre the code barre
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	@Override
	public Medicament fetchByCodeBarre_First(
		String codeBarre, OrderByComparator<Medicament> orderByComparator) {

		List<Medicament> list = findByCodeBarre(
			codeBarre, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last medicament in the ordered set where codeBarre = &#63;.
	 *
	 * @param codeBarre the code barre
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	@Override
	public Medicament findByCodeBarre_Last(
			String codeBarre, OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByCodeBarre_Last(
			codeBarre, orderByComparator);

		if (medicament != null) {
			return medicament;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("codeBarre=");
		sb.append(codeBarre);

		sb.append("}");

		throw new NoSuchMedicamentException(sb.toString());
	}

	/**
	 * Returns the last medicament in the ordered set where codeBarre = &#63;.
	 *
	 * @param codeBarre the code barre
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	@Override
	public Medicament fetchByCodeBarre_Last(
		String codeBarre, OrderByComparator<Medicament> orderByComparator) {

		int count = countByCodeBarre(codeBarre);

		if (count == 0) {
			return null;
		}

		List<Medicament> list = findByCodeBarre(
			codeBarre, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the medicaments before and after the current medicament in the ordered set where codeBarre = &#63;.
	 *
	 * @param idMedicament the primary key of the current medicament
	 * @param codeBarre the code barre
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament[] findByCodeBarre_PrevAndNext(
			long idMedicament, String codeBarre,
			OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		codeBarre = Objects.toString(codeBarre, "");

		Medicament medicament = findByPrimaryKey(idMedicament);

		Session session = null;

		try {
			session = openSession();

			Medicament[] array = new MedicamentImpl[3];

			array[0] = getByCodeBarre_PrevAndNext(
				session, medicament, codeBarre, orderByComparator, true);

			array[1] = medicament;

			array[2] = getByCodeBarre_PrevAndNext(
				session, medicament, codeBarre, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Medicament getByCodeBarre_PrevAndNext(
		Session session, Medicament medicament, String codeBarre,
		OrderByComparator<Medicament> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MEDICAMENT_WHERE);

		boolean bindCodeBarre = false;

		if (codeBarre.isEmpty()) {
			sb.append(_FINDER_COLUMN_CODEBARRE_CODEBARRE_3);
		}
		else {
			bindCodeBarre = true;

			sb.append(_FINDER_COLUMN_CODEBARRE_CODEBARRE_2);
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
			sb.append(MedicamentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCodeBarre) {
			queryPos.add(codeBarre);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(medicament)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Medicament> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the medicaments where codeBarre = &#63; from the database.
	 *
	 * @param codeBarre the code barre
	 */
	@Override
	public void removeByCodeBarre(String codeBarre) {
		for (Medicament medicament :
				findByCodeBarre(
					codeBarre, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(medicament);
		}
	}

	/**
	 * Returns the number of medicaments where codeBarre = &#63;.
	 *
	 * @param codeBarre the code barre
	 * @return the number of matching medicaments
	 */
	@Override
	public int countByCodeBarre(String codeBarre) {
		codeBarre = Objects.toString(codeBarre, "");

		FinderPath finderPath = _finderPathCountByCodeBarre;

		Object[] finderArgs = new Object[] {codeBarre};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MEDICAMENT_WHERE);

			boolean bindCodeBarre = false;

			if (codeBarre.isEmpty()) {
				sb.append(_FINDER_COLUMN_CODEBARRE_CODEBARRE_3);
			}
			else {
				bindCodeBarre = true;

				sb.append(_FINDER_COLUMN_CODEBARRE_CODEBARRE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCodeBarre) {
					queryPos.add(codeBarre);
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

	private static final String _FINDER_COLUMN_CODEBARRE_CODEBARRE_2 =
		"medicament.codeBarre = ?";

	private static final String _FINDER_COLUMN_CODEBARRE_CODEBARRE_3 =
		"(medicament.codeBarre IS NULL OR medicament.codeBarre = '')";

	private FinderPath _finderPathWithPaginationFindByNom;
	private FinderPath _finderPathWithoutPaginationFindByNom;
	private FinderPath _finderPathCountByNom;

	/**
	 * Returns all the medicaments where nom = &#63;.
	 *
	 * @param nom the nom
	 * @return the matching medicaments
	 */
	@Override
	public List<Medicament> findByNom(String nom) {
		return findByNom(nom, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the medicaments where nom = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param nom the nom
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of matching medicaments
	 */
	@Override
	public List<Medicament> findByNom(String nom, int start, int end) {
		return findByNom(nom, start, end, null);
	}

	/**
	 * Returns an ordered range of all the medicaments where nom = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param nom the nom
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching medicaments
	 */
	@Override
	public List<Medicament> findByNom(
		String nom, int start, int end,
		OrderByComparator<Medicament> orderByComparator) {

		return findByNom(nom, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the medicaments where nom = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param nom the nom
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching medicaments
	 */
	@Override
	public List<Medicament> findByNom(
		String nom, int start, int end,
		OrderByComparator<Medicament> orderByComparator,
		boolean useFinderCache) {

		nom = Objects.toString(nom, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByNom;
				finderArgs = new Object[] {nom};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByNom;
			finderArgs = new Object[] {nom, start, end, orderByComparator};
		}

		List<Medicament> list = null;

		if (useFinderCache) {
			list = (List<Medicament>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Medicament medicament : list) {
					if (!nom.equals(medicament.getNom())) {
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

			sb.append(_SQL_SELECT_MEDICAMENT_WHERE);

			boolean bindNom = false;

			if (nom.isEmpty()) {
				sb.append(_FINDER_COLUMN_NOM_NOM_3);
			}
			else {
				bindNom = true;

				sb.append(_FINDER_COLUMN_NOM_NOM_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MedicamentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindNom) {
					queryPos.add(nom);
				}

				list = (List<Medicament>)QueryUtil.list(
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
	 * Returns the first medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	@Override
	public Medicament findByNom_First(
			String nom, OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByNom_First(nom, orderByComparator);

		if (medicament != null) {
			return medicament;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("nom=");
		sb.append(nom);

		sb.append("}");

		throw new NoSuchMedicamentException(sb.toString());
	}

	/**
	 * Returns the first medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	@Override
	public Medicament fetchByNom_First(
		String nom, OrderByComparator<Medicament> orderByComparator) {

		List<Medicament> list = findByNom(nom, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	@Override
	public Medicament findByNom_Last(
			String nom, OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByNom_Last(nom, orderByComparator);

		if (medicament != null) {
			return medicament;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("nom=");
		sb.append(nom);

		sb.append("}");

		throw new NoSuchMedicamentException(sb.toString());
	}

	/**
	 * Returns the last medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	@Override
	public Medicament fetchByNom_Last(
		String nom, OrderByComparator<Medicament> orderByComparator) {

		int count = countByNom(nom);

		if (count == 0) {
			return null;
		}

		List<Medicament> list = findByNom(
			nom, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the medicaments before and after the current medicament in the ordered set where nom = &#63;.
	 *
	 * @param idMedicament the primary key of the current medicament
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament[] findByNom_PrevAndNext(
			long idMedicament, String nom,
			OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		nom = Objects.toString(nom, "");

		Medicament medicament = findByPrimaryKey(idMedicament);

		Session session = null;

		try {
			session = openSession();

			Medicament[] array = new MedicamentImpl[3];

			array[0] = getByNom_PrevAndNext(
				session, medicament, nom, orderByComparator, true);

			array[1] = medicament;

			array[2] = getByNom_PrevAndNext(
				session, medicament, nom, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Medicament getByNom_PrevAndNext(
		Session session, Medicament medicament, String nom,
		OrderByComparator<Medicament> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MEDICAMENT_WHERE);

		boolean bindNom = false;

		if (nom.isEmpty()) {
			sb.append(_FINDER_COLUMN_NOM_NOM_3);
		}
		else {
			bindNom = true;

			sb.append(_FINDER_COLUMN_NOM_NOM_2);
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
			sb.append(MedicamentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindNom) {
			queryPos.add(nom);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(medicament)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Medicament> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the medicaments where nom = &#63; from the database.
	 *
	 * @param nom the nom
	 */
	@Override
	public void removeByNom(String nom) {
		for (Medicament medicament :
				findByNom(nom, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(medicament);
		}
	}

	/**
	 * Returns the number of medicaments where nom = &#63;.
	 *
	 * @param nom the nom
	 * @return the number of matching medicaments
	 */
	@Override
	public int countByNom(String nom) {
		nom = Objects.toString(nom, "");

		FinderPath finderPath = _finderPathCountByNom;

		Object[] finderArgs = new Object[] {nom};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MEDICAMENT_WHERE);

			boolean bindNom = false;

			if (nom.isEmpty()) {
				sb.append(_FINDER_COLUMN_NOM_NOM_3);
			}
			else {
				bindNom = true;

				sb.append(_FINDER_COLUMN_NOM_NOM_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindNom) {
					queryPos.add(nom);
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

	private static final String _FINDER_COLUMN_NOM_NOM_2 = "medicament.nom = ?";

	private static final String _FINDER_COLUMN_NOM_NOM_3 =
		"(medicament.nom IS NULL OR medicament.nom = '')";

	private FinderPath _finderPathWithPaginationFindByCategorie;
	private FinderPath _finderPathWithoutPaginationFindByCategorie;
	private FinderPath _finderPathCountByCategorie;

	/**
	 * Returns all the medicaments where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @return the matching medicaments
	 */
	@Override
	public List<Medicament> findByCategorie(String categorie) {
		return findByCategorie(
			categorie, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the medicaments where categorie = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param categorie the categorie
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCategorie(
		String categorie, int start, int end) {

		return findByCategorie(categorie, start, end, null);
	}

	/**
	 * Returns an ordered range of all the medicaments where categorie = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param categorie the categorie
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCategorie(
		String categorie, int start, int end,
		OrderByComparator<Medicament> orderByComparator) {

		return findByCategorie(categorie, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the medicaments where categorie = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param categorie the categorie
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching medicaments
	 */
	@Override
	public List<Medicament> findByCategorie(
		String categorie, int start, int end,
		OrderByComparator<Medicament> orderByComparator,
		boolean useFinderCache) {

		categorie = Objects.toString(categorie, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCategorie;
				finderArgs = new Object[] {categorie};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCategorie;
			finderArgs = new Object[] {
				categorie, start, end, orderByComparator
			};
		}

		List<Medicament> list = null;

		if (useFinderCache) {
			list = (List<Medicament>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Medicament medicament : list) {
					if (!categorie.equals(medicament.getCategorie())) {
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

			sb.append(_SQL_SELECT_MEDICAMENT_WHERE);

			boolean bindCategorie = false;

			if (categorie.isEmpty()) {
				sb.append(_FINDER_COLUMN_CATEGORIE_CATEGORIE_3);
			}
			else {
				bindCategorie = true;

				sb.append(_FINDER_COLUMN_CATEGORIE_CATEGORIE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MedicamentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCategorie) {
					queryPos.add(categorie);
				}

				list = (List<Medicament>)QueryUtil.list(
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
	 * Returns the first medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	@Override
	public Medicament findByCategorie_First(
			String categorie, OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByCategorie_First(
			categorie, orderByComparator);

		if (medicament != null) {
			return medicament;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("categorie=");
		sb.append(categorie);

		sb.append("}");

		throw new NoSuchMedicamentException(sb.toString());
	}

	/**
	 * Returns the first medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	@Override
	public Medicament fetchByCategorie_First(
		String categorie, OrderByComparator<Medicament> orderByComparator) {

		List<Medicament> list = findByCategorie(
			categorie, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	@Override
	public Medicament findByCategorie_Last(
			String categorie, OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByCategorie_Last(
			categorie, orderByComparator);

		if (medicament != null) {
			return medicament;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("categorie=");
		sb.append(categorie);

		sb.append("}");

		throw new NoSuchMedicamentException(sb.toString());
	}

	/**
	 * Returns the last medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	@Override
	public Medicament fetchByCategorie_Last(
		String categorie, OrderByComparator<Medicament> orderByComparator) {

		int count = countByCategorie(categorie);

		if (count == 0) {
			return null;
		}

		List<Medicament> list = findByCategorie(
			categorie, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the medicaments before and after the current medicament in the ordered set where categorie = &#63;.
	 *
	 * @param idMedicament the primary key of the current medicament
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament[] findByCategorie_PrevAndNext(
			long idMedicament, String categorie,
			OrderByComparator<Medicament> orderByComparator)
		throws NoSuchMedicamentException {

		categorie = Objects.toString(categorie, "");

		Medicament medicament = findByPrimaryKey(idMedicament);

		Session session = null;

		try {
			session = openSession();

			Medicament[] array = new MedicamentImpl[3];

			array[0] = getByCategorie_PrevAndNext(
				session, medicament, categorie, orderByComparator, true);

			array[1] = medicament;

			array[2] = getByCategorie_PrevAndNext(
				session, medicament, categorie, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected Medicament getByCategorie_PrevAndNext(
		Session session, Medicament medicament, String categorie,
		OrderByComparator<Medicament> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MEDICAMENT_WHERE);

		boolean bindCategorie = false;

		if (categorie.isEmpty()) {
			sb.append(_FINDER_COLUMN_CATEGORIE_CATEGORIE_3);
		}
		else {
			bindCategorie = true;

			sb.append(_FINDER_COLUMN_CATEGORIE_CATEGORIE_2);
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
			sb.append(MedicamentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCategorie) {
			queryPos.add(categorie);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(medicament)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<Medicament> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the medicaments where categorie = &#63; from the database.
	 *
	 * @param categorie the categorie
	 */
	@Override
	public void removeByCategorie(String categorie) {
		for (Medicament medicament :
				findByCategorie(
					categorie, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(medicament);
		}
	}

	/**
	 * Returns the number of medicaments where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @return the number of matching medicaments
	 */
	@Override
	public int countByCategorie(String categorie) {
		categorie = Objects.toString(categorie, "");

		FinderPath finderPath = _finderPathCountByCategorie;

		Object[] finderArgs = new Object[] {categorie};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MEDICAMENT_WHERE);

			boolean bindCategorie = false;

			if (categorie.isEmpty()) {
				sb.append(_FINDER_COLUMN_CATEGORIE_CATEGORIE_3);
			}
			else {
				bindCategorie = true;

				sb.append(_FINDER_COLUMN_CATEGORIE_CATEGORIE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCategorie) {
					queryPos.add(categorie);
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

	private static final String _FINDER_COLUMN_CATEGORIE_CATEGORIE_2 =
		"medicament.categorie = ?";

	private static final String _FINDER_COLUMN_CATEGORIE_CATEGORIE_3 =
		"(medicament.categorie IS NULL OR medicament.categorie = '')";

	public MedicamentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("code", "code_");

		setDBColumnNames(dbColumnNames);

		setModelClass(Medicament.class);

		setModelImplClass(MedicamentImpl.class);
		setModelPKClass(long.class);

		setTable(MedicamentTable.INSTANCE);
	}

	/**
	 * Caches the medicament in the entity cache if it is enabled.
	 *
	 * @param medicament the medicament
	 */
	@Override
	public void cacheResult(Medicament medicament) {
		entityCache.putResult(
			MedicamentImpl.class, medicament.getPrimaryKey(), medicament);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the medicaments in the entity cache if it is enabled.
	 *
	 * @param medicaments the medicaments
	 */
	@Override
	public void cacheResult(List<Medicament> medicaments) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (medicaments.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (Medicament medicament : medicaments) {
			if (entityCache.getResult(
					MedicamentImpl.class, medicament.getPrimaryKey()) == null) {

				cacheResult(medicament);
			}
		}
	}

	/**
	 * Clears the cache for all medicaments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MedicamentImpl.class);

		finderCache.clearCache(MedicamentImpl.class);
	}

	/**
	 * Clears the cache for the medicament.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Medicament medicament) {
		entityCache.removeResult(MedicamentImpl.class, medicament);
	}

	@Override
	public void clearCache(List<Medicament> medicaments) {
		for (Medicament medicament : medicaments) {
			entityCache.removeResult(MedicamentImpl.class, medicament);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MedicamentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MedicamentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new medicament with the primary key. Does not add the medicament to the database.
	 *
	 * @param idMedicament the primary key for the new medicament
	 * @return the new medicament
	 */
	@Override
	public Medicament create(long idMedicament) {
		Medicament medicament = new MedicamentImpl();

		medicament.setNew(true);
		medicament.setPrimaryKey(idMedicament);

		return medicament;
	}

	/**
	 * Removes the medicament with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament that was removed
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament remove(long idMedicament)
		throws NoSuchMedicamentException {

		return remove((Serializable)idMedicament);
	}

	/**
	 * Removes the medicament with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the medicament
	 * @return the medicament that was removed
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament remove(Serializable primaryKey)
		throws NoSuchMedicamentException {

		Session session = null;

		try {
			session = openSession();

			Medicament medicament = (Medicament)session.get(
				MedicamentImpl.class, primaryKey);

			if (medicament == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMedicamentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(medicament);
		}
		catch (NoSuchMedicamentException noSuchEntityException) {
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
	protected Medicament removeImpl(Medicament medicament) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(medicament)) {
				medicament = (Medicament)session.get(
					MedicamentImpl.class, medicament.getPrimaryKeyObj());
			}

			if (medicament != null) {
				session.delete(medicament);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (medicament != null) {
			clearCache(medicament);
		}

		return medicament;
	}

	@Override
	public Medicament updateImpl(Medicament medicament) {
		boolean isNew = medicament.isNew();

		if (!(medicament instanceof MedicamentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(medicament.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(medicament);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in medicament proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Medicament implementation " +
					medicament.getClass());
		}

		MedicamentModelImpl medicamentModelImpl =
			(MedicamentModelImpl)medicament;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(medicament);
			}
			else {
				medicament = (Medicament)session.merge(medicament);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MedicamentImpl.class, medicamentModelImpl, false, true);

		if (isNew) {
			medicament.setNew(false);
		}

		medicament.resetOriginalValues();

		return medicament;
	}

	/**
	 * Returns the medicament with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the medicament
	 * @return the medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMedicamentException {

		Medicament medicament = fetchByPrimaryKey(primaryKey);

		if (medicament == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMedicamentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return medicament;
	}

	/**
	 * Returns the medicament with the primary key or throws a <code>NoSuchMedicamentException</code> if it could not be found.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament findByPrimaryKey(long idMedicament)
		throws NoSuchMedicamentException {

		return findByPrimaryKey((Serializable)idMedicament);
	}

	/**
	 * Returns the medicament with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament, or <code>null</code> if a medicament with the primary key could not be found
	 */
	@Override
	public Medicament fetchByPrimaryKey(long idMedicament) {
		return fetchByPrimaryKey((Serializable)idMedicament);
	}

	/**
	 * Returns all the medicaments.
	 *
	 * @return the medicaments
	 */
	@Override
	public List<Medicament> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the medicaments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @return the range of medicaments
	 */
	@Override
	public List<Medicament> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the medicaments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of medicaments
	 */
	@Override
	public List<Medicament> findAll(
		int start, int end, OrderByComparator<Medicament> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the medicaments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MedicamentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of medicaments
	 * @param end the upper bound of the range of medicaments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of medicaments
	 */
	@Override
	public List<Medicament> findAll(
		int start, int end, OrderByComparator<Medicament> orderByComparator,
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

		List<Medicament> list = null;

		if (useFinderCache) {
			list = (List<Medicament>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MEDICAMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MEDICAMENT;

				sql = sql.concat(MedicamentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<Medicament>)QueryUtil.list(
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
	 * Removes all the medicaments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Medicament medicament : findAll()) {
			remove(medicament);
		}
	}

	/**
	 * Returns the number of medicaments.
	 *
	 * @return the number of medicaments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MEDICAMENT);

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
		return "idMedicament";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MEDICAMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MedicamentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the medicament persistence.
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

		_finderPathWithPaginationFindByCode = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCode",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"code_"}, true);

		_finderPathWithoutPaginationFindByCode = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCode",
			new String[] {String.class.getName()}, new String[] {"code_"},
			true);

		_finderPathCountByCode = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCode",
			new String[] {String.class.getName()}, new String[] {"code_"},
			false);

		_finderPathWithPaginationFindByCodeBarre = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCodeBarre",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"codeBarre"}, true);

		_finderPathWithoutPaginationFindByCodeBarre = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCodeBarre",
			new String[] {String.class.getName()}, new String[] {"codeBarre"},
			true);

		_finderPathCountByCodeBarre = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCodeBarre",
			new String[] {String.class.getName()}, new String[] {"codeBarre"},
			false);

		_finderPathWithPaginationFindByNom = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByNom",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"nom"}, true);

		_finderPathWithoutPaginationFindByNom = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByNom",
			new String[] {String.class.getName()}, new String[] {"nom"}, true);

		_finderPathCountByNom = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByNom",
			new String[] {String.class.getName()}, new String[] {"nom"}, false);

		_finderPathWithPaginationFindByCategorie = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCategorie",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"categorie"}, true);

		_finderPathWithoutPaginationFindByCategorie = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCategorie",
			new String[] {String.class.getName()}, new String[] {"categorie"},
			true);

		_finderPathCountByCategorie = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCategorie",
			new String[] {String.class.getName()}, new String[] {"categorie"},
			false);

		MedicamentUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		MedicamentUtil.setPersistence(null);

		entityCache.removeCache(MedicamentImpl.class.getName());
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

	private static final String _SQL_SELECT_MEDICAMENT =
		"SELECT medicament FROM Medicament medicament";

	private static final String _SQL_SELECT_MEDICAMENT_WHERE =
		"SELECT medicament FROM Medicament medicament WHERE ";

	private static final String _SQL_COUNT_MEDICAMENT =
		"SELECT COUNT(medicament) FROM Medicament medicament";

	private static final String _SQL_COUNT_MEDICAMENT_WHERE =
		"SELECT COUNT(medicament) FROM Medicament medicament WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "medicament.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Medicament exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Medicament exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MedicamentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"code"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
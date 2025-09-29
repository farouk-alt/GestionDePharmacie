/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import gestion_de_pharmacie.exception.NoSuchVenteDetailException;

import gestion_de_pharmacie.model.VenteDetail;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the vente detail service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see VenteDetailUtil
 * @generated
 */
@ProviderType
public interface VenteDetailPersistence extends BasePersistence<VenteDetail> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VenteDetailUtil} to access the vente detail persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the vente details where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @return the matching vente details
	 */
	public java.util.List<VenteDetail> findByVente(long idVente);

	/**
	 * Returns a range of all the vente details where idVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idVente the id vente
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @return the range of matching vente details
	 */
	public java.util.List<VenteDetail> findByVente(
		long idVente, int start, int end);

	/**
	 * Returns an ordered range of all the vente details where idVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idVente the id vente
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching vente details
	 */
	public java.util.List<VenteDetail> findByVente(
		long idVente, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator);

	/**
	 * Returns an ordered range of all the vente details where idVente = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idVente the id vente
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching vente details
	 */
	public java.util.List<VenteDetail> findByVente(
		long idVente, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente detail
	 * @throws NoSuchVenteDetailException if a matching vente detail could not be found
	 */
	public VenteDetail findByVente_First(
			long idVente,
			com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
				orderByComparator)
		throws NoSuchVenteDetailException;

	/**
	 * Returns the first vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente detail, or <code>null</code> if a matching vente detail could not be found
	 */
	public VenteDetail fetchByVente_First(
		long idVente,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator);

	/**
	 * Returns the last vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente detail
	 * @throws NoSuchVenteDetailException if a matching vente detail could not be found
	 */
	public VenteDetail findByVente_Last(
			long idVente,
			com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
				orderByComparator)
		throws NoSuchVenteDetailException;

	/**
	 * Returns the last vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente detail, or <code>null</code> if a matching vente detail could not be found
	 */
	public VenteDetail fetchByVente_Last(
		long idVente,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator);

	/**
	 * Returns the vente details before and after the current vente detail in the ordered set where idVente = &#63;.
	 *
	 * @param idDetail the primary key of the current vente detail
	 * @param idVente the id vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente detail
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	public VenteDetail[] findByVente_PrevAndNext(
			long idDetail, long idVente,
			com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
				orderByComparator)
		throws NoSuchVenteDetailException;

	/**
	 * Removes all the vente details where idVente = &#63; from the database.
	 *
	 * @param idVente the id vente
	 */
	public void removeByVente(long idVente);

	/**
	 * Returns the number of vente details where idVente = &#63;.
	 *
	 * @param idVente the id vente
	 * @return the number of matching vente details
	 */
	public int countByVente(long idVente);

	/**
	 * Returns all the vente details where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the matching vente details
	 */
	public java.util.List<VenteDetail> findByMedicament(long idMedicament);

	/**
	 * Returns a range of all the vente details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @return the range of matching vente details
	 */
	public java.util.List<VenteDetail> findByMedicament(
		long idMedicament, int start, int end);

	/**
	 * Returns an ordered range of all the vente details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching vente details
	 */
	public java.util.List<VenteDetail> findByMedicament(
		long idMedicament, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator);

	/**
	 * Returns an ordered range of all the vente details where idMedicament = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>VenteDetailModelImpl</code>.
	 * </p>
	 *
	 * @param idMedicament the id medicament
	 * @param start the lower bound of the range of vente details
	 * @param end the upper bound of the range of vente details (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching vente details
	 */
	public java.util.List<VenteDetail> findByMedicament(
		long idMedicament, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente detail
	 * @throws NoSuchVenteDetailException if a matching vente detail could not be found
	 */
	public VenteDetail findByMedicament_First(
			long idMedicament,
			com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
				orderByComparator)
		throws NoSuchVenteDetailException;

	/**
	 * Returns the first vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente detail, or <code>null</code> if a matching vente detail could not be found
	 */
	public VenteDetail fetchByMedicament_First(
		long idMedicament,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator);

	/**
	 * Returns the last vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente detail
	 * @throws NoSuchVenteDetailException if a matching vente detail could not be found
	 */
	public VenteDetail findByMedicament_Last(
			long idMedicament,
			com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
				orderByComparator)
		throws NoSuchVenteDetailException;

	/**
	 * Returns the last vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente detail, or <code>null</code> if a matching vente detail could not be found
	 */
	public VenteDetail fetchByMedicament_Last(
		long idMedicament,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator);

	/**
	 * Returns the vente details before and after the current vente detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idDetail the primary key of the current vente detail
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente detail
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	public VenteDetail[] findByMedicament_PrevAndNext(
			long idDetail, long idMedicament,
			com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
				orderByComparator)
		throws NoSuchVenteDetailException;

	/**
	 * Removes all the vente details where idMedicament = &#63; from the database.
	 *
	 * @param idMedicament the id medicament
	 */
	public void removeByMedicament(long idMedicament);

	/**
	 * Returns the number of vente details where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the number of matching vente details
	 */
	public int countByMedicament(long idMedicament);

	/**
	 * Caches the vente detail in the entity cache if it is enabled.
	 *
	 * @param venteDetail the vente detail
	 */
	public void cacheResult(VenteDetail venteDetail);

	/**
	 * Caches the vente details in the entity cache if it is enabled.
	 *
	 * @param venteDetails the vente details
	 */
	public void cacheResult(java.util.List<VenteDetail> venteDetails);

	/**
	 * Creates a new vente detail with the primary key. Does not add the vente detail to the database.
	 *
	 * @param idDetail the primary key for the new vente detail
	 * @return the new vente detail
	 */
	public VenteDetail create(long idDetail);

	/**
	 * Removes the vente detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail that was removed
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	public VenteDetail remove(long idDetail) throws NoSuchVenteDetailException;

	public VenteDetail updateImpl(VenteDetail venteDetail);

	/**
	 * Returns the vente detail with the primary key or throws a <code>NoSuchVenteDetailException</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail
	 * @throws NoSuchVenteDetailException if a vente detail with the primary key could not be found
	 */
	public VenteDetail findByPrimaryKey(long idDetail)
		throws NoSuchVenteDetailException;

	/**
	 * Returns the vente detail with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the vente detail
	 * @return the vente detail, or <code>null</code> if a vente detail with the primary key could not be found
	 */
	public VenteDetail fetchByPrimaryKey(long idDetail);

	/**
	 * Returns all the vente details.
	 *
	 * @return the vente details
	 */
	public java.util.List<VenteDetail> findAll();

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
	public java.util.List<VenteDetail> findAll(int start, int end);

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
	public java.util.List<VenteDetail> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator);

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
	public java.util.List<VenteDetail> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VenteDetail>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the vente details from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of vente details.
	 *
	 * @return the number of vente details
	 */
	public int countAll();

}
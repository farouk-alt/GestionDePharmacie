/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import gestion_de_pharmacie.exception.NoSuchCommandeDetailException;

import gestion_de_pharmacie.model.CommandeDetail;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commande detail service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see CommandeDetailUtil
 * @generated
 */
@ProviderType
public interface CommandeDetailPersistence
	extends BasePersistence<CommandeDetail> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommandeDetailUtil} to access the commande detail persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commande details where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @return the matching commande details
	 */
	public java.util.List<CommandeDetail> findByIdCommande(long idCommande);

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
	public java.util.List<CommandeDetail> findByIdCommande(
		long idCommande, int start, int end);

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
	public java.util.List<CommandeDetail> findByIdCommande(
		long idCommande, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator);

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
	public java.util.List<CommandeDetail> findByIdCommande(
		long idCommande, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande detail
	 * @throws NoSuchCommandeDetailException if a matching commande detail could not be found
	 */
	public CommandeDetail findByIdCommande_First(
			long idCommande,
			com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
				orderByComparator)
		throws NoSuchCommandeDetailException;

	/**
	 * Returns the first commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande detail, or <code>null</code> if a matching commande detail could not be found
	 */
	public CommandeDetail fetchByIdCommande_First(
		long idCommande,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator);

	/**
	 * Returns the last commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande detail
	 * @throws NoSuchCommandeDetailException if a matching commande detail could not be found
	 */
	public CommandeDetail findByIdCommande_Last(
			long idCommande,
			com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
				orderByComparator)
		throws NoSuchCommandeDetailException;

	/**
	 * Returns the last commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande detail, or <code>null</code> if a matching commande detail could not be found
	 */
	public CommandeDetail fetchByIdCommande_Last(
		long idCommande,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator);

	/**
	 * Returns the commande details before and after the current commande detail in the ordered set where idCommande = &#63;.
	 *
	 * @param idDetail the primary key of the current commande detail
	 * @param idCommande the id commande
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commande detail
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	public CommandeDetail[] findByIdCommande_PrevAndNext(
			long idDetail, long idCommande,
			com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
				orderByComparator)
		throws NoSuchCommandeDetailException;

	/**
	 * Removes all the commande details where idCommande = &#63; from the database.
	 *
	 * @param idCommande the id commande
	 */
	public void removeByIdCommande(long idCommande);

	/**
	 * Returns the number of commande details where idCommande = &#63;.
	 *
	 * @param idCommande the id commande
	 * @return the number of matching commande details
	 */
	public int countByIdCommande(long idCommande);

	/**
	 * Returns all the commande details where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the matching commande details
	 */
	public java.util.List<CommandeDetail> findByIdMedicament(long idMedicament);

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
	public java.util.List<CommandeDetail> findByIdMedicament(
		long idMedicament, int start, int end);

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
	public java.util.List<CommandeDetail> findByIdMedicament(
		long idMedicament, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator);

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
	public java.util.List<CommandeDetail> findByIdMedicament(
		long idMedicament, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande detail
	 * @throws NoSuchCommandeDetailException if a matching commande detail could not be found
	 */
	public CommandeDetail findByIdMedicament_First(
			long idMedicament,
			com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
				orderByComparator)
		throws NoSuchCommandeDetailException;

	/**
	 * Returns the first commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande detail, or <code>null</code> if a matching commande detail could not be found
	 */
	public CommandeDetail fetchByIdMedicament_First(
		long idMedicament,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator);

	/**
	 * Returns the last commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande detail
	 * @throws NoSuchCommandeDetailException if a matching commande detail could not be found
	 */
	public CommandeDetail findByIdMedicament_Last(
			long idMedicament,
			com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
				orderByComparator)
		throws NoSuchCommandeDetailException;

	/**
	 * Returns the last commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande detail, or <code>null</code> if a matching commande detail could not be found
	 */
	public CommandeDetail fetchByIdMedicament_Last(
		long idMedicament,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator);

	/**
	 * Returns the commande details before and after the current commande detail in the ordered set where idMedicament = &#63;.
	 *
	 * @param idDetail the primary key of the current commande detail
	 * @param idMedicament the id medicament
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commande detail
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	public CommandeDetail[] findByIdMedicament_PrevAndNext(
			long idDetail, long idMedicament,
			com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
				orderByComparator)
		throws NoSuchCommandeDetailException;

	/**
	 * Removes all the commande details where idMedicament = &#63; from the database.
	 *
	 * @param idMedicament the id medicament
	 */
	public void removeByIdMedicament(long idMedicament);

	/**
	 * Returns the number of commande details where idMedicament = &#63;.
	 *
	 * @param idMedicament the id medicament
	 * @return the number of matching commande details
	 */
	public int countByIdMedicament(long idMedicament);

	/**
	 * Caches the commande detail in the entity cache if it is enabled.
	 *
	 * @param commandeDetail the commande detail
	 */
	public void cacheResult(CommandeDetail commandeDetail);

	/**
	 * Caches the commande details in the entity cache if it is enabled.
	 *
	 * @param commandeDetails the commande details
	 */
	public void cacheResult(java.util.List<CommandeDetail> commandeDetails);

	/**
	 * Creates a new commande detail with the primary key. Does not add the commande detail to the database.
	 *
	 * @param idDetail the primary key for the new commande detail
	 * @return the new commande detail
	 */
	public CommandeDetail create(long idDetail);

	/**
	 * Removes the commande detail with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail that was removed
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	public CommandeDetail remove(long idDetail)
		throws NoSuchCommandeDetailException;

	public CommandeDetail updateImpl(CommandeDetail commandeDetail);

	/**
	 * Returns the commande detail with the primary key or throws a <code>NoSuchCommandeDetailException</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail
	 * @throws NoSuchCommandeDetailException if a commande detail with the primary key could not be found
	 */
	public CommandeDetail findByPrimaryKey(long idDetail)
		throws NoSuchCommandeDetailException;

	/**
	 * Returns the commande detail with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idDetail the primary key of the commande detail
	 * @return the commande detail, or <code>null</code> if a commande detail with the primary key could not be found
	 */
	public CommandeDetail fetchByPrimaryKey(long idDetail);

	/**
	 * Returns all the commande details.
	 *
	 * @return the commande details
	 */
	public java.util.List<CommandeDetail> findAll();

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
	public java.util.List<CommandeDetail> findAll(int start, int end);

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
	public java.util.List<CommandeDetail> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator);

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
	public java.util.List<CommandeDetail> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommandeDetail>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commande details from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commande details.
	 *
	 * @return the number of commande details
	 */
	public int countAll();

}
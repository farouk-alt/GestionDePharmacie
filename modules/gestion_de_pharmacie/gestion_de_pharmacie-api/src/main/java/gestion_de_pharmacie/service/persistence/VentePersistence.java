/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import gestion_de_pharmacie.exception.NoSuchVenteException;

import gestion_de_pharmacie.model.Vente;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the vente service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see VenteUtil
 * @generated
 */
@ProviderType
public interface VentePersistence extends BasePersistence<Vente> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VenteUtil} to access the vente persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ventes where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the matching ventes
	 */
	public java.util.List<Vente> findByUser(long idUtilisateur);

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
	public java.util.List<Vente> findByUser(
		long idUtilisateur, int start, int end);

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
	public java.util.List<Vente> findByUser(
		long idUtilisateur, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator);

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
	public java.util.List<Vente> findByUser(
		long idUtilisateur, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	public Vente findByUser_First(
			long idUtilisateur,
			com.liferay.portal.kernel.util.OrderByComparator<Vente>
				orderByComparator)
		throws NoSuchVenteException;

	/**
	 * Returns the first vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente, or <code>null</code> if a matching vente could not be found
	 */
	public Vente fetchByUser_First(
		long idUtilisateur,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator);

	/**
	 * Returns the last vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	public Vente findByUser_Last(
			long idUtilisateur,
			com.liferay.portal.kernel.util.OrderByComparator<Vente>
				orderByComparator)
		throws NoSuchVenteException;

	/**
	 * Returns the last vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente, or <code>null</code> if a matching vente could not be found
	 */
	public Vente fetchByUser_Last(
		long idUtilisateur,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator);

	/**
	 * Returns the ventes before and after the current vente in the ordered set where idUtilisateur = &#63;.
	 *
	 * @param idVente the primary key of the current vente
	 * @param idUtilisateur the id utilisateur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	public Vente[] findByUser_PrevAndNext(
			long idVente, long idUtilisateur,
			com.liferay.portal.kernel.util.OrderByComparator<Vente>
				orderByComparator)
		throws NoSuchVenteException;

	/**
	 * Removes all the ventes where idUtilisateur = &#63; from the database.
	 *
	 * @param idUtilisateur the id utilisateur
	 */
	public void removeByUser(long idUtilisateur);

	/**
	 * Returns the number of ventes where idUtilisateur = &#63;.
	 *
	 * @param idUtilisateur the id utilisateur
	 * @return the number of matching ventes
	 */
	public int countByUser(long idUtilisateur);

	/**
	 * Returns all the ventes where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @return the matching ventes
	 */
	public java.util.List<Vente> findByDate(Date dateVente);

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
	public java.util.List<Vente> findByDate(Date dateVente, int start, int end);

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
	public java.util.List<Vente> findByDate(
		Date dateVente, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator);

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
	public java.util.List<Vente> findByDate(
		Date dateVente, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	public Vente findByDate_First(
			Date dateVente,
			com.liferay.portal.kernel.util.OrderByComparator<Vente>
				orderByComparator)
		throws NoSuchVenteException;

	/**
	 * Returns the first vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching vente, or <code>null</code> if a matching vente could not be found
	 */
	public Vente fetchByDate_First(
		Date dateVente,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator);

	/**
	 * Returns the last vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente
	 * @throws NoSuchVenteException if a matching vente could not be found
	 */
	public Vente findByDate_Last(
			Date dateVente,
			com.liferay.portal.kernel.util.OrderByComparator<Vente>
				orderByComparator)
		throws NoSuchVenteException;

	/**
	 * Returns the last vente in the ordered set where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching vente, or <code>null</code> if a matching vente could not be found
	 */
	public Vente fetchByDate_Last(
		Date dateVente,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator);

	/**
	 * Returns the ventes before and after the current vente in the ordered set where dateVente = &#63;.
	 *
	 * @param idVente the primary key of the current vente
	 * @param dateVente the date vente
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	public Vente[] findByDate_PrevAndNext(
			long idVente, Date dateVente,
			com.liferay.portal.kernel.util.OrderByComparator<Vente>
				orderByComparator)
		throws NoSuchVenteException;

	/**
	 * Removes all the ventes where dateVente = &#63; from the database.
	 *
	 * @param dateVente the date vente
	 */
	public void removeByDate(Date dateVente);

	/**
	 * Returns the number of ventes where dateVente = &#63;.
	 *
	 * @param dateVente the date vente
	 * @return the number of matching ventes
	 */
	public int countByDate(Date dateVente);

	/**
	 * Caches the vente in the entity cache if it is enabled.
	 *
	 * @param vente the vente
	 */
	public void cacheResult(Vente vente);

	/**
	 * Caches the ventes in the entity cache if it is enabled.
	 *
	 * @param ventes the ventes
	 */
	public void cacheResult(java.util.List<Vente> ventes);

	/**
	 * Creates a new vente with the primary key. Does not add the vente to the database.
	 *
	 * @param idVente the primary key for the new vente
	 * @return the new vente
	 */
	public Vente create(long idVente);

	/**
	 * Removes the vente with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente that was removed
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	public Vente remove(long idVente) throws NoSuchVenteException;

	public Vente updateImpl(Vente vente);

	/**
	 * Returns the vente with the primary key or throws a <code>NoSuchVenteException</code> if it could not be found.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente
	 * @throws NoSuchVenteException if a vente with the primary key could not be found
	 */
	public Vente findByPrimaryKey(long idVente) throws NoSuchVenteException;

	/**
	 * Returns the vente with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idVente the primary key of the vente
	 * @return the vente, or <code>null</code> if a vente with the primary key could not be found
	 */
	public Vente fetchByPrimaryKey(long idVente);

	/**
	 * Returns all the ventes.
	 *
	 * @return the ventes
	 */
	public java.util.List<Vente> findAll();

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
	public java.util.List<Vente> findAll(int start, int end);

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
	public java.util.List<Vente> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator);

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
	public java.util.List<Vente> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Vente>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ventes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ventes.
	 *
	 * @return the number of ventes
	 */
	public int countAll();

}
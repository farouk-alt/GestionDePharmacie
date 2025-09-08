/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import gestion_de_pharmacie.exception.NoSuchMedicamentException;

import gestion_de_pharmacie.model.Medicament;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the medicament service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see MedicamentUtil
 * @generated
 */
@ProviderType
public interface MedicamentPersistence extends BasePersistence<Medicament> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MedicamentUtil} to access the medicament persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the medicaments where nom = &#63;.
	 *
	 * @param nom the nom
	 * @return the matching medicaments
	 */
	public java.util.List<Medicament> findByNom(String nom);

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
	public java.util.List<Medicament> findByNom(String nom, int start, int end);

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
	public java.util.List<Medicament> findByNom(
		String nom, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator);

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
	public java.util.List<Medicament> findByNom(
		String nom, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	public Medicament findByNom_First(
			String nom,
			com.liferay.portal.kernel.util.OrderByComparator<Medicament>
				orderByComparator)
		throws NoSuchMedicamentException;

	/**
	 * Returns the first medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	public Medicament fetchByNom_First(
		String nom,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator);

	/**
	 * Returns the last medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	public Medicament findByNom_Last(
			String nom,
			com.liferay.portal.kernel.util.OrderByComparator<Medicament>
				orderByComparator)
		throws NoSuchMedicamentException;

	/**
	 * Returns the last medicament in the ordered set where nom = &#63;.
	 *
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	public Medicament fetchByNom_Last(
		String nom,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator);

	/**
	 * Returns the medicaments before and after the current medicament in the ordered set where nom = &#63;.
	 *
	 * @param idMedicament the primary key of the current medicament
	 * @param nom the nom
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	public Medicament[] findByNom_PrevAndNext(
			long idMedicament, String nom,
			com.liferay.portal.kernel.util.OrderByComparator<Medicament>
				orderByComparator)
		throws NoSuchMedicamentException;

	/**
	 * Removes all the medicaments where nom = &#63; from the database.
	 *
	 * @param nom the nom
	 */
	public void removeByNom(String nom);

	/**
	 * Returns the number of medicaments where nom = &#63;.
	 *
	 * @param nom the nom
	 * @return the number of matching medicaments
	 */
	public int countByNom(String nom);

	/**
	 * Returns all the medicaments where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @return the matching medicaments
	 */
	public java.util.List<Medicament> findByCategorie(String categorie);

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
	public java.util.List<Medicament> findByCategorie(
		String categorie, int start, int end);

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
	public java.util.List<Medicament> findByCategorie(
		String categorie, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator);

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
	public java.util.List<Medicament> findByCategorie(
		String categorie, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	public Medicament findByCategorie_First(
			String categorie,
			com.liferay.portal.kernel.util.OrderByComparator<Medicament>
				orderByComparator)
		throws NoSuchMedicamentException;

	/**
	 * Returns the first medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	public Medicament fetchByCategorie_First(
		String categorie,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator);

	/**
	 * Returns the last medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament
	 * @throws NoSuchMedicamentException if a matching medicament could not be found
	 */
	public Medicament findByCategorie_Last(
			String categorie,
			com.liferay.portal.kernel.util.OrderByComparator<Medicament>
				orderByComparator)
		throws NoSuchMedicamentException;

	/**
	 * Returns the last medicament in the ordered set where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching medicament, or <code>null</code> if a matching medicament could not be found
	 */
	public Medicament fetchByCategorie_Last(
		String categorie,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator);

	/**
	 * Returns the medicaments before and after the current medicament in the ordered set where categorie = &#63;.
	 *
	 * @param idMedicament the primary key of the current medicament
	 * @param categorie the categorie
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	public Medicament[] findByCategorie_PrevAndNext(
			long idMedicament, String categorie,
			com.liferay.portal.kernel.util.OrderByComparator<Medicament>
				orderByComparator)
		throws NoSuchMedicamentException;

	/**
	 * Removes all the medicaments where categorie = &#63; from the database.
	 *
	 * @param categorie the categorie
	 */
	public void removeByCategorie(String categorie);

	/**
	 * Returns the number of medicaments where categorie = &#63;.
	 *
	 * @param categorie the categorie
	 * @return the number of matching medicaments
	 */
	public int countByCategorie(String categorie);

	/**
	 * Caches the medicament in the entity cache if it is enabled.
	 *
	 * @param medicament the medicament
	 */
	public void cacheResult(Medicament medicament);

	/**
	 * Caches the medicaments in the entity cache if it is enabled.
	 *
	 * @param medicaments the medicaments
	 */
	public void cacheResult(java.util.List<Medicament> medicaments);

	/**
	 * Creates a new medicament with the primary key. Does not add the medicament to the database.
	 *
	 * @param idMedicament the primary key for the new medicament
	 * @return the new medicament
	 */
	public Medicament create(long idMedicament);

	/**
	 * Removes the medicament with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament that was removed
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	public Medicament remove(long idMedicament)
		throws NoSuchMedicamentException;

	public Medicament updateImpl(Medicament medicament);

	/**
	 * Returns the medicament with the primary key or throws a <code>NoSuchMedicamentException</code> if it could not be found.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament
	 * @throws NoSuchMedicamentException if a medicament with the primary key could not be found
	 */
	public Medicament findByPrimaryKey(long idMedicament)
		throws NoSuchMedicamentException;

	/**
	 * Returns the medicament with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idMedicament the primary key of the medicament
	 * @return the medicament, or <code>null</code> if a medicament with the primary key could not be found
	 */
	public Medicament fetchByPrimaryKey(long idMedicament);

	/**
	 * Returns all the medicaments.
	 *
	 * @return the medicaments
	 */
	public java.util.List<Medicament> findAll();

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
	public java.util.List<Medicament> findAll(int start, int end);

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
	public java.util.List<Medicament> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator);

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
	public java.util.List<Medicament> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Medicament>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the medicaments from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of medicaments.
	 *
	 * @return the number of medicaments
	 */
	public int countAll();

}
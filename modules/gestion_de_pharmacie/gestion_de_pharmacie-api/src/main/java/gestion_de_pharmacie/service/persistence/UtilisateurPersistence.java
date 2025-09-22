/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import gestion_de_pharmacie.exception.NoSuchUtilisateurException;

import gestion_de_pharmacie.model.Utilisateur;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the utilisateur service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see UtilisateurUtil
 * @generated
 */
@ProviderType
public interface UtilisateurPersistence extends BasePersistence<Utilisateur> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UtilisateurUtil} to access the utilisateur persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the utilisateur where email = &#63; or throws a <code>NoSuchUtilisateurException</code> if it could not be found.
	 *
	 * @param email the email
	 * @return the matching utilisateur
	 * @throws NoSuchUtilisateurException if a matching utilisateur could not be found
	 */
	public Utilisateur findByEmail(String email)
		throws NoSuchUtilisateurException;

	/**
	 * Returns the utilisateur where email = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param email the email
	 * @return the matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	public Utilisateur fetchByEmail(String email);

	/**
	 * Returns the utilisateur where email = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param email the email
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	public Utilisateur fetchByEmail(String email, boolean useFinderCache);

	/**
	 * Removes the utilisateur where email = &#63; from the database.
	 *
	 * @param email the email
	 * @return the utilisateur that was removed
	 */
	public Utilisateur removeByEmail(String email)
		throws NoSuchUtilisateurException;

	/**
	 * Returns the number of utilisateurs where email = &#63;.
	 *
	 * @param email the email
	 * @return the number of matching utilisateurs
	 */
	public int countByEmail(String email);

	/**
	 * Returns all the utilisateurs where role = &#63;.
	 *
	 * @param role the role
	 * @return the matching utilisateurs
	 */
	public java.util.List<Utilisateur> findByRole(String role);

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
	public java.util.List<Utilisateur> findByRole(
		String role, int start, int end);

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
	public java.util.List<Utilisateur> findByRole(
		String role, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
			orderByComparator);

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
	public java.util.List<Utilisateur> findByRole(
		String role, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first utilisateur in the ordered set where role = &#63;.
	 *
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching utilisateur
	 * @throws NoSuchUtilisateurException if a matching utilisateur could not be found
	 */
	public Utilisateur findByRole_First(
			String role,
			com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
				orderByComparator)
		throws NoSuchUtilisateurException;

	/**
	 * Returns the first utilisateur in the ordered set where role = &#63;.
	 *
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	public Utilisateur fetchByRole_First(
		String role,
		com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
			orderByComparator);

	/**
	 * Returns the last utilisateur in the ordered set where role = &#63;.
	 *
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching utilisateur
	 * @throws NoSuchUtilisateurException if a matching utilisateur could not be found
	 */
	public Utilisateur findByRole_Last(
			String role,
			com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
				orderByComparator)
		throws NoSuchUtilisateurException;

	/**
	 * Returns the last utilisateur in the ordered set where role = &#63;.
	 *
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching utilisateur, or <code>null</code> if a matching utilisateur could not be found
	 */
	public Utilisateur fetchByRole_Last(
		String role,
		com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
			orderByComparator);

	/**
	 * Returns the utilisateurs before and after the current utilisateur in the ordered set where role = &#63;.
	 *
	 * @param idUtilisateur the primary key of the current utilisateur
	 * @param role the role
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next utilisateur
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	public Utilisateur[] findByRole_PrevAndNext(
			long idUtilisateur, String role,
			com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
				orderByComparator)
		throws NoSuchUtilisateurException;

	/**
	 * Removes all the utilisateurs where role = &#63; from the database.
	 *
	 * @param role the role
	 */
	public void removeByRole(String role);

	/**
	 * Returns the number of utilisateurs where role = &#63;.
	 *
	 * @param role the role
	 * @return the number of matching utilisateurs
	 */
	public int countByRole(String role);

	/**
	 * Caches the utilisateur in the entity cache if it is enabled.
	 *
	 * @param utilisateur the utilisateur
	 */
	public void cacheResult(Utilisateur utilisateur);

	/**
	 * Caches the utilisateurs in the entity cache if it is enabled.
	 *
	 * @param utilisateurs the utilisateurs
	 */
	public void cacheResult(java.util.List<Utilisateur> utilisateurs);

	/**
	 * Creates a new utilisateur with the primary key. Does not add the utilisateur to the database.
	 *
	 * @param idUtilisateur the primary key for the new utilisateur
	 * @return the new utilisateur
	 */
	public Utilisateur create(long idUtilisateur);

	/**
	 * Removes the utilisateur with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur that was removed
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	public Utilisateur remove(long idUtilisateur)
		throws NoSuchUtilisateurException;

	public Utilisateur updateImpl(Utilisateur utilisateur);

	/**
	 * Returns the utilisateur with the primary key or throws a <code>NoSuchUtilisateurException</code> if it could not be found.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur
	 * @throws NoSuchUtilisateurException if a utilisateur with the primary key could not be found
	 */
	public Utilisateur findByPrimaryKey(long idUtilisateur)
		throws NoSuchUtilisateurException;

	/**
	 * Returns the utilisateur with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idUtilisateur the primary key of the utilisateur
	 * @return the utilisateur, or <code>null</code> if a utilisateur with the primary key could not be found
	 */
	public Utilisateur fetchByPrimaryKey(long idUtilisateur);

	/**
	 * Returns all the utilisateurs.
	 *
	 * @return the utilisateurs
	 */
	public java.util.List<Utilisateur> findAll();

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
	public java.util.List<Utilisateur> findAll(int start, int end);

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
	public java.util.List<Utilisateur> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
			orderByComparator);

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
	public java.util.List<Utilisateur> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Utilisateur>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the utilisateurs from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of utilisateurs.
	 *
	 * @return the number of utilisateurs
	 */
	public int countAll();

}
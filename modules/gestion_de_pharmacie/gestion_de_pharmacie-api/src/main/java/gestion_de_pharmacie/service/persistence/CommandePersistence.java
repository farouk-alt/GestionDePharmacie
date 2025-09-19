/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import gestion_de_pharmacie.exception.NoSuchCommandeException;

import gestion_de_pharmacie.model.Commande;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commande service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see CommandeUtil
 * @generated
 */
@ProviderType
public interface CommandePersistence extends BasePersistence<Commande> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommandeUtil} to access the commande persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commandes where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @return the matching commandes
	 */
	public java.util.List<Commande> findByFournisseur(long idFournisseur);

	/**
	 * Returns a range of all the commandes where idFournisseur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param idFournisseur the id fournisseur
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @return the range of matching commandes
	 */
	public java.util.List<Commande> findByFournisseur(
		long idFournisseur, int start, int end);

	/**
	 * Returns an ordered range of all the commandes where idFournisseur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param idFournisseur the id fournisseur
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commandes
	 */
	public java.util.List<Commande> findByFournisseur(
		long idFournisseur, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Commande>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commandes where idFournisseur = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param idFournisseur the id fournisseur
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commandes
	 */
	public java.util.List<Commande> findByFournisseur(
		long idFournisseur, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Commande>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande
	 * @throws NoSuchCommandeException if a matching commande could not be found
	 */
	public Commande findByFournisseur_First(
			long idFournisseur,
			com.liferay.portal.kernel.util.OrderByComparator<Commande>
				orderByComparator)
		throws NoSuchCommandeException;

	/**
	 * Returns the first commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commande, or <code>null</code> if a matching commande could not be found
	 */
	public Commande fetchByFournisseur_First(
		long idFournisseur,
		com.liferay.portal.kernel.util.OrderByComparator<Commande>
			orderByComparator);

	/**
	 * Returns the last commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande
	 * @throws NoSuchCommandeException if a matching commande could not be found
	 */
	public Commande findByFournisseur_Last(
			long idFournisseur,
			com.liferay.portal.kernel.util.OrderByComparator<Commande>
				orderByComparator)
		throws NoSuchCommandeException;

	/**
	 * Returns the last commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commande, or <code>null</code> if a matching commande could not be found
	 */
	public Commande fetchByFournisseur_Last(
		long idFournisseur,
		com.liferay.portal.kernel.util.OrderByComparator<Commande>
			orderByComparator);

	/**
	 * Returns the commandes before and after the current commande in the ordered set where idFournisseur = &#63;.
	 *
	 * @param idCommande the primary key of the current commande
	 * @param idFournisseur the id fournisseur
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commande
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	public Commande[] findByFournisseur_PrevAndNext(
			long idCommande, long idFournisseur,
			com.liferay.portal.kernel.util.OrderByComparator<Commande>
				orderByComparator)
		throws NoSuchCommandeException;

	/**
	 * Removes all the commandes where idFournisseur = &#63; from the database.
	 *
	 * @param idFournisseur the id fournisseur
	 */
	public void removeByFournisseur(long idFournisseur);

	/**
	 * Returns the number of commandes where idFournisseur = &#63;.
	 *
	 * @param idFournisseur the id fournisseur
	 * @return the number of matching commandes
	 */
	public int countByFournisseur(long idFournisseur);

	/**
	 * Caches the commande in the entity cache if it is enabled.
	 *
	 * @param commande the commande
	 */
	public void cacheResult(Commande commande);

	/**
	 * Caches the commandes in the entity cache if it is enabled.
	 *
	 * @param commandes the commandes
	 */
	public void cacheResult(java.util.List<Commande> commandes);

	/**
	 * Creates a new commande with the primary key. Does not add the commande to the database.
	 *
	 * @param idCommande the primary key for the new commande
	 * @return the new commande
	 */
	public Commande create(long idCommande);

	/**
	 * Removes the commande with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande that was removed
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	public Commande remove(long idCommande) throws NoSuchCommandeException;

	public Commande updateImpl(Commande commande);

	/**
	 * Returns the commande with the primary key or throws a <code>NoSuchCommandeException</code> if it could not be found.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande
	 * @throws NoSuchCommandeException if a commande with the primary key could not be found
	 */
	public Commande findByPrimaryKey(long idCommande)
		throws NoSuchCommandeException;

	/**
	 * Returns the commande with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande, or <code>null</code> if a commande with the primary key could not be found
	 */
	public Commande fetchByPrimaryKey(long idCommande);

	/**
	 * Returns all the commandes.
	 *
	 * @return the commandes
	 */
	public java.util.List<Commande> findAll();

	/**
	 * Returns a range of all the commandes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @return the range of commandes
	 */
	public java.util.List<Commande> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commandes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commandes
	 */
	public java.util.List<Commande> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Commande>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commandes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commandes
	 */
	public java.util.List<Commande> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Commande>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commandes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commandes.
	 *
	 * @return the number of commandes
	 */
	public int countAll();

}
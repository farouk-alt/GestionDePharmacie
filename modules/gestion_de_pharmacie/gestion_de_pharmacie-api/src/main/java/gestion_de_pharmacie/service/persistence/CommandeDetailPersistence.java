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
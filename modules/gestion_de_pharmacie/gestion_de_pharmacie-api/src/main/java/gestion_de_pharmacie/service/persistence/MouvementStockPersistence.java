/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import gestion_de_pharmacie.exception.NoSuchMouvementStockException;

import gestion_de_pharmacie.model.MouvementStock;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the mouvement stock service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Farouk
 * @see MouvementStockUtil
 * @generated
 */
@ProviderType
public interface MouvementStockPersistence
	extends BasePersistence<MouvementStock> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MouvementStockUtil} to access the mouvement stock persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the mouvement stock in the entity cache if it is enabled.
	 *
	 * @param mouvementStock the mouvement stock
	 */
	public void cacheResult(MouvementStock mouvementStock);

	/**
	 * Caches the mouvement stocks in the entity cache if it is enabled.
	 *
	 * @param mouvementStocks the mouvement stocks
	 */
	public void cacheResult(java.util.List<MouvementStock> mouvementStocks);

	/**
	 * Creates a new mouvement stock with the primary key. Does not add the mouvement stock to the database.
	 *
	 * @param idMouvement the primary key for the new mouvement stock
	 * @return the new mouvement stock
	 */
	public MouvementStock create(long idMouvement);

	/**
	 * Removes the mouvement stock with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock that was removed
	 * @throws NoSuchMouvementStockException if a mouvement stock with the primary key could not be found
	 */
	public MouvementStock remove(long idMouvement)
		throws NoSuchMouvementStockException;

	public MouvementStock updateImpl(MouvementStock mouvementStock);

	/**
	 * Returns the mouvement stock with the primary key or throws a <code>NoSuchMouvementStockException</code> if it could not be found.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock
	 * @throws NoSuchMouvementStockException if a mouvement stock with the primary key could not be found
	 */
	public MouvementStock findByPrimaryKey(long idMouvement)
		throws NoSuchMouvementStockException;

	/**
	 * Returns the mouvement stock with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param idMouvement the primary key of the mouvement stock
	 * @return the mouvement stock, or <code>null</code> if a mouvement stock with the primary key could not be found
	 */
	public MouvementStock fetchByPrimaryKey(long idMouvement);

	/**
	 * Returns all the mouvement stocks.
	 *
	 * @return the mouvement stocks
	 */
	public java.util.List<MouvementStock> findAll();

	/**
	 * Returns a range of all the mouvement stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MouvementStockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mouvement stocks
	 * @param end the upper bound of the range of mouvement stocks (not inclusive)
	 * @return the range of mouvement stocks
	 */
	public java.util.List<MouvementStock> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the mouvement stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MouvementStockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mouvement stocks
	 * @param end the upper bound of the range of mouvement stocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mouvement stocks
	 */
	public java.util.List<MouvementStock> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MouvementStock>
			orderByComparator);

	/**
	 * Returns an ordered range of all the mouvement stocks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MouvementStockModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mouvement stocks
	 * @param end the upper bound of the range of mouvement stocks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mouvement stocks
	 */
	public java.util.List<MouvementStock> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MouvementStock>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the mouvement stocks from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of mouvement stocks.
	 *
	 * @return the number of mouvement stocks
	 */
	public int countAll();

}
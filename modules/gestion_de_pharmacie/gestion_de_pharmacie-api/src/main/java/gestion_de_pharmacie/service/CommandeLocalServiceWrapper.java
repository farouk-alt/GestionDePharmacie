/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link CommandeLocalService}.
 *
 * @author Farouk
 * @see CommandeLocalService
 * @generated
 */
public class CommandeLocalServiceWrapper
	implements CommandeLocalService, ServiceWrapper<CommandeLocalService> {

	public CommandeLocalServiceWrapper() {
		this(null);
	}

	public CommandeLocalServiceWrapper(
		CommandeLocalService commandeLocalService) {

		_commandeLocalService = commandeLocalService;
	}

	/**
	 * Adds the commande to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommandeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commande the commande
	 * @return the commande that was added
	 */
	@Override
	public gestion_de_pharmacie.model.Commande addCommande(
		gestion_de_pharmacie.model.Commande commande) {

		return _commandeLocalService.addCommande(commande);
	}

	/**
	 * Creates a new commande with the primary key. Does not add the commande to the database.
	 *
	 * @param idCommande the primary key for the new commande
	 * @return the new commande
	 */
	@Override
	public gestion_de_pharmacie.model.Commande createCommande(long idCommande) {
		return _commandeLocalService.createCommande(idCommande);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commande from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommandeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commande the commande
	 * @return the commande that was removed
	 */
	@Override
	public gestion_de_pharmacie.model.Commande deleteCommande(
		gestion_de_pharmacie.model.Commande commande) {

		return _commandeLocalService.deleteCommande(commande);
	}

	/**
	 * Deletes the commande with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommandeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande that was removed
	 * @throws PortalException if a commande with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Commande deleteCommande(long idCommande)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeLocalService.deleteCommande(idCommande);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commandeLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commandeLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commandeLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _commandeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _commandeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _commandeLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _commandeLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _commandeLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public gestion_de_pharmacie.model.Commande fetchCommande(long idCommande) {
		return _commandeLocalService.fetchCommande(idCommande);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commandeLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the commande with the primary key.
	 *
	 * @param idCommande the primary key of the commande
	 * @return the commande
	 * @throws PortalException if a commande with the primary key could not be found
	 */
	@Override
	public gestion_de_pharmacie.model.Commande getCommande(long idCommande)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeLocalService.getCommande(idCommande);
	}

	/**
	 * Returns a range of all the commandes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>gestion_de_pharmacie.model.impl.CommandeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commandes
	 * @param end the upper bound of the range of commandes (not inclusive)
	 * @return the range of commandes
	 */
	@Override
	public java.util.List<gestion_de_pharmacie.model.Commande> getCommandes(
		int start, int end) {

		return _commandeLocalService.getCommandes(start, end);
	}

	@Override
	public java.util.List<gestion_de_pharmacie.model.Commande>
		getCommandesByUtilisateurId(long idUtilisateur) {

		return _commandeLocalService.getCommandesByUtilisateurId(idUtilisateur);
	}

	/**
	 * Returns the number of commandes.
	 *
	 * @return the number of commandes
	 */
	@Override
	public int getCommandesCount() {
		return _commandeLocalService.getCommandesCount();
	}

	@Override
	public String getFournisseurName(long commandeId) {
		return _commandeLocalService.getFournisseurName(commandeId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commandeLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commandeLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commandeLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the commande in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommandeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commande the commande
	 * @return the commande that was updated
	 */
	@Override
	public gestion_de_pharmacie.model.Commande updateCommande(
		gestion_de_pharmacie.model.Commande commande) {

		return _commandeLocalService.updateCommande(commande);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commandeLocalService.getBasePersistence();
	}

	@Override
	public CommandeLocalService getWrappedService() {
		return _commandeLocalService;
	}

	@Override
	public void setWrappedService(CommandeLocalService commandeLocalService) {
		_commandeLocalService = commandeLocalService;
	}

	private CommandeLocalService _commandeLocalService;

}
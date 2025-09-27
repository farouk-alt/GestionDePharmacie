/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommandeDetailService}.
 *
 * @author Farouk
 * @see CommandeDetailService
 * @generated
 */
public class CommandeDetailServiceWrapper
	implements CommandeDetailService, ServiceWrapper<CommandeDetailService> {

	public CommandeDetailServiceWrapper() {
		this(null);
	}

	public CommandeDetailServiceWrapper(
		CommandeDetailService commandeDetailService) {

		_commandeDetailService = commandeDetailService;
	}

	@Override
	public void deleteCommandeWithDetails(long commandeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commandeDetailService.deleteCommandeWithDetails(commandeId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commandeDetailService.getOSGiServiceIdentifier();
	}

	@Override
	public CommandeDetailService getWrappedService() {
		return _commandeDetailService;
	}

	@Override
	public void setWrappedService(CommandeDetailService commandeDetailService) {
		_commandeDetailService = commandeDetailService;
	}

	private CommandeDetailService _commandeDetailService;

}
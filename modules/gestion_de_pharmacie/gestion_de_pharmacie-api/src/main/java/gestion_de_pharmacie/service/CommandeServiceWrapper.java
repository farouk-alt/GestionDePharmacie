/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommandeService}.
 *
 * @author Farouk
 * @see CommandeService
 * @generated
 */
public class CommandeServiceWrapper
	implements CommandeService, ServiceWrapper<CommandeService> {

	public CommandeServiceWrapper() {
		this(null);
	}

	public CommandeServiceWrapper(CommandeService commandeService) {
		_commandeService = commandeService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commandeService.getOSGiServiceIdentifier();
	}

	@Override
	public CommandeService getWrappedService() {
		return _commandeService;
	}

	@Override
	public void setWrappedService(CommandeService commandeService) {
		_commandeService = commandeService;
	}

	private CommandeService _commandeService;

}
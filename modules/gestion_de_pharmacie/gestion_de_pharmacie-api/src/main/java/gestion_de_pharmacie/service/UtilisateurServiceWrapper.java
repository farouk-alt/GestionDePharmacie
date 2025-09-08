/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link UtilisateurService}.
 *
 * @author Farouk
 * @see UtilisateurService
 * @generated
 */
public class UtilisateurServiceWrapper
	implements ServiceWrapper<UtilisateurService>, UtilisateurService {

	public UtilisateurServiceWrapper() {
		this(null);
	}

	public UtilisateurServiceWrapper(UtilisateurService utilisateurService) {
		_utilisateurService = utilisateurService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _utilisateurService.getOSGiServiceIdentifier();
	}

	@Override
	public UtilisateurService getWrappedService() {
		return _utilisateurService;
	}

	@Override
	public void setWrappedService(UtilisateurService utilisateurService) {
		_utilisateurService = utilisateurService;
	}

	private UtilisateurService _utilisateurService;

}
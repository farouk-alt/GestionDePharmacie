/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FournisseurService}.
 *
 * @author Farouk
 * @see FournisseurService
 * @generated
 */
public class FournisseurServiceWrapper
	implements FournisseurService, ServiceWrapper<FournisseurService> {

	public FournisseurServiceWrapper() {
		this(null);
	}

	public FournisseurServiceWrapper(FournisseurService fournisseurService) {
		_fournisseurService = fournisseurService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _fournisseurService.getOSGiServiceIdentifier();
	}

	@Override
	public FournisseurService getWrappedService() {
		return _fournisseurService;
	}

	@Override
	public void setWrappedService(FournisseurService fournisseurService) {
		_fournisseurService = fournisseurService;
	}

	private FournisseurService _fournisseurService;

}
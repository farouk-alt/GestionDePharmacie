/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link VenteService}.
 *
 * @author Farouk
 * @see VenteService
 * @generated
 */
public class VenteServiceWrapper
	implements ServiceWrapper<VenteService>, VenteService {

	public VenteServiceWrapper() {
		this(null);
	}

	public VenteServiceWrapper(VenteService venteService) {
		_venteService = venteService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _venteService.getOSGiServiceIdentifier();
	}

	@Override
	public VenteService getWrappedService() {
		return _venteService;
	}

	@Override
	public void setWrappedService(VenteService venteService) {
		_venteService = venteService;
	}

	private VenteService _venteService;

}
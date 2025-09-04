/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link VenteDetailService}.
 *
 * @author Farouk
 * @see VenteDetailService
 * @generated
 */
public class VenteDetailServiceWrapper
	implements ServiceWrapper<VenteDetailService>, VenteDetailService {

	public VenteDetailServiceWrapper() {
		this(null);
	}

	public VenteDetailServiceWrapper(VenteDetailService venteDetailService) {
		_venteDetailService = venteDetailService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _venteDetailService.getOSGiServiceIdentifier();
	}

	@Override
	public VenteDetailService getWrappedService() {
		return _venteDetailService;
	}

	@Override
	public void setWrappedService(VenteDetailService venteDetailService) {
		_venteDetailService = venteDetailService;
	}

	private VenteDetailService _venteDetailService;

}
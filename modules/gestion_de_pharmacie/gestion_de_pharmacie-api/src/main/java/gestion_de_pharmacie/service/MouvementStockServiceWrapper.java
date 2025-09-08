/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MouvementStockService}.
 *
 * @author Farouk
 * @see MouvementStockService
 * @generated
 */
public class MouvementStockServiceWrapper
	implements MouvementStockService, ServiceWrapper<MouvementStockService> {

	public MouvementStockServiceWrapper() {
		this(null);
	}

	public MouvementStockServiceWrapper(
		MouvementStockService mouvementStockService) {

		_mouvementStockService = mouvementStockService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mouvementStockService.getOSGiServiceIdentifier();
	}

	@Override
	public MouvementStockService getWrappedService() {
		return _mouvementStockService;
	}

	@Override
	public void setWrappedService(MouvementStockService mouvementStockService) {
		_mouvementStockService = mouvementStockService;
	}

	private MouvementStockService _mouvementStockService;

}
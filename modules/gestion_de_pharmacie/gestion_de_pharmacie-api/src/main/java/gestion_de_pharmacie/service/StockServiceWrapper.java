/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link StockService}.
 *
 * @author Farouk
 * @see StockService
 * @generated
 */
public class StockServiceWrapper
	implements ServiceWrapper<StockService>, StockService {

	public StockServiceWrapper() {
		this(null);
	}

	public StockServiceWrapper(StockService stockService) {
		_stockService = stockService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _stockService.getOSGiServiceIdentifier();
	}

	@Override
	public StockService getWrappedService() {
		return _stockService;
	}

	@Override
	public void setWrappedService(StockService stockService) {
		_stockService = stockService;
	}

	private StockService _stockService;

}
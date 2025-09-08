/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MedicamentService}.
 *
 * @author Farouk
 * @see MedicamentService
 * @generated
 */
public class MedicamentServiceWrapper
	implements MedicamentService, ServiceWrapper<MedicamentService> {

	public MedicamentServiceWrapper() {
		this(null);
	}

	public MedicamentServiceWrapper(MedicamentService medicamentService) {
		_medicamentService = medicamentService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _medicamentService.getOSGiServiceIdentifier();
	}

	@Override
	public MedicamentService getWrappedService() {
		return _medicamentService;
	}

	@Override
	public void setWrappedService(MedicamentService medicamentService) {
		_medicamentService = medicamentService;
	}

	private MedicamentService _medicamentService;

}
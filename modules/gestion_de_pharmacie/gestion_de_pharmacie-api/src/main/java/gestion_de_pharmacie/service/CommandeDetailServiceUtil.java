/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * Provides the remote service utility for CommandeDetail. This utility wraps
 * <code>gestion_de_pharmacie.service.impl.CommandeDetailServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Farouk
 * @see CommandeDetailService
 * @generated
 */
public class CommandeDetailServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>gestion_de_pharmacie.service.impl.CommandeDetailServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCommandeWithDetails(long commandeId)
		throws PortalException {

		getService().deleteCommandeWithDetails(commandeId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommandeDetailService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<CommandeDetailService> _serviceSnapshot =
		new Snapshot<>(
			CommandeDetailServiceUtil.class, CommandeDetailService.class);

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Transactional;
import gestion_de_pharmacie.model.CommandeDetail;
import gestion_de_pharmacie.service.base.CommandeDetailServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

import java.util.List;

import static gestion_de_pharmacie.service.CommandeLocalServiceUtil.deleteCommande;

/**
 * @author Farouk
 */
@Component(
	property = {
		"json.web.service.context.name=pharma",
		"json.web.service.context.path=CommandeDetail"
	},
	service = AopService.class
)
public class CommandeDetailServiceImpl extends CommandeDetailServiceBaseImpl {

    @Override
    public void deleteCommandeWithDetails(long commandeId) throws PortalException {

    }
}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import gestion_de_pharmacie.service.base.FournisseurServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Farouk
 */
@Component(
	property = {
		"json.web.service.context.name=pharma",
		"json.web.service.context.path=Fournisseur"
	},
	service = AopService.class
)
public class FournisseurServiceImpl extends FournisseurServiceBaseImpl {
}
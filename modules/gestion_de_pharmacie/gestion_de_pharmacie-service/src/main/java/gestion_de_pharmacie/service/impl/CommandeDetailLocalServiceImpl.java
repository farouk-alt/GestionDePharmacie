/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import gestion_de_pharmacie.model.CommandeDetail;
import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.service.base.CommandeDetailLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * @author Farouk
 */
@Component(
	property = "model.class.name=gestion_de_pharmacie.model.CommandeDetail",
	service = AopService.class
)
public class CommandeDetailLocalServiceImpl
	extends CommandeDetailLocalServiceBaseImpl {
    @Override
    public List<CommandeDetail> findByCommandeId(long commandeId) {
        return List.of();
    }

    @Override
    public Medicament getMedicament(CommandeDetail commandeDetail) {
        return null;
    }
}
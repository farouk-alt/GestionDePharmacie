/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import gestion_de_pharmacie.model.CommandeDetail;
import gestion_de_pharmacie.model.Medicament;
import gestion_de_pharmacie.service.MedicamentLocalService;
import gestion_de_pharmacie.service.base.CommandeDetailLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

    @Reference
    private MedicamentLocalService medicamentLocalService;

    /** No pagination wrapper */
    public List<CommandeDetail> findByCommandeId(long commandeId) {
        return commandeDetailPersistence.findByIdCommande(
                commandeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
    }

    /** With pagination */
    public List<CommandeDetail> findByCommandeId(long commandeId, int start, int end) {
        return commandeDetailPersistence.findByIdCommande(commandeId, start, end);
    }

    public int countByCommandeId(long commandeId) {
        return (int) commandeDetailPersistence.countByIdCommande(commandeId);
    }

    /** Helper to fetch the linked Medicament */
    public Medicament getMedicament(CommandeDetail commandeDetail) {
        return medicamentLocalService.fetchMedicament(commandeDetail.getIdMedicament());
    }
}
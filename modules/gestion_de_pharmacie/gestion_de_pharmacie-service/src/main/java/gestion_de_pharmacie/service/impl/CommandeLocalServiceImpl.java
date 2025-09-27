/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.transaction.Transactional;
import gestion_de_pharmacie.model.Commande;
import gestion_de_pharmacie.model.CommandeDetail;
import gestion_de_pharmacie.service.CommandeDetailLocalService;
import gestion_de_pharmacie.service.FournisseurLocalService;
import gestion_de_pharmacie.service.base.CommandeLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

import static gestion_de_pharmacie.service.CommandeLocalServiceUtil.deleteCommande;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=gestion_de_pharmacie.model.Commande",
	service = AopService.class
)
public class CommandeLocalServiceImpl extends CommandeLocalServiceBaseImpl {

    @Reference
    private FournisseurLocalService fournisseurLocalService;

    @Reference
    private CommandeDetailLocalService commandeDetailLocalService;

    @Override
    public List<Commande> getCommandesByUtilisateurId(long idUtilisateur) {
        return commandePersistence.findByIdFournisseur(idUtilisateur);
    }

    public String getFournisseurName(long commandeId) {
        try {
            long fournisseurId = commandePersistence.findByPrimaryKey(commandeId).getIdUtilisateur();
            return fournisseurLocalService.fetchFournisseur(fournisseurId).getNom();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCommandeWithDetails(long commandeId) throws PortalException {
        // Delete children first
        List<CommandeDetail> details = commandeDetailPersistence.findByIdCommande(commandeId);
        for (CommandeDetail d : details) {
            commandeDetailLocalService.deleteCommandeDetail(d);
        }

        // Delete parent
        deleteCommande(commandeId); // baseImpl method removes the entity
    }
}
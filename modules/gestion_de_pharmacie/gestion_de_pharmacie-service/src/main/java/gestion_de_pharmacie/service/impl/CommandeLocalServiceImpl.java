/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import gestion_de_pharmacie.model.Commande;
import gestion_de_pharmacie.service.FournisseurLocalService;
import gestion_de_pharmacie.service.base.CommandeLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

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
}
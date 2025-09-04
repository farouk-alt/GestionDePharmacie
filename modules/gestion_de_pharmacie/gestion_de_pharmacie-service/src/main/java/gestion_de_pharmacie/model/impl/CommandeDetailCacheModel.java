/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.CommandeDetail;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CommandeDetail in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class CommandeDetailCacheModel
	implements CacheModel<CommandeDetail>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommandeDetailCacheModel)) {
			return false;
		}

		CommandeDetailCacheModel commandeDetailCacheModel =
			(CommandeDetailCacheModel)object;

		if (idDetail == commandeDetailCacheModel.idDetail) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idDetail);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{idDetail=");
		sb.append(idDetail);
		sb.append(", idCommande=");
		sb.append(idCommande);
		sb.append(", idMedicament=");
		sb.append(idMedicament);
		sb.append(", quantite=");
		sb.append(quantite);
		sb.append(", prixUnitaire=");
		sb.append(prixUnitaire);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommandeDetail toEntityModel() {
		CommandeDetailImpl commandeDetailImpl = new CommandeDetailImpl();

		commandeDetailImpl.setIdDetail(idDetail);
		commandeDetailImpl.setIdCommande(idCommande);
		commandeDetailImpl.setIdMedicament(idMedicament);
		commandeDetailImpl.setQuantite(quantite);
		commandeDetailImpl.setPrixUnitaire(prixUnitaire);

		commandeDetailImpl.resetOriginalValues();

		return commandeDetailImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idDetail = objectInput.readLong();

		idCommande = objectInput.readLong();

		idMedicament = objectInput.readLong();

		quantite = objectInput.readInt();

		prixUnitaire = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idDetail);

		objectOutput.writeLong(idCommande);

		objectOutput.writeLong(idMedicament);

		objectOutput.writeInt(quantite);

		objectOutput.writeDouble(prixUnitaire);
	}

	public long idDetail;
	public long idCommande;
	public long idMedicament;
	public int quantite;
	public double prixUnitaire;

}
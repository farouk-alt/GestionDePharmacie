/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.Commande;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Commande in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class CommandeCacheModel
	implements CacheModel<Commande>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommandeCacheModel)) {
			return false;
		}

		CommandeCacheModel commandeCacheModel = (CommandeCacheModel)object;

		if (idCommande == commandeCacheModel.idCommande) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idCommande);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{idCommande=");
		sb.append(idCommande);
		sb.append(", idFournisseur=");
		sb.append(idFournisseur);
		sb.append(", dateCommande=");
		sb.append(dateCommande);
		sb.append(", statut=");
		sb.append(statut);
		sb.append(", montantTotal=");
		sb.append(montantTotal);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Commande toEntityModel() {
		CommandeImpl commandeImpl = new CommandeImpl();

		commandeImpl.setIdCommande(idCommande);
		commandeImpl.setIdFournisseur(idFournisseur);

		if (dateCommande == Long.MIN_VALUE) {
			commandeImpl.setDateCommande(null);
		}
		else {
			commandeImpl.setDateCommande(new Date(dateCommande));
		}

		if (statut == null) {
			commandeImpl.setStatut("");
		}
		else {
			commandeImpl.setStatut(statut);
		}

		commandeImpl.setMontantTotal(montantTotal);

		commandeImpl.resetOriginalValues();

		return commandeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idCommande = objectInput.readLong();

		idFournisseur = objectInput.readLong();
		dateCommande = objectInput.readLong();
		statut = objectInput.readUTF();

		montantTotal = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idCommande);

		objectOutput.writeLong(idFournisseur);
		objectOutput.writeLong(dateCommande);

		if (statut == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statut);
		}

		objectOutput.writeDouble(montantTotal);
	}

	public long idCommande;
	public long idFournisseur;
	public long dateCommande;
	public String statut;
	public double montantTotal;

}
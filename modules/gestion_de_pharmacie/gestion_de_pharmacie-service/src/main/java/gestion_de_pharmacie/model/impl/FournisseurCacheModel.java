/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.Fournisseur;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Fournisseur in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class FournisseurCacheModel
	implements CacheModel<Fournisseur>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FournisseurCacheModel)) {
			return false;
		}

		FournisseurCacheModel fournisseurCacheModel =
			(FournisseurCacheModel)object;

		if (idFournisseur == fournisseurCacheModel.idFournisseur) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idFournisseur);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{idFournisseur=");
		sb.append(idFournisseur);
		sb.append(", nom=");
		sb.append(nom);
		sb.append(", adresse=");
		sb.append(adresse);
		sb.append(", telephone=");
		sb.append(telephone);
		sb.append(", email=");
		sb.append(email);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Fournisseur toEntityModel() {
		FournisseurImpl fournisseurImpl = new FournisseurImpl();

		fournisseurImpl.setIdFournisseur(idFournisseur);

		if (nom == null) {
			fournisseurImpl.setNom("");
		}
		else {
			fournisseurImpl.setNom(nom);
		}

		if (adresse == null) {
			fournisseurImpl.setAdresse("");
		}
		else {
			fournisseurImpl.setAdresse(adresse);
		}

		if (telephone == null) {
			fournisseurImpl.setTelephone("");
		}
		else {
			fournisseurImpl.setTelephone(telephone);
		}

		if (email == null) {
			fournisseurImpl.setEmail("");
		}
		else {
			fournisseurImpl.setEmail(email);
		}

		fournisseurImpl.resetOriginalValues();

		return fournisseurImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idFournisseur = objectInput.readLong();
		nom = objectInput.readUTF();
		adresse = objectInput.readUTF();
		telephone = objectInput.readUTF();
		email = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idFournisseur);

		if (nom == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nom);
		}

		if (adresse == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(adresse);
		}

		if (telephone == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(telephone);
		}

		if (email == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(email);
		}
	}

	public long idFournisseur;
	public String nom;
	public String adresse;
	public String telephone;
	public String email;

}
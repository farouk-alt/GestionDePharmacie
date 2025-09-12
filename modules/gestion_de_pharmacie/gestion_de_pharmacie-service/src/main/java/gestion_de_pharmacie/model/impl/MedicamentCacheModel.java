/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.Medicament;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Medicament in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class MedicamentCacheModel
	implements CacheModel<Medicament>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MedicamentCacheModel)) {
			return false;
		}

		MedicamentCacheModel medicamentCacheModel =
			(MedicamentCacheModel)object;

		if (idMedicament == medicamentCacheModel.idMedicament) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idMedicament);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{idMedicament=");
		sb.append(idMedicament);
		sb.append(", code=");
		sb.append(code);
		sb.append(", codeBarre=");
		sb.append(codeBarre);
		sb.append(", nom=");
		sb.append(nom);
		sb.append(", description=");
		sb.append(description);
		sb.append(", categorie=");
		sb.append(categorie);
		sb.append(", prixUnitaire=");
		sb.append(prixUnitaire);
		sb.append(", seuilMinimum=");
		sb.append(seuilMinimum);
		sb.append(", dateAjout=");
		sb.append(dateAjout);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Medicament toEntityModel() {
		MedicamentImpl medicamentImpl = new MedicamentImpl();

		medicamentImpl.setIdMedicament(idMedicament);

		if (code == null) {
			medicamentImpl.setCode("");
		}
		else {
			medicamentImpl.setCode(code);
		}

		if (codeBarre == null) {
			medicamentImpl.setCodeBarre("");
		}
		else {
			medicamentImpl.setCodeBarre(codeBarre);
		}

		if (nom == null) {
			medicamentImpl.setNom("");
		}
		else {
			medicamentImpl.setNom(nom);
		}

		if (description == null) {
			medicamentImpl.setDescription("");
		}
		else {
			medicamentImpl.setDescription(description);
		}

		if (categorie == null) {
			medicamentImpl.setCategorie("");
		}
		else {
			medicamentImpl.setCategorie(categorie);
		}

		medicamentImpl.setPrixUnitaire(prixUnitaire);
		medicamentImpl.setSeuilMinimum(seuilMinimum);

		if (dateAjout == Long.MIN_VALUE) {
			medicamentImpl.setDateAjout(null);
		}
		else {
			medicamentImpl.setDateAjout(new Date(dateAjout));
		}

		medicamentImpl.resetOriginalValues();

		return medicamentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idMedicament = objectInput.readLong();
		code = objectInput.readUTF();
		codeBarre = objectInput.readUTF();
		nom = objectInput.readUTF();
		description = objectInput.readUTF();
		categorie = objectInput.readUTF();

		prixUnitaire = objectInput.readDouble();

		seuilMinimum = objectInput.readInt();
		dateAjout = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idMedicament);

		if (code == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(code);
		}

		if (codeBarre == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(codeBarre);
		}

		if (nom == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nom);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (categorie == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(categorie);
		}

		objectOutput.writeDouble(prixUnitaire);

		objectOutput.writeInt(seuilMinimum);
		objectOutput.writeLong(dateAjout);
	}

	public long idMedicament;
	public String code;
	public String codeBarre;
	public String nom;
	public String description;
	public String categorie;
	public double prixUnitaire;
	public int seuilMinimum;
	public long dateAjout;

}
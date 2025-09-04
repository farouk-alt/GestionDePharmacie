/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.Utilisateur;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Utilisateur in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class UtilisateurCacheModel
	implements CacheModel<Utilisateur>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UtilisateurCacheModel)) {
			return false;
		}

		UtilisateurCacheModel utilisateurCacheModel =
			(UtilisateurCacheModel)object;

		if (idUtilisateur == utilisateurCacheModel.idUtilisateur) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idUtilisateur);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{idUtilisateur=");
		sb.append(idUtilisateur);
		sb.append(", nom=");
		sb.append(nom);
		sb.append(", prenom=");
		sb.append(prenom);
		sb.append(", email=");
		sb.append(email);
		sb.append(", motDePasse=");
		sb.append(motDePasse);
		sb.append(", role=");
		sb.append(role);
		sb.append(", dateCreation=");
		sb.append(dateCreation);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Utilisateur toEntityModel() {
		UtilisateurImpl utilisateurImpl = new UtilisateurImpl();

		utilisateurImpl.setIdUtilisateur(idUtilisateur);

		if (nom == null) {
			utilisateurImpl.setNom("");
		}
		else {
			utilisateurImpl.setNom(nom);
		}

		if (prenom == null) {
			utilisateurImpl.setPrenom("");
		}
		else {
			utilisateurImpl.setPrenom(prenom);
		}

		if (email == null) {
			utilisateurImpl.setEmail("");
		}
		else {
			utilisateurImpl.setEmail(email);
		}

		if (motDePasse == null) {
			utilisateurImpl.setMotDePasse("");
		}
		else {
			utilisateurImpl.setMotDePasse(motDePasse);
		}

		if (role == null) {
			utilisateurImpl.setRole("");
		}
		else {
			utilisateurImpl.setRole(role);
		}

		if (dateCreation == Long.MIN_VALUE) {
			utilisateurImpl.setDateCreation(null);
		}
		else {
			utilisateurImpl.setDateCreation(new Date(dateCreation));
		}

		utilisateurImpl.resetOriginalValues();

		return utilisateurImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idUtilisateur = objectInput.readLong();
		nom = objectInput.readUTF();
		prenom = objectInput.readUTF();
		email = objectInput.readUTF();
		motDePasse = objectInput.readUTF();
		role = objectInput.readUTF();
		dateCreation = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idUtilisateur);

		if (nom == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nom);
		}

		if (prenom == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(prenom);
		}

		if (email == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(email);
		}

		if (motDePasse == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(motDePasse);
		}

		if (role == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(role);
		}

		objectOutput.writeLong(dateCreation);
	}

	public long idUtilisateur;
	public String nom;
	public String prenom;
	public String email;
	public String motDePasse;
	public String role;
	public long dateCreation;

}
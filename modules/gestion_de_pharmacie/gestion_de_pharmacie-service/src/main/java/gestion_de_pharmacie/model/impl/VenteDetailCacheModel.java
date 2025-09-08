/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.VenteDetail;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing VenteDetail in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class VenteDetailCacheModel
	implements CacheModel<VenteDetail>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof VenteDetailCacheModel)) {
			return false;
		}

		VenteDetailCacheModel venteDetailCacheModel =
			(VenteDetailCacheModel)object;

		if (idDetail == venteDetailCacheModel.idDetail) {
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
		StringBundler sb = new StringBundler(13);

		sb.append("{idDetail=");
		sb.append(idDetail);
		sb.append(", idVente=");
		sb.append(idVente);
		sb.append(", idMedicament=");
		sb.append(idMedicament);
		sb.append(", quantite=");
		sb.append(quantite);
		sb.append(", prixUnitaire=");
		sb.append(prixUnitaire);
		sb.append(", sousTotal=");
		sb.append(sousTotal);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public VenteDetail toEntityModel() {
		VenteDetailImpl venteDetailImpl = new VenteDetailImpl();

		venteDetailImpl.setIdDetail(idDetail);
		venteDetailImpl.setIdVente(idVente);
		venteDetailImpl.setIdMedicament(idMedicament);
		venteDetailImpl.setQuantite(quantite);
		venteDetailImpl.setPrixUnitaire(prixUnitaire);
		venteDetailImpl.setSousTotal(sousTotal);

		venteDetailImpl.resetOriginalValues();

		return venteDetailImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idDetail = objectInput.readLong();

		idVente = objectInput.readLong();

		idMedicament = objectInput.readLong();

		quantite = objectInput.readInt();

		prixUnitaire = objectInput.readDouble();

		sousTotal = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idDetail);

		objectOutput.writeLong(idVente);

		objectOutput.writeLong(idMedicament);

		objectOutput.writeInt(quantite);

		objectOutput.writeDouble(prixUnitaire);

		objectOutput.writeDouble(sousTotal);
	}

	public long idDetail;
	public long idVente;
	public long idMedicament;
	public int quantite;
	public double prixUnitaire;
	public double sousTotal;

}
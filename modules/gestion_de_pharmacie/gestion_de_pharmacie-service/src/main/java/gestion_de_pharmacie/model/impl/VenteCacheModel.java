/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.Vente;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Vente in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class VenteCacheModel implements CacheModel<Vente>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof VenteCacheModel)) {
			return false;
		}

		VenteCacheModel venteCacheModel = (VenteCacheModel)object;

		if (idVente == venteCacheModel.idVente) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idVente);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{idVente=");
		sb.append(idVente);
		sb.append(", idUtilisateur=");
		sb.append(idUtilisateur);
		sb.append(", dateVente=");
		sb.append(dateVente);
		sb.append(", montantTotal=");
		sb.append(montantTotal);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Vente toEntityModel() {
		VenteImpl venteImpl = new VenteImpl();

		venteImpl.setIdVente(idVente);
		venteImpl.setIdUtilisateur(idUtilisateur);

		if (dateVente == Long.MIN_VALUE) {
			venteImpl.setDateVente(null);
		}
		else {
			venteImpl.setDateVente(new Date(dateVente));
		}

		venteImpl.setMontantTotal(montantTotal);

		venteImpl.resetOriginalValues();

		return venteImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idVente = objectInput.readLong();

		idUtilisateur = objectInput.readLong();
		dateVente = objectInput.readLong();

		montantTotal = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idVente);

		objectOutput.writeLong(idUtilisateur);
		objectOutput.writeLong(dateVente);

		objectOutput.writeDouble(montantTotal);
	}

	public long idVente;
	public long idUtilisateur;
	public long dateVente;
	public double montantTotal;

}
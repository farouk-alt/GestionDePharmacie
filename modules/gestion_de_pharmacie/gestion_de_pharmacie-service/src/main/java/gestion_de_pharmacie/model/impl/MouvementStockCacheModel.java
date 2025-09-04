/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.MouvementStock;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MouvementStock in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class MouvementStockCacheModel
	implements CacheModel<MouvementStock>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MouvementStockCacheModel)) {
			return false;
		}

		MouvementStockCacheModel mouvementStockCacheModel =
			(MouvementStockCacheModel)object;

		if (idMouvement == mouvementStockCacheModel.idMouvement) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idMouvement);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{idMouvement=");
		sb.append(idMouvement);
		sb.append(", idStock=");
		sb.append(idStock);
		sb.append(", typeMouvement=");
		sb.append(typeMouvement);
		sb.append(", quantite=");
		sb.append(quantite);
		sb.append(", dateMouvement=");
		sb.append(dateMouvement);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MouvementStock toEntityModel() {
		MouvementStockImpl mouvementStockImpl = new MouvementStockImpl();

		mouvementStockImpl.setIdMouvement(idMouvement);
		mouvementStockImpl.setIdStock(idStock);

		if (typeMouvement == null) {
			mouvementStockImpl.setTypeMouvement("");
		}
		else {
			mouvementStockImpl.setTypeMouvement(typeMouvement);
		}

		mouvementStockImpl.setQuantite(quantite);

		if (dateMouvement == Long.MIN_VALUE) {
			mouvementStockImpl.setDateMouvement(null);
		}
		else {
			mouvementStockImpl.setDateMouvement(new Date(dateMouvement));
		}

		mouvementStockImpl.resetOriginalValues();

		return mouvementStockImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idMouvement = objectInput.readLong();

		idStock = objectInput.readLong();
		typeMouvement = objectInput.readUTF();

		quantite = objectInput.readInt();
		dateMouvement = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idMouvement);

		objectOutput.writeLong(idStock);

		if (typeMouvement == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeMouvement);
		}

		objectOutput.writeInt(quantite);
		objectOutput.writeLong(dateMouvement);
	}

	public long idMouvement;
	public long idStock;
	public String typeMouvement;
	public int quantite;
	public long dateMouvement;

}
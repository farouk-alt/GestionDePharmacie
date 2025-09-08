/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.Stock;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Stock in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class StockCacheModel implements CacheModel<Stock>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StockCacheModel)) {
			return false;
		}

		StockCacheModel stockCacheModel = (StockCacheModel)object;

		if (idStock == stockCacheModel.idStock) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idStock);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{idStock=");
		sb.append(idStock);
		sb.append(", idMedicament=");
		sb.append(idMedicament);
		sb.append(", quantiteDisponible=");
		sb.append(quantiteDisponible);
		sb.append(", dateDerniereMaj=");
		sb.append(dateDerniereMaj);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Stock toEntityModel() {
		StockImpl stockImpl = new StockImpl();

		stockImpl.setIdStock(idStock);
		stockImpl.setIdMedicament(idMedicament);
		stockImpl.setQuantiteDisponible(quantiteDisponible);

		if (dateDerniereMaj == Long.MIN_VALUE) {
			stockImpl.setDateDerniereMaj(null);
		}
		else {
			stockImpl.setDateDerniereMaj(new Date(dateDerniereMaj));
		}

		stockImpl.resetOriginalValues();

		return stockImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idStock = objectInput.readLong();

		idMedicament = objectInput.readLong();

		quantiteDisponible = objectInput.readInt();
		dateDerniereMaj = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idStock);

		objectOutput.writeLong(idMedicament);

		objectOutput.writeInt(quantiteDisponible);
		objectOutput.writeLong(dateDerniereMaj);
	}

	public long idStock;
	public long idMedicament;
	public int quantiteDisponible;
	public long dateDerniereMaj;

}
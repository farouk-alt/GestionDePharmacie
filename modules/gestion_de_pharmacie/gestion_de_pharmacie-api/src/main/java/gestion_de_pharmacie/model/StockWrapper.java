/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Stock}.
 * </p>
 *
 * @author Farouk
 * @see Stock
 * @generated
 */
public class StockWrapper
	extends BaseModelWrapper<Stock> implements ModelWrapper<Stock>, Stock {

	public StockWrapper(Stock stock) {
		super(stock);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idStock", getIdStock());
		attributes.put("idMedicament", getIdMedicament());
		attributes.put("quantiteDisponible", getQuantiteDisponible());
		attributes.put("dateDerniereMaj", getDateDerniereMaj());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idStock = (Long)attributes.get("idStock");

		if (idStock != null) {
			setIdStock(idStock);
		}

		Long idMedicament = (Long)attributes.get("idMedicament");

		if (idMedicament != null) {
			setIdMedicament(idMedicament);
		}

		Integer quantiteDisponible = (Integer)attributes.get(
			"quantiteDisponible");

		if (quantiteDisponible != null) {
			setQuantiteDisponible(quantiteDisponible);
		}

		Date dateDerniereMaj = (Date)attributes.get("dateDerniereMaj");

		if (dateDerniereMaj != null) {
			setDateDerniereMaj(dateDerniereMaj);
		}
	}

	@Override
	public Stock cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the date derniere maj of this stock.
	 *
	 * @return the date derniere maj of this stock
	 */
	@Override
	public Date getDateDerniereMaj() {
		return model.getDateDerniereMaj();
	}

	/**
	 * Returns the id medicament of this stock.
	 *
	 * @return the id medicament of this stock
	 */
	@Override
	public long getIdMedicament() {
		return model.getIdMedicament();
	}

	/**
	 * Returns the id stock of this stock.
	 *
	 * @return the id stock of this stock
	 */
	@Override
	public long getIdStock() {
		return model.getIdStock();
	}

	/**
	 * Returns the primary key of this stock.
	 *
	 * @return the primary key of this stock
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the quantite disponible of this stock.
	 *
	 * @return the quantite disponible of this stock
	 */
	@Override
	public int getQuantiteDisponible() {
		return model.getQuantiteDisponible();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the date derniere maj of this stock.
	 *
	 * @param dateDerniereMaj the date derniere maj of this stock
	 */
	@Override
	public void setDateDerniereMaj(Date dateDerniereMaj) {
		model.setDateDerniereMaj(dateDerniereMaj);
	}

	/**
	 * Sets the id medicament of this stock.
	 *
	 * @param idMedicament the id medicament of this stock
	 */
	@Override
	public void setIdMedicament(long idMedicament) {
		model.setIdMedicament(idMedicament);
	}

	/**
	 * Sets the id stock of this stock.
	 *
	 * @param idStock the id stock of this stock
	 */
	@Override
	public void setIdStock(long idStock) {
		model.setIdStock(idStock);
	}

	/**
	 * Sets the primary key of this stock.
	 *
	 * @param primaryKey the primary key of this stock
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the quantite disponible of this stock.
	 *
	 * @param quantiteDisponible the quantite disponible of this stock
	 */
	@Override
	public void setQuantiteDisponible(int quantiteDisponible) {
		model.setQuantiteDisponible(quantiteDisponible);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected StockWrapper wrap(Stock stock) {
		return new StockWrapper(stock);
	}

}
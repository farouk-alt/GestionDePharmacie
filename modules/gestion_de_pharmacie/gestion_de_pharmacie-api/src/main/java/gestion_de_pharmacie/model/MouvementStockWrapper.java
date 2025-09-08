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
 * This class is a wrapper for {@link MouvementStock}.
 * </p>
 *
 * @author Farouk
 * @see MouvementStock
 * @generated
 */
public class MouvementStockWrapper
	extends BaseModelWrapper<MouvementStock>
	implements ModelWrapper<MouvementStock>, MouvementStock {

	public MouvementStockWrapper(MouvementStock mouvementStock) {
		super(mouvementStock);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idMouvement", getIdMouvement());
		attributes.put("idStock", getIdStock());
		attributes.put("typeMouvement", getTypeMouvement());
		attributes.put("quantite", getQuantite());
		attributes.put("dateMouvement", getDateMouvement());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idMouvement = (Long)attributes.get("idMouvement");

		if (idMouvement != null) {
			setIdMouvement(idMouvement);
		}

		Long idStock = (Long)attributes.get("idStock");

		if (idStock != null) {
			setIdStock(idStock);
		}

		String typeMouvement = (String)attributes.get("typeMouvement");

		if (typeMouvement != null) {
			setTypeMouvement(typeMouvement);
		}

		Integer quantite = (Integer)attributes.get("quantite");

		if (quantite != null) {
			setQuantite(quantite);
		}

		Date dateMouvement = (Date)attributes.get("dateMouvement");

		if (dateMouvement != null) {
			setDateMouvement(dateMouvement);
		}
	}

	@Override
	public MouvementStock cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the date mouvement of this mouvement stock.
	 *
	 * @return the date mouvement of this mouvement stock
	 */
	@Override
	public Date getDateMouvement() {
		return model.getDateMouvement();
	}

	/**
	 * Returns the id mouvement of this mouvement stock.
	 *
	 * @return the id mouvement of this mouvement stock
	 */
	@Override
	public long getIdMouvement() {
		return model.getIdMouvement();
	}

	/**
	 * Returns the id stock of this mouvement stock.
	 *
	 * @return the id stock of this mouvement stock
	 */
	@Override
	public long getIdStock() {
		return model.getIdStock();
	}

	/**
	 * Returns the primary key of this mouvement stock.
	 *
	 * @return the primary key of this mouvement stock
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the quantite of this mouvement stock.
	 *
	 * @return the quantite of this mouvement stock
	 */
	@Override
	public int getQuantite() {
		return model.getQuantite();
	}

	/**
	 * Returns the type mouvement of this mouvement stock.
	 *
	 * @return the type mouvement of this mouvement stock
	 */
	@Override
	public String getTypeMouvement() {
		return model.getTypeMouvement();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the date mouvement of this mouvement stock.
	 *
	 * @param dateMouvement the date mouvement of this mouvement stock
	 */
	@Override
	public void setDateMouvement(Date dateMouvement) {
		model.setDateMouvement(dateMouvement);
	}

	/**
	 * Sets the id mouvement of this mouvement stock.
	 *
	 * @param idMouvement the id mouvement of this mouvement stock
	 */
	@Override
	public void setIdMouvement(long idMouvement) {
		model.setIdMouvement(idMouvement);
	}

	/**
	 * Sets the id stock of this mouvement stock.
	 *
	 * @param idStock the id stock of this mouvement stock
	 */
	@Override
	public void setIdStock(long idStock) {
		model.setIdStock(idStock);
	}

	/**
	 * Sets the primary key of this mouvement stock.
	 *
	 * @param primaryKey the primary key of this mouvement stock
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the quantite of this mouvement stock.
	 *
	 * @param quantite the quantite of this mouvement stock
	 */
	@Override
	public void setQuantite(int quantite) {
		model.setQuantite(quantite);
	}

	/**
	 * Sets the type mouvement of this mouvement stock.
	 *
	 * @param typeMouvement the type mouvement of this mouvement stock
	 */
	@Override
	public void setTypeMouvement(String typeMouvement) {
		model.setTypeMouvement(typeMouvement);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected MouvementStockWrapper wrap(MouvementStock mouvementStock) {
		return new MouvementStockWrapper(mouvementStock);
	}

}
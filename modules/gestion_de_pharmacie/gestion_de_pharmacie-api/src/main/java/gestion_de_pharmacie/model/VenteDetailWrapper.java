/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link VenteDetail}.
 * </p>
 *
 * @author Farouk
 * @see VenteDetail
 * @generated
 */
public class VenteDetailWrapper
	extends BaseModelWrapper<VenteDetail>
	implements ModelWrapper<VenteDetail>, VenteDetail {

	public VenteDetailWrapper(VenteDetail venteDetail) {
		super(venteDetail);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idDetail", getIdDetail());
		attributes.put("idVente", getIdVente());
		attributes.put("idMedicament", getIdMedicament());
		attributes.put("quantite", getQuantite());
		attributes.put("prixUnitaire", getPrixUnitaire());
		attributes.put("sousTotal", getSousTotal());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idDetail = (Long)attributes.get("idDetail");

		if (idDetail != null) {
			setIdDetail(idDetail);
		}

		Long idVente = (Long)attributes.get("idVente");

		if (idVente != null) {
			setIdVente(idVente);
		}

		Long idMedicament = (Long)attributes.get("idMedicament");

		if (idMedicament != null) {
			setIdMedicament(idMedicament);
		}

		Integer quantite = (Integer)attributes.get("quantite");

		if (quantite != null) {
			setQuantite(quantite);
		}

		Double prixUnitaire = (Double)attributes.get("prixUnitaire");

		if (prixUnitaire != null) {
			setPrixUnitaire(prixUnitaire);
		}

		Double sousTotal = (Double)attributes.get("sousTotal");

		if (sousTotal != null) {
			setSousTotal(sousTotal);
		}
	}

	@Override
	public VenteDetail cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the id detail of this vente detail.
	 *
	 * @return the id detail of this vente detail
	 */
	@Override
	public long getIdDetail() {
		return model.getIdDetail();
	}

	/**
	 * Returns the id medicament of this vente detail.
	 *
	 * @return the id medicament of this vente detail
	 */
	@Override
	public long getIdMedicament() {
		return model.getIdMedicament();
	}

	/**
	 * Returns the id vente of this vente detail.
	 *
	 * @return the id vente of this vente detail
	 */
	@Override
	public long getIdVente() {
		return model.getIdVente();
	}

	/**
	 * Returns the primary key of this vente detail.
	 *
	 * @return the primary key of this vente detail
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the prix unitaire of this vente detail.
	 *
	 * @return the prix unitaire of this vente detail
	 */
	@Override
	public double getPrixUnitaire() {
		return model.getPrixUnitaire();
	}

	/**
	 * Returns the quantite of this vente detail.
	 *
	 * @return the quantite of this vente detail
	 */
	@Override
	public int getQuantite() {
		return model.getQuantite();
	}

	/**
	 * Returns the sous total of this vente detail.
	 *
	 * @return the sous total of this vente detail
	 */
	@Override
	public double getSousTotal() {
		return model.getSousTotal();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the id detail of this vente detail.
	 *
	 * @param idDetail the id detail of this vente detail
	 */
	@Override
	public void setIdDetail(long idDetail) {
		model.setIdDetail(idDetail);
	}

	/**
	 * Sets the id medicament of this vente detail.
	 *
	 * @param idMedicament the id medicament of this vente detail
	 */
	@Override
	public void setIdMedicament(long idMedicament) {
		model.setIdMedicament(idMedicament);
	}

	/**
	 * Sets the id vente of this vente detail.
	 *
	 * @param idVente the id vente of this vente detail
	 */
	@Override
	public void setIdVente(long idVente) {
		model.setIdVente(idVente);
	}

	/**
	 * Sets the primary key of this vente detail.
	 *
	 * @param primaryKey the primary key of this vente detail
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the prix unitaire of this vente detail.
	 *
	 * @param prixUnitaire the prix unitaire of this vente detail
	 */
	@Override
	public void setPrixUnitaire(double prixUnitaire) {
		model.setPrixUnitaire(prixUnitaire);
	}

	/**
	 * Sets the quantite of this vente detail.
	 *
	 * @param quantite the quantite of this vente detail
	 */
	@Override
	public void setQuantite(int quantite) {
		model.setQuantite(quantite);
	}

	/**
	 * Sets the sous total of this vente detail.
	 *
	 * @param sousTotal the sous total of this vente detail
	 */
	@Override
	public void setSousTotal(double sousTotal) {
		model.setSousTotal(sousTotal);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected VenteDetailWrapper wrap(VenteDetail venteDetail) {
		return new VenteDetailWrapper(venteDetail);
	}

}
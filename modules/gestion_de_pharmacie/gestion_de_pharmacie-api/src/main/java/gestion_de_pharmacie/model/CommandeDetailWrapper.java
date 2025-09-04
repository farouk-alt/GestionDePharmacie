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
 * This class is a wrapper for {@link CommandeDetail}.
 * </p>
 *
 * @author Farouk
 * @see CommandeDetail
 * @generated
 */
public class CommandeDetailWrapper
	extends BaseModelWrapper<CommandeDetail>
	implements CommandeDetail, ModelWrapper<CommandeDetail> {

	public CommandeDetailWrapper(CommandeDetail commandeDetail) {
		super(commandeDetail);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idDetail", getIdDetail());
		attributes.put("idCommande", getIdCommande());
		attributes.put("idMedicament", getIdMedicament());
		attributes.put("quantite", getQuantite());
		attributes.put("prixUnitaire", getPrixUnitaire());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idDetail = (Long)attributes.get("idDetail");

		if (idDetail != null) {
			setIdDetail(idDetail);
		}

		Long idCommande = (Long)attributes.get("idCommande");

		if (idCommande != null) {
			setIdCommande(idCommande);
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
	}

	@Override
	public CommandeDetail cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the id commande of this commande detail.
	 *
	 * @return the id commande of this commande detail
	 */
	@Override
	public long getIdCommande() {
		return model.getIdCommande();
	}

	/**
	 * Returns the id detail of this commande detail.
	 *
	 * @return the id detail of this commande detail
	 */
	@Override
	public long getIdDetail() {
		return model.getIdDetail();
	}

	/**
	 * Returns the id medicament of this commande detail.
	 *
	 * @return the id medicament of this commande detail
	 */
	@Override
	public long getIdMedicament() {
		return model.getIdMedicament();
	}

	/**
	 * Returns the primary key of this commande detail.
	 *
	 * @return the primary key of this commande detail
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the prix unitaire of this commande detail.
	 *
	 * @return the prix unitaire of this commande detail
	 */
	@Override
	public double getPrixUnitaire() {
		return model.getPrixUnitaire();
	}

	/**
	 * Returns the quantite of this commande detail.
	 *
	 * @return the quantite of this commande detail
	 */
	@Override
	public int getQuantite() {
		return model.getQuantite();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the id commande of this commande detail.
	 *
	 * @param idCommande the id commande of this commande detail
	 */
	@Override
	public void setIdCommande(long idCommande) {
		model.setIdCommande(idCommande);
	}

	/**
	 * Sets the id detail of this commande detail.
	 *
	 * @param idDetail the id detail of this commande detail
	 */
	@Override
	public void setIdDetail(long idDetail) {
		model.setIdDetail(idDetail);
	}

	/**
	 * Sets the id medicament of this commande detail.
	 *
	 * @param idMedicament the id medicament of this commande detail
	 */
	@Override
	public void setIdMedicament(long idMedicament) {
		model.setIdMedicament(idMedicament);
	}

	/**
	 * Sets the primary key of this commande detail.
	 *
	 * @param primaryKey the primary key of this commande detail
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the prix unitaire of this commande detail.
	 *
	 * @param prixUnitaire the prix unitaire of this commande detail
	 */
	@Override
	public void setPrixUnitaire(double prixUnitaire) {
		model.setPrixUnitaire(prixUnitaire);
	}

	/**
	 * Sets the quantite of this commande detail.
	 *
	 * @param quantite the quantite of this commande detail
	 */
	@Override
	public void setQuantite(int quantite) {
		model.setQuantite(quantite);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CommandeDetailWrapper wrap(CommandeDetail commandeDetail) {
		return new CommandeDetailWrapper(commandeDetail);
	}

}
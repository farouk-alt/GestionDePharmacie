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
 * This class is a wrapper for {@link Commande}.
 * </p>
 *
 * @author Farouk
 * @see Commande
 * @generated
 */
public class CommandeWrapper
	extends BaseModelWrapper<Commande>
	implements Commande, ModelWrapper<Commande> {

	public CommandeWrapper(Commande commande) {
		super(commande);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idCommande", getIdCommande());
		attributes.put("idUtilisateur", getIdUtilisateur());
		attributes.put("dateCommande", getDateCommande());
		attributes.put("statut", getStatut());
		attributes.put("montantTotal", getMontantTotal());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idCommande = (Long)attributes.get("idCommande");

		if (idCommande != null) {
			setIdCommande(idCommande);
		}

		Long idUtilisateur = (Long)attributes.get("idUtilisateur");

		if (idUtilisateur != null) {
			setIdUtilisateur(idUtilisateur);
		}

		Date dateCommande = (Date)attributes.get("dateCommande");

		if (dateCommande != null) {
			setDateCommande(dateCommande);
		}

		String statut = (String)attributes.get("statut");

		if (statut != null) {
			setStatut(statut);
		}

		Double montantTotal = (Double)attributes.get("montantTotal");

		if (montantTotal != null) {
			setMontantTotal(montantTotal);
		}
	}

	@Override
	public Commande cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the date commande of this commande.
	 *
	 * @return the date commande of this commande
	 */
	@Override
	public Date getDateCommande() {
		return model.getDateCommande();
	}

	/**
	 * Returns the id commande of this commande.
	 *
	 * @return the id commande of this commande
	 */
	@Override
	public long getIdCommande() {
		return model.getIdCommande();
	}

	/**
	 * Returns the id utilisateur of this commande.
	 *
	 * @return the id utilisateur of this commande
	 */
	@Override
	public long getIdUtilisateur() {
		return model.getIdUtilisateur();
	}

	/**
	 * Returns the montant total of this commande.
	 *
	 * @return the montant total of this commande
	 */
	@Override
	public double getMontantTotal() {
		return model.getMontantTotal();
	}

	/**
	 * Returns the primary key of this commande.
	 *
	 * @return the primary key of this commande
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the statut of this commande.
	 *
	 * @return the statut of this commande
	 */
	@Override
	public String getStatut() {
		return model.getStatut();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the date commande of this commande.
	 *
	 * @param dateCommande the date commande of this commande
	 */
	@Override
	public void setDateCommande(Date dateCommande) {
		model.setDateCommande(dateCommande);
	}

	/**
	 * Sets the id commande of this commande.
	 *
	 * @param idCommande the id commande of this commande
	 */
	@Override
	public void setIdCommande(long idCommande) {
		model.setIdCommande(idCommande);
	}

	/**
	 * Sets the id utilisateur of this commande.
	 *
	 * @param idUtilisateur the id utilisateur of this commande
	 */
	@Override
	public void setIdUtilisateur(long idUtilisateur) {
		model.setIdUtilisateur(idUtilisateur);
	}

	/**
	 * Sets the montant total of this commande.
	 *
	 * @param montantTotal the montant total of this commande
	 */
	@Override
	public void setMontantTotal(double montantTotal) {
		model.setMontantTotal(montantTotal);
	}

	/**
	 * Sets the primary key of this commande.
	 *
	 * @param primaryKey the primary key of this commande
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the statut of this commande.
	 *
	 * @param statut the statut of this commande
	 */
	@Override
	public void setStatut(String statut) {
		model.setStatut(statut);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CommandeWrapper wrap(Commande commande) {
		return new CommandeWrapper(commande);
	}

}
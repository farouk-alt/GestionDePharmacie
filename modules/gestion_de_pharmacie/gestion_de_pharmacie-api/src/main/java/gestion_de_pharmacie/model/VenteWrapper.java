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
 * This class is a wrapper for {@link Vente}.
 * </p>
 *
 * @author Farouk
 * @see Vente
 * @generated
 */
public class VenteWrapper
	extends BaseModelWrapper<Vente> implements ModelWrapper<Vente>, Vente {

	public VenteWrapper(Vente vente) {
		super(vente);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idVente", getIdVente());
		attributes.put("idUtilisateur", getIdUtilisateur());
		attributes.put("dateVente", getDateVente());
		attributes.put("montantTotal", getMontantTotal());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idVente = (Long)attributes.get("idVente");

		if (idVente != null) {
			setIdVente(idVente);
		}

		Long idUtilisateur = (Long)attributes.get("idUtilisateur");

		if (idUtilisateur != null) {
			setIdUtilisateur(idUtilisateur);
		}

		Date dateVente = (Date)attributes.get("dateVente");

		if (dateVente != null) {
			setDateVente(dateVente);
		}

		Double montantTotal = (Double)attributes.get("montantTotal");

		if (montantTotal != null) {
			setMontantTotal(montantTotal);
		}
	}

	@Override
	public Vente cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the date vente of this vente.
	 *
	 * @return the date vente of this vente
	 */
	@Override
	public Date getDateVente() {
		return model.getDateVente();
	}

	/**
	 * Returns the id utilisateur of this vente.
	 *
	 * @return the id utilisateur of this vente
	 */
	@Override
	public long getIdUtilisateur() {
		return model.getIdUtilisateur();
	}

	/**
	 * Returns the id vente of this vente.
	 *
	 * @return the id vente of this vente
	 */
	@Override
	public long getIdVente() {
		return model.getIdVente();
	}

	/**
	 * Returns the montant total of this vente.
	 *
	 * @return the montant total of this vente
	 */
	@Override
	public double getMontantTotal() {
		return model.getMontantTotal();
	}

	/**
	 * Returns the primary key of this vente.
	 *
	 * @return the primary key of this vente
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the date vente of this vente.
	 *
	 * @param dateVente the date vente of this vente
	 */
	@Override
	public void setDateVente(Date dateVente) {
		model.setDateVente(dateVente);
	}

	/**
	 * Sets the id utilisateur of this vente.
	 *
	 * @param idUtilisateur the id utilisateur of this vente
	 */
	@Override
	public void setIdUtilisateur(long idUtilisateur) {
		model.setIdUtilisateur(idUtilisateur);
	}

	/**
	 * Sets the id vente of this vente.
	 *
	 * @param idVente the id vente of this vente
	 */
	@Override
	public void setIdVente(long idVente) {
		model.setIdVente(idVente);
	}

	/**
	 * Sets the montant total of this vente.
	 *
	 * @param montantTotal the montant total of this vente
	 */
	@Override
	public void setMontantTotal(double montantTotal) {
		model.setMontantTotal(montantTotal);
	}

	/**
	 * Sets the primary key of this vente.
	 *
	 * @param primaryKey the primary key of this vente
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected VenteWrapper wrap(Vente vente) {
		return new VenteWrapper(vente);
	}

}
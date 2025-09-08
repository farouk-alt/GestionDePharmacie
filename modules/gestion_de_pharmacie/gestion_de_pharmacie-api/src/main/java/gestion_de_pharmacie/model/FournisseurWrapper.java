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
 * This class is a wrapper for {@link Fournisseur}.
 * </p>
 *
 * @author Farouk
 * @see Fournisseur
 * @generated
 */
public class FournisseurWrapper
	extends BaseModelWrapper<Fournisseur>
	implements Fournisseur, ModelWrapper<Fournisseur> {

	public FournisseurWrapper(Fournisseur fournisseur) {
		super(fournisseur);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idFournisseur", getIdFournisseur());
		attributes.put("nom", getNom());
		attributes.put("adresse", getAdresse());
		attributes.put("telephone", getTelephone());
		attributes.put("email", getEmail());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idFournisseur = (Long)attributes.get("idFournisseur");

		if (idFournisseur != null) {
			setIdFournisseur(idFournisseur);
		}

		String nom = (String)attributes.get("nom");

		if (nom != null) {
			setNom(nom);
		}

		String adresse = (String)attributes.get("adresse");

		if (adresse != null) {
			setAdresse(adresse);
		}

		String telephone = (String)attributes.get("telephone");

		if (telephone != null) {
			setTelephone(telephone);
		}

		String email = (String)attributes.get("email");

		if (email != null) {
			setEmail(email);
		}
	}

	@Override
	public Fournisseur cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the adresse of this fournisseur.
	 *
	 * @return the adresse of this fournisseur
	 */
	@Override
	public String getAdresse() {
		return model.getAdresse();
	}

	/**
	 * Returns the email of this fournisseur.
	 *
	 * @return the email of this fournisseur
	 */
	@Override
	public String getEmail() {
		return model.getEmail();
	}

	/**
	 * Returns the id fournisseur of this fournisseur.
	 *
	 * @return the id fournisseur of this fournisseur
	 */
	@Override
	public long getIdFournisseur() {
		return model.getIdFournisseur();
	}

	/**
	 * Returns the nom of this fournisseur.
	 *
	 * @return the nom of this fournisseur
	 */
	@Override
	public String getNom() {
		return model.getNom();
	}

	/**
	 * Returns the primary key of this fournisseur.
	 *
	 * @return the primary key of this fournisseur
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the telephone of this fournisseur.
	 *
	 * @return the telephone of this fournisseur
	 */
	@Override
	public String getTelephone() {
		return model.getTelephone();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the adresse of this fournisseur.
	 *
	 * @param adresse the adresse of this fournisseur
	 */
	@Override
	public void setAdresse(String adresse) {
		model.setAdresse(adresse);
	}

	/**
	 * Sets the email of this fournisseur.
	 *
	 * @param email the email of this fournisseur
	 */
	@Override
	public void setEmail(String email) {
		model.setEmail(email);
	}

	/**
	 * Sets the id fournisseur of this fournisseur.
	 *
	 * @param idFournisseur the id fournisseur of this fournisseur
	 */
	@Override
	public void setIdFournisseur(long idFournisseur) {
		model.setIdFournisseur(idFournisseur);
	}

	/**
	 * Sets the nom of this fournisseur.
	 *
	 * @param nom the nom of this fournisseur
	 */
	@Override
	public void setNom(String nom) {
		model.setNom(nom);
	}

	/**
	 * Sets the primary key of this fournisseur.
	 *
	 * @param primaryKey the primary key of this fournisseur
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the telephone of this fournisseur.
	 *
	 * @param telephone the telephone of this fournisseur
	 */
	@Override
	public void setTelephone(String telephone) {
		model.setTelephone(telephone);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected FournisseurWrapper wrap(Fournisseur fournisseur) {
		return new FournisseurWrapper(fournisseur);
	}

}
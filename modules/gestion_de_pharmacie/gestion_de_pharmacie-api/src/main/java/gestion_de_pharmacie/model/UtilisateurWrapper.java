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
 * This class is a wrapper for {@link Utilisateur}.
 * </p>
 *
 * @author Farouk
 * @see Utilisateur
 * @generated
 */
public class UtilisateurWrapper
	extends BaseModelWrapper<Utilisateur>
	implements ModelWrapper<Utilisateur>, Utilisateur {

	public UtilisateurWrapper(Utilisateur utilisateur) {
		super(utilisateur);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idUtilisateur", getIdUtilisateur());
		attributes.put("nom", getNom());
		attributes.put("prenom", getPrenom());
		attributes.put("email", getEmail());
		attributes.put("motDePasse", getMotDePasse());
		attributes.put("role", getRole());
		attributes.put("dateCreation", getDateCreation());
		attributes.put("lastLogin", getLastLogin());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idUtilisateur = (Long)attributes.get("idUtilisateur");

		if (idUtilisateur != null) {
			setIdUtilisateur(idUtilisateur);
		}

		String nom = (String)attributes.get("nom");

		if (nom != null) {
			setNom(nom);
		}

		String prenom = (String)attributes.get("prenom");

		if (prenom != null) {
			setPrenom(prenom);
		}

		String email = (String)attributes.get("email");

		if (email != null) {
			setEmail(email);
		}

		String motDePasse = (String)attributes.get("motDePasse");

		if (motDePasse != null) {
			setMotDePasse(motDePasse);
		}

		String role = (String)attributes.get("role");

		if (role != null) {
			setRole(role);
		}

		Date dateCreation = (Date)attributes.get("dateCreation");

		if (dateCreation != null) {
			setDateCreation(dateCreation);
		}

		Date lastLogin = (Date)attributes.get("lastLogin");

		if (lastLogin != null) {
			setLastLogin(lastLogin);
		}
	}

	@Override
	public Utilisateur cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the date creation of this utilisateur.
	 *
	 * @return the date creation of this utilisateur
	 */
	@Override
	public Date getDateCreation() {
		return model.getDateCreation();
	}

	/**
	 * Returns the email of this utilisateur.
	 *
	 * @return the email of this utilisateur
	 */
	@Override
	public String getEmail() {
		return model.getEmail();
	}

	/**
	 * Returns the id utilisateur of this utilisateur.
	 *
	 * @return the id utilisateur of this utilisateur
	 */
	@Override
	public long getIdUtilisateur() {
		return model.getIdUtilisateur();
	}

	/**
	 * Returns the last login of this utilisateur.
	 *
	 * @return the last login of this utilisateur
	 */
	@Override
	public Date getLastLogin() {
		return model.getLastLogin();
	}

	/**
	 * Returns the mot de passe of this utilisateur.
	 *
	 * @return the mot de passe of this utilisateur
	 */
	@Override
	public String getMotDePasse() {
		return model.getMotDePasse();
	}

	/**
	 * Returns the nom of this utilisateur.
	 *
	 * @return the nom of this utilisateur
	 */
	@Override
	public String getNom() {
		return model.getNom();
	}

	/**
	 * Returns the prenom of this utilisateur.
	 *
	 * @return the prenom of this utilisateur
	 */
	@Override
	public String getPrenom() {
		return model.getPrenom();
	}

	/**
	 * Returns the primary key of this utilisateur.
	 *
	 * @return the primary key of this utilisateur
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the role of this utilisateur.
	 *
	 * @return the role of this utilisateur
	 */
	@Override
	public String getRole() {
		return model.getRole();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the date creation of this utilisateur.
	 *
	 * @param dateCreation the date creation of this utilisateur
	 */
	@Override
	public void setDateCreation(Date dateCreation) {
		model.setDateCreation(dateCreation);
	}

	/**
	 * Sets the email of this utilisateur.
	 *
	 * @param email the email of this utilisateur
	 */
	@Override
	public void setEmail(String email) {
		model.setEmail(email);
	}

	/**
	 * Sets the id utilisateur of this utilisateur.
	 *
	 * @param idUtilisateur the id utilisateur of this utilisateur
	 */
	@Override
	public void setIdUtilisateur(long idUtilisateur) {
		model.setIdUtilisateur(idUtilisateur);
	}

	/**
	 * Sets the last login of this utilisateur.
	 *
	 * @param lastLogin the last login of this utilisateur
	 */
	@Override
	public void setLastLogin(Date lastLogin) {
		model.setLastLogin(lastLogin);
	}

	/**
	 * Sets the mot de passe of this utilisateur.
	 *
	 * @param motDePasse the mot de passe of this utilisateur
	 */
	@Override
	public void setMotDePasse(String motDePasse) {
		model.setMotDePasse(motDePasse);
	}

	/**
	 * Sets the nom of this utilisateur.
	 *
	 * @param nom the nom of this utilisateur
	 */
	@Override
	public void setNom(String nom) {
		model.setNom(nom);
	}

	/**
	 * Sets the prenom of this utilisateur.
	 *
	 * @param prenom the prenom of this utilisateur
	 */
	@Override
	public void setPrenom(String prenom) {
		model.setPrenom(prenom);
	}

	/**
	 * Sets the primary key of this utilisateur.
	 *
	 * @param primaryKey the primary key of this utilisateur
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the role of this utilisateur.
	 *
	 * @param role the role of this utilisateur
	 */
	@Override
	public void setRole(String role) {
		model.setRole(role);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected UtilisateurWrapper wrap(Utilisateur utilisateur) {
		return new UtilisateurWrapper(utilisateur);
	}

}
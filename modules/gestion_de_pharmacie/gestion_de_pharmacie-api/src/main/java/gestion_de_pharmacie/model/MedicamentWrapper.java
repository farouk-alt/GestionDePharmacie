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
 * This class is a wrapper for {@link Medicament}.
 * </p>
 *
 * @author Farouk
 * @see Medicament
 * @generated
 */
public class MedicamentWrapper
	extends BaseModelWrapper<Medicament>
	implements Medicament, ModelWrapper<Medicament> {

	public MedicamentWrapper(Medicament medicament) {
		super(medicament);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idMedicament", getIdMedicament());
		attributes.put("code", getCode());
		attributes.put("codeBarre", getCodeBarre());
		attributes.put("nom", getNom());
		attributes.put("description", getDescription());
		attributes.put("categorie", getCategorie());
		attributes.put("prixUnitaire", getPrixUnitaire());
		attributes.put("seuilMinimum", getSeuilMinimum());
		attributes.put("dateAjout", getDateAjout());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idMedicament = (Long)attributes.get("idMedicament");

		if (idMedicament != null) {
			setIdMedicament(idMedicament);
		}

		String code = (String)attributes.get("code");

		if (code != null) {
			setCode(code);
		}

		String codeBarre = (String)attributes.get("codeBarre");

		if (codeBarre != null) {
			setCodeBarre(codeBarre);
		}

		String nom = (String)attributes.get("nom");

		if (nom != null) {
			setNom(nom);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String categorie = (String)attributes.get("categorie");

		if (categorie != null) {
			setCategorie(categorie);
		}

		Double prixUnitaire = (Double)attributes.get("prixUnitaire");

		if (prixUnitaire != null) {
			setPrixUnitaire(prixUnitaire);
		}

		Integer seuilMinimum = (Integer)attributes.get("seuilMinimum");

		if (seuilMinimum != null) {
			setSeuilMinimum(seuilMinimum);
		}

		Date dateAjout = (Date)attributes.get("dateAjout");

		if (dateAjout != null) {
			setDateAjout(dateAjout);
		}
	}

	@Override
	public Medicament cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the categorie of this medicament.
	 *
	 * @return the categorie of this medicament
	 */
	@Override
	public String getCategorie() {
		return model.getCategorie();
	}

	/**
	 * Returns the code of this medicament.
	 *
	 * @return the code of this medicament
	 */
	@Override
	public String getCode() {
		return model.getCode();
	}

	/**
	 * Returns the code barre of this medicament.
	 *
	 * @return the code barre of this medicament
	 */
	@Override
	public String getCodeBarre() {
		return model.getCodeBarre();
	}

	/**
	 * Returns the date ajout of this medicament.
	 *
	 * @return the date ajout of this medicament
	 */
	@Override
	public Date getDateAjout() {
		return model.getDateAjout();
	}

	/**
	 * Returns the description of this medicament.
	 *
	 * @return the description of this medicament
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the id medicament of this medicament.
	 *
	 * @return the id medicament of this medicament
	 */
	@Override
	public long getIdMedicament() {
		return model.getIdMedicament();
	}

	/**
	 * Returns the nom of this medicament.
	 *
	 * @return the nom of this medicament
	 */
	@Override
	public String getNom() {
		return model.getNom();
	}

	/**
	 * Returns the primary key of this medicament.
	 *
	 * @return the primary key of this medicament
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the prix unitaire of this medicament.
	 *
	 * @return the prix unitaire of this medicament
	 */
	@Override
	public double getPrixUnitaire() {
		return model.getPrixUnitaire();
	}

	/**
	 * Returns the seuil minimum of this medicament.
	 *
	 * @return the seuil minimum of this medicament
	 */
	@Override
	public int getSeuilMinimum() {
		return model.getSeuilMinimum();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the categorie of this medicament.
	 *
	 * @param categorie the categorie of this medicament
	 */
	@Override
	public void setCategorie(String categorie) {
		model.setCategorie(categorie);
	}

	/**
	 * Sets the code of this medicament.
	 *
	 * @param code the code of this medicament
	 */
	@Override
	public void setCode(String code) {
		model.setCode(code);
	}

	/**
	 * Sets the code barre of this medicament.
	 *
	 * @param codeBarre the code barre of this medicament
	 */
	@Override
	public void setCodeBarre(String codeBarre) {
		model.setCodeBarre(codeBarre);
	}

	/**
	 * Sets the date ajout of this medicament.
	 *
	 * @param dateAjout the date ajout of this medicament
	 */
	@Override
	public void setDateAjout(Date dateAjout) {
		model.setDateAjout(dateAjout);
	}

	/**
	 * Sets the description of this medicament.
	 *
	 * @param description the description of this medicament
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the id medicament of this medicament.
	 *
	 * @param idMedicament the id medicament of this medicament
	 */
	@Override
	public void setIdMedicament(long idMedicament) {
		model.setIdMedicament(idMedicament);
	}

	/**
	 * Sets the nom of this medicament.
	 *
	 * @param nom the nom of this medicament
	 */
	@Override
	public void setNom(String nom) {
		model.setNom(nom);
	}

	/**
	 * Sets the primary key of this medicament.
	 *
	 * @param primaryKey the primary key of this medicament
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the prix unitaire of this medicament.
	 *
	 * @param prixUnitaire the prix unitaire of this medicament
	 */
	@Override
	public void setPrixUnitaire(double prixUnitaire) {
		model.setPrixUnitaire(prixUnitaire);
	}

	/**
	 * Sets the seuil minimum of this medicament.
	 *
	 * @param seuilMinimum the seuil minimum of this medicament
	 */
	@Override
	public void setSeuilMinimum(int seuilMinimum) {
		model.setSeuilMinimum(seuilMinimum);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected MedicamentWrapper wrap(Medicament medicament) {
		return new MedicamentWrapper(medicament);
	}

}
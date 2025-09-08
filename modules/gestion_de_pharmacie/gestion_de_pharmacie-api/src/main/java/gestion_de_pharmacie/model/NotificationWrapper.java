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
 * This class is a wrapper for {@link Notification}.
 * </p>
 *
 * @author Farouk
 * @see Notification
 * @generated
 */
public class NotificationWrapper
	extends BaseModelWrapper<Notification>
	implements ModelWrapper<Notification>, Notification {

	public NotificationWrapper(Notification notification) {
		super(notification);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("idNotification", getIdNotification());
		attributes.put("idUtilisateur", getIdUtilisateur());
		attributes.put("type", getType());
		attributes.put("message", getMessage());
		attributes.put("statut", getStatut());
		attributes.put("dateCreation", getDateCreation());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long idNotification = (Long)attributes.get("idNotification");

		if (idNotification != null) {
			setIdNotification(idNotification);
		}

		Long idUtilisateur = (Long)attributes.get("idUtilisateur");

		if (idUtilisateur != null) {
			setIdUtilisateur(idUtilisateur);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String message = (String)attributes.get("message");

		if (message != null) {
			setMessage(message);
		}

		String statut = (String)attributes.get("statut");

		if (statut != null) {
			setStatut(statut);
		}

		Date dateCreation = (Date)attributes.get("dateCreation");

		if (dateCreation != null) {
			setDateCreation(dateCreation);
		}
	}

	@Override
	public Notification cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the date creation of this notification.
	 *
	 * @return the date creation of this notification
	 */
	@Override
	public Date getDateCreation() {
		return model.getDateCreation();
	}

	/**
	 * Returns the id notification of this notification.
	 *
	 * @return the id notification of this notification
	 */
	@Override
	public long getIdNotification() {
		return model.getIdNotification();
	}

	/**
	 * Returns the id utilisateur of this notification.
	 *
	 * @return the id utilisateur of this notification
	 */
	@Override
	public long getIdUtilisateur() {
		return model.getIdUtilisateur();
	}

	/**
	 * Returns the message of this notification.
	 *
	 * @return the message of this notification
	 */
	@Override
	public String getMessage() {
		return model.getMessage();
	}

	/**
	 * Returns the primary key of this notification.
	 *
	 * @return the primary key of this notification
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the statut of this notification.
	 *
	 * @return the statut of this notification
	 */
	@Override
	public String getStatut() {
		return model.getStatut();
	}

	/**
	 * Returns the type of this notification.
	 *
	 * @return the type of this notification
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the date creation of this notification.
	 *
	 * @param dateCreation the date creation of this notification
	 */
	@Override
	public void setDateCreation(Date dateCreation) {
		model.setDateCreation(dateCreation);
	}

	/**
	 * Sets the id notification of this notification.
	 *
	 * @param idNotification the id notification of this notification
	 */
	@Override
	public void setIdNotification(long idNotification) {
		model.setIdNotification(idNotification);
	}

	/**
	 * Sets the id utilisateur of this notification.
	 *
	 * @param idUtilisateur the id utilisateur of this notification
	 */
	@Override
	public void setIdUtilisateur(long idUtilisateur) {
		model.setIdUtilisateur(idUtilisateur);
	}

	/**
	 * Sets the message of this notification.
	 *
	 * @param message the message of this notification
	 */
	@Override
	public void setMessage(String message) {
		model.setMessage(message);
	}

	/**
	 * Sets the primary key of this notification.
	 *
	 * @param primaryKey the primary key of this notification
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the statut of this notification.
	 *
	 * @param statut the statut of this notification
	 */
	@Override
	public void setStatut(String statut) {
		model.setStatut(statut);
	}

	/**
	 * Sets the type of this notification.
	 *
	 * @param type the type of this notification
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected NotificationWrapper wrap(Notification notification) {
		return new NotificationWrapper(notification);
	}

}
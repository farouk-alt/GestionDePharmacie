/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import gestion_de_pharmacie.model.Notification;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Notification in entity cache.
 *
 * @author Farouk
 * @generated
 */
public class NotificationCacheModel
	implements CacheModel<Notification>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationCacheModel)) {
			return false;
		}

		NotificationCacheModel notificationCacheModel =
			(NotificationCacheModel)object;

		if (idNotification == notificationCacheModel.idNotification) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, idNotification);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{idNotification=");
		sb.append(idNotification);
		sb.append(", idUtilisateur=");
		sb.append(idUtilisateur);
		sb.append(", type=");
		sb.append(type);
		sb.append(", message=");
		sb.append(message);
		sb.append(", statut=");
		sb.append(statut);
		sb.append(", dateCreation=");
		sb.append(dateCreation);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Notification toEntityModel() {
		NotificationImpl notificationImpl = new NotificationImpl();

		notificationImpl.setIdNotification(idNotification);
		notificationImpl.setIdUtilisateur(idUtilisateur);

		if (type == null) {
			notificationImpl.setType("");
		}
		else {
			notificationImpl.setType(type);
		}

		if (message == null) {
			notificationImpl.setMessage("");
		}
		else {
			notificationImpl.setMessage(message);
		}

		if (statut == null) {
			notificationImpl.setStatut("");
		}
		else {
			notificationImpl.setStatut(statut);
		}

		if (dateCreation == Long.MIN_VALUE) {
			notificationImpl.setDateCreation(null);
		}
		else {
			notificationImpl.setDateCreation(new Date(dateCreation));
		}

		notificationImpl.resetOriginalValues();

		return notificationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		idNotification = objectInput.readLong();

		idUtilisateur = objectInput.readLong();
		type = objectInput.readUTF();
		message = objectInput.readUTF();
		statut = objectInput.readUTF();
		dateCreation = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(idNotification);

		objectOutput.writeLong(idUtilisateur);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (message == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(message);
		}

		if (statut == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statut);
		}

		objectOutput.writeLong(dateCreation);
	}

	public long idNotification;
	public long idUtilisateur;
	public String type;
	public String message;
	public String statut;
	public long dateCreation;

}
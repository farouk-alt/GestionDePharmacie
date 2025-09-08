/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Pharma_Notification&quot; database table.
 *
 * @author Farouk
 * @see Notification
 * @generated
 */
public class NotificationTable extends BaseTable<NotificationTable> {

	public static final NotificationTable INSTANCE = new NotificationTable();

	public final Column<NotificationTable, Long> idNotification = createColumn(
		"idNotification", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<NotificationTable, Long> idUtilisateur = createColumn(
		"idUtilisateur", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NotificationTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTable, String> message = createColumn(
		"message", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTable, String> statut = createColumn(
		"statut", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<NotificationTable, Date> dateCreation = createColumn(
		"dateCreation", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private NotificationTable() {
		super("Pharma_Notification", NotificationTable::new);
	}

}
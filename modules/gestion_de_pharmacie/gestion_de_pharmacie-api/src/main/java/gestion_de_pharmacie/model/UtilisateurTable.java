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
 * The table class for the &quot;Pharma_Utilisateur&quot; database table.
 *
 * @author Farouk
 * @see Utilisateur
 * @generated
 */
public class UtilisateurTable extends BaseTable<UtilisateurTable> {

	public static final UtilisateurTable INSTANCE = new UtilisateurTable();

	public final Column<UtilisateurTable, Long> idUtilisateur = createColumn(
		"idUtilisateur", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UtilisateurTable, Long> liferayUserId = createColumn(
		"liferayUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UtilisateurTable, String> nom = createColumn(
		"nom", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UtilisateurTable, String> prenom = createColumn(
		"prenom", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UtilisateurTable, String> email = createColumn(
		"email", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UtilisateurTable, String> motDePasse = createColumn(
		"motDePasse", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UtilisateurTable, String> role = createColumn(
		"role_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UtilisateurTable, Date> dateCreation = createColumn(
		"dateCreation", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private UtilisateurTable() {
		super("Pharma_Utilisateur", UtilisateurTable::new);
	}

}
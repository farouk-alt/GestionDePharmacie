/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Pharma_Fournisseur&quot; database table.
 *
 * @author Farouk
 * @see Fournisseur
 * @generated
 */
public class FournisseurTable extends BaseTable<FournisseurTable> {

	public static final FournisseurTable INSTANCE = new FournisseurTable();

	public final Column<FournisseurTable, Long> idFournisseur = createColumn(
		"idFournisseur", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FournisseurTable, String> nom = createColumn(
		"nom", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FournisseurTable, String> adresse = createColumn(
		"adresse", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FournisseurTable, String> telephone = createColumn(
		"telephone", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FournisseurTable, String> email = createColumn(
		"email", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private FournisseurTable() {
		super("Pharma_Fournisseur", FournisseurTable::new);
	}

}
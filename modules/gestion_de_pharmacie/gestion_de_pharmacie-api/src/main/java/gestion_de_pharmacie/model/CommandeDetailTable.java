/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Pharma_CommandeDetail&quot; database table.
 *
 * @author Farouk
 * @see CommandeDetail
 * @generated
 */
public class CommandeDetailTable extends BaseTable<CommandeDetailTable> {

	public static final CommandeDetailTable INSTANCE =
		new CommandeDetailTable();

	public final Column<CommandeDetailTable, Long> idDetail = createColumn(
		"idDetail", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommandeDetailTable, Long> idCommande = createColumn(
		"idCommande", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommandeDetailTable, Long> idMedicament = createColumn(
		"idMedicament", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommandeDetailTable, Integer> quantite = createColumn(
		"quantite", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommandeDetailTable, Double> prixUnitaire =
		createColumn(
			"prixUnitaire", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CommandeDetailTable() {
		super("Pharma_CommandeDetail", CommandeDetailTable::new);
	}

}
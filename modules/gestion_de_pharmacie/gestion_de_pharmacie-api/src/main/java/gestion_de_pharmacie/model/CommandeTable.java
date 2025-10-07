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
 * The table class for the &quot;Pharma_Commande&quot; database table.
 *
 * @author Farouk
 * @see Commande
 * @generated
 */
public class CommandeTable extends BaseTable<CommandeTable> {

	public static final CommandeTable INSTANCE = new CommandeTable();

	public final Column<CommandeTable, Long> idCommande = createColumn(
		"idCommande", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommandeTable, Long> idUtilisateur = createColumn(
		"idUtilisateur", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommandeTable, Date> dateCommande = createColumn(
		"dateCommande", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommandeTable, String> statut = createColumn(
		"statut", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommandeTable, Double> montantTotal = createColumn(
		"montantTotal", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CommandeTable() {
		super("Pharma_Commande", CommandeTable::new);
	}

}
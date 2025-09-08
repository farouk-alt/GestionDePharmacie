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
 * The table class for the &quot;Pharma_MouvementStock&quot; database table.
 *
 * @author Farouk
 * @see MouvementStock
 * @generated
 */
public class MouvementStockTable extends BaseTable<MouvementStockTable> {

	public static final MouvementStockTable INSTANCE =
		new MouvementStockTable();

	public final Column<MouvementStockTable, Long> idMouvement = createColumn(
		"idMouvement", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MouvementStockTable, Long> idStock = createColumn(
		"idStock", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MouvementStockTable, String> typeMouvement =
		createColumn(
			"typeMouvement", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MouvementStockTable, Integer> quantite = createColumn(
		"quantite", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<MouvementStockTable, Date> dateMouvement = createColumn(
		"dateMouvement", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private MouvementStockTable() {
		super("Pharma_MouvementStock", MouvementStockTable::new);
	}

}
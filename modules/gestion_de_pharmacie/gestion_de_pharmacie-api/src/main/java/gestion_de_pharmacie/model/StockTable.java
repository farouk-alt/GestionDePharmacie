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
 * The table class for the &quot;Pharma_Stock&quot; database table.
 *
 * @author Farouk
 * @see Stock
 * @generated
 */
public class StockTable extends BaseTable<StockTable> {

	public static final StockTable INSTANCE = new StockTable();

	public final Column<StockTable, Long> idStock = createColumn(
		"idStock", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<StockTable, Long> idMedicament = createColumn(
		"idMedicament", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<StockTable, Integer> quantiteDisponible = createColumn(
		"quantiteDisponible", Integer.class, Types.INTEGER,
		Column.FLAG_DEFAULT);
	public final Column<StockTable, Date> dateDerniereMaj = createColumn(
		"dateDerniereMaj", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private StockTable() {
		super("Pharma_Stock", StockTable::new);
	}

}
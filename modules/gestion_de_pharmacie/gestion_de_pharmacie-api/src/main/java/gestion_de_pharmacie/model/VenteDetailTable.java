/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Pharma_VenteDetail&quot; database table.
 *
 * @author Farouk
 * @see VenteDetail
 * @generated
 */
public class VenteDetailTable extends BaseTable<VenteDetailTable> {

	public static final VenteDetailTable INSTANCE = new VenteDetailTable();

	public final Column<VenteDetailTable, Long> idDetail = createColumn(
		"idDetail", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<VenteDetailTable, Long> idVente = createColumn(
		"idVente", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VenteDetailTable, Long> idMedicament = createColumn(
		"idMedicament", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VenteDetailTable, Integer> quantite = createColumn(
		"quantite", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<VenteDetailTable, Double> prixUnitaire = createColumn(
		"prixUnitaire", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<VenteDetailTable, Double> sousTotal = createColumn(
		"sousTotal", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private VenteDetailTable() {
		super("Pharma_VenteDetail", VenteDetailTable::new);
	}

}
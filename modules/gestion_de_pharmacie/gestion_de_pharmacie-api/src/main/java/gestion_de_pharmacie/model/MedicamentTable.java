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
 * The table class for the &quot;Pharma_Medicament&quot; database table.
 *
 * @author Farouk
 * @see Medicament
 * @generated
 */
public class MedicamentTable extends BaseTable<MedicamentTable> {

	public static final MedicamentTable INSTANCE = new MedicamentTable();

	public final Column<MedicamentTable, Long> idMedicament = createColumn(
		"idMedicament", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MedicamentTable, String> code = createColumn(
		"code_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MedicamentTable, String> codeBarre = createColumn(
		"codeBarre", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MedicamentTable, String> nom = createColumn(
		"nom", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MedicamentTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MedicamentTable, String> categorie = createColumn(
		"categorie", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MedicamentTable, Double> prixUnitaire = createColumn(
		"prixUnitaire", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<MedicamentTable, Integer> seuilMinimum = createColumn(
		"seuilMinimum", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<MedicamentTable, Date> dateAjout = createColumn(
		"dateAjout", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private MedicamentTable() {
		super("Pharma_Medicament", MedicamentTable::new);
	}

}
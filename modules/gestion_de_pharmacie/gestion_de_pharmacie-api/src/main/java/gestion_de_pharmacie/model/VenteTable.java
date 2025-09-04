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
 * The table class for the &quot;Pharma_Vente&quot; database table.
 *
 * @author Farouk
 * @see Vente
 * @generated
 */
public class VenteTable extends BaseTable<VenteTable> {

	public static final VenteTable INSTANCE = new VenteTable();

	public final Column<VenteTable, Long> idVente = createColumn(
		"idVente", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<VenteTable, Long> idUtilisateur = createColumn(
		"idUtilisateur", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<VenteTable, Date> dateVente = createColumn(
		"dateVente", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<VenteTable, Double> montantTotal = createColumn(
		"montantTotal", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private VenteTable() {
		super("Pharma_Vente", VenteTable::new);
	}

}
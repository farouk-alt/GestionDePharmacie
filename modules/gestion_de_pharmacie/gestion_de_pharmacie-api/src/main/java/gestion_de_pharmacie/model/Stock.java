/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the Stock service. Represents a row in the &quot;Pharma_Stock&quot; database table, with each column mapped to a property of this class.
 *
 * @author Farouk
 * @see StockModel
 * @generated
 */
@ImplementationClassName("gestion_de_pharmacie.model.impl.StockImpl")
@ProviderType
public interface Stock extends PersistedModel, StockModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>gestion_de_pharmacie.model.impl.StockImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Stock, Long> ID_STOCK_ACCESSOR =
		new Accessor<Stock, Long>() {

			@Override
			public Long get(Stock stock) {
				return stock.getIdStock();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Stock> getTypeClass() {
				return Stock.class;
			}

		};

}
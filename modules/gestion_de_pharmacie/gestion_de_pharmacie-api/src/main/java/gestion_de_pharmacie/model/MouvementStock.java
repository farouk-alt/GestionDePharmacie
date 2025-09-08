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
 * The extended model interface for the MouvementStock service. Represents a row in the &quot;Pharma_MouvementStock&quot; database table, with each column mapped to a property of this class.
 *
 * @author Farouk
 * @see MouvementStockModel
 * @generated
 */
@ImplementationClassName("gestion_de_pharmacie.model.impl.MouvementStockImpl")
@ProviderType
public interface MouvementStock extends MouvementStockModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>gestion_de_pharmacie.model.impl.MouvementStockImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MouvementStock, Long> ID_MOUVEMENT_ACCESSOR =
		new Accessor<MouvementStock, Long>() {

			@Override
			public Long get(MouvementStock mouvementStock) {
				return mouvementStock.getIdMouvement();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MouvementStock> getTypeClass() {
				return MouvementStock.class;
			}

		};

}
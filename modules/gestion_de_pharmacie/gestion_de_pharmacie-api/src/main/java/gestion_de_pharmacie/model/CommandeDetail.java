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
 * The extended model interface for the CommandeDetail service. Represents a row in the &quot;Pharma_CommandeDetail&quot; database table, with each column mapped to a property of this class.
 *
 * @author Farouk
 * @see CommandeDetailModel
 * @generated
 */
@ImplementationClassName("gestion_de_pharmacie.model.impl.CommandeDetailImpl")
@ProviderType
public interface CommandeDetail extends CommandeDetailModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>gestion_de_pharmacie.model.impl.CommandeDetailImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommandeDetail, Long> ID_DETAIL_ACCESSOR =
		new Accessor<CommandeDetail, Long>() {

			@Override
			public Long get(CommandeDetail commandeDetail) {
				return commandeDetail.getIdDetail();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommandeDetail> getTypeClass() {
				return CommandeDetail.class;
			}

		};

}
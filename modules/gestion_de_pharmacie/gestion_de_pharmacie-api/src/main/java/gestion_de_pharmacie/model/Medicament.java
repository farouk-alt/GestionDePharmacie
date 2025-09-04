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
 * The extended model interface for the Medicament service. Represents a row in the &quot;Pharma_Medicament&quot; database table, with each column mapped to a property of this class.
 *
 * @author Farouk
 * @see MedicamentModel
 * @generated
 */
@ImplementationClassName("gestion_de_pharmacie.model.impl.MedicamentImpl")
@ProviderType
public interface Medicament extends MedicamentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>gestion_de_pharmacie.model.impl.MedicamentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Medicament, Long> ID_MEDICAMENT_ACCESSOR =
		new Accessor<Medicament, Long>() {

			@Override
			public Long get(Medicament medicament) {
				return medicament.getIdMedicament();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Medicament> getTypeClass() {
				return Medicament.class;
			}

		};

}
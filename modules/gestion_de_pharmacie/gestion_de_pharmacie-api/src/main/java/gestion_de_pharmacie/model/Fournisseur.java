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
 * The extended model interface for the Fournisseur service. Represents a row in the &quot;Pharma_Fournisseur&quot; database table, with each column mapped to a property of this class.
 *
 * @author Farouk
 * @see FournisseurModel
 * @generated
 */
@ImplementationClassName("gestion_de_pharmacie.model.impl.FournisseurImpl")
@ProviderType
public interface Fournisseur extends FournisseurModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>gestion_de_pharmacie.model.impl.FournisseurImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Fournisseur, Long> ID_FOURNISSEUR_ACCESSOR =
		new Accessor<Fournisseur, Long>() {

			@Override
			public Long get(Fournisseur fournisseur) {
				return fournisseur.getIdFournisseur();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Fournisseur> getTypeClass() {
				return Fournisseur.class;
			}

		};

}
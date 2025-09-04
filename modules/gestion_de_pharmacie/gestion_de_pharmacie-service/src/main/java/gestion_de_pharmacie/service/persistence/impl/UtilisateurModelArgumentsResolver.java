/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import gestion_de_pharmacie.model.UtilisateurTable;
import gestion_de_pharmacie.model.impl.UtilisateurImpl;
import gestion_de_pharmacie.model.impl.UtilisateurModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from Utilisateur.
 *
 * @author Farouk
 * @generated
 */
@Component(
	property = {
		"class.name=gestion_de_pharmacie.model.impl.UtilisateurImpl",
		"table.name=Pharma_Utilisateur"
	},
	service = ArgumentsResolver.class
)
public class UtilisateurModelArgumentsResolver implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		UtilisateurModelImpl utilisateurModelImpl =
			(UtilisateurModelImpl)baseModel;

		long columnBitmask = utilisateurModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(utilisateurModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					utilisateurModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(utilisateurModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return UtilisateurImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return UtilisateurTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		UtilisateurModelImpl utilisateurModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = utilisateurModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = utilisateurModelImpl.getColumnValue(columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}
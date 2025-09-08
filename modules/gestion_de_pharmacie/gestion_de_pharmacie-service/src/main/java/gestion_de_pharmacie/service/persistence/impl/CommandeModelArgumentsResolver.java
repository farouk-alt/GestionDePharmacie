/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import gestion_de_pharmacie.model.CommandeTable;
import gestion_de_pharmacie.model.impl.CommandeImpl;
import gestion_de_pharmacie.model.impl.CommandeModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from Commande.
 *
 * @author Farouk
 * @generated
 */
@Component(
	property = {
		"class.name=gestion_de_pharmacie.model.impl.CommandeImpl",
		"table.name=Pharma_Commande"
	},
	service = ArgumentsResolver.class
)
public class CommandeModelArgumentsResolver implements ArgumentsResolver {

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

		CommandeModelImpl commandeModelImpl = (CommandeModelImpl)baseModel;

		long columnBitmask = commandeModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(commandeModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |= commandeModelImpl.getColumnBitmask(
					columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(commandeModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return CommandeImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return CommandeTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		CommandeModelImpl commandeModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = commandeModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = commandeModelImpl.getColumnValue(columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}
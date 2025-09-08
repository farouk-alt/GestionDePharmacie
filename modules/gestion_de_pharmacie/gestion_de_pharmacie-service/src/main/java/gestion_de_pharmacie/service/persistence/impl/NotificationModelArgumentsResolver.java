/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import gestion_de_pharmacie.model.NotificationTable;
import gestion_de_pharmacie.model.impl.NotificationImpl;
import gestion_de_pharmacie.model.impl.NotificationModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from Notification.
 *
 * @author Farouk
 * @generated
 */
@Component(
	property = {
		"class.name=gestion_de_pharmacie.model.impl.NotificationImpl",
		"table.name=Pharma_Notification"
	},
	service = ArgumentsResolver.class
)
public class NotificationModelArgumentsResolver implements ArgumentsResolver {

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

		NotificationModelImpl notificationModelImpl =
			(NotificationModelImpl)baseModel;

		long columnBitmask = notificationModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(notificationModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					notificationModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(notificationModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return NotificationImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return NotificationTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		NotificationModelImpl notificationModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = notificationModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = notificationModelImpl.getColumnValue(columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}
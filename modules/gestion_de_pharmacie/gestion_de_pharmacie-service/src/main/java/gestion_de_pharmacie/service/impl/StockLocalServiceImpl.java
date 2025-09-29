/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.impl;

import com.liferay.portal.aop.AopService;

import gestion_de_pharmacie.model.Stock;
import gestion_de_pharmacie.service.base.StockLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=gestion_de_pharmacie.model.Stock",
	service = AopService.class
)
public class StockLocalServiceImpl extends StockLocalServiceBaseImpl {

    public Stock fetchStockByMedicamentId(long medicamentId) {
        return stockPersistence.fetchByMed(medicamentId);
    }

}
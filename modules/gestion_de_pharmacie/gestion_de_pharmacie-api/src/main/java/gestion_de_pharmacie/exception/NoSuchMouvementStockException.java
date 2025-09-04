/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package gestion_de_pharmacie.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Farouk
 */
public class NoSuchMouvementStockException extends NoSuchModelException {

	public NoSuchMouvementStockException() {
	}

	public NoSuchMouvementStockException(String msg) {
		super(msg);
	}

	public NoSuchMouvementStockException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchMouvementStockException(Throwable throwable) {
		super(throwable);
	}

}
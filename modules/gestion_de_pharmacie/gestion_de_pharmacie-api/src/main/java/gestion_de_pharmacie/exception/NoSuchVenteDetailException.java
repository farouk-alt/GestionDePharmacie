/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package gestion_de_pharmacie.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Farouk
 */
public class NoSuchVenteDetailException extends NoSuchModelException {

	public NoSuchVenteDetailException() {
	}

	public NoSuchVenteDetailException(String msg) {
		super(msg);
	}

	public NoSuchVenteDetailException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchVenteDetailException(Throwable throwable) {
		super(throwable);
	}

}
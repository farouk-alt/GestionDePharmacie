/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package gestion_de_pharmacie.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchMedicamentException extends NoSuchModelException {

	public NoSuchMedicamentException() {
	}

	public NoSuchMedicamentException(String msg) {
		super(msg);
	}

	public NoSuchMedicamentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchMedicamentException(Throwable throwable) {
		super(throwable);
	}

}
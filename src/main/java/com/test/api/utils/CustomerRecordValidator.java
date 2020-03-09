package com.test.api.utils;

import nl.garvelink.iban.IBAN;

/**
 * The Class CustomerRecordValidator.
 */
public class CustomerRecordValidator {

	/**
	 * Validate account number.
	 *
	 * @param accountNumber
	 *            the account number
	 * @return true, if successful
	 */
	public static boolean validateAccountNumber(String accountNumber) {
		try {
			IBAN.valueOf(accountNumber);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

}

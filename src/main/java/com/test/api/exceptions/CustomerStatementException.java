package com.test.api.exceptions;

/**
 * The Class CustomerStatementException.
 */
public class CustomerStatementException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new customer statement exception.
	 *
	 * @param message the message
	 */
	public CustomerStatementException(String message) {
		super(message);
	}

}

package com.test.api.models;

/**
 * The Class Report.
 */
public class Report {

	/** The transaction reference number. */
	private int transactionReferenceNumber;
	
	/** The description. */
	private String description;
	
	/** The status. */
	private String status;

	/**
	 * Instantiates a new report.
	 */
	public Report() {
		super();
	}

	/**
	 * Instantiates a new report.
	 *
	 * @param transactionReferenceNumber the transaction reference number
	 * @param description the description
	 * @param status the status
	 */
	public Report(int transactionReferenceNumber, String description, String status) {
		super();
		this.transactionReferenceNumber = transactionReferenceNumber;
		this.description = description;
		this.status = status;
	}

	/**
	 * Gets the transaction reference number.
	 *
	 * @return the transaction reference number
	 */
	public int getTransactionReferenceNumber() {
		return transactionReferenceNumber;
	}

	/**
	 * Sets the transaction reference number.
	 *
	 * @param transactionReferenceNumber the new transaction reference number
	 */
	public void setTransactionReferenceNumber(int transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}

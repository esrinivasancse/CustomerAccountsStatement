package com.test.api.models;

/**
 * The Class Record.
 */
public class Record {
	
	/** The transaction reference number. */
	private int transactionReferenceNumber;
	
	/** The account number. */
	private String accountNumber;
	
	/** The start balance. */
	private Double startBalance;
	
	/** The mutation. */
	private Double mutation;
	
	/** The description. */
	private String description;
	
	/** The end balance. */
	private Double endBalance;

	/**
	 * Instantiates a new record.
	 */
	public Record() {
		super();
	}

	/**
	 * Instantiates a new record.
	 *
	 * @param transactionReferenceNumber the transaction reference number
	 * @param accountNumber the account number
	 * @param startBalance the start balance
	 * @param mutation the mutation
	 * @param description the description
	 * @param endBalance the end balance
	 */
	public Record(int transactionReferenceNumber, String accountNumber, Double startBalance, Double mutation,
			String description, Double endBalance) {
		super();
		this.transactionReferenceNumber = transactionReferenceNumber;
		this.accountNumber = accountNumber;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.description = description;
		this.endBalance = endBalance;
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
	 * Gets the account number.
	 *
	 * @return the account number
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the account number.
	 *
	 * @param accountNumber the new account number
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Gets the start balance.
	 *
	 * @return the start balance
	 */
	public Double getStartBalance() {
		return startBalance;
	}

	/**
	 * Sets the start balance.
	 *
	 * @param startBalance the new start balance
	 */
	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}

	/**
	 * Gets the mutation.
	 *
	 * @return the mutation
	 */
	public Double getMutation() {
		return mutation;
	}

	/**
	 * Sets the mutation.
	 *
	 * @param mutation the new mutation
	 */
	public void setMutation(Double mutation) {
		this.mutation = mutation;
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
	 * Gets the end balance.
	 *
	 * @return the end balance
	 */
	public Double getEndBalance() {
		return endBalance;
	}

	/**
	 * Sets the end balance.
	 *
	 * @param endBalance the new end balance
	 */
	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}

}

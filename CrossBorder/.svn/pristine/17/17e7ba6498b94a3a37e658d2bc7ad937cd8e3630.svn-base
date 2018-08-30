package com.impalapay.airtel.beans.accountmgmt.balance;

import java.util.Date;

import com.impalapay.airtel.beans.StorableBean;

/**
 * A generic float purchase of account
 * <p>
 * Copyright (c) ImpalaPay Ltd., Oct 12, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */

public class AccountPurchase extends StorableBean {

	private String accountUuid;
	private double amount;
	private Date purchaseDate;

	/**
	 * Default constructor
	 * 
	 */
	public AccountPurchase() {
		super();

		accountUuid = "";
		amount = 0;
		purchaseDate = new Date();

	}

	public String getAccountUuid() {
		return accountUuid;
	}

	/**
	 * 
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * 
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate() {
		return new Date(purchaseDate.getTime());
	}

	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	/**
	 * 
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		if (amount >= 0) {
			this.amount = amount;
		}
	}

	/**
	 * 
	 * @param date
	 *            the purchaseDate to set
	 */
	public void setPurchaseDate(Date date) {
		if (date != null) {
			purchaseDate = new Date(date.getTime());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountPurchase [getUuid()=");
		builder.append(getUuid());
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", purchaseDate=");
		builder.append(purchaseDate);
		builder.append("]");
		return builder.toString();
	}

	  
	
}

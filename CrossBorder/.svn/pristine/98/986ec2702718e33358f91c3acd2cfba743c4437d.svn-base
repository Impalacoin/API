package com.impalapay.airtel.beans.accountmgmt.balance;

import com.impalapay.airtel.beans.StorableBean;

/**
 * A generic balance of account
 * <p>
 * Copyright (c) ImpalaPay Ltd., Oct 12, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class AccountBalance extends StorableBean {

	private double balance;

	private String accountUuid;

	/**
     * 
     */
	public AccountBalance() {
		super();

		balance = 0;
		accountUuid = "";

	}

	public double getBalance() {
		return balance;
	}

	public String getAccountUuid() {
		return accountUuid;
	}

	public void setBalance(double balance) {
		if (balance >= 0) {
			this.balance = balance;
		}
	}

	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountBalance [getUuid()=");
		builder.append(getUuid());
		builder.append(", balance=");
		builder.append(balance);
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append("]");
		return builder.toString();
	}

}

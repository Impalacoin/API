package com.impalapay.airtel.beans.accountmgmt.logincount;

import java.util.Date;

import com.impalapay.airtel.beans.StorableBean;

/**
 * Represents a count for loging into the system.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Jan 22, 2015
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */

public class LoginCount extends StorableBean {

	private String accountUuid;
	private int countlogin;

	/**
	 * 
	 */
	public LoginCount() {
		super();

		accountUuid = "";
		countlogin = 0;
	}

	/**
	 * 
	 * @return
	 */

	public String getAccountUuid() {
		return accountUuid;
	}

	/**
	 * 
	 * @return
	 */
	public int getCountlogin() {
		return countlogin;
	}

	/**
	 * 
	 * @param accountUuid
	 */
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	/**
	 * 
	 * @param countlogin
	 */
	public void setCountlogin(int countlogin) {
		this.countlogin = countlogin;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginCount [getUuid()=");
		builder.append(getUuid());
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append(", countlogin=");
		builder.append(countlogin);
		builder.append("]");
		return builder.toString();
	}

}

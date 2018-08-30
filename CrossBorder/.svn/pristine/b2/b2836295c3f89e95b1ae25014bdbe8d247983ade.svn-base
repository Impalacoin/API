package com.impalapay.airtel.beans.sessionlog;

import java.util.Date;
import java.io.Serializable;

/**
 * Represents a session id that is used to authenticate client interaction
 * <p>
 * Copyright (c) ImpalaPay Ltd.,August 27,2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */

public class SessionLog implements Serializable {

	private String sessionUuid;
	private String accountUuid;
	private Date creationTime;
	private boolean valid;

	/**
	 * 
	 */
	public SessionLog() {
		super();

		sessionUuid = "";
		accountUuid = "";
		creationTime = new Date();
		valid = false;
	}

	/**
	 * 
	 * @return the sessionUuid
	 */

	public String getSessionUuid() {
		return sessionUuid;
	}

	/**
	 * 
	 * @return the accountUuid
	 */
	public String getAccountUuid() {
		return accountUuid;
	}

	/**
	 * 
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return new Date(creationTime.getTime());
	}

	public boolean isValid() {
		return valid;
	}

	/**
	 * 
	 * @param sessionUuid
	 *            the sessionUuid to set
	 */
	public void setSessionUuid(String sessionUuid) {
		this.sessionUuid = sessionUuid;
	}

	/**
	 * 
	 * @param accountUuid
	 *            the accountUuid to set
	 */
	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	/**
	 * 
	 * @param d
	 *            the date to set
	 */
	public void setCreationTime(Date d) {
		if (d != null) {
			creationTime = new Date(d.getTime());
		}
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SessionLog [sessionUuid=");
		builder.append(sessionUuid);
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append(", creationTime=");
		builder.append(creationTime);
		builder.append(", valid=");
		builder.append(valid);
		builder.append("]");
		return builder.toString();
	}
}

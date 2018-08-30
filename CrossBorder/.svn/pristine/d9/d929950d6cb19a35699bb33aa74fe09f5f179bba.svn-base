package com.impalapay.airtel.beans.sessionlog;

import java.util.Date;

import com.impalapay.airtel.beans.StorableBean;

/**
 * Represents a client url where the sessionlog is sent
 * <p>
 * Copyright (c) ImpalaPay Ltd.,August 27,2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */
public class ClientUrl extends StorableBean {

	private String accountUuid;
	private String url;
	private Date dateActive;
	private Date dateInactive;
	private boolean active;

	public ClientUrl() {
		super();
		accountUuid = "";
		url = "";
		dateActive = new Date();
		dateInactive = new Date();
		active = false;
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
	 * @return the client url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * ]
	 * 
	 * @return the date and time when status was set to active
	 */

	public Date getDateActive() {

		return new Date(dateActive.getTime());
	}

	/**
	 * 
	 * @return the date and time when status was set to inactive
	 */
	public Date getDateInactive() {
		return new Date(dateActive.getTime());
	}

	/**
	 * 
	 * @return active
	 */

	public boolean isActive() {
		return active;
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
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @param d
	 *            set date active
	 */
	public void setDateActive(Date d) {

		if (d != null) {
			dateActive = new Date(d.getTime());
		}
	}

	/**
	 * 
	 * @param d
	 *            set date inactive
	 */

	public void setDateInactive(Date d) {

		if (d != null) {
			dateInactive = new Date(d.getTime());
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientUrl [getUuid()=");
		builder.append(getUuid());
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append(", url=");
		builder.append(url);
		builder.append(", dateActive=");
		builder.append(dateActive);
		builder.append(", dateInactive=");
		builder.append(dateInactive);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}

}

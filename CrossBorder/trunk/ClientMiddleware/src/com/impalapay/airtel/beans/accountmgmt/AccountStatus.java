package com.impalapay.airtel.beans.accountmgmt;

import com.impalapay.airtel.beans.StorableBean;

/**
 * The status of an account holder on the system, for example Active & 
 * Suspended.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class AccountStatus extends StorableBean {

	private String description;
	
	
	/**
	 * 
	 */
	public AccountStatus() {
		super();
		description = "";
	}
		

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountStatus [getUuid()=");
		builder.append(getUuid());
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}		
}

/*
** Local Variables:
**   mode: java
**   c-basic-offset: 2
**   tab-width: 2
**   indent-tabs-mode: nil
** End:
**
** ex: set softtabstop=2 tabstop=2 expandtab:
**
*/
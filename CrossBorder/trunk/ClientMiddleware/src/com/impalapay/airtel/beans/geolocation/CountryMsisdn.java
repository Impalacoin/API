package com.impalapay.airtel.beans.geolocation;

import org.apache.commons.lang3.StringUtils;

import com.impalapay.airtel.beans.StorableBean;

import java.util.Date;
/**
 * Represents a collection of misdn for each account in the 17 countries.
 * <p>
 * Copyright (c) ImpalaPay LTD., Feb 14, 2015
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */

public class CountryMsisdn extends StorableBean {
	
	private String countryUuid;
	
	private String accountUuid;
	
	private String msisdn;
	
	private Date creationDate;

	public CountryMsisdn() {
		super();
		
		countryUuid = "";
		accountUuid = "";
		msisdn = "";
		creationDate = new Date();
	}
    
	/**
	 * 
	 * @return
	 */
	public String getCountryUuid() {
		return countryUuid;
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
	public String getMsisdn() {
		return msisdn;
	}
    
	/**
	 * 
	 * @return
	 */
	public Date getCreationDate() {
		return new Date(creationDate.getTime());
	}

    
	/**
	 * 
	 * @param countryUuid
	 */
	public void setCountryUuid(String countryUuid) {
		this.countryUuid = countryUuid;
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
	 * @param msisdn
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
    
	/**
	 * @param date
	 *            the creationDate to set
	 */
	public void setCreationDate(Date date) {
		if (date != null) {
			creationDate = new Date(date.getTime());
		}
	}
    
	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CountryMisdn [getUuid()=");
		builder.append(getUuid());
		builder.append(", countryUuid=");
		builder.append(countryUuid);
		builder.append(", accountUuid=");
		builder.append(accountUuid);
		builder.append(", msisdn=");
		builder.append(msisdn);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}

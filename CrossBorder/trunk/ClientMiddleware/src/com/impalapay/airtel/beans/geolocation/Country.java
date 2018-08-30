package com.impalapay.airtel.beans.geolocation;

import com.impalapay.airtel.beans.StorableBean;

/**
 * represents a country country 
 * <p>
 * Copyright (c) impalapay Ltd., June 24, 2014
 * 
 * @author <a href="mailto:eugenechimita@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class Country extends StorableBean {

	private String name;
	private String countrycode;
	private String currency;
	private String currencycode;
	private String airtelnetwork;
	private String countryremitip;
	private String countrybalanceip;
	private String countryverifyip;

	
	/**
	 * 
	 */
	public Country() {
		super();
		name = "";
	    countrycode = "";
	    currency = "";
	    currencycode = "";
	    airtelnetwork = "";
	    countryremitip = "";
	    countrybalanceip = "";
	    countryverifyip = "";
	    
	}


	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return countryCode
	 */
	public String getCountrycode() {
		return countrycode;
	}


	/**
	 * @param countrycode
	 */
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}


	/**
	 * @return currency
	 */
	public String getCurrency() {
		return currency;
	}


	/**
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}


	/**
	 * @return  currencycode
	 */
	public String getCurrencycode() {
		return currencycode;
	}


	/**
	 * @param currencycode
	 */
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}


	/**
	 * @return airtel network
	 */
	public String getAirtelnetwork() {
		return airtelnetwork;
	}


	/**
	 * @param airtelnetwork
	 */
	public void setAirtelnetwork(String airtelnetwork) {
		this.airtelnetwork = airtelnetwork;
	}

    /**
     * 
     * @return countryremitip
     */
	public String getCountryremitip() {
		return countryremitip;
	}

    /**
     * 
     * @param countryremitip
     */
	public void setCountryremitip(String countryremitip) {
		this.countryremitip = countryremitip;
	}

    /**
     * 
     * @return countrybalanceip
     */
	public String getCountrybalanceip() {
		return countrybalanceip;
	}

    /**
     * 
     * @param countrybalanceip
     */
	public void setCountrybalanceip(String countrybalanceip) {
		this.countrybalanceip = countrybalanceip;
	}
    
    /**
     * 
     * @return
     */
	public String getCountryverifyip() {
		return countryverifyip;
	}

    /**
     * 
     * @param countryverifyip
     */
	public void setCountryverifyip(String countryverifyip) {
		this.countryverifyip = countryverifyip;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Country [getUuid()=");
		builder.append(getUuid());
		builder.append(", name=");
		builder.append(name);
		builder.append(", countrycode=");
		builder.append(countrycode);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", currencycode=");
		builder.append(currencycode);
		builder.append(", airtelnetwork=");
		builder.append(airtelnetwork);
		builder.append(", countryremitip=");
		builder.append(countryremitip);
		builder.append(", countrybalanceip=");
		builder.append(countrybalanceip);
		builder.append(", countryverifyip=");
		builder.append(countryverifyip);
		builder.append("]");
		return builder.toString();
	}


}

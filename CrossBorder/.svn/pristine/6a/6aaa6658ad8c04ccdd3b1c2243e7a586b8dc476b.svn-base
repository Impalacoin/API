package com.impalapay.airtel.accountmgmt.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.impalapay.airtel.beans.geolocation.Country;

/**
 * This object holds data that is displayed on the portal once a user has logged
 * in. It is meant to be cached while a session is still active to avoid
 * expensive computations with the RDBMS.
 * <p>
 *
 * Copyright (c) Impalapay Ltd., August 15, 2014
 *
 * @author <a href="mailto:eugene@impalapay">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class SessionStatistics implements Serializable {

	private int transactionCountTotal;

	private int transactionCountSuccess;

	private int transactionByUuidCount;
	
	private int transactionByMsisdnCount;
	
	 // This is used to keep a count of all Transaction against a country(status is "Accepted for delivery")
    private Map<Country, Integer> countryTransactionCountTotal;
    
     // This is used to keep a count of Transaction against a Country
    // for Transaction requests that have been successful(status is "Transaction status success")
    private Map<Country, Integer> countryTransactionCountSuccess;
    
    
        
    // This is used to keep a value of Transaction amount against a Country
    // for Transaction requests that have been successful(status is "Transaction status success")
    private Map<Country, Double> countryTransactionAmountSuccess;
    
    
    private Map<Country, Double> countryTransactionAmountDays;

    

    
    // These are used to keep a  value of Transaction amount against Country on particular days
    // They are used to generate bar charts
    private Map<String, Map<Country, Double>> countryTransactionAmountDay;

	/**
	 * Default constructor,intializes objects
	 *
	 */
	public SessionStatistics() {
		countryTransactionCountTotal = new HashMap<>();
		countryTransactionCountSuccess = new HashMap<>();
		countryTransactionAmountSuccess = new HashMap<>();
		countryTransactionAmountDay = new HashMap<>();
		countryTransactionAmountDays = new HashMap<>();

	}

	public int getTransactionCountTotal(){
		
		return transactionCountTotal;
		
	}

	public int getTransactionCountSuccess() {
		
		return transactionCountSuccess;
	}

	public int getTransactionByUuidCount() {
		return transactionByUuidCount;
	}

	public int getTransactionByMsisdnCount() {
		return transactionByMsisdnCount;
	}

	public void setTransactionCountTotal(int transactionCountTotal) {
		this.transactionCountTotal = transactionCountTotal;
	}

	public void setTransactionCountSuccess(int transactionCountSuccess) {
		this.transactionCountSuccess = transactionCountSuccess;
	}

	public void setTransactionByUuidCount(int transactionByUuidCount) {
		this.transactionByUuidCount = transactionByUuidCount;
	}

	public void setTransactionByMsisdnCount(int transactionByMsisdnCount) {
		this.transactionByMsisdnCount = transactionByMsisdnCount;
	}
    
	/**
	 * 
	 * @return
	 */
	public Map<Country, Integer> getCountryTransactionCountTotal() {
		return countryTransactionCountTotal;
	}
	
	/**
     * add transaction activity of {@link Country}.
     *
     * @param country the receiver country
     * @param count total number of Transaction requests
     */
    public void addCountryTransactionCountTotal(Country country, int count) {
        countryTransactionCountTotal.put(country, new Integer(count));
    }
    
    /**
     * 
     * @return
     */
	public Map<Country, Integer> getCountryTransactionCountSuccess() {
		return countryTransactionCountSuccess;
	}
	
	/**
     * add transaction activity of {@link Country}.
     *
     * @param country the receiver country
     * @param count number of transaction requests that have been successful
     */
    public void addCountryTransactionCountSuccess(Country country, int count) {
    	countryTransactionCountSuccess.put(country, new Integer(count));
    }
    
    /**
     * 
     * @return
     */
	public Map<Country, Double> getCountryTransactionAmountSuccess() {
		return countryTransactionAmountSuccess;
	}
    
	/**
     * add transaction amount of {@link Country}.
     *
     * @param country the receiver country
     * @param amount the value of Transaction requests that have been successful
     */
    public void addCountryTransactionAmountSuccess(Country country, double amount) {
    	countryTransactionAmountSuccess.put(country, new Double(amount));
    }
    
    /**
     * 
     * @return
     */
	public Map<String, Map<Country, Double>> getCountryTransactionAmountDay() {
		return countryTransactionAmountDay;
	}
	
	 /**
     * 
     * @return
     */
	public Map<Country, Double> getCountryTransactionAmountDays() {
		return countryTransactionAmountDays;
	}
    
	 /**
    *
    * @param day
    * @param country
    * @param amount 
    */
   public void addCountryTransactionAmountDay(String day, Country country, int amount) {
       Map<Country, Double> countryMap = countryTransactionAmountDay.get(day);

       if (countryMap == null) {
    	   countryMap = new HashMap<>();
       }

       countryMap.put(country, new Double(amount));
       countryTransactionAmountDay.put(day, countryMap);
   }
   
   
   public void addCountryTransactionAmountDays(Country country, double amount) {
       countryTransactionAmountDays.put(country, amount);
   }
}

/*
 * * Local Variables:* mode: java* c-basic-offset: 2* tab-width: 2*
 * indent-tabs-mode: nil* End:** ex: set softtabstop=2 tabstop=2 expandtab:*
 */
package com.impalapay.airtel.persistence.geolocation;

import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.geolocation.CountryMsisdn;

public interface AirtelCountryMsisdnDAO {
	/**
	 * Retrieve the countrymsisdn corresponding to the uuid.
	 * 
	 * @param uuid
	 * @return  countrymsisdn
	 */
    
	public CountryMsisdn getCountryMsisdn(String uuid);
	
	
	/**
	 * 
	 * @param account
	 * @return a list of msisdn belonging to this account by countries
	 */
	
	public List<CountryMsisdn>getCountryMsisdn(Account account);
	

	/**
	 * 
	 * @param account
	 * @param countryMsisdn
	 * @return 
	 */
	
	public CountryMsisdn getCountryMsisdn(Account account,Country country);
	
	
	/**
	 * @param countryMsisdn
	 * @return whether the action was successful or not
	 */
	public boolean putCountryMsisdn(CountryMsisdn countrymsisdn);

    /**
     * 
     * @param uuid
     * @param countrymsisdn
     * @return
     */
	boolean updateCountryMsisdn(String uuid, CountryMsisdn countrymsisdn);
	
	
	
}

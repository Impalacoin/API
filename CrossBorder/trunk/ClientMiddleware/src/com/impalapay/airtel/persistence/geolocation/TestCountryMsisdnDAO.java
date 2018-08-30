package com.impalapay.airtel.persistence.geolocation;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.geolocation.CountryMsisdn;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

/**
 * Tests the  com.impalapay.airtel.persistence.country.CountryDAO
 * <p>
 * Copyright (c) impalapay  Ltd., june 24, 2014  
 * 
 * @author <a href="mailto:eugenechimita@impalapay.com">Eugene Chimita</a>
 * @author <a href="mailto:michael@impalapay.com">Michael Wakahe</a>
 * 
 */
public class TestCountryMsisdnDAO {

	final String DB_NAME = "airteldb2";
    final String DB_HOST = "localhost"; 
    final String DB_USERNAME = "airtel2"; 
    final String DB_PASSWD = "ThejKoyb3";
    final int DB_PORT = 5432;
    
    final String Account_UUID ="9756f889-811a-4a94-b13d-1c66c7655a7f";
    final String UUID = "cbe6063a-66ae-4ee1-abb7-0e35a5814821";
    final String Country_MSISDN="25473348575";
    final String Country_UUID = "1e0ea2bfe4294e78b63fdd1b27ea3b1d";
    
    final String Account_UUID2 ="9756f889-811a-4a94-b13d-1c66c7655a7f";
    final String UUID2 = "3ec83cb1-b030-44be-a8bc-0df73d0628bf";
    final String Country_MSISDN2="25473348678";
    final String Country_UUID2 = "5db5fa02790e4ee0a8d7a538b4df820a";
   
    final int Country_COUNT = 17;
            
    private CountryMsisdnDAO storage;
    
    	

	/**
	 * Test method for  com.impalapay.airtel.persistence.country.CountryDAO#getCountry(java.lang.String).
	 */
    @Ignore
	@Test
	public void testCountryMsisdnString() {
		storage = new CountryMsisdnDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
        
        CountryMsisdn countrymsisdn = storage.getCountryMsisdn(UUID);
        assertEquals(countrymsisdn.getUuid(),UUID);
        assertEquals(countrymsisdn.getAccountUuid(), Account_UUID);
        assertEquals(countrymsisdn.getCountryUuid(), Country_UUID);
        assertEquals(countrymsisdn.getMsisdn(), Country_MSISDN);
        
          
	}

	
	/**
	 * Test method for  com.impalapay.airtel.persistence.geolocation.CountryMsisdnDAO#getCountryMsisdn(com.impalapay.airtel.beans.accountmgmt.Account)
	 */
	@Test
	@Ignore
	public void testGetAccountMsisdns() {
		storage = new CountryMsisdnDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
        Account testaccount = new Account();
        testaccount.setUuid(Account_UUID);
        List<CountryMsisdn> list = storage.getCountryMsisdn(testaccount);
        assertEquals(list.size(), Country_COUNT);
        
        System.out.println(list);
	}
	/**
	 * 
	 */
	@Ignore
	@Test
	public void testGetAccountCountryMsisdn(){
		storage = new CountryMsisdnDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		Account testaccount2 = new Account();
		
		testaccount2.setUuid(Account_UUID);
		
		Country testcountry = new Country();
		
		testcountry.setUuid(Country_UUID);
		
		CountryMsisdn countryMsisdn = storage.getCountryMsisdn(testaccount2, testcountry);
		
		assertEquals(countryMsisdn.getMsisdn(), Country_MSISDN);
		//System.out.println(countryMsisdn);
	}
	
	/**
	 * 
	 */
	@Ignore
	@Test
	public void testPutCountryMsisdn(){
		
		storage = new CountryMsisdnDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		CountryMsisdn countrymsisdn = new CountryMsisdn();
		
		countrymsisdn.setUuid(UUID2);
		countrymsisdn.setCountryUuid(Country_UUID2);
		countrymsisdn.setAccountUuid(Account_UUID2);
		countrymsisdn.setMsisdn(Country_MSISDN2);
		
		assertTrue(storage.putCountryMsisdn(countrymsisdn));
		
		
		
	}
	/**
	 * 
	 */
	//@Ignore
	@Test
	public void testUpdateCountryMsisdn(){
		
		storage = new CountryMsisdnDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		CountryMsisdn countrymsisdn = new CountryMsisdn();
		
		countrymsisdn.setUuid(UUID2);
		countrymsisdn.setCountryUuid(Country_UUID2);
		countrymsisdn.setAccountUuid(Account_UUID2);
		countrymsisdn.setMsisdn("254715290375");
		
		assertTrue(storage.updateCountryMsisdn(UUID2,countrymsisdn));
		
	}
}


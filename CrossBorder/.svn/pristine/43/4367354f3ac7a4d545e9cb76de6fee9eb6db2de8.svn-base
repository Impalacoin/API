package com.impalapay.airtel.persistence.geolocation;

import com.impalapay.airtel.beans.geolocation.Country;

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
public class TestCountryDAO {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
    final int DB_PORT = 5432;
    
    final String Country_UUID = "1e0ea2bfe4294e78b63fdd1b27ea3b1d";
    final String Country_NAME = "Burkina Faso";
    final int Country_COUNT = 17;
            
    private CountryDAO storage;
    
    	

	/**
	 * Test method for  com.impalapay.airtel.persistence.country.CountryDAO#getCountry(java.lang.String).
	 */
    @Ignore
	@Test
	public void testCountryString() {
		storage = new CountryDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
        
        Country country = storage.getCountry(Country_UUID);
        assertEquals(country.getUuid(), Country_UUID);
        assertEquals(country.getName(), Country_NAME);
          
	}

	
	/**
	 * Test method for  com.impalapay.airtel.persistence.country.CountryDAO#getAllCountries()
	 */
	@Test
	//@Ignore
	public void testGetAllCountries() {
		storage = new CountryDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
        
        List<Country> list = storage.getAllCountries();
        assertEquals(list.size(), Country_COUNT);
        
	}
}


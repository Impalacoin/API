package com.impalapay.airtel.persistence.util;

/**
 * Test for {@link CountUtils}
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Sep 23, 2013
 *
 * @author <a href="mailto:anthonym@shujaa.co.ke">Anthony Wafula</a>
 * @version %I%, %G%
 *
 */
import static org.junit.Assert.assertEquals;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.transaction.TransactionStatus;
//import com.impalapay.airtel.util.randomgenerator.PopulateTopup;







import org.joda.time.DateMidnight;
import org.joda.time.Hours;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

public class TestCountUtils {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
	final int DB_PORT = 5432;
	
    final String DEMO_ACCOUNT_UUID = "9756f889-811a-4a94-b13d-1c66c7655a7f";
    final String DEMO_ACCOUNT_UUID2 = "48e249c2-856a-4269-820f-7b72c76b4957";
    
    final String ACCOUNT_USERNAME ="demo";
    final String SHUJAA_SOLUTIONS = "9756f889-811a-4a94-b13d-1c66c7655a7f";
    final String COUNTRY_UUID = "5db5fa02790e4ee0a8d7a538b4df820a";
    final String STATUS_UUID = "3b6edb35-654d-4049-b7ea-0f1db29c6e77";
    final String COUNTRY_UUID2 = "193a12b854234e30aa43090d8b4ee810";
    final String STATUS_UUID2 = "4a991e99-ffa2-4fcd-91ed-27e6ce078832";
    
   // private CountUtils countUtils = new CountUtils(DB_NAME, DB_HOST,
            //DB_USERNAME, DB_PASSWD, DB_PORT);
    Account account = new Account();
    TransactionStatus status = new TransactionStatus();
   
    Country country = new Country();
    
    private  CountUtils storage;

  /**
     * Test method for  CountUtils#getAllTransactionCount(com.impalapay.airtel.beans.accountmgmt.Account)
     * 
     */
   
    @Test
    @Ignore
    public void testGetAllTransactionCountString(){
    	
    	account.setUuid(DEMO_ACCOUNT_UUID);
    	
    	storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
    	System.out.println("transaction count is:" + storage.getTransactionCount(account));
    }
    @Test
    @Ignore
	public void testAllITransactionCountString() {
		storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		System.out.println("transaction count is: " + storage.getAllTransactionCount(ACCOUNT_USERNAME));
	}
    @Test
    @Ignore
	public void TransactionCountString() {
		storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		System.out.println("transaction count is: " + storage.getAllTransactionCount(SHUJAA_SOLUTIONS));
	}
    /**
     * Test method for {@link CountUtils#getTransactionCount(com.impalapay.airtel.beans.accountmgmt.Account, com.impalapay.airtel.beans.topup.TransactionStatus)
     * }
     */
    @Test
    @Ignore
    public void testGetTransactionCountAccountTransactionStatus() {
        int count = 1000;

        account.setUuid(SHUJAA_SOLUTIONS);
        status.setUuid(STATUS_UUID);
        
        storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);

        assertEquals(storage.getTransactionCount(account, status), count);
    }

    /**
     * Test method for {@link CountUtils#getTransactionCount(com.impalapay.airtel.beans.accountmgmt.Account, com.impalapay.airtel.beans.geolocation.Country)
     * }
     */
    @Test
    @Ignore
    public void testGetTransactionCountAccountCountry() {
        int count = 118;

        account.setUuid(SHUJAA_SOLUTIONS);
        country.setUuid(COUNTRY_UUID);
        
        storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);

        assertEquals(storage.getTransactionCount(account, country), count);

    }

   /**
     * Test method for {@link CountUtils#getTransactionCount(com.impalapay.airtel.beans.accountmgmt.Account, com.impalapay.airtel.beans.geolocation.Country, com.impalapay.airtel.beans.transaction.TransactionStatus)
     * }
     */
    //@Ignore
    @Test
    public void testGetTransactionCountAccountCountryTransactionStatus() {
        int count = 65;
        account.setUuid(SHUJAA_SOLUTIONS);
        country.setUuid(COUNTRY_UUID2);
        status.setUuid(STATUS_UUID2);
        
        storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);

       assertEquals(storage.getTransactionCount(account, country, status), count);
       
       System.out.println(storage.getTransactionCount(account, country, status));

    }

    /**
     * Test method for {@link CountUtils#getTopupCount(com.impalapay.airtel.beans.accountmgmt.Account, com.impalapay.airtel.beans.network.Network, com.impalapay.airtel.beans.topup.TopupStatus, java.util.Date, java.util.Date)
     * }
     */
    @Ignore
    @Test
    public void testGetTransactionCountAccountCountryTransactionStatusDateDate() {

        DateMidnight midnight = new DateMidnight(2014, 8, 17);

        Date startTime = new Date(midnight.getMillis());

        DateMidnight midnight2 = midnight.plus(Hours.hours(24));
        Date endTime = new Date(midnight2.getMillis());

        int count = 17;

        account.setUuid(SHUJAA_SOLUTIONS);
        country.setUuid(COUNTRY_UUID);
        //status.setUuid(STATUS_UUID);
        storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);

        assertEquals(storage.getTransactionCount(account, country,startTime, endTime), count);

    }

    /**
     * Test method for  CountUtils#getTopupByUuidCount(com.impalapay.airtel.beans.accountmgmt.Account, java.lang.String)
     * 
     */
    @Test
    @Ignore
    public void TestGetTransactionByUuidCount() {
        String uuid = "61797229-eb8b-4f84-bb15-a0410dc5d33b";
        account.setUuid(DEMO_ACCOUNT_UUID);

        int expectedSize = storage.getTransactionByUuidCount(account, uuid);

        int actualSize = 1;
        assertEquals(expectedSize, actualSize);
    }

    /**
     * Test method for {@link CountUtils#RecipientPhone(com.impalapay.airtel.beans.accountmgmt.Account, java.lang.String)
     * }
     */ 
    @Test
    @Ignore
    public void TestGetTopupByRecipientPhoneCount() {
        String phone = "444-499-3303";
        account.setUuid(DEMO_ACCOUNT_UUID);
        
        storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
        
        int expectedSize = storage.getTransactionByRecipientmobileCount(account, phone);

        int actualSize = 1;
        assertEquals(expectedSize, actualSize);

    }
   
    
    /**
     * Test method for {@link CountUtils#getTransactionAmount(com.impalapay.airtel.beans.accountmgmt.Account, com.impalapay.airtel.beans.geolocation.Country, com.impalapay.airtel.beans.transaction.TransactionStatus) 
     * }
     */
    @Test
    @Ignore
    public void TestGetTransactionAmount() {
        
        account.setUuid("9756f889-811a-4a94-b13d-1c66c7655a7f");
        country.setUuid("193a12b854234e30aa43090d8b4ee810");
        status.setUuid("3b6edb35-654d-4049-b7ea-0f1db29c6e77");
        
        storage = new CountUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);

        double expectedAmount = storage.getTransactionAmount(account,country,status);

        //int actualAmount = 19798519;
        
       // assertEquals(expectedAmount, actualAmount);
        
        System.out.println(expectedAmount);

    }
    
}

package com.impalapay.airtel.persistence.sessionlog;

import java.util.Date;

import com.impalapay.airtel.beans.sessionlog.ClientUrl;
import com.impalapay.airtel.beans.accountmgmt.Account;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test our persistence implementation for {@link ClientURL}
 * <p>
 * Copyright (c) impalaPay Ltd.August 30, 2014
 * 
 * @author <a href="mailto:eugenechimita@impalapay.com">Eugene Chimita</a>
 * 
 */
public class TestClientURLDAO {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost";
	final String DB_USERNAME = "airtel";
	final String DB_PASSWD = "LignuAv7";
	final int DB_PORT = 5432;
    
    // For time conversions to and from Unix time, use http://www.epochconverter.com
    // The format used here is: YYYY-MM-DD HH:MM:SS
    
    final String CLIENTURL_UUID = "8ea752c927d44d9b862244821c56f6f9",
    		CLIENTURL_UUID2 = "88b5af491d6b431fbfd74d95ab9c54b5";
    final String ACCOUNT_UUID = "9756f889-811a-4a94-b13d-1c66c7655a7f",
    		ACCOUNT_UUID2 = "91fc8aae-cb76-4c64-ac45-48448fb5673f";
    final String CLIENTURL_URL = "https://localhost/TestHttpPost.php",
    		CLIENTURL_URL2 = "http://www.examples.com/implement.php";     
    final Date CLIENTURL_ACTIVATIONDATE = 
    		new Date(new Long("1393157127000").longValue());  // 2014-02-23 15:05:27 EAT
    final boolean CLIENTURL_ISACTIVE = true, CLIENTURL_ISACTIVE2 = false;
    
    final String CLIENTURL_NEW_UUID = "91160b2eb346a88d01095ab9c54b5421c5";
    final String CLIENTURL_NEW_URL = "http://www.cnn.com/cgi.php";
    final Date CLIENTURL_NEW_ACTIVATIONDATE = 
    		new Date(new Long("1367597206000").longValue());  // May 03 2013 19:06:46 EAT 
    final boolean CLIENTURL_NEW_ISACTIVE = false;
        
    private ClientURLDAO storage;
    
    
	/**
	 * Test method for {@link mobi.tawi.airtimegw.persistence.session.ClientURLDAO#putClientUrl(mobi.tawi.airtimegw.beans.session.ClientURL)}.
	 */
    @Ignore
	@Test
	public void testPutClientUrl() {
		storage = new ClientURLDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		ClientUrl c = new ClientUrl();
		c.setUuid(CLIENTURL_NEW_UUID);
		c.setAccountUuid(ACCOUNT_UUID);
		c.setUrl(CLIENTURL_NEW_URL);
		c.setDateActive(CLIENTURL_NEW_ACTIVATIONDATE);
		c.setActive(CLIENTURL_NEW_ISACTIVE);
				
		assertTrue(storage.putClientUrl(c));
	}
	

	/**
	 * Test method for {@link com.impalapay.airtel.persistence.sessionlog.ClientURLDAO#isActive(java.lang.String)}.
	 */
	//@Ignore
	@Test
	public void testIsActive() {
		storage = new ClientURLDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		assertEquals(storage.isActive(CLIENTURL_UUID), CLIENTURL_ISACTIVE);
	}
	

	/**
	 * Test method for {@link com.impalapay.airtel.persistence.sessionlog.ClientURLDAO#getClientUrl(com.impalapay.airtel.beans.accountmgmt.Account)}.
	 */
  
	@Test
	public void testGetClientUrl() {
		storage = new ClientURLDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		Account acc = new Account();
		acc.setUuid(ACCOUNT_UUID);
		
		ClientUrl c = storage.getClientUrl(acc);
		assertEquals(c.getAccountUuid(), ACCOUNT_UUID);
		assertEquals(c.getUrl(), CLIENTURL_URL);
		assertEquals(c.getDateActive(), CLIENTURL_ACTIVATIONDATE);
		assertEquals(c.getUrl(), CLIENTURL_URL);
		assertEquals(c.isActive(), CLIENTURL_ISACTIVE);
	}
	
	
	/**
	 * Test method for {@link com.impalapay.airtel.persistence.sessionlog.ClientURLDAO#deactivate(com.impalapay.airtel.beans.sessionlog.ClientUrl)}.
	 */
	@Ignore
	@Test
	public void testDeactivate() {
		storage = new ClientURLDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		Account acc = new Account();
		acc.setUuid(ACCOUNT_UUID);
		
		ClientUrl c = storage.getClientUrl(acc);
		c.setActive(false);
		
		assertTrue(storage.deactivate(c));		
	}
}


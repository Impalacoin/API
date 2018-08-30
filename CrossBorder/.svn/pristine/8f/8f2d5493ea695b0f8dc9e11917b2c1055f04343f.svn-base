package com.impalapay.airtel.persistence.sessionlog;

import static org.junit.Assert.*;

import java.util.Date;

import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.sessionlog.SessionLog;

public class TestSessionLogDAO {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost";
	final String DB_USERNAME = "airtel";
	final String DB_PASSWD = "LignuAv7";
	final int DB_PORT = 5432;
 
    final String SESSIONLOG_ACCOUNTUUID_DEMO = "9756f889-811a-4a94-b13d-1c66c7655a7f";
    final String SESSIONLOG_ACCOUNTUUID_KAKUZI = "48e249c2-856a-4269-820f-7b72c76b4957";
        
    final String SESSIONLOG_UUID_NEW = "c089e01983d744fab21ae34982f174e0";
    final Date SESSIONLOG_DATE_NEW = new Date(new Long("1367597206000").longValue()); // Fri May 03 19:06:46 EAT 2013
    final boolean SESSIONLOG_VALIDITY_NEW = true;
    
    final String SESSIONLOG_UUID_INVALID = "7691160b2eb346a88d0100249dba8ae6";
    
    // This refers to a particular Session Id
    final String SESSIONLOG_UUID_VALID = "c089e01983d744fab21ae34982f174e0";
    final Date SESSIONLOG_DATE_VALID = new Date(new Long("1360065927000").longValue()); // 2013-02-05 15:05:27 (Feb 5th)
    
    private SessionLogDAO storage;
    
    
	/**
	 * Test method for {@link com.impalapay.airtel.persistence.sessionlog.SessionLogDAO#putSessionLog(com.impalapay.airtel.beans.sessionlog.SessionLog)}.
	 */
    //@Ignore
	@Test
	public void testPutSessionLog() {
		storage = new SessionLogDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		SessionLog s = new SessionLog();
	      
	    s.setSessionUuid(SESSIONLOG_UUID_NEW);
	    s.setAccountUuid(SESSIONLOG_ACCOUNTUUID_DEMO);
	    s.setCreationTime(SESSIONLOG_DATE_NEW);
	    s.setValid(SESSIONLOG_VALIDITY_NEW);
	     
	    assertTrue(storage.putSessionLog(s));
	}
	

	/**
	 * Test method for {@link com.impalapay.airtel.persistence.sessionlog.SessionLogDAO#isValid(String)}.
	 */
    @Ignore
	@Test
	public void testIsValid() {
		storage = new SessionLogDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		assertEquals(storage.isValid(SESSIONLOG_UUID_VALID), true);
		assertEquals(storage.isValid(SESSIONLOG_UUID_INVALID), false);		
	}
	

	/**
	 * Test method for {@link com.impalapay.airtel.persistence.sessionlog.SessionLogDAO#invalidate(com.impalapay.airtel.beans.sessionlog.SessionLog)}.
	 */
    @Ignore
	@Test
	public void testInvalidate() {
		storage = new SessionLogDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		SessionLog s = new SessionLog();	      
	    s.setSessionUuid(SESSIONLOG_UUID_VALID);
	    
	    assertTrue(storage.invalidate(s));
	    assertEquals(storage.isValid(SESSIONLOG_UUID_VALID), false);	
	}

	
	/**
	 * Test method for {@link com.impalapay.airtel.persistence.sessionlog.SessionLogDAO#getValidSessionLog(com.impalapay.airtel.beans.accountmgmt.Account)}.
	 */
    @Ignore
	@Test
	public void testGetValidSessionLog() {
		storage = new SessionLogDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		Account acc = new Account();
		acc.setUuid(SESSIONLOG_ACCOUNTUUID_DEMO);
		
		SessionLog s = storage.getValidSessionLog(acc);
		assertEquals(s.getAccountUuid(), SESSIONLOG_ACCOUNTUUID_DEMO);
		assertEquals(s.getSessionUuid(), SESSIONLOG_UUID_VALID);
		assertEquals(s.getCreationTime(), SESSIONLOG_DATE_VALID);
		
		acc = new Account();
		acc.setUuid(SESSIONLOG_ACCOUNTUUID_KAKUZI);		
		s = storage.getValidSessionLog(acc);		
		assertNull(s);
	}

	
	/**
	 * Test method for {@link com.impalapay.airtel.persistence.sessionlog.SessionLogDAO#expireSessionLogs(java.util.Date)}.
	 */
	@Ignore
	@Test
	public void testExpireSessionLogs() {
		storage = new SessionLogDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		DateTime dateTime = new DateTime().minusMinutes(10);		
		Date date = dateTime.toGregorianCalendar().getTime();
		
		storage.expireSessionLogs(date);
	}
}
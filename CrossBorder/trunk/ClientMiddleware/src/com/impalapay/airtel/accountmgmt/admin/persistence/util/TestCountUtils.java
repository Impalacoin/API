package com.impalapay.airtel.accountmgmt.admin.persistence.util;

import static org.junit.Assert.assertEquals;

import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.transaction.TransactionStatus;
//import ke.co.shujaa.airtimegw.server.util.randomgenerator.PopulateTopup;


import org.junit.Ignore;
import org.junit.Test;

public class TestCountUtils {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost";
	final String DB_USERNAME = "airtel";
	final String DB_PASSWD = "LignuAv7";
	final int DB_PORT = 5432;
	private CountUtils countUtils = new CountUtils(DB_NAME, DB_HOST,
			DB_USERNAME, DB_PASSWD, DB_PORT);
	TransactionStatus status = new TransactionStatus();
	Country country = new Country();

	/**
	 * Test method for
	 * {@link CountUtils#getAllTransactionByRecipientMsisdnCount(java.lang.String) 
     * 
	 */
	@Ignore
	@Test
	public void TestGetAllTopupByMsisdnCount() {
		String msisdn = "444-163-1113";

		int expectedCount = countUtils
				.getAllTransactionByRecipientMsisdnCount(msisdn);

		int actualCount = 1;

		assertEquals(expectedCount, actualCount);
	}

	/**
	 * Test method for
	 * {@link CountUtils#getAllTransactionByUuidCount(java.lang.String) 
     * 
	 */
	@Ignore
	@Test
	public void TestGetAllTransactionByUuidCount() {
		String uuid = "61797229-eb8b-4f84-bb15-a0410dc5d33b";

		int expectedCount = countUtils.getAllTransactionByUuidCount(uuid);

		int actualCount = 2;

		assertEquals(expectedCount, actualCount);
	}
    @Ignore
	@Test
	public void testGetTransactionCount() {
		int count = 10000;

		assertEquals(countUtils.getAllTransactionCount(), count);

	}
    @Ignore
	@Test
	public void testGetTransactionCountByReceivercountry(){
		Country veve = new Country();
		
		veve.setUuid("ed0cd3cd0f5246ef83f90721f8d38105");
		
		System.out.println(countUtils.getAllTransactionByReceiverCountry(veve));
		
	}
}
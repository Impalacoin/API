package com.impalapay.airtel.persistence.util;

import java.io.File;

import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link DbFileUtils}
 * <p>
 * Copyright (c) ImpalaPay Ltd., June 30, 2014  
 * 
 * @author <a href="mailto:michael@impalapay.com">Michael Wakahe</a>
 * @version %I%, %G%
 * 
 */
public class TestDbFileUtils {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
    final int DB_PORT = 5432;

    final String SQL_QUERY = "SELECT * FROM transaction WHERE accountuuid ='9756f889-811a-4a94-b13d-1c66c7655a7f';";
    
    final String query2 = "SELECT uuid,sourceCountrycode,senderName,recipientMobile,senderToken,currencyCode,recipientcountryUuid,referenceNumber FROM transaction WHERE accountuuid ='9756f889-811a-4a94-b13d-1c66c7655a7f';";
    	
        
    final String CSV_FILE = "/tmp/airtel/Transaction.csv",
    		CSV_FILE2 = "/tmp/airtel2/Transaction2.csv";
    		
    private DbFileUtils dbFileUtils;
    
    
	/**
	 * Test method for  com.impalapay.airtel.persistence.util.DbFileUtils
	 * sqlResultToCSV(java.lang.String, java.lang.String)
	 */
	@Test
	public void testSqlResultToCSV() {
		String username ="9756f889-811a-4a94-b13d-1c66c7655a7f";
		
		dbFileUtils = new DbFileUtils(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		StringBuffer query = new StringBuffer("SELECT transaction.uuid,transaction.sourceCountrycode,transaction.senderName,transaction.recipientMobile, ")
		.append("transaction.senderToken,transaction.currencyCode,transaction.recipientcountryUuid,transaction.referenceNumber,")
		.append("transaction.transactionStatusUuid,transaction.clientTime ")
		.append("FROM transaction ")
		.append("INNER JOIN country ON recipientcountryUuid=country.uuid ")
		.append("INNER JOIN transactionStatus ON transaction.transactionStatusUuid=transactionStatus.uuid ")
		.append("WHERE accountUuid = '")
		.append(username)
		.append("';");
		
		String SQL_QUERY2 = query.toString();
		
		FileUtils.deleteQuietly(new File(CSV_FILE));
		FileUtils.deleteQuietly(new File(CSV_FILE2));
		
		assertTrue(dbFileUtils.sqlResultToCSV(SQL_QUERY2, CSV_FILE2, '|'));
		
	}

}

/*
** Local Variables:
**   mode: java
**   c-basic-offset: 2
**   tab-width: 2
**   indent-tabs-mode: nil
** End:
**
** ex: set softtabstop=2 tabstop=2 expandtab:
**
*/
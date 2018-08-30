package com.impalapay.airtel.accountmgmt.admin.pagination;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.impalapay.airtel.accountmgmt.pagination.TransactionPage;
import com.impalapay.airtel.beans.transaction.Transaction;

public class TestSearchUuidPaginator {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
	final String USERNAME ="demo";
	final int DB_PORT = 5432;
	final String UUID ="61797229-eb8b-4f84-bb15-a0410dc5d33b";
	
	
	
	

	/**
	 * Test method for getting firstpage
	 */
	@Test
	public void testGetFirstPage() {
		SearchUuidPaginator searchuuidPaginator = new SearchUuidPaginator(UUID, DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		TransactionPage firstPage = searchuuidPaginator.getFirstPage();
		List<Transaction> transactionList = firstPage.getContents();
		//assertEquals(transactionList.size(), TransactionPaginator.PAGESIZE);
		
		for(Transaction s : transactionList) {
			System.out.println(s);
		}
	}
	
	

}

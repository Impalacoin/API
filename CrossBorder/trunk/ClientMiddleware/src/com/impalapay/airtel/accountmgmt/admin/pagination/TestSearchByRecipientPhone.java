package com.impalapay.airtel.accountmgmt.admin.pagination;

import java.util.List;

import org.junit.Test;

import com.impalapay.airtel.accountmgmt.pagination.TransactionPage;
import com.impalapay.airtel.beans.transaction.Transaction;

public class TestSearchByRecipientPhone {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
	final String USERNAME ="mhits";
	final int DB_PORT = 5432;
	final String PHONE ="254-715-266-678";
	
	
	
	
	

	/**
	 * Test method for getting firstpage
	 */
	@Test
	public void testGetFirstPage() {
		SearchRecipientMobilePaginator searchuuidPaginator = new SearchRecipientMobilePaginator(PHONE, DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		TransactionPage firstPage = searchuuidPaginator.getFirstPage();
		List<Transaction> transactionList = firstPage.getContents();
		//assertEquals(transactionList.size(), TransactionPaginator.PAGESIZE);
		
		for(Transaction s : transactionList) {
			System.out.println(s);
		}
	}
}

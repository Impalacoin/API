package com.impalapay.airtel.accountmgmt.pagination;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;




import org.junit.Ignore;
import org.junit.Test;

import com.impalapay.airtel.beans.transaction.Transaction;

public class TestTransactionPaginator {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
	final String USERNAME ="demo";
	final int DB_PORT = 5432;
	
	
	
	

	/**
	 * Test method for getting firstpage
	 */
	@Test
	public void testGetFirstPage() {
		TransactionPaginator transactionPaginator = new TransactionPaginator(USERNAME, DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		TransactionPage firstPage = transactionPaginator.getFirstPage();
		List<Transaction> transactionList = firstPage.getContents();
		assertEquals(transactionList.size(), TransactionPaginator.PAGESIZE);
		
		for(Transaction s : transactionList) {
			System.out.println(s);
		}
	}
	/**
	 * Test method for  getting lastpage
	 */
	@Test
	public void testGetLastPage() {
		TransactionPaginator transactionPaginator = new TransactionPaginator(USERNAME, DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		
		TransactionPage firstPage = transactionPaginator.getLastPage();
		List<Transaction> transactionList = firstPage.getContents();
				
		for(Transaction s : transactionList) {
			System.out.println(s);
		}
	}

	@Test
	public void testGetNextPage() {
		TransactionPaginator paginator = new TransactionPaginator(USERNAME, DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		int currentPageNum = 3;
		
		TransactionPage page = new TransactionPage(currentPageNum, 100, TransactionPaginator.PAGESIZE, new ArrayList<Transaction>());
		TransactionPage nextPage = paginator.getNextPage(page);
		List<Transaction> transactionList = nextPage.getContents();
		
		assertEquals(transactionList .size(), TransactionPaginator.PAGESIZE);
		
		for(Transaction s : transactionList ) {
			System.out.println(s);
		}
	}
	

	
	
	}

	

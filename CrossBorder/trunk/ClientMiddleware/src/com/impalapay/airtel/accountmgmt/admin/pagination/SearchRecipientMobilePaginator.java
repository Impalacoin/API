package com.impalapay.airtel.accountmgmt.admin.pagination;

import java.util.List;

import com.impalapay.airtel.accountmgmt.pagination.*;
import com.impalapay.airtel.accountmgmt.admin.persistence.util.CountUtils;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.persistence.transaction.TransactionDAO;


public class SearchRecipientMobilePaginator implements TransactionPaginating {

	    public static final int PAGESIZE = 15; // The number of Transactions to display per page
	    private Account account;
	    private CountUtils countUtils;
	    private TransactionDAO transactionDAO;
	    private String phone;
	    /**
	     * Disable the default constructor
	     */
	    public SearchRecipientMobilePaginator() {}

	    /**
	     *
	     * @param username
	     */
	    public SearchRecipientMobilePaginator(String phone) {
	         
	    	
	    	this.phone = phone;
	    	countUtils = CountUtils.getInstance();
	    	transactionDAO=TransactionDAO.getInstance();

	       
	        
	        


	    }

	    /**
	     *
	     * @param username
	     * @param dbName
	     * @param dbHost
	     * @param dbUsername
	     * @param dbPasswd
	     * @param dbPort
	     */
	    public SearchRecipientMobilePaginator(String phone,String dbName,
	            String dbHost, String dbUsername, String dbPasswd, int dbPort) {
	    	//this.username = username;
	    	this.phone = phone;

	        countUtils = new CountUtils(dbName,
	                dbHost, dbUsername, dbPasswd, dbPort);
	        
	         transactionDAO=new TransactionDAO(dbName, dbHost, dbUsername, dbPasswd, dbPort);


	     

	    }

	    /**
	     * @see
	     * com.impalapay.airtel.accountmgmt.pagination.TransactionPaginating#getFirstPage()  
	     */
	    @Override
	    public TransactionPage getFirstPage() {
	        TransactionPage result = new TransactionPage();
	        List<Transaction> transactionList;
	        
	        transactionList = transactionDAO.getAllTransactionByReceiverPhone(phone, 0, PAGESIZE);

	        result = new TransactionPage(1, getTotalPage(), PAGESIZE, transactionList);

	        return result;
	    }

	    
	    /**
	     *
	     * @return TransactionPage
	     */
	    @Override
	    public TransactionPage getLastPage() {
	        int transactionCount, startIndex;
	        int totalPage = getTotalPage();

	        TransactionPage result = new TransactionPage();
	        List<Transaction> transactionList;

	        startIndex = (totalPage - 1) * PAGESIZE;
	        
	        transactionCount = countUtils.getAllTransactionByRecipientMsisdnCount(phone);
	        
	        transactionList = transactionDAO.getAllTransactionByReceiverPhone(phone, startIndex, transactionCount);

	        result = new TransactionPage(totalPage, totalPage, PAGESIZE, transactionList);

	        return result;
	    }

	    
	    /**
	     *
	     * @return TransactionPage
	     */
	    @Override
	    public TransactionPage getNextPage(TransactionPage currentPage) {
	        int totalPage = getTotalPage();

	        TransactionPage result = new TransactionPage();
	        List<Transaction> transactionList;

	        transactionList = transactionDAO.getAllTransactionByReceiverPhone(phone, currentPage.getPageNum() * PAGESIZE,
	                (currentPage.getPageNum() * PAGESIZE) + PAGESIZE);


	        result = new TransactionPage(currentPage.getPageNum() + 1, totalPage, PAGESIZE, transactionList);

	        return result;
	    }

	    
	    /**
	     *
	     * @return TransactionPage
	     */
	    @Override
	    public TransactionPage getPrevPage(TransactionPage currentPage) {
	        int totalPage = getTotalPage();

	        TransactionPage result = new TransactionPage();
	        List<Transaction> transactionList;

	        transactionList = transactionDAO.getAllTransactionByReceiverPhone(phone, (currentPage.getPageNum() - 2) * PAGESIZE,
	                (currentPage.getPageNum() - 1) * PAGESIZE);


	        result = new TransactionPage(currentPage.getPageNum() - 1, totalPage, PAGESIZE, transactionList);

	        return result;
	    }

	    
	    /**
	     *
	     * @return int
	     */
	    public int getTotalPage() {

	        int totalSize = 0;

	        totalSize = countUtils.getAllTransactionByRecipientMsisdnCount(phone);

	        return ((totalSize - 1) / PAGESIZE) + 1;
	    }
	}


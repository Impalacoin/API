package com.impalapay.airtel.accountmgmt.pagination;

import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.persistence.transaction.TransactionDAO;
import com.impalapay.airtel.persistence.util.CountUtils;

public class SearchRecipientMobilePaginator implements TransactionPaginating {

	    public static final int PAGESIZE = 15; // The number of Transactions to display per page
	    private Account account;
	    private CountUtils countUtils;
	    private AccountDAO accountDAO;
	    private TransactionDAO transactionDAO;
	    private String username;
	    private String phone;
	    /**
	     * Disable the default constructor
	     */
	    public SearchRecipientMobilePaginator() {}

	    /**
	     *
	     * @param username
	     */
	    public SearchRecipientMobilePaginator(String username,String phone) {
	         
	    	this.username = username;
	    	this.phone = phone;

	        countUtils = CountUtils.getInstance();


	        accountDAO = AccountDAO.getInstance();


	        account = accountDAO.getAccountName(username);
	        
	        
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
	    public SearchRecipientMobilePaginator(String username, String phone,String dbName,
	            String dbHost, String dbUsername, String dbPasswd, int dbPort) {
	    	this.username = username;
	    	this.phone = phone;

	        countUtils = new CountUtils(dbName,
	                dbHost, dbUsername, dbPasswd, dbPort);
	        
	         transactionDAO=new TransactionDAO(dbName, dbHost, dbUsername, dbPasswd, dbPort);


	        accountDAO = new AccountDAO(dbName,
	                dbHost, dbUsername, dbPasswd, dbPort);

	        account = accountDAO.getAccountName(username);

	    }

	    /**
	     * @see
	     * com.impalapay.airtel.accountmgmt.pagination.TransactionPaginating#getFirstPage()  
	     */
	    @Override
	    public TransactionPage getFirstPage() {
	        TransactionPage result = new TransactionPage();
	        List<Transaction> transactionList;
	        
	        transactionList = transactionDAO.getTransactionByReceiverPhone(account, phone, 0, PAGESIZE);

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
	        
	        transactionCount = countUtils.getTransactionByRecipientmobileCount(account, phone);
	        
	        transactionList = transactionDAO.getTransactionByReceiverPhone(account, phone, startIndex, transactionCount);

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

	        transactionList = transactionDAO.getTransactionByReceiverPhone(account,phone, currentPage.getPageNum() * PAGESIZE,
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

	        transactionList = transactionDAO.getTransactionByReceiverPhone(account,phone, (currentPage.getPageNum() - 2) * PAGESIZE,
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

	        totalSize = countUtils.getTransactionByRecipientmobileCount(account,phone);

	        return ((totalSize - 1) / PAGESIZE) + 1;
	    }
	}


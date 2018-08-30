package com.impalapay.airtel.accountmgmt.pagination;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.transaction.Transaction;


import java.util.List;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.persistence.transaction.TransactionDAO;
import com.impalapay.airtel.persistence.util.CountUtils;

/**
 * Class responsible for breaking down a {@link java.util.List} of
 * {@link com.impalapay.airtel.beans.transaction.Transaction} into
 * {@link com.impalapay.airtel.accountmgmt.pagination.TransactionPage}
 * <p>
 * It is assumed that this class will only be called once an account user is
 * logged in.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class TransactionPaginator implements TransactionPaginating {

    public static final int PAGESIZE = 15; // The number of Transactions to display per page
    private Account account;
    private CountUtils countUtils;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private String username;
    /**
     * Disable the default constructor
     */
    public TransactionPaginator() {}

    /**
     *
     * @param username
     */
    public TransactionPaginator(String username) {
         
    	this.username = username;

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
    public TransactionPaginator(String username, String dbName,
            String dbHost, String dbUsername, String dbPasswd, int dbPort) {
    	this.username = username;

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
        
        transactionList = transactionDAO.getTransactions(account, 0, PAGESIZE);


        result = new TransactionPage(1, getTotalPage(), PAGESIZE, transactionList);

        return result;
    }

    /**
     * @see
     * com.impalapay.airtel.accountmgmt.pagination.TransactionPaginating#getLastPage() 
     */
    @Override
    public TransactionPage getLastPage() {
    	
    	TransactionPage result = new TransactionPage();
    	List<Transaction> transactionList;
    	int transactionCount,startIndex;
    	int totalPage = getTotalPage();
    	
    	startIndex = (totalPage - 1) * PAGESIZE;
    	transactionCount = countUtils.getTransactionCount(account);
    	transactionList = transactionDAO.getTransactions(account, startIndex, transactionCount);
    	
    	
    	result = new TransactionPage(totalPage,totalPage,PAGESIZE,transactionList);
    	
    	 return result;
    	/*
    	 * 
        int transactionCount, startIndex;
        int totalPage = getTotalPage();

        TransactionPage result = new TransactionPage();
        List<Transaction> transactionList;

        startIndex = (totalPage - 1) * PAGESIZE;
        transactionCount = countUtils.getTransactionCount(account);
        transactionList = transactionDAO.getTransactions(account, startIndex, transactionCount);


        result = new TransactionPage(totalPage, totalPage, PAGESIZE, transactionList);

        return result;
        */
    }

    
    /**
     * @see
     * com.impalapay.airtel.accountmgmt.pagination.TransactionPaginating#getNextPage(com.impalapay.airtel.accountmgmt.pagination.TransactionPage)  
     */
    @Override
    public TransactionPage getNextPage(final TransactionPage currentPage) {
        int totalPage = getTotalPage();

        TransactionPage result = new TransactionPage();
        List<Transaction> transactionList;

        transactionList = transactionDAO.getTransactions(account, currentPage.getPageNum() * PAGESIZE,
                (currentPage.getPageNum() * PAGESIZE) + PAGESIZE);


        result = new TransactionPage(currentPage.getPageNum() + 1, totalPage, PAGESIZE, transactionList);

        return result;
    }

    /**
     * @see
     * com.impalapay.airtel.accountmgmt.pagination.TransactionPaginating#getPrevPage(com.impalapay.airtel.accountmgmt.pagination.TransactionPage)  
     */
    @Override
    public TransactionPage getPrevPage(final TransactionPage currentPage) {
        int totalPage = getTotalPage();

        TransactionPage result = new TransactionPage();
        List<Transaction> transactionList;

        transactionList = transactionDAO.getTransactions(account, (currentPage.getPageNum() - 2) * PAGESIZE,
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

        totalSize = countUtils.getTransactionCount(account);

        return ((totalSize - 1) / PAGESIZE) + 1;
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
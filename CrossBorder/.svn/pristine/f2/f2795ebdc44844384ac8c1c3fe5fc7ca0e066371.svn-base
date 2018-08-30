package com.impalapay.airtel.accountmgmt.admin.pagination;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.persistence.transaction.TransactionDAO;
import com.impalapay.airtel.accountmgmt.admin.persistence.util.CountUtils;
import com.impalapay.airtel.accountmgmt.pagination.*;

import java.util.List;




/**
 * Class responsible for breaking down a {@link List} of {@link Transaction}s
 * matching a uuid into a {@link TransactionPage}.
 * <p>
 * It is assumed that this class will only be called once an account user is
 * logged in.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class SearchUuidPaginator implements TransactionPaginating {

    public static final int PAGESIZE = 15; // The number of Transactions to display per page
 
    private CountUtils countUtils;
    private TransactionDAO transactionDAO;
    private String uuid;
    /**
     * Disable the default constructor
     */
    public SearchUuidPaginator() {}

    /**
     *
     * @param username
     */
    public SearchUuidPaginator(String uuid) {
         
    	
    	this.uuid = uuid;
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
    public SearchUuidPaginator(String uuid,String dbName,
            String dbHost, String dbUsername, String dbPasswd, int dbPort) {
    	
    	this.uuid = uuid;

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
        
        transactionList = transactionDAO.getAllTransactionByUuid(uuid, 0, PAGESIZE);

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
        
        transactionCount = countUtils.getAllTransactionByUuidCount(uuid);
        
        transactionList = transactionDAO.getAllTransactionByUuid(uuid, startIndex, transactionCount);

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

        transactionList = transactionDAO.getAllTransactionByUuid(uuid, currentPage.getPageNum() * PAGESIZE,
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

        transactionList = transactionDAO.getAllTransactionByUuid(uuid, (currentPage.getPageNum() - 2) * PAGESIZE,
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

        totalSize = countUtils.getAllTransactionByUuidCount(uuid);

        return ((totalSize - 1) / PAGESIZE) + 1;
    }
}

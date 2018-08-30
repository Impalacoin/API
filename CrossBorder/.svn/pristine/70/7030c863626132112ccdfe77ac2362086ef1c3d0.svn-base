package com.impalapay.airtel.accountmgmt.admin.pagination;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.accountmgmt.admin.persistence.util.CountUtils;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.persistence.transaction.TransactionDAO;

import com.impalapay.airtel.accountmgmt.pagination.*;

import java.util.List;



/**
 * Class responsible for breaking down a {@link java.util.List} of
 * {@link com.impalapay.airtel.beans.transaction.Transaction} into
 * {@link com.impalapay.airtel.accountmgmt.pagination.TransactionPage} in the
 * admin section.
 *
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class TransactionPaginator implements TransactionPaginating {

    public static final int PAGESIZE = 15; // The number of Topups to display per page
    private Account account;
    private CountUtils countUtils;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    /**
     * Disable the default constructor
     */
    public TransactionPaginator() {

        countUtils = CountUtils.getInstance();

        transactionDAO = TransactionDAO.getInstance();

    }

    /**
     *
     * @param dbName
     * @param dbHost
     * @param dbUsername
     * @param dbPasswd
     * @param dbPort
     */
    public TransactionPaginator(String dbName,
            String dbHost, String dbUsername, String dbPasswd, int dbPort) {

        countUtils = new CountUtils(dbName, dbHost, dbUsername, dbPasswd, dbPort);


        transactionDAO = new TransactionDAO(dbName, dbHost, dbUsername, dbPasswd, dbPort);
    }
    

    /**
     * @see
     * com.impalapay.airtel.accountmgmt.pagination.TransactionPaginating#getFirstPage()
     */
    @Override
    public TransactionPage getFirstPage() {
        TransactionPage result = new TransactionPage();
        List<Transaction> transactionList;

        transactionList = transactionDAO.getAllTransactions(0, PAGESIZE);


        result = new TransactionPage(1, getTotalPage(), PAGESIZE, transactionList);

        return result;
    }

    /**
     * @see
     * com.impalapay.airtel.accountmgmt.pagination.TransactionPaginating#getLastPage()
     */
    @Override
    public TransactionPage getLastPage() {
        int transactionCount, startIndex;
        int totalPage = getTotalPage();

        TransactionPage result = new TransactionPage();
        List<Transaction> transactionList;

        startIndex = (totalPage - 1) * PAGESIZE;
        transactionCount = countUtils.getAllTransactionCount();
        transactionList = transactionDAO.getAllTransactions(startIndex, transactionCount);


        result = new TransactionPage(totalPage, totalPage, PAGESIZE, transactionList);

        return result;
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

        transactionList = transactionDAO.getAllTransactions(currentPage.getPageNum() * PAGESIZE,
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

        transactionList = transactionDAO.getAllTransactions((currentPage.getPageNum() - 2) * PAGESIZE,
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

        totalSize = countUtils.getAllTransactionCount();

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
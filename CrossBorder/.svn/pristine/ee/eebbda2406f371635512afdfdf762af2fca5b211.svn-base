package com.impalapay.airtel.accountmgmt.pagination;

/**
 * Description of how to break down a {@link java.util.List} of
 * {@link com.impalapay.airtel.beans.transaction.Transaction} into
 * {@link com.impalapay.airtel.accountmgmt.pagination.TransactionPage}
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public interface TransactionPaginating {

    /**
     *
     * @return TransactionPage
     */
    public TransactionPage getFirstPage();

    /**
     *
     * @return TransactionPage
     */
    public TransactionPage getLastPage();

    /**
     *
     * @param currentPage
     * @return TransactionPage
     */
    public TransactionPage getNextPage(TransactionPage currentPage);

    /**
     *
     * @param currentPage
     * @return TransactionPage
     */
    public TransactionPage getPrevPage(TransactionPage currentPage);
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
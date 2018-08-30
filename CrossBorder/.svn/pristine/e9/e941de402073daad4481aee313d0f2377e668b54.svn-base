package com.impalapay.airtel.persistence.transaction;

import com.impalapay.airtel.beans.transaction.TransactionStatus;

import java.util.List;

/**
 * Persistence description for {@link TransactionStatus}.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public interface AirtelTransactionStatus {

    /**
     * Gets the {@link TransactionStatus} corresponding to status integer.
     *
     * @param status
     * @return {@link TransactionStatus}
     */
    public TransactionStatus getTransactionStatus(int status);

    /**
     * Gets the transaction status corresponding to uuid.
     *
     * @param uuid
     * @return {@link TransactionStatus}
     */
    public TransactionStatus getTransactionStatus(String uuid);

    /**
     * Gets all transaction statuses
     *
     * @return List<{@link TransactionStatus}>
     */
    public List<TransactionStatus> getAllTransactionStatus();

    /**
     * Add a transaction status to the database.
     *
     * @param TransactionStatus
     * @return boolean indicating whether {@link TransactionStatus} has
     * been added or not.
     */
    public boolean addTransactionStatus(TransactionStatus TransactionStatus);
}

package com.impalapay.airtel.persistence.transaction;

import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.beans.transaction.TransactionStatus;

public interface AirtelTransaction {

	/**
	 * Gets all {@link Transaction}s which have an UUID matching the argument.
	 * The list returned is not arranged in any particular order.
	 * 
	 * @param uuid
	 * @return transaction request(s)
	 */
	public List<Transaction> getTransactions(String uuid);

	/**
	 * Gets all transaction request(s) that belong to a particular account
	 * holder.
	 *
	 * @param account
	 * @return List<{@link Transaction}> transaction request(s)
	 */
	public List<Transaction> getTransactions(Account account);

	/**
	 * Gets all transaction request(s) that belong to a particular account
	 * username.
	 *
	 *
	 * @param account
	 * @return List<{@link Transaction}> transaction request(s)
	 * 
	 *         public List<Transaction> getAllTransactions(String username);
	 */
	/**
	 * Gets all transactions request(s) that correspond to a particular country
	 *
	 * @param country
	 * @return country transaction request(s)
	 */
	public List<Transaction> getTransactions(Country country);

	/**
	 * Gets all transaction requests
	 *
	 * @return List<{@link Transaction}> all transaction requests
	 */
	public List<Transaction> getAllTransactions();

	/**
	 * Gets all transactions requests between the specified fromIndex,
	 * inclusive, and toIndex, exclusive.
	 *
	 * @param fromIndex
	 * @param toIndex
	 * 
	 * @return List<{@link Transaction}> all transaction requests
	 */
	public List<Transaction> getAllTransactions(int fromIndex, int toIndex);

	/**
	 *
	 * @param transaction
	 * @return transaction
	 */
	public boolean addTransaction(Transaction transaction);

	/**
	 * Returns a view of the portion of an Account's Transaction activity
	 * between the specified fromIndex, inclusive, and toIndex, exclusive.
	 *
	 * @param account
	 * @param fromIndex
	 * @param toIndex
	 * @return List<{@link Transaction}> all transaction requests
	 */
	public List<Transaction> getTransactions(Account account, int fromIndex,
			int toIndex);

	/**
	 * Returns a view of the portion of an Account's username Transaction
	 * activity between the specified fromIndex, inclusive, and toIndex,
	 * exclusive.
	 *
	 * @param account
	 * @param fromIndex
	 * @param toIndex
	 * @return List<{@link Transaction}> all transaction requests
	 * 
	 *         public List<Transaction> getTransactions(String username, int
	 *         fromIndex, int toIndex);
	 **/
	/**
	 * Returns a view of the portion of an Account's Transaction activity
	 * between the specified fromIndex, inclusive, and toIndex, exclusive. The
	 * {@link Transaction}s in the list have an UUID matching to that given as a
	 * parameter in this method.
	 *
	 * @param account
	 * @param uuid
	 * @param fromIndex
	 * @param toIndex
	 * 
	 * @return List<{@link Transaction}> all transactions requests matching the
	 *         Uuid
	 */
	public List<Transaction> getTransactionByUuid(Account account, String uuid,
			int fromIndex, int toIndex);

	/**
	 * Returns all Transaction activity between the specified fromIndex,
	 * inclusive, and toIndex, exclusive matching the uuid.
	 *
	 * @param uuid
	 * @param fromIndex
	 * @param toIndex
	 * @return List<{@link Transaction}> all transaction requests matching the
	 *         uuid
	 */
	public List<Transaction> getAllTransactionByUuid(String uuid,
			int fromIndex, int toIndex);

	/**
	 * Gets the transaction request(s) status with the provided referencenumber
	 * 
	 *
	 * @param transactionreference
	 * @return {@link Transaction} transaction request(s)
	 */
	public List<Transaction> getTransactionstatus(String referencenumber);

	/**
	 * Gets the transaction request(s) status with the provided referencenumber
	 * 
	 *
	 * @param transactionreference
	 * @return {Transaction} transaction request(s)
	 */
	public Transaction getTransactionstatus1(String referencenumber);
	/**
	 *  @return List<{@link Transaction}> all transactions requests matching the
	 *         receiver phone from an IMT account
	 */
	public List<Transaction> getTransactionByReceiverPhone(Account account, String phone,
			int fromIndex, int toIndex);
	
	public List<Transaction> getAllTransactionByReceiverPhone(String phone,
			int fromIndex, int toIndex);
	 
	 /**
     * Gets all transaction request(s) that correspond to a particular status.
     *
     * @param transactionStatus
     * @return List<{@link Transaction}> transaction request(s)
     */
    public List<Transaction> getTransactionByStatusUuid(TransactionStatus transactionStatus);

}

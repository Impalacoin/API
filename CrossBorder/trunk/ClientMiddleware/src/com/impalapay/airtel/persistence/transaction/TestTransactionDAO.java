package com.impalapay.airtel.persistence.transaction;

import java.util.Date;
import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.beans.transaction.TransactionStatus;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TestTransactionDAO {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost";
	final String DB_USERNAME = "airtel";
	final String DB_PASSWD = "LignuAv7";
	final int DB_PORT = 5432;

	final String TRANSACTION_UUID = "61797229-eb8b-4f84-bb15-a0410dc5d33b";
	final String TRANSACTION_REF = "fc47bd5c-7fdd-4def-92d7-f2b840aebe0f";
	final String TRANSACTION_REF2 = "fc47b-7fdd-4def-92d7-f2b840aebe0f";
	final String TRANSCONFIRM_UUID = "9756f889-811a-4a94-b13d-1c66c7655a7f";
	final String TRANSACTIONSTATUS_UUID = "3b6edb35-654d-4049-b7ea-0f1db29c6e77";
	final int ID = 1;
	final String UUID = "9756f889-811a-4a94-b13d-1c66c7655a7f";
	final String DEMO_ACCOUNT_UUID = "9756f889-811a-4a94-b13d-1c66c7655a7f";
	final String NEW_ACCOUNT_UUID = "9756f889-811a-4a94-b13d-1c66c7655a7f";

	final String NEWUUID = "c089e01983d744fab21ae34982f174e0";
	final String SENDER_TOKEN = "f889-811a-1c66c7655a7f";
	final String SENDERNAME = "eugene chimita";
	final String NEWSOURCE_COUNTRYCODE = "KE";
	final String NEW_RECIPIENTCODE = "d4a676822f4546a0bee789e83070f788";
	final String NEW_RECIPIENTMOBILE = "254-715-266-678";
	final String NEW_CURRENCYCODE = "USD";
	final Date TRANSACTION_DATE_NEW = new Date(
			new Long("1367597206000").longValue()); // Fri May 03 19:06:46 EAT
													// 2013;
	final Date TRANSACTION_DATE_VALID = new Date(
			new Long("1360065927000").longValue()); // 2013-02-05 15:05:27 (Feb
    final String NEWDATES ="2013-02-05 15:05:27";											// 5th)
	final int AMOUNT = 500;
	final String NETWORK_UUID = "602c66b6-83a4-46f4-a109-17a3bcd8d70b";

	final int TRANSACTION_COUNT = 1;

	private TransactionDAO storage = new TransactionDAO(DB_NAME, DB_HOST,
			DB_USERNAME, DB_PASSWD, DB_PORT);

	/**
	 * Test method for TransactionDAO#getTransactionByUuid(String)
	 */
	@Ignore
	@Test
	public void testGetTransactionsByUuid() {

		List<Transaction> transaction = storage
				.getTransactions(TRANSACTION_UUID);

		// assertEquals(transaction, 3);

		for (Transaction data : transaction) {
			System.out.println(data);
		}
	}

	/**
	 * Test method for
	 * {@link TransactionDAO#getTransactionByUuid(Account, String, int, int)}
	 */
	@Test
	 @Ignore
	public void testGetTransactionsByUuids() {

		String uuid = "61797229-eb8b-4f84-bb15-a0410dc5d33b";
		int fromIndex = 0;
		int toIndex = 15;
		Account account = new Account();
		account.setUuid(DEMO_ACCOUNT_UUID);

		int expectedSize = storage.getTransactionByUuid(account, uuid,
				fromIndex, toIndex).size();
		int actualSize = 1;
		assertEquals(expectedSize, actualSize);
		// System.out.println(expectedSize);
	}
    
	/**
	 * Test method for
	 * {@link TransactionDAO#getTransactionByUuid(Account, String, int, int)}
	 */
	@Test
	@Ignore
	public void testGetTransactionsByReceiverPhone() {

		String phone = "254-715-266-678";
		int fromIndex = 0;
		int toIndex = 15;
		Account account = new Account();
		account.setUuid(NEW_ACCOUNT_UUID);

		int expectedSize = storage.getTransactionByReceiverPhone(account, phone,
				fromIndex, toIndex).size();
		int actualSize = 1;
		//assertEquals(expectedSize, actualSize);
	     System.out.println(expectedSize);
	}
	/**
	 * Test method for {@link TransactionDAO#addTransaction(Transaction)}
	 * 
	 **/
	//@Ignore
	@Test
	public void testAddTransaction() {

		Transaction saved = new Transaction();

		// saved.setId(ID);
		saved.setUuid(NEWUUID);
		saved.setAccountUuid(NEW_ACCOUNT_UUID);
		saved.setSourceCountrycode(NEWSOURCE_COUNTRYCODE);
		saved.setSenderName(SENDERNAME);
		saved.setRecipientMobile(NEW_RECIPIENTMOBILE);
		saved.setAmount(AMOUNT);
		saved.setCurrencyCode(NEWSOURCE_COUNTRYCODE);
		saved.setRecipientCountryUuid(NEW_RECIPIENTCODE);
		saved.setSenderToken(SENDER_TOKEN);
		saved.setClientTime(NEWDATES);
		saved.setServerTime(TRANSACTION_DATE_VALID);
		saved.setTransactionStatusUuid(TRANSACTIONSTATUS_UUID);
		saved.setReferenceNumber(TRANSACTION_REF2);

		assertTrue(storage.addTransaction(saved));
	}

	/**
	 * Test method fo TransactionDAO#getAllTransactionsByUuid(java.lang.String,
	 * int, int)
	 * 
	 */
	@Ignore
	@Test
	public void TestGetAllTransactionsUuid() {
		// String uuid = "9756f889-811a-4a94-b13d-1c66c7655a7f";
		Account account = new Account();
		account.setUuid("9756f889-811a-4a94-b13d-1c66c7655a7f");
		int fromIndex = 0;
		int toIndex = 15;

		int expectedSize = storage.getTransactions(account, fromIndex, toIndex)
				.size();
		System.out.println(expectedSize);
		// int actualSize = 2;
		// assertEquals(expectedSize, actualSize);

	}

	/**
	 * Test method for TransactionDAO#getAllTransactionsByUuid(java.lang.String,
	 * int, int)
	 */
	@Ignore
	@Test
	public void TestgetAllTransactions() {

		int fromIndex = 0;
		int toIndex = 30;

		// String uuid = "61797229-eb8b-4f84-bb15-a0410dc5d33b";

		int expectedSize = storage.getAllTransactions(fromIndex, toIndex)
				.size();
		int actualSize = 2;
		// System.out.print(expectedSize);
		// assertEquals(expectedSize, actualSize);

	}

	/**
	 * Test method for {@link TransactionDAO#getAllTransactions() }
	 */
	@Ignore
	@Test
	public void testGetAllTransactions() {
		Account account = new Account();
		account.setUuid(DEMO_ACCOUNT_UUID);

		int fromIndex = 0;
		int toIndex = 30;

		int count = 10000;
		int expectedSize = storage.getTransactions(account, fromIndex, toIndex)
				.size();
		// int actualSize = 15;
		// assertEquals(expectedSize, actualSize);

		System.out.println(expectedSize);

		List<Transaction> transactionsByAccount = storage.getAllTransactions();
	

		assertEquals(transactionsByAccount.size(), count);

	}

	/**
	 * Test method for TransactionDAO#getAllTransactions(int, int)}
	 */
	@Ignore
	@Test
	public void testGetAllTopupsIndex() {
		Account account = new Account();
		account.setUuid(DEMO_ACCOUNT_UUID);

		int fromIndex = 0, toIndex = 15, count = 15;

		List<Transaction> transactions = storage.getAllTransactions(fromIndex,
				toIndex);

		assertEquals(transactions.size(), count);
	}

	/**
	 * Test method for TransactionDAO#getAllTransactions(account,int, int)
	 */
	@Ignore
	@Test
	public void testGetTransactions() {
		int fromIndex = 0;
		int toIndex = 15;
		Account account = new Account();
		account.setUuid(DEMO_ACCOUNT_UUID);

		int expectedSize = storage.getTransactions(account, fromIndex, toIndex)
				.size();
		int actualSize = 15;
		assertEquals(expectedSize, actualSize);

	}

	/**
	 * Test method for
	 * TopupDAO#getTopupsByAccount(com.impalapay.airtel.beans.accountmgmt
	 * .Account)
	 */
	@Test
	@Ignore
	public void testGetTransactionsByAccount() {
		Account account = new Account();
		account.setUuid(DEMO_ACCOUNT_UUID);

		List<Transaction> transactionsByAccount = storage
				.getTransactions(account);

		assertNotNull(transactionsByAccount);

		for (Transaction t : transactionsByAccount) {
			System.out.println(t);
		}
	}
	
	@Test
    @Ignore
	public void testGeAlltTransactionsByReceiverPhone() {

		String phone = "254-715-266-678";
		int fromIndex = 0;
		int toIndex = 15;
	

		int expectedSize = storage.getAllTransactionByReceiverPhone(phone,
				fromIndex, toIndex).size();
		int actualSize = 1;
		//assertEquals(expectedSize, actualSize);
	     System.out.println(expectedSize);
	}
	@Test
	@Ignore
 
   public void testGetTransactionByStatus() {
		TransactionStatus ts = new TransactionStatus();
      ts.setUuid(TRANSACTIONSTATUS_UUID);
       List<Transaction> transactionByStatus = storage.getTransactionByStatusUuid(ts);

       assertNotNull(transactionByStatus);

       for(Transaction t:transactionByStatus){
        System.out.println(t);
       }

   }
}
package com.impalapay.airtel.persistence.accountmgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.persistence.GenericDAO;

/**
 * Persistence abstraction for {@link Account}
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class AccountDAO extends GenericDAO implements AirtelAccountDAO {

	private static AccountDAO accountDAO;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return AccountDAO
	 */
	public static AccountDAO getInstance() {
		if (accountDAO == null) {
			accountDAO = new AccountDAO();
		}

		return accountDAO;
	}

	
	/**
     *
     */
	protected AccountDAO() {
		super();
	}
	

	/**
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public AccountDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}
	
	
	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.AirtelAccountDAO#getAccount(java.lang.String)
	 */
	@Override
	public Account getAccount(String uuid) {
		Account a = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM account WHERE uuid = ?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				a = beanProcessor.toBean(rset, Account.class);
				//a.setId(rset.getInt("accountId"));
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting account with uuid '" + uuid + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {rset.close();} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return a;
	}
	

	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.AirtelAccountDAO#getAccount(String)
	 */
	@Override
	public Account getAccountName(String username) {
		Account a = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM account WHERE username = ?;");
			pstmt.setString(1, username);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				a = beanProcessor.toBean(rset, Account.class);
				//a.setId(rset.getInt("accountId"));
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting account with unique name '"
					+ username + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {rset.close();} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return a;
	}

	
	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.AirtelAccountDAO#getAccountEmail(String)
	 */
	@Override
	public Account getAccountEmail(String email) {
		Account a = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM account WHERE email = ?;");
			pstmt.setString(1, email);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				a = beanProcessor.toBean(rset, Account.class);
				//a.setId(rset.getInt("accountId"));
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting account with email'" + email + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {rset.close();} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return a;
	}

	
	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.AirtelAccountDAO#getAllAccounts()
	 */
	@Override
	public List<Account> getAllAccounts() {
		List<Account> list = new ArrayList<>();
		Account a = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM account;");
			rset = pstmt.executeQuery();

			while (rset.next()) {
				a = beanProcessor.toBean(rset, Account.class);
				//a.setId(rset.getInt("accountId"));

				list.add(a);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all accounts.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {rset.close();} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return list;
	}

	
	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.AirtelAccountDAO#addAccount(com.impalapay.airtel.beans.accountmgmt.Account)
	 */
	@Override
	public boolean addAccount(Account account) {
		boolean success = true;
		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO account (uuid, accountStatusUuid, firstName, lastName, username,"
							+ "loginPasswd, apiUsername, apiPasswd, email, phone) VALUES(?,?,?,?,?,?,?,?,?,?);");

			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, account.getAccountStatusUuid());
			pstmt.setString(3, account.getFirstName());
			pstmt.setString(4, account.getLastName());
			pstmt.setString(5, account.getUsername());
			pstmt.setString(6, account.getLoginPasswd());
			pstmt.setString(7, account.getApiUsername());
			pstmt.setString(8, account.getApiPasswd());
			pstmt.setString(9, account.getEmail());
			pstmt.setString(10, account.getPhone());

			pstmt.execute();
			
			//introduced to take care of default sessionlog
			pstmt2 = conn.prepareStatement("INSERT INTO sessionlog(sessionuuid, accountuuid, creationtime, "
            		+ "valid) VALUES (?, ?, ?, ?);");

            pstmt2.setString(1,UUID.randomUUID().toString());
            pstmt2.setString(2,account.getUuid());
            pstmt2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt2.setBoolean(4, true);
            
            pstmt2.execute();
			
			

		} catch (SQLException e) {
			logger.error("SQLException when trying to put: " + account);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return success;
	}

	
	/**
	 * @see AirtelAccountDAO#updateAccount(java.lang.String, com.impalapay.airtel.beans.accountmgmt.Account) 
	 */
	@Override
	public boolean updateAccount(String uuid, Account a) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM account WHERE uuid=?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				pstmt2 = conn.prepareStatement("UPDATE account SET firstName=?, "
								+ "lastName=?, username=?, loginPasswd=?, apiUsername=?, apiPasswd=?, email=?, "
								+ "phone=?, accountStatusUuid=? WHERE uuid=?;");

				pstmt2.setString(1, a.getFirstName());
				pstmt2.setString(2, a.getLastName());
				pstmt2.setString(3, a.getUsername());
				pstmt2.setString(4, a.getLoginPasswd());
				pstmt2.setString(5, a.getApiUsername());
				pstmt2.setString(6, a.getApiPasswd());
				pstmt2.setString(7, a.getEmail());
				pstmt2.setString(8, a.getPhone());
				pstmt2.setString(9, a.getAccountStatusUuid());
				pstmt2.setString(10, a.getUuid());

				pstmt2.executeUpdate();

			} else {
				addAccount(a);
			}

		} catch (SQLException e) {
			logger.error("SQLException when trying to update account with uuid '" + uuid + "' with " 
					+ a + ".");
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		} finally {
			if (rset != null) {
				try {rset.close();} catch (SQLException e) {}
			}

			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}
			
			if (pstmt2 != null) {
				try {pstmt.close();} catch (SQLException e) {}
			}

			if (conn != null) {
				try {conn.close();} catch (SQLException e) {}
			}
		}

		return success;
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
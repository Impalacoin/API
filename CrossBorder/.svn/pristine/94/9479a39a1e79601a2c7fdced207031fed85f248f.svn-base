package com.impalapay.airtel.persistence.accountmgmt.balance;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.balance.ClientAccountBalanceByCountry;
import com.impalapay.airtel.beans.accountmgmt.balance.MasterAccountBalance;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.persistence.GenericDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * Persistence description for
 * {@link com.impalapay.airtel.beans.accountmgmt.balance.AccountBalance}
 * <p>
 * Copyright (c) ImpalaPay Ltd., Oct 12, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class AccountBalanceDAO extends GenericDAO implements AccountBalance {

	public static AccountBalanceDAO accountbalanceDAO;

	private Logger logger = Logger.getLogger(this.getClass());

	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return {@link TransactionDAO}
	 */
	public static AccountBalanceDAO getInstance() {

		if (accountbalanceDAO == null) {
			accountbalanceDAO = new AccountBalanceDAO();
		}

		return accountbalanceDAO;
	}

	/**
     * 
     */
	public AccountBalanceDAO() {
		super();

	}

	/**
	 * 
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public AccountBalanceDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}
    
	
	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalance#hasBalance(com.impalapay.airtel.beans.accountmgmt.Account,country,
	 *      double)
	 */
	@Override
	public boolean hasBalance(Account account,Country country, double balance) {
		boolean hasBalance = false;
		int accountBalance = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT balance FROM balancebycountry WHERE accountUuid=? AND countryuuid=?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, country.getUuid());
			rset = pstmt.executeQuery();

			if (rset.next()) {
				accountBalance = rset.getInt("balance");
			}

			if (accountBalance >= balance) {
				hasBalance = true;
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while checking whether '"
					+ account + "' has balance of " + balance + ".");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return hasBalance;
	}
	
	/**
	 * 
	 * 
	 */
	
	@Override
	public boolean deductBalanceByCountry(Account account, Country country,double amount) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("UPDATE balancebycountry "
							+ "SET balance = (SELECT balance FROM balancebycountry WHERE accountUuid=? AND countryuuid=?) "
							+ "- ? "
							+ "WHERE uuid = (SELECT uuid FROM balancebycountry WHERE accountUuid=? AND countryuuid=?);");

			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, country.getUuid());
			pstmt.setDouble(3, amount);
			pstmt.setString(4, account.getUuid());
			pstmt.setString(5, country.getUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException exception while deducting balance");
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return success;
	}
	
	@Override
	public boolean addBalanceByCountry(Account account, Country country,double amount) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			// Check whether this account has an existing balance or not.
			pstmt = conn
					.prepareStatement("SELECT uuid FROM balancebycountry WHERE accountUuid=? AND countryuuid=?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, country.getUuid());
			rset = pstmt.executeQuery();

			// The account has a pre-existing balance
			if (rset.next()) {
				pstmt = conn
						.prepareStatement("UPDATE balancebycountry "
								+ "SET balance = (SELECT balance FROM balancebycountry WHERE accountUuid=? AND countryuuid=?) "
								+ "+ ? "
								+ "WHERE uuid = (SELECT uuid FROM balancebycountry WHERE accountUuid=? AND countryuuid=?);");

				pstmt.setString(1, account.getUuid());
				pstmt.setString(2, country.getUuid());
				pstmt.setDouble(3, amount);
				pstmt.setString(4, account.getUuid());
				pstmt.setString(5, country.getUuid());
				pstmt.executeUpdate();

				// The account does not have a pre-existing balance
			} else {
				pstmt2 = conn
						.prepareStatement("INSERT INTO balancebycountry(uuid, countryuuid, accountUuid,"
								+ "balance) VALUES(?,?,?,?);");

				pstmt2.setString(1, UUID.randomUUID().toString());
				pstmt.setString(2, country.getUuid());
				pstmt2.setString(3, account.getUuid());
				pstmt2.setDouble(4, amount);
				pstmt2.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQLException exception while adding the balance of '"
					+ account + "' of amount " + amount + "' of country "+country+".");
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return success;
	}
	
	/**
	 * 
	 * 
	 */
	@Override
	public List<ClientAccountBalanceByCountry> getClientBalanceByCountry(Account account) {
		List<ClientAccountBalanceByCountry> list = new ArrayList<>();
		ClientAccountBalanceByCountry balance = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all balances on the account
			pstmt = conn
					.prepareStatement("SELECT * FROM balancebycountry WHERE accountUuid=?;");
			pstmt.setString(1, account.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				balance = b.toBean(rset, ClientAccountBalanceByCountry.class);
				
				list.add(balance);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting balances of '"
					+ account + "'.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}
    
	
	/**
	 * 
	 * 
	 */
	
	@Override
	public List<ClientAccountBalanceByCountry> getClientBalanceByCountry(Account account, Country country) {
		List<ClientAccountBalanceByCountry> list = new ArrayList<>();
		ClientAccountBalanceByCountry bl;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all balances on the account
			pstmt = conn
					.prepareStatement("SELECT * FROM balancebycountry WHERE accountUuid=? AND countryuuid=?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, country.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				bl = b.toBean(rset, ClientAccountBalanceByCountry.class);
				//bl.setId(rset.getInt("balanceId"));

				list.add(bl);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting balances of '"
					+ account + "'.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}
    
	/**
	 * 
	 * 
	 */
	
	@Override
	public double getBalanceByCountry1(Account account,Country country) {
		ClientAccountBalanceByCountry balance = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all balances on the account
			pstmt = conn
					.prepareStatement("SELECT * FROM balancebycountry WHERE accountUuid=? AND countryuuid=?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, country.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				balance = b.toBean(rset, ClientAccountBalanceByCountry.class);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting balances of '"
					+ account + "'.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return balance.getBalance();
	}

	
	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalance#getClientBalances(com.impalapay.airtel.beans.accountmgmt.Account)
	
	@Override
	public List<ClientAccountBalance> getClientBalance(Account account) {
		List<ClientAccountBalance> list = new ArrayList<>();
		ClientAccountBalance bl;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all balances on the account
			pstmt = conn
					.prepareStatement("SELECT * FROM clientbalance WHERE accountUuid=?;");
			pstmt.setString(1, account.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				bl = b.toBean(rset, ClientAccountBalance.class);
				//bl.setId(rset.getInt("balanceId"));

				list.add(bl);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting balances of '"
					+ account + "'.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}
	 */
	
	

	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalance#deductBalance(com.impalapay.airtel.beans.accountmgmt.Account,
	 *      int)
	 **/
	@Override
	public boolean deductBalance(Account account, double amount) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("UPDATE clientbalance "
							+ "SET balance = (SELECT balance FROM clientBalance WHERE accountUuid=?) "
							+ "- ? "
							+ "WHERE uuid = (SELECT uuid FROM clientbalance WHERE accountUuid=?);");

			pstmt.setString(1, account.getUuid());
			pstmt.setDouble(2, amount);
			pstmt.setString(3, account.getUuid());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException exception while deducting balance");
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return success;
	}
   
	@Override
	public MasterAccountBalance getMasterAccountBalance(String uuid) {
		MasterAccountBalance bl = new MasterAccountBalance();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn
					.prepareStatement("SELECT * FROM clientBalance WHERE uuid=?;");
			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				bl = b.toBean(rset, MasterAccountBalance.class);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting a client balance "
					+ "with uuid '" + uuid + "'.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return bl;
	}

	@Override
	public MasterAccountBalance getMasterAccountBalance(Account account) {
		MasterAccountBalance balance = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all balances on the account
			pstmt = conn
					.prepareStatement("SELECT * FROM clientbalance WHERE accountUuid=?;");
			pstmt.setString(1, account.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				balance = b.toBean(rset, MasterAccountBalance.class);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting balances of '"
					+ account + "'.");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return balance;
	}

	@Override
	public List<MasterAccountBalance> getMasterAccountBalances() {
		List<MasterAccountBalance> list = new ArrayList<>();
		MasterAccountBalance bl;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all balances on the account
			pstmt = conn
					.prepareStatement("SELECT * FROM clientbalance;");
			rset = pstmt.executeQuery();

			while (rset.next()) {
				bl = b.toBean(rset, MasterAccountBalance.class);
				//bl.setId(rset.getInt("balanceId"));

				list.add(bl);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting master balances");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}

	

	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalance#addBalance(com.impalapay.airtel.beans.accountmgmt.Account,
	 *      int)
	 
	@Override
	public boolean addBalance(Account account, double amount) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			// Check whether this account has an existing balance or not.
			pstmt = conn
					.prepareStatement("SELECT uuid FROM clientbalance WHERE accountUuid=?;");
			pstmt.setString(1, account.getUuid());
			rset = pstmt.executeQuery();

			// The account has a pre-existing balance
			if (rset.next()) {
				pstmt2 = conn
						.prepareStatement("UPDATE clientbalance "
								+ "SET balance = (SELECT balance FROM clientBalance WHERE accountUuid=?) "
								+ "+ ? "
								+ "WHERE uuid = (SELECT uuid FROM clientbalance WHERE accountUuid=?);");

				pstmt2.setString(1, account.getUuid());
				pstmt2.setDouble(2, amount);
				pstmt2.setString(3, account.getUuid());
				pstmt2.executeUpdate();

				// The account does not have a pre-existing balance
			} else {
				pstmt2 = conn
						.prepareStatement("INSERT INTO clientBalance(uuid, accountUuid, "
								+ "balance) VALUES(?,?,?);");

				pstmt2.setString(1, UUID.randomUUID().toString());
				pstmt2.setString(2, account.getUuid());
				pstmt2.setDouble(3, amount);
				pstmt2.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQLException exception while adding the balance of '"
					+ account + "' of amount " + amount + ".");
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;

		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		return success;
	}
*/
	

	
	
	
	
	
	
	
	
	}


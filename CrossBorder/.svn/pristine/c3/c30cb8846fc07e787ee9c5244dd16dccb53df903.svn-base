package com.impalapay.airtel.persistence.accountmgmt.balance;

import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.balance.AccountPurchaseByCountry;
import com.impalapay.airtel.beans.accountmgmt.balance.ClientAccountBalanceByCountry;
import com.impalapay.airtel.beans.accountmgmt.balance.MasterAccountBalance;
import com.impalapay.airtel.beans.accountmgmt.balance.MasterAccountFloatPurchase;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.persistence.GenericDAO;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.persistence.geolocation.CountryDAO;

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

public class AccountPurchaseDAO extends GenericDAO implements AccountPurchase {

	public static AccountPurchaseDAO accountpurchaseDAO;

	private AccountBalanceDAO accountBalanceDAO;
	private AccountDAO accountDAO;
	private CountryDAO countryDAO;

	private Logger logger;

	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return {@link TransactionDAO}
	 */
	public static AccountPurchaseDAO getInstance() {

		if (accountpurchaseDAO == null) {
			accountpurchaseDAO = new AccountPurchaseDAO();
		}

		return accountpurchaseDAO;
	}

	/**
     * 
     */
	public AccountPurchaseDAO() {
		super();

		accountBalanceDAO = AccountBalanceDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
		countryDAO = CountryDAO.getInstance();

		logger = Logger.getLogger(this.getClass());
	}

	/**
	 * 
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public AccountPurchaseDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);

		accountBalanceDAO = new AccountBalanceDAO(dbName, dbHost, dbUsername,
				dbPassword, dbPort);
		accountDAO = new AccountDAO(dbName, dbHost, dbUsername, dbPassword,
				dbPort);
		countryDAO = new CountryDAO(dbName, dbHost, dbUsername, dbPassword,
				dbPort);

		logger = Logger.getLogger(this.getClass());
	}

	@Override
	public MasterAccountFloatPurchase getMasterFloat(String uuid) {
		MasterAccountFloatPurchase p = new MasterAccountFloatPurchase();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn.prepareStatement("SELECT * FROM topup WHERE uuid=?;");
			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				p = b.toBean(rset, MasterAccountFloatPurchase.class);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting a master topup "
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

		return p;
	}

	@Override
	public boolean putMasterFloat(MasterAccountFloatPurchase purchase) {
		boolean success = true;

		int masterBalanceId = 0;
		double masterBalance = 0;

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null, pstmt3 = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn
					.prepareStatement("INSERT INTO topup(uuid,accountuuid,amount,topuptime) "
							+ "VALUES(?,?,?,?);");

			pstmt.setString(1, purchase.getUuid());
			pstmt.setString(2, purchase.getAccountUuid());
			pstmt.setDouble(3, purchase.getAmount());
			pstmt.setTimestamp(4, new Timestamp(purchase.getPurchaseDate()
					.getTime()));
			pstmt.execute();

			// Credit the master float balance(clientbalance)
			pstmt2 = conn
					.prepareStatement("SELECT * FROM clientBalance WHERE accountUuid = ?;");

			pstmt2.setString(1, purchase.getAccountUuid());
			rset = pstmt2.executeQuery();

			if (rset.next()) {
				masterBalanceId = rset.getInt("balanceid");
				masterBalance = rset.getDouble("balance");
			}

			// if master balance already exists, credit the balance
			if (masterBalanceId > 0) {
				pstmt3 = conn
						.prepareStatement("UPDATE clientBalance SET balance=? WHERE balanceid=?;");
				pstmt3.setDouble(1, masterBalance + purchase.getAmount());
				pstmt3.setInt(2, masterBalanceId);
				pstmt3.executeUpdate();

			} else {
				pstmt3 = conn
						.prepareStatement("INSERT INTO clientBalance(uuid, accountuuid, balance) "
								+ "VALUES(?,?,?);");

				pstmt3.setString(1, UUID.randomUUID().toString());
				pstmt3.setString(2, purchase.getAccountUuid());
				pstmt3.setDouble(3, purchase.getAmount());
				pstmt3.execute();
			}

			conn.commit();

		} catch (SQLException e) {
			logger.error("SQLException exception while inserting: " + purchase);
			logger.error(ExceptionUtils.getStackTrace(e));

			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
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
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt3 != null) {
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
	public boolean deleteMasterFloat(String uuid) {
		boolean success = true;

		double masterBalance = 0, purchaseAmount = 0;
		String accountUuid = "";

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null, pstmt3 = null, pstmt4 = null;
		ResultSet rset = null, rset2 = null;

		try {
			conn = dbCredentials.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement("SELECT * FROM topup WHERE uuid=?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				accountUuid = rset.getString("accountuuid");
				purchaseAmount = rset.getDouble("amount");
			}

			// Delete the purchase
			pstmt2 = conn.prepareStatement("DELETE FROM topup WHERE uuid=?;");
			pstmt2.setString(1, uuid);
			pstmt2.executeUpdate();

			// debit the Master account balance for a client
			pstmt3 = conn
					.prepareStatement("SELECT * FROM clientBalance WHERE accountUuid = ?;");

			pstmt3.setString(1, accountUuid);
			rset2 = pstmt3.executeQuery();

			if (rset2.next()) {
				masterBalance = rset2.getDouble("balance");
			}

			// debit the balance
			pstmt4 = conn
					.prepareStatement("UPDATE clientBalance SET balance=? WHERE accountUuid = ?;");
			pstmt4.setDouble(1, masterBalance - purchaseAmount);
			pstmt4.setString(2, accountUuid);
			pstmt4.executeUpdate();

			conn.commit();

		} catch (SQLException e) {
			logger.error("SQLException exception while deleting master topup with uuid: "
					+ uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}

		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (rset2 != null) {
					rset2.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (pstmt3 != null) {
					pstmt3.close();
				}
				if (pstmt4 != null) {
					pstmt4.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
			}
		}

		return success;
	}

	public List<MasterAccountFloatPurchase> getMasterFloat() {
		List<MasterAccountFloatPurchase> list = new ArrayList<>();
		MasterAccountFloatPurchase p = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM topup;");
			rset = pstmt.executeQuery();

			while (rset.next()) {
				p = b.toBean(rset, MasterAccountFloatPurchase.class);
				// p.setId(rset.getInt("masterAirtimePurchaseId"));

				list.add(p);
			}

			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all topup.");
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

	@Override
	public List<MasterAccountFloatPurchase> getMasterFloat(Account account) {
		List<MasterAccountFloatPurchase> list = new ArrayList<>();
		MasterAccountFloatPurchase ap;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all purchases belonging to the account
			pstmt = conn
					.prepareStatement("SELECT * FROM topup WHERE accountUuid=?;");
			pstmt.setString(1, account.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				ap = b.toBean(rset, MasterAccountFloatPurchase.class);

				list.add(ap);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting topup of '"
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

	@Override
	public AccountPurchaseByCountry getByCountryPurchase(String uuid) {
		AccountPurchaseByCountry p = new AccountPurchaseByCountry();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();
		try {

			conn = dbCredentials.getConnection();

			pstmt = conn
					.prepareStatement("SELECT * FROM topupbycountry WHERE uuid=?;");

			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				p = b.toBean(rset, AccountPurchaseByCountry.class);

			}
		} catch (SQLException e) {
			logger.error("SQLException exception while getting a topup by country "
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

		return p;

	}

	@Override
	public boolean putClientPurchaseByCountry(AccountPurchaseByCountry purchase) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			conn.setAutoCommit(false);

			// We are to add to Airtime purchase

			// Record a purchase
			pstmt = conn
					.prepareStatement("INSERT INTO topupbycountry (uuid, accountuuid , countryuuid, "
							+ "amount,topuptime) VALUES (?, ?, ?, ?, ?);");

			pstmt.setString(1, purchase.getUuid());
			pstmt.setString(2, purchase.getAccountUuid());
			pstmt.setString(3, purchase.getCountryUuid());
			pstmt.setDouble(4, purchase.getAmount());
			pstmt.setTimestamp(5, new Timestamp(purchase.getPurchaseDate()
					.getTime()));

			pstmt.execute();

			// Credit the client's Airtime account balance
			// This method of doing it is more thread safe than direct SQL
			// manipulation of the
			// airtime balance table
			if (accountBalanceDAO.addBalanceByCountry(
					accountDAO.getAccount(purchase.getAccountUuid()),
					countryDAO.getCountry(purchase.getCountryUuid()),
					purchase.getAmount())) {
				conn.commit();
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while putting: " + purchase);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}

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

		return success;
	}

	@Override
	public boolean deleteClientPurchaseByCountry(String uuid) {
		boolean success = true;

		double purchaseAmount = 0;
		String accountUuid = "", countryUuid = "";

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn
					.prepareStatement("SELECT * FROM topupbycountry WHERE uuid=?;");
			pstmt.setString(1, uuid);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				accountUuid = rset.getString("accountuuid");
				countryUuid = rset.getString("countryuuid");
				purchaseAmount = rset.getDouble("amount");
			}

			// Delete the top up by country
			pstmt2 = conn
					.prepareStatement("DELETE FROM topupbycountry WHERE uuid=?;");
			pstmt2.setString(1, uuid);
			pstmt2.executeUpdate();

			// Debit the balance by country
			if (accountBalanceDAO.deductBalanceByCountry(
					accountDAO.getAccount(accountUuid),
					countryDAO.getCountry(countryUuid), purchaseAmount)) {
				conn.commit();
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while deleting toup by country with uuid: "
					+ uuid);
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}

		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
			}
		}

		return success;
	}

	@Override
	public List<AccountPurchaseByCountry> getClientPurchasesByCountry(
			Account account) {
		List<AccountPurchaseByCountry> list = new ArrayList<>();
		AccountPurchaseByCountry ap;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all top up by country
			pstmt = conn
					.prepareStatement("SELECT * FROM topupbycountry WHERE accountUuid=?;");
			pstmt.setString(1, account.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				ap = b.toBean(rset, AccountPurchaseByCountry.class);

				list.add(ap);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting top up by country belonging to account"
					+ account + ".");
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

	@Override
	public List<AccountPurchaseByCountry> getAllClientPurchasesByCountry() {
		List<AccountPurchaseByCountry> list = new ArrayList<>();
		AccountPurchaseByCountry ap;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all top up by country
			pstmt = conn.prepareStatement("SELECT * FROM topupbycountry;");
			rset = pstmt.executeQuery();

			while (rset.next()) {
				ap = b.toBean(rset, AccountPurchaseByCountry.class);

				list.add(ap);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all top up by country.");
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

	@Override
	public List<AccountPurchaseByCountry> getClientPurchasesByCountry(
			Account account, Country country) {
		List<AccountPurchaseByCountry> list = new ArrayList<>();
		AccountPurchaseByCountry ap;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all top up by country
			pstmt = conn
					.prepareStatement("SELECT * FROM topupbycountry WHERE accountUuid=? AND countryuuid=?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, country.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				ap = b.toBean(rset, AccountPurchaseByCountry.class);

				list.add(ap);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting top up by country belonging to account"
					+ account + ".");
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

	@Override
	public boolean putClientPurchaseByCountry2(AccountPurchaseByCountry purchase) {
		boolean success = true;

		int countryBalanceId = 0;
		double countryBalance = 0;

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null, pstmt3 = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn
					.prepareStatement("INSERT INTO topupbycountry (uuid, accountuuid , countryuuid, "
							+ "amount,topuptime) VALUES (?, ?, ?, ?, ?);");

			pstmt.setString(1, purchase.getUuid());
			pstmt.setString(2, purchase.getAccountUuid());
			pstmt.setString(3, purchase.getCountryUuid());
			pstmt.setDouble(4, purchase.getAmount());
			pstmt.setTimestamp(5, new Timestamp(purchase.getPurchaseDate()
					.getTime()));

			pstmt.execute();

			// Credit
			pstmt2 = conn
					.prepareStatement("SELECT * FROM balancebycountry WHERE accountUuid = ? AND countryuuid=?;");

			pstmt2.setString(1, purchase.getAccountUuid());
			pstmt2.setString(2, purchase.getCountryUuid());
			rset = pstmt2.executeQuery();

			if (rset.next()) {
				countryBalanceId = rset.getInt("balanceid");
				countryBalance = rset.getDouble("balance");
			}

			// if master balance already exists, credit the balance
			if (countryBalanceId > 0) {
				pstmt3 = conn
						.prepareStatement("UPDATE balancebycountry SET balance=? WHERE balanceid=?;");
				pstmt3.setDouble(1, countryBalance + purchase.getAmount());
				pstmt3.setInt(2, countryBalanceId);
				pstmt3.executeUpdate();

			} else {
				pstmt3 = conn
						.prepareStatement("INSERT INTO balancebycountry(uuid,countryuuid, accountuuid, balance) "
								+ "VALUES(?,?,?,?);");

				pstmt3.setString(1, UUID.randomUUID().toString());
				pstmt3.setString(2, purchase.getCountryUuid());
				pstmt3.setString(3, purchase.getAccountUuid());
				pstmt3.setDouble(4, purchase.getAmount());
				pstmt3.execute();
			}

			conn.commit();

		} catch (SQLException e) {
			logger.error("SQLException exception while inserting: " + purchase);
			logger.error(ExceptionUtils.getStackTrace(e));

			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
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
					pstmt.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt3 != null) {
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

}

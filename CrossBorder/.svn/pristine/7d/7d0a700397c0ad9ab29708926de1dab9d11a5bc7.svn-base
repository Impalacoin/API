package com.impalapay.airtel.persistence.transaction;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.beans.transaction.TransactionStatus;
import com.impalapay.airtel.persistence.GenericDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;








import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class TransactionDAO extends GenericDAO implements AirtelTransaction {

	public static TransactionDAO transactionDAO;

	private Logger logger = Logger.getLogger(this.getClass());

	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return {@link TransactionDAO}
	 */
	public static TransactionDAO getInstance() {

		if (transactionDAO == null) {
			transactionDAO = new TransactionDAO();
		}

		return transactionDAO;
	}

	/**
     * 
     */
	public TransactionDAO() {
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
	public TransactionDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}
    
	@Override
	public boolean addTransaction(Transaction transaction) {
		boolean success = true;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbCredentials.getConnection();
            pstmt = conn.prepareStatement("INSERT INTO transaction (uuid, accountuuid , sourcecountrycode, sendername, "
            		+ "recipientmobile, amount, currencycode, recipientcountryuuid, sendertoken, clienttime, servertime,transactionstatusuuid, referencenumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            pstmt.setString(1,transaction.getUuid());
            pstmt.setString(2,transaction.getAccountUuid());
            pstmt.setString(3, transaction.getSourceCountrycode());
			pstmt.setString(4, transaction.getSenderName());
			pstmt.setString(5, transaction.getRecipientMobile());
			pstmt.setDouble(6, transaction.getAmount());
			pstmt.setString(7, transaction.getCurrencyCode());
			pstmt.setString(8, transaction.getRecipientCountryUuid());
			pstmt.setString(9, transaction.getSenderToken());
			pstmt.setString(10, transaction.getClientTime());
			pstmt.setTimestamp(11, new Timestamp(transaction.getServerTime().getTime()));
			//pstmt.setString(12, transaction.getTransactionStatusUuid());
			pstmt.setString(12, transaction.getTransactionStatusUuid());
			pstmt.setString(13, transaction.getReferenceNumber());
			
            
            pstmt.execute();

        } catch (SQLException e) {
            logger.error("SQLException exception while adding " + transaction);
            logger.error(ExceptionUtils.getStackTrace(e));
            success = false;
            
        } finally {
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) {}
            }

            if (conn != null) {
                try { conn.close(); } catch (SQLException e) {}
            }
        }

        return success;
	}
	
	
	/**
	 * @see com.impalapay.airtel.persistence.transaction.AirtelTransaction#getTransactions(java.lang.String)
	 */
	@Override
	public List<Transaction> getTransactions(String uuid) {
		// TODO Auto-generated method stub
		List<Transaction> list = new LinkedList<>();

		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE uuid=?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);

				list.add(transaction);
			}

		} catch (SQLException e) {
			logger.error("SQL exception while fetching transaction with uuid"
					+ uuid);
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
	 */

	@Override
	public List<Transaction> getTransactions(Account account) {
		// TODO Auto-generated method stub

		List<Transaction> list = new LinkedList<>();

		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE accountuuid=?;");
			pstmt.setString(1, account.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);
				//transaction.setId(rset.getInt("id"));
				list.add(transaction);

			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting transaction of"
					+ account);
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
	public List<Transaction> getTransactions(Country country) {
		// TODO Auto-generated method stub

		List<Transaction> list = new LinkedList<>();

		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE recipientCountryUuid=?;");
			pstmt.setString(1, country.getUuid());

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);
				//transaction.setId(rset.getInt("id"));
				list.add(transaction);

			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting transaction of"
					+ country);
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
	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		List<Transaction> list = new LinkedList<>();

		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM transaction;");

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);
				//transaction.setId(rset.getInt("id"));
				list.add(transaction);

			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all transactions");
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
	public List<Transaction> getAllTransactions(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		List<Transaction> list = new LinkedList<>();

		// Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction ORDER BY  clientTime DESC LIMIT ? OFFSET ? ;");

			pstmt.setInt(1, toIndex - fromIndex);
			pstmt.setInt(2, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				list = beanProcessor.toBeanList(rset, Transaction.class);
				// transaction = b.toBean(rset, Transaction.class);
				// transaction.setId(rset.getInt("sessionid"));

				// list.add(transaction);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting transactions with uuid  from "
					+ fromIndex + " to " + toIndex);
			logger.error(e.toString());

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
	public List<Transaction> getTransactions(Account account, int fromIndex,
			int toIndex) {
		// TODO Auto-generated method stub
		List<Transaction> list = new LinkedList<>();

		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE  accountUuid=? ORDER BY clienttime DESC LIMIT ? OFFSET ?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setInt(2, toIndex - fromIndex);
			pstmt.setInt(3, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);
				//transaction.setId(rset.getInt("id"));

				list.add(transaction);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting transaction with ' "
					+ account + "' from " + fromIndex + " to " + toIndex);
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
	public List<Transaction> getTransactionByUuid(Account account, String uuid,
			int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		List<Transaction> list = new LinkedList<>();

		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE uuid=? AND accountUuid=? "
							+ "ORDER BY clienttime  DESC LIMIT ? OFFSET ?;");
			pstmt.setString(1, uuid);
			pstmt.setString(2, account.getUuid());
			pstmt.setInt(3, toIndex - fromIndex);
			pstmt.setInt(4, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);
				//transaction.setId(rset.getInt("id"));

				list.add(transaction);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting transaction with uuid ' "
					+ uuid + "' from " + fromIndex + " to " + toIndex);
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
	public List<Transaction> getAllTransactionByUuid(String uuid,
			int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		List<Transaction> list = new LinkedList<>();

		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE  uuid=? ORDER BY clienttime DESC LIMIT ? OFFSET ?;");
			pstmt.setString(1, uuid);
			pstmt.setInt(2, toIndex - fromIndex);
			pstmt.setInt(3, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);
				//transaction.setId(rset.getInt("id"));

				list.add(transaction);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting transaction with uuid ' "
					+ uuid + "' from " + fromIndex + " to " + toIndex);
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
	public List<Transaction> getTransactionstatus(String referencenumber) {
		List<Transaction> list = new LinkedList<>();
		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE referenceNumber=?;");
			pstmt.setString(1, referencenumber);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);

				list.add(transaction);
			}

		} catch (SQLException e) {
			logger.error("SQL exception while fetching transaction with referenceNumber"
					+ referencenumber);
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
	public Transaction getTransactionstatus1(String referencenumber) {
		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE referenceNumber=?;");
			pstmt.setString(1, referencenumber);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);

			}

		} catch (SQLException e) {
			logger.error("SQL exception while fetching transaction with referenceNumber"
					+ referencenumber);
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

		return transaction;
	}

	@Override
	public List<Transaction> getTransactionByReceiverPhone(Account account,
			String phone, int fromIndex, int toIndex) {
		
				List<Transaction> list = new LinkedList<>();

				Transaction transaction = null;

				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rset = null;

				try {
					conn = dbCredentials.getConnection();
					pstmt = conn
							.prepareStatement("SELECT * FROM transaction WHERE recipientmobile=? AND accountUuid=? "
									+ "ORDER BY clienttime  DESC LIMIT ? OFFSET ?;");
					pstmt.setString(1, phone);
					pstmt.setString(2, account.getUuid());
					pstmt.setInt(3, toIndex - fromIndex);
					pstmt.setInt(4, fromIndex);

					rset = pstmt.executeQuery();

					while (rset.next()) {
						transaction = beanProcessor.toBean(rset, Transaction.class);
						//transaction.setId(rset.getInt("id"));

						list.add(transaction);
					}

				} catch (SQLException e) {
					logger.error("SQLException exception while getting transaction with phonenumber ' "
							+ phone + "' from " + fromIndex + " to " + toIndex);
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
	public List<Transaction> getAllTransactionByReceiverPhone(String phone,
			int fromIndex, int toIndex) {
		List<Transaction> list = new LinkedList<>();

		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE recipientmobile=?"
							+ "ORDER BY clienttime  DESC LIMIT ? OFFSET ?;");
			pstmt.setString(1, phone);
			pstmt.setInt(2, toIndex - fromIndex);
			pstmt.setInt(3, fromIndex);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);
				//transaction.setId(rset.getInt("id"));

				list.add(transaction);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting transaction with phonenumber ' "
					+ phone + "' from " + fromIndex + " to " + toIndex);
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
	public List<Transaction> getTransactionByStatusUuid(TransactionStatus transactionStatus) {
		List<Transaction> list = new LinkedList<>();
		Transaction transaction = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT * FROM transaction WHERE transactionstatusuuid=?;");
			pstmt.setString(1, transactionStatus.getUuid());

			rset = pstmt.executeQuery();

			while (rset.next()) {
				transaction = beanProcessor.toBean(rset, Transaction.class);

				list.add(transaction);
			}

		} catch (SQLException e) {
			logger.error("SQL exception while fetching transaction with transactionstatus"
					+ transactionStatus);
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
}

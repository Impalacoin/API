package com.impalapay.airtel.persistence.util;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.transaction.TransactionStatus;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.persistence.GenericDAO;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * Database utilities used for counting.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */
public class CountUtils extends GenericDAO {

	private static CountUtils countUtils;

	private AccountDAO accountDAO;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 *
	 * @return {@link CountUtils}
	 */
	public static CountUtils getInstance() {
		if (countUtils == null) {
			countUtils = new CountUtils();
		}

		return countUtils;
	}

	/**
     *
     */
	protected CountUtils() {
		super();

		accountDAO = AccountDAO.getInstance();
	}

	/**
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public CountUtils(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);

		accountDAO = new AccountDAO(dbName, dbHost, dbUsername, dbPassword,
				dbPort);
	}

	public int getTransactionCount(Account account) {
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn
					.prepareStatement("SELECT count(*) FROM transaction WHERE accountUuid = ?;");
			pstmt.setString(1, account.getUuid());

			rset = pstmt.executeQuery();
			rset.next();
			count = count + rset.getInt(1);

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all transactions count of username  '"
					+ account + "'");
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

		return count;
	}
    
	 /**
     * Gets the count of all transaction requests by delivery status belonging to
     * this account.
     *
     * @param account
     * @param status
     *
     * @return int total count of transaction requests
     */
	public int getTransactionCount(Account account,TransactionStatus status) {
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn
					.prepareStatement("SELECT count(*) FROM transaction WHERE accountUuid = ? AND transactionstatusuuid=?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, status.getUuid());

			rset = pstmt.executeQuery();
			rset.next();
			count = count + rset.getInt(1);

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all transactions count of username  '"
					+ account + "'");
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

		return count;
	}
	
	/**
     * Gets the count of all airtime requests by network operator and status,
     * belonging to this account.
     *
     * @param account
     * @param network
     * @param status
     *
     * @return int the count of all top up activity
     */
    public int getTransactionCount(Account account, Country country, TransactionStatus status) {
        int count = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            conn = dbCredentials.getConnection();

            pstmt = conn.prepareStatement("SELECT COUNT(DISTINCT uuid) FROM transaction WHERE accountuuid = ?"
                    + " AND transactionstatusuuid = ? AND recipientcountryuuid = ?;");
            pstmt.setString(1, account.getUuid());
            pstmt.setString(2, status.getUuid());
            pstmt.setString(3, country.getUuid());

            rset = pstmt.executeQuery();
            rset.next();
            count = rset.getInt(1);

        } catch (SQLException e) {
            logger.error("SQLException exception while getting all transactions of '"
                    + account + "' and '" + status + "' and '" + country + "'.");
            logger.error(ExceptionUtils.getStackTrace(e));

        } finally {
        	if (rset != null) {
                try { rset.close(); } catch (SQLException e) { }
            }

            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) { }
            }

            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }

        return count;
    }
    public int getTransactionCount(Account account, Country country) {
        int count = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            conn = dbCredentials.getConnection();

            pstmt = conn.prepareStatement("SELECT COUNT(DISTINCT uuid) FROM transaction WHERE accountuuid = ?"
                    + " AND recipientcountryuuid = ?;");
            pstmt.setString(1, account.getUuid());
            pstmt.setString(2, country.getUuid());

            rset = pstmt.executeQuery();
            rset.next();
            count = rset.getInt(1);

        } catch (SQLException e) {
            logger.error("SQLException exception while getting all transactions of '"
                    + account + "' and '" + country + "'.");
            logger.error(ExceptionUtils.getStackTrace(e));

        } finally {
        	if (rset != null) {
                try { rset.close(); } catch (SQLException e) { }
            }

            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) { }
            }

            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }

        return count;
    }
	/**
	 * Gets the count of all transaction requests belonging to this account
	 * holder.
	 *
	 * @param username
	 *
	 * @return int total count of transaction requests
	 */
	public int getAllTransactionCount(String username) {
		int count = 0;

		Account account = accountDAO.getAccountName(username);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn
					.prepareStatement("SELECT count(*) FROM transaction WHERE accountUuid = ?;");
			pstmt.setString(1, account.getUuid());

			rset = pstmt.executeQuery();
			rset.next();
			count = count + rset.getInt(1);

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all transactions count of username  '"
					+ account + "'");
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

		return count;
	}

	/**
	 * get the count of all transactions
	 *
	 * 
	 * @return getAllTransactionCount()
	 */

	public int getAllTransactionCount() {
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn.prepareStatement("SELECT count(*) FROM transaction;");
			rset = pstmt.executeQuery();
			rset.next();
			count = rset.getInt(1);

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all transactions count");
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

		return count;
	}

	/**
	 * Gets the value(in usd) of all transaction requests by country and status,
	 * belonging to this account,between a time interval..
	 *
	 * @param account
	 * @param startTime
	 * @param endTime
	 * @return the amount of top up activity
	 */
	 public double getTransactionAmount(Account account, Country country, TransactionStatus status) {
	        int count = 0;
	        
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;

	        try {
	            conn = dbCredentials.getConnection();

	            pstmt = conn.prepareStatement("SELECT SUM(amount) FROM transaction WHERE accountuuid = ?"
	                    + " AND transactionstatusuuid = ? AND recipientcountryuuid = ?;");
	            pstmt.setString(1, account.getUuid());
	            pstmt.setString(2, status.getUuid());
	            pstmt.setString(3, country.getUuid());

	            rset = pstmt.executeQuery();
	            rset.next();
	            count = rset.getInt(1);

	        } catch (SQLException e) {
	            logger.error("SQLException exception while getting all transactios of '"
	                    + account + "' and '" + status + "' and '" + country + "'.");
	            logger.error(ExceptionUtils.getStackTrace(e));

	        } finally {
	        	if (rset != null) {
	                try { rset.close(); } catch (SQLException e) { }
	            }

	            if (pstmt != null) {
	                try { pstmt.close(); } catch (SQLException e) { }
	            }

	            if (conn != null) {
	                try { conn.close(); } catch (SQLException e) { }
	            }
	        }
	        
	        return count;
	    
	    }
	 
	 
	public int getTransactionAmount(Account account, Country country,
			Date startTime, Date endTime) {
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn
					.prepareStatement("SELECT SUM(amount) FROM transaction WHERE accountuuid = ?"
							+ " AND  recipientcountryuuid= ? AND clienttime BETWEEN ? AND ?;");
			pstmt.setString(1, account.getUuid());

			pstmt.setString(2, country.getUuid());
			pstmt.setTimestamp(3, new Timestamp(startTime.getTime()));
			pstmt.setTimestamp(4, new Timestamp(endTime.getTime()));

			rset = pstmt.executeQuery();
			rset.next();
			count = rset.getInt(1);

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all Transaction of '"
					+ account + "'  and '" + country + "'.");
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

		return count;
	}
    
	
	 public int getTransactionAmount(Account account, Country country, TransactionStatus status, Date startTime, Date endTime) {
	        int count = 0;
	        
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;

	        try {
	            conn = dbCredentials.getConnection();

	            pstmt = conn.prepareStatement("SELECT SUM(amount) FROM transaction WHERE accountuuid = ?"
	                    + " AND transactionstatusuuid = ? AND recipientcountryuuid = ? AND clienttime BETWEEN ? AND ?;");
	            pstmt.setString(1, account.getUuid());
	            pstmt.setString(2, status.getUuid());
	            pstmt.setString(3, country.getUuid());
	            pstmt.setTimestamp(4, new Timestamp(startTime.getTime()));
	            pstmt.setTimestamp(5, new Timestamp(endTime.getTime()));

	            rset = pstmt.executeQuery();
	            rset.next();
	            count = rset.getInt(1);

	        } catch (SQLException e) {
	            logger.error("SQLException exception while getting all transactions of '"
	                    + account + "' and '" + status + "' and '" + country + "'.");
	            logger.error(ExceptionUtils.getStackTrace(e));

	        } finally {
	        	if (rset != null) {
	                try { rset.close(); } catch (SQLException e) { }
	            }

	            if (pstmt != null) {
	                try { pstmt.close(); } catch (SQLException e) { }
	            }

	            if (conn != null) {
	                try { conn.close(); } catch (SQLException e) { }
	            }
	        }
	        
	        return count;
	    }
	 
	 
	 public int getTransactionAmounts(Account account, Country country, TransactionStatus status) {
	        int count = 0;
	        
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;

	        try {
	            conn = dbCredentials.getConnection();

	            pstmt = conn.prepareStatement("SELECT SUM(amount) FROM transaction WHERE accountuuid = ?"
	                    + " AND transactionstatusuuid = ? AND recipientcountryuuid = ?;");
	            pstmt.setString(1, account.getUuid());
	            pstmt.setString(2, status.getUuid());
	            pstmt.setString(3, country.getUuid());
	         

	            rset = pstmt.executeQuery();
	            rset.next();
	            count = rset.getInt(1);

	        } catch (SQLException e) {
	            logger.error("SQLException exception while getting all transactions of '"
	                    + account + "' and '" + status + "' and '" + country + "'.");
	            logger.error(ExceptionUtils.getStackTrace(e));

	        } finally {
	        	if (rset != null) {
	                try { rset.close(); } catch (SQLException e) { }
	            }

	            if (pstmt != null) {
	                try { pstmt.close(); } catch (SQLException e) { }
	            }

	            if (conn != null) {
	                try { conn.close(); } catch (SQLException e) { }
	            }
	        }
	        
	        return count;
	    }
	/**
	 * Gets the count of all transaction requests by country belonging to this
	 * account,between a time interval.
	 *
	 * @param account
	 * @param country
	 * @param startTime
	 * @param endTime
	 *
	 * @return int the count of all transaction activity
	 */
	public int getTransactionCount(Account account, Country country,
			Date startTime, Date endTime) {
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn
					.prepareStatement("SELECT COUNT(DISTINCT uuid) FROM transaction WHERE accountuuid = ?"
							+ " AND recipientcountryUuid = ? AND clienttime BETWEEN ? AND ?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, country.getUuid());

			pstmt.setTimestamp(3, new Timestamp(startTime.getTime()));
			pstmt.setTimestamp(4, new Timestamp(endTime.getTime()));

			rset = pstmt.executeQuery();
			rset.next();
			count = rset.getInt(1);

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all transactions count of account '"
					+ account
					+ "'  and '"
					+ country
					+ "' "
					+ "between "
					+ startTime + " and " + endTime);
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

		return count;
	}

	/**
	 * 
	 * @param account
	 * @param uuid
	 * @return the count
	 */
	public int getTransactionByUuidCount(Account account, String uuid) {

		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("SELECT COUNT(DISTINCT uuid) FROM transaction WHERE  uuid=? AND accountuuid = ?;");
			pstmt.setString(1, uuid);
			pstmt.setString(2, account.getUuid());

			rset = pstmt.executeQuery();
			rset.next();
			count = rset.getInt(1);

		} catch (SQLException e) {
			logger.error("SQLException exception while getting count of Transaction  with uuid ' "
					+ uuid);
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

		return count;
	}
	/**
	 * 
	 * @param account
	 * @param uuid
	 * @return the count
	 */
	public int getTransactionByRecipientmobileCount(Account account, String phone) {

		 int count = 0;

	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;
	        try {
	            conn = dbCredentials.getConnection();
	            pstmt = conn.prepareStatement("SELECT COUNT(DISTINCT uuid) FROM transaction WHERE recipientmobile=? AND accountuuid=?;");
	            pstmt.setString(1, phone);
	             pstmt.setString(2, account.getUuid());

	            rset = pstmt.executeQuery();
	            rset.next();
	            count = rset.getInt(1);


	        } catch (SQLException e) {
	            logger.error("SQLException exception while getting count of transaction  with uuid ' " + phone);
	            logger.error(e.toString());

	        } finally {
	        	if (rset != null) {
	                try { rset.close(); } catch (SQLException e) { }
	            }

	            if (pstmt != null) {
	                try { pstmt.close(); } catch (SQLException e) { }
	            }

	            if (conn != null) {
	                try { conn.close(); } catch (SQLException e) { }
	            }
	        }

	        return count;
	}

}

/*
 * * Local Variables:* mode: java* c-basic-offset: 2* tab-width: 2*
 * indent-tabs-mode: nil* End:** ex: set softtabstop=2 tabstop=2 expandtab:*
 */
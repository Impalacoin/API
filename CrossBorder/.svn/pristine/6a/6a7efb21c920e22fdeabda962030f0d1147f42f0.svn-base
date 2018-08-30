package com.impalapay.airtel.accountmgmt.admin.persistence.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.persistence.GenericDAO;

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

	}

	/**
	 * Gets the count of all transactions belonging to all account holders
	 * 
	 * @return the count of all transactions
	 */
	public int getAllTransactionCount() {
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();

			pstmt = conn.prepareStatement("SELECT count(*) FROM transaction ;");

			rset = pstmt.executeQuery();
			rset.next();
			count = count + rset.getInt(1);

		} catch (SQLException e) {
			logger.error("SQLException exception while getting all transactions count ");
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

	 public int getAllTransactionByRecipientMsisdnCount(String msisdn) {
	        int count = 0;


	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;

	        try {
	            conn = dbCredentials.getConnection();


	            pstmt = conn.prepareStatement("SELECT count(*) FROM transaction WHERE recipientmobile=?;");
	            pstmt.setString(1, msisdn);

	            rset = pstmt.executeQuery();
	            rset.next();
	            count = count + rset.getInt(1);


	        } catch (SQLException e) {
	            logger.error("SQLException exception while getting all transaction count matching phone number " + msisdn);
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

	    public int getAllTransactionByUuidCount(String uuid) {
	        int count = 0;

	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;

	        try {
	            conn = dbCredentials.getConnection();


	            pstmt = conn.prepareStatement("SELECT count(*) FROM transaction WHERE uuid=?;");
	            pstmt.setString(1, uuid);

	            rset = pstmt.executeQuery();
	            rset.next();
	            count = count + rset.getInt(1);


	        } catch (SQLException e) {
	            logger.error("SQLException exception while getting all transaction count matching uuid " + uuid);
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
	    
	    public int getAllTransactionByReceiverCountry(Country country){
	    	 int count = 0;


		        Connection conn = null;
		        PreparedStatement pstmt = null;
		        ResultSet rset = null;

		        try {
		            conn = dbCredentials.getConnection();

		            
		            pstmt = conn.prepareStatement("SELECT count(DISTINCT uuid) FROM transaction WHERE recipientcountryuuid=?;");
		            pstmt.setString(1, country.getUuid());

		            rset = pstmt.executeQuery();
		            rset.next();
		            count = count + rset.getInt(1);


		        } catch (SQLException e) {
		            logger.error("SQLException exception while getting all transaction count with receiver country " + country);
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
}

/*
 * * Local Variables:* mode: java* c-basic-offset: 2* tab-width: 2*
 * indent-tabs-mode: nil* End:** ex: set softtabstop=2 tabstop=2 expandtab:*
 */
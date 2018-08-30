package com.impalapay.airtel.persistence.geolocation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.geolocation.CountryMsisdn;
import com.impalapay.airtel.persistence.GenericDAO;


public class CountryMsisdnDAO extends GenericDAO implements AirtelCountryMsisdnDAO {
private static CountryMsisdnDAO countrymsisdnDAO;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return CountryMsisdnDAO
	 */
	public static CountryMsisdnDAO getInstance() {
		if (countrymsisdnDAO == null) {
			countrymsisdnDAO = new CountryMsisdnDAO();
		}

		return countrymsisdnDAO;
	}
    
	
	protected CountryMsisdnDAO() {
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
	public CountryMsisdnDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
		
	}
	
	/**
	 * 
	 */
	@Override
	public CountryMsisdn getCountryMsisdn(String uuid) {
		CountryMsisdn s = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM msisdnbycountry WHERE uuid = ?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				s = beanProcessor.toBean(rset, CountryMsisdn.class);
				//s.setId(rset.getInt("id"));
			}

		} catch (SQLException e) {
			logger.error("SQLException while getting countrymsisdn with uuid '"
					+ uuid + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try { rset.close(); } catch (SQLException e) { }
			}

			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { }
			}

			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { 	}
			}
		}

		return s;
	}
    
	/**
	 * 
	 */
	@Override
	public List<CountryMsisdn> getCountryMsisdn(Account account) {
		List<CountryMsisdn> list = new ArrayList<>();
		CountryMsisdn countrymsisdn = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		BeanProcessor b = new BeanProcessor();

		try {
			conn = dbCredentials.getConnection();

			// Get all msisdn on the account
			pstmt = conn
					.prepareStatement("SELECT * FROM msisdnbycountry WHERE accountUuid=?;");
			pstmt.setString(1, account.getUuid());
			rset = pstmt.executeQuery();

			while (rset.next()) {
				countrymsisdn = b.toBean(rset, CountryMsisdn.class);
				
				list.add(countrymsisdn);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting countrymsisdn of '"
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
	 */
	@Override
	public CountryMsisdn getCountryMsisdn(Account account, Country country) {
		CountryMsisdn c = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM msisdnbycountry WHERE accountUuid=? AND countryuuid=?;");
			pstmt.setString(1, account.getUuid());
			pstmt.setString(2, country.getUuid());

			rset = pstmt.executeQuery();

			if (rset.next()) {
				c = beanProcessor.toBean(rset, CountryMsisdn.class);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting a specific country msisdn for '"
					+ account + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try { rset.close(); } catch (SQLException e) { }
			}

			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) {	}
			}

			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { }
			}
		}

		return c;
	}
	
    
	/**
	 * 
	 */
	@Override
	public boolean putCountryMsisdn(CountryMsisdn countrymsisdn) {
		boolean success = true;
		Date date;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("INSERT INTO msisdnbycountry(uuid,countryuuid,accountuuid,"
							+ "msisdn) VALUES (?, ?, ?, ?);");

			pstmt.setString(1, countrymsisdn.getUuid());
			pstmt.setString(2, countrymsisdn.getCountryUuid());
			pstmt.setString(3, countrymsisdn.getAccountUuid());
			pstmt.setString(4, countrymsisdn.getMsisdn());
			

			pstmt.execute();

		} catch (SQLException e) {
			logger.error("SQLException exception while adding " + countrymsisdn);
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
    
	/**
	 * 
	 */
	@Override
	public boolean updateCountryMsisdn(String uuid, CountryMsisdn a) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM msisdnbycountry WHERE uuid=?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				pstmt2 = conn.prepareStatement("UPDATE msisdnbycountry SET countryuuid=?, "
								+ "accountuuid=?,msisdn=?"
								+ "WHERE uuid=?;");

				pstmt2.setString(1, a.getCountryUuid());
				pstmt2.setString(2, a.getAccountUuid());
				pstmt2.setString(3, a.getMsisdn());
				pstmt2.setString(4, a.getUuid());

				pstmt2.executeUpdate();

			} else {
				putCountryMsisdn(a);
			}

		} catch (SQLException e) {
			logger.error("SQLException when trying to update msisdnbycountry with uuid '" + uuid + "' with " 
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


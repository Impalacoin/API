package com.impalapay.airtel.persistence.sessionlog;

import com.impalapay.airtel.persistence.GenericDAO;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.sessionlog.ClientUrl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;



import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * Persistence implementation for {@link ClientUrl}
 * <p>
 * Copyright (c) impalaPay Ltd.,August 30, 2014
 * 
 * @author <a href="mailto:eugenechimita@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */

public class ClientURLDAO extends GenericDAO implements AirtelClientURL {

	private static ClientURLDAO clientURLDAO;

	private Logger logger = Logger.getLogger(this.getClass());

	private BeanProcessor beanProcessor = new BeanProcessor();
    
	/**
	 * 
	 * @return {@link ClientURLDAO}
	 */
	public static ClientURLDAO getInstance() {
		if (clientURLDAO == null) {
			clientURLDAO = new ClientURLDAO();
		}

		return clientURLDAO;
	}

	/**
	 * 
	 */
	protected ClientURLDAO() {
		super();

	}

	/**
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public ClientURLDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}

	/**
	 * @see com.impalapay.airtel.persistence.sessionlog.ClientURLDAO#putClientUrl(com.impalapay.airtel.beans.sessionlog.ClientUrll)
	 */
	@Override
	public boolean putClientUrl(ClientUrl clienturl) {

		boolean success = true;
		Date date;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbCredentials.getConnection();
			pstmt = conn
					.prepareStatement("INSERT INTO clienturl(uuid,accountuuid,url,dateactive,"
							+ "dateinactive,active) VALUES (?, ?, ?, ?, ?, ?);");

			pstmt.setString(1, clienturl.getUuid());
			pstmt.setString(2, clienturl.getAccountUuid());
			pstmt.setString(3, clienturl.getUrl());
			
			if((date = clienturl.getDateActive()) != null){
			pstmt.setTimestamp(4, new Timestamp(date.getTime()));
			
			} else {
				pstmt.setNull(4, Types.DATE);
			}
			
			if((date = clienturl.getDateInactive()) !=null){
			pstmt.setTimestamp(5, new Timestamp(date.getTime()));
			
			} else {
				pstmt.setNull(5, Types.DATE);
			}
			
			pstmt.setBoolean(6, clienturl.isActive());

			pstmt.execute();

		} catch (SQLException e) {
			logger.error("SQLException exception while adding " + clienturl);
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
	 * @see com.impalapay.airtel.persistence.sessionlog.ClientURLDAO#isActive(com.impalapay.airtel.beans.sessionlog.ClientUrl)
	 */
	@Override
	public boolean isActive(String uuid) {
		boolean active = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT active FROM clienturl WHERE uuid=?;");
			pstmt.setString(1, uuid);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				active = rset.getBoolean("active");
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while checking active ClientURL with uuid '"
					+ uuid + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} finally {
			if (rset != null) {
				try { rset.close();	} catch (SQLException e) { }
			}

			if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) {	}
			}

			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { }
			}
		}

		return active;
	}

	/**
	 * @see com.impalapay.airtel.persistence.sessionlog.ClientURLDAO#deactivate(com.impalapay.airtel.beans.sessionlog.ClientUrl)
	 */
	@Override
	public boolean deactivate(ClientUrl clienturl) {
		boolean success = true;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("UPDATE clienturl SET active=? WHERE uuid=?;");
			pstmt.setBoolean(1, false);
			pstmt.setString(2, clienturl.getUuid());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException while deactivating '" + clienturl + "'");
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
	 * @see com.impalapay.airtel.persistence.sessionlog.ClientURLDAO#getClientUrl(com.impalapay.airtel.beans.accountmgmt.Account)
	 */
	@Override
	public ClientUrl getClientUrl(Account account) {
		ClientUrl c = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM clienturl WHERE accountuuid=? and active=true;");
			pstmt.setString(1, account.getUuid());

			rset = pstmt.executeQuery();

			if (rset.next()) {
				c = beanProcessor.toBean(rset, ClientUrl.class);
			}

		} catch (SQLException e) {
			logger.error("SQLException exception while getting a valid Client URL for '"
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
}
package com.impalapay.airtel.persistence.sessionlog;

import com.impalapay.airtel.persistence.GenericDAO;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.sessionlog.SessionLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;








import javax.validation.Valid;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * Persistence implementation for  {@link SessionLog}.
 * <p>
 * Copyright (c) impalaPay Ltd.,August 30, 2014
 * 
 * @author <a href="mailto:eugenechimita@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class SessionLogDAO extends GenericDAO implements AirtelSessionLog {

	private static SessionLogDAO sessionLogDAO;

	private Logger logger = Logger.getLogger(this.getClass());

	private BeanProcessor beanProcessor = new BeanProcessor();

	/**
	 * 
	 * @return sessionlogDAO
	 */
	public static SessionLogDAO getInstance() {
		if (sessionLogDAO == null) {
			sessionLogDAO = new SessionLogDAO();
		}

		return sessionLogDAO;
	}

	/**
	 * 
	 */
	protected SessionLogDAO() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public SessionLogDAO(String dbName, String dbHost, String dbUsername,
			String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}
    
	
	/**
	 * @see com.impalapay.airtel.persistence.sessionlog.AirtelSessionLog#putSessionLog(com.impalapay.airtel.beans.sessionlog.SessionLog)
	 */
	@Override
	public boolean putSessionLog(SessionLog sessionlog) {
		boolean success = true;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dbCredentials.getConnection();
            pstmt = conn.prepareStatement("INSERT INTO sessionlog(sessionuuid, accountuuid, creationtime, "
            		+ "valid) VALUES (?, ?, ?, ?);");

            pstmt.setString(1,sessionlog.getSessionUuid());
            pstmt.setString(2,sessionlog.getAccountUuid());
            pstmt.setTimestamp(3, new Timestamp(sessionlog.getCreationTime().getTime()));
            pstmt.setBoolean(4, sessionlog.isValid());
            
            pstmt.execute();

        } catch (SQLException e) {
            logger.error("SQLException exception while adding " + sessionlog);
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
	 * @see com.impalapay.airtel.persistence.sessionlog.AirtelSessionLog#isValid(String)
	 */
	@Override
	public boolean isValid(String uuid) {	
		boolean valid = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("SELECT valid FROM sessionlog WHERE sessionuuid=?;");
			pstmt.setString(1, uuid);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				valid = rset.getBoolean("valid");	 
			}
			
		} catch(SQLException e) {
			logger.error("SQLException exception while checking validity of SessionLog with uuid '" + 
					uuid + "'");
			logger.error(ExceptionUtils.getStackTrace(e));
			
		} finally {
		    if(rset != null ) {
               try{rset.close();} catch(SQLException e) {} 
		    }              
		           
		    if(pstmt != null ) {
		        try{pstmt.close();} catch(SQLException e) {} 
		    }
		                
		    if(conn != null ) {
		        try{conn.close( );} catch(SQLException e) {} 
		    }    
		}
				
		return valid;
	}
		
	
	
	/**
	 * @see com.impalapay.airtel.persistence.sessionlog.AirtelSessionLog#inValidate(com.impalapay.airtel.beans.sessionlog.SessionLog)
	 */

	@Override
	public boolean invalidate(SessionLog sessionlog) {	
		boolean success = true;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("UPDATE sessionlog SET valid=? WHERE sessionuuid=?;");
			pstmt.setBoolean(1, false);
			pstmt.setString(2, sessionlog.getSessionUuid());
			
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			logger.error("SQLException while invalidating '" + sessionlog + "'");
			logger.error(ExceptionUtils.getStackTrace(e));
			success = false;
			
		} finally {
		    if(pstmt != null ) {
		        try {pstmt.close();} catch(SQLException e) {} 
		    }
		                
		    if(conn != null ) {
		        try {conn.close();} catch(SQLException e) {} 
		    }    
		}
		
		return success ;  
	}
    
	
	/**
	 * @see com.impalapay.airtel.persistence.sessionlog.AirtelSessionLog#getValidSessionLog(com.impalapay.airtel.beans.accountmgmt.Account)
	 */
	@Override
	public SessionLog getValidSessionLog(Account account) {
	       SessionLog s = null;
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
					
			try {
				conn = dbCredentials.getConnection();	
				pstmt = conn.prepareStatement("SELECT * FROM sessionlog WHERE accountuuid=? and valid=true;");
				pstmt.setString(1, account.getUuid());
				
				rset = pstmt.executeQuery();
				
				if(rset.next()) {				
					s = beanProcessor.toBean(rset, SessionLog.class);		
				}

			} catch(SQLException e) {
				logger.error("SQLException exception while getting a valid SessionLog for '" + account + "'");
				logger.error(ExceptionUtils.getStackTrace(e));
				
			} finally {
			    if(rset != null ) {
	               try{rset.close();} catch(SQLException e) {} 
			    }              
			           
			    if(pstmt != null ) {
			        try{pstmt.close();} catch(SQLException e) {} 
			    }
			                
			    if(conn != null ) {
			        try{conn.close( );} catch(SQLException e) {} 
			    }    
			}
			
			return s;
		}
	
	
	/**
	 * @see com.impalapay.airtel.persistence.sessionlog.AirtelSessionLog#expireSessionLogs(java.util.Date)
	 */
	@Override
	public void expireSessionLogs(Date date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			conn = dbCredentials.getConnection();
			pstmt = conn.prepareStatement("UPDATE sessionlog SET valid=? WHERE creationTime <= ?;");
			pstmt.setBoolean(1, false);
			pstmt.setTimestamp(2, new Timestamp(date.getTime()));
			
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			logger.error("SQLException while session logs with date less than " + date);
			logger.error(ExceptionUtils.getStackTrace(e));
			
		} finally {
		    if(pstmt != null ) {
		        try {pstmt.close();} catch(SQLException e) {} 
		    }
		                
		    if(conn != null ) {
		        try {conn.close();} catch(SQLException e) {} 
		    }    
		}
		
	}
}


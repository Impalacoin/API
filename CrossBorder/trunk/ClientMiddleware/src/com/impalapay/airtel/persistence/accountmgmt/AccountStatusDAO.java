package com.impalapay.airtel.persistence.accountmgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.impalapay.airtel.beans.accountmgmt.AccountStatus;
import com.impalapay.airtel.persistence.GenericDAO;


/**
 * Persistence abstraction for {@link AccountStatus}
 * <p>
 * Copyright (c) impalapay Ltd., June 24, 2014
 * 
 * @author <a href="mailto:eugenechimita@impalapay.com">Eugene Chimita</a>
 */

public class AccountStatusDAO extends GenericDAO implements AirtelAccountStatusDAO {

	private static AccountStatusDAO accountStatusDAO;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private BeanProcessor beanProcessor = new BeanProcessor();
	
	/**
	 * 
	 * @return AccountStatusDAO
	 */
	public static AccountStatusDAO getInstance() {
		if(accountStatusDAO == null) {
			accountStatusDAO = new AccountStatusDAO();
		}
		
		return accountStatusDAO;
	}
	
	
	/**
	 * 
	 */
	protected AccountStatusDAO() {
		super();
	}

	
	/**
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public AccountStatusDAO(String dbName, String dbHost, String dbUsername, String dbPassword, int dbPort) {
		super(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}
	
	
	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.AirtelAccountStatusDAO#getAccountStatus(java.lang.String)
	 */
	@Override
	public AccountStatus getAccountStatus(String uuid) {
		AccountStatus a = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
				
	
		
		try {
			conn = dbCredentials.getConnection();	
			pstmt = conn.prepareStatement("SELECT * FROM accountStatus WHERE uuid = ?;");
			pstmt.setString(1, uuid);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				a = beanProcessor.toBean(rset, AccountStatus.class);
				//a.setId(rset.getInt("accountStatusId"));
			}

		} catch(SQLException e) {
			logger.error("SQLException exception while getting account status with uuid '" + uuid + "'");
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
		
		return a;
		
	}


	/**
	 * @see com.impalapay.airtel.persistence.accountmgmt.AirtelAccountStatusDAO#getAllAccountStatus()
	 */
	@Override
	public List<AccountStatus> getAllAccountStatus() {
		List<AccountStatus> list = new ArrayList<>();
		AccountStatus a = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
			
		
		
		try {
			conn = dbCredentials.getConnection();	
			pstmt = conn.prepareStatement("SELECT * FROM accountStatus;");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				a = beanProcessor.toBean(rset, AccountStatus.class);
				//a.setId(rset.getInt("accountStatusId"));  
				
				list.add(a);
			}

		} catch(SQLException e) {
			logger.error("SQLException exception while getting all AccountStatus.");
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
		
		return list;
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
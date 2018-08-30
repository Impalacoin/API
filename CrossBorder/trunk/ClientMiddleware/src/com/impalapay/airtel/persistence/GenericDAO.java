package com.impalapay.airtel.persistence;

import com.impalapay.airtel.servlet.util.DbPoolUtil;


/**
 * What is common to all Data Access Objects.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class GenericDAO {
    protected DBCredentials dbCredentials;
	
	/**
	 * 
	 */
	public GenericDAO() {
		dbCredentials = DbPoolUtil.getDBCredentials();
	}
	
	
	/**
	 * 
	 * @param dbName
	 * @param dbHost
	 * @param dbUsername
	 * @param dbPassword
	 * @param dbPort
	 */
	public GenericDAO(String dbName, String dbHost, String dbUsername, String dbPassword, int dbPort) {
		dbCredentials = new DBCredentials(dbName, dbHost, dbUsername, dbPassword, dbPort);
	}
	
	
	/**
	 * 
	 */
	public void closeConnections() {
		dbCredentials.closeConnections();
	}

}

package com.impalapay.airtel.servlet.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.impalapay.airtel.persistence.DBCredentials;

import org.apache.log4j.Logger;

/**
 * Utilities dealing with database connection pooling.
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Oct 15, 2012  
 * 
 * @author <a href="mailto:michael@impalapay.com">Michael Wakahe</a>
 * @version %I%, %G%
 * 
 */
public class DbPoolUtil extends HttpServlet {

	private static DBCredentials dbCredentials;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	/**
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        dbCredentials = new DBCredentials();
    }
    
    
    /**
     * @return the database credentials class
     */
    public static DBCredentials getDBCredentials() {
    	return dbCredentials;
    }
    
    
    /**
     * 
     */
    @Override
    public void destroy() {
		logger.info("Now shutting down database pools.");
    	
		dbCredentials.closeConnections();		
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
**/
package com.impalapay.airtel.servlet.init;

import com.impalapay.airtel.scheduledjobs.sessionid.SessionMgmtLauncher;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

/**
 * Description of class.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */

public class ScheduledJobsInit extends HttpServlet {

	private Logger logger;
	
	/**
    *
    * @param config
    * @throws ServletException
    */
   @Override
   public void init(ServletConfig config) throws ServletException {
       super.init(config);
       
       logger = Logger.getLogger(this.getClass());
       
       logger.info("Starting to initialize scheduled jobs");
       
       new SessionMgmtLauncher().start();
       
       logger.info("Have finished initializing scheduled jobs");
   }
   
   

}

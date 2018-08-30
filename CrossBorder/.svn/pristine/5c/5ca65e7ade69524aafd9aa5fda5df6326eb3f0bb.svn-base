package com.impalapay.airtel.servlet.init;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Initializing with the log4j configuration file.
 * <p>
 * Ensure that corresponding entry is set in the web.xml file. 
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class Log4jInit extends HttpServlet {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    
    /**
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        initConfig();
        Logger logger = Logger.getLogger(this.getClass());
        logger.info("Have initialized log4j");
    }

    
    /**
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @throws ServletException, IOException
	 */	
    @Override
    public void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws ServletException, IOException {
    	initConfig();
    	
    	PrintWriter out = servletResponse.getWriter();
    	out.println("Have reloaded log4j settings.");
    	out.close();
    }
    
    
    /**
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @throws ServletException, IOException
	 */	
    @Override
    public void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws ServletException, IOException {
    	doPost(servletRequest, servletResponse);
    }
    
    
    /**
     * 
     */
    private void initConfig() {
    	   String prefix = getServletContext().getRealPath("/");
        String file = getServletConfig().getInitParameter("log4j-init-file");

        // if the log4j-init-file is not set, then no point in trying
        if (file != null) {
            PropertyConfigurator.configure(prefix + file);
        }
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
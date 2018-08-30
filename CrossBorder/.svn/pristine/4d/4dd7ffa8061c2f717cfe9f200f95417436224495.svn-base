package com.impalapay.airtel.servlet.admin;



import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Used to discontinue the session of a logged in Administrator.
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Oct 11, 2013
 *
 * @author <a href="mailto:anthonym@shujaa.co.ke">Anthony Wafula</a>
 * @version %I%, %G%
 *
 */
public class Logout extends HttpServlet {

	/**
	 * 
	 * @param config
	 * @throws ServletException
	 */
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
    }

    
    /**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.sendRedirect("logout.jsp");
		
		// get current session, and don't create one if it doesn't exist
		// get current session, and don't create one if it doesn't exist
	    HttpSession session = request.getSession(false);
	    if(session != null) {
	    	session.invalidate();
	    }  	
	    
	    // Release the lists of incoming and outgoing SMS from memory.
	    // AccountCache.uncacheAccounts();
	}
	
	
	
    /**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException, IOException
	 */	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request, response);
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
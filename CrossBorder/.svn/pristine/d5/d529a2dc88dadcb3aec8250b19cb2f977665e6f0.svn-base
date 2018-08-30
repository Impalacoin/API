package com.impalapay.airtel.servlet.admin.accounts;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.impalapay.airtel.accountmgmt.admin.SessionConstants;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;

import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import com.impalapay.airtel.beans.sessionlog.ClientUrl;
import com.impalapay.airtel.persistence.sessionlog.ClientURLDAO;

/**
 * Servlet that receives parameters from an administrative web form that are
 * used to create a new account.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Nov 24, 2014
 *
 * @author <a href="mailto:mike@impalapay.com">Michael Wakahe</a>
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */
public class AddAccountUrl extends HttpServlet {

	final String ERROR_NO_CLIENTURL = "Please provide a client url.";
	final String ERROR_UNABLE_ADD = "Unable to add client url.";
	

	private String accountUuid, clientUrl;

	// This is used to store parameter names and values from the form.
	private HashMap<String, String> paramHash;
	//private EmailValidator emailValidator;

	private HttpSession session;
	
	private ClientURLDAO clienturlDAO;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		//emailValidator = EmailValidator.getInstance();

		AccountDAO.getInstance();
		
		clienturlDAO = ClientURLDAO.getInstance();

		CacheManager.getInstance();
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 *             , IOException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession(true);

		setClassParameters(request);

		initParamHash();
		session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_URL_PARAMETERS,
				paramHash);

		// No client url provided
		if (StringUtils.isBlank(clientUrl)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_URL_ERROR_KEY,
					ERROR_NO_CLIENTURL);

			// The email already exists in the system

		}else if(!addAccountUrl()){
            session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_URL_ERROR_KEY, ERROR_UNABLE_ADD);
        
        } else {
            // If we get this far then all parameter checks are ok.		
            session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_URL_SUCCESS_KEY, "s");

            // Reduce our session data
            session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_URL_PARAMETERS, null);
            session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_URL_ERROR_KEY, null);

            
        }


        response.sendRedirect("addAccount.jsp");

    }
    
    
    /**
     * Add airtime purchased in bulk
     * 
     * @return boolean indicating if client purchase has
     * been added or not.
     */
    private boolean addAccountUrl() {
        ClientUrl p = new ClientUrl();
        
        p.setAccountUuid(accountUuid);
        p.setActive(true);
        p.setUrl(clientUrl);
        p.setDateActive(new Date());
    
        return clienturlDAO.putClientUrl(p);

    }

		 /**
     * Set the class variables that represent form parameters.
     *
     * @param request
     */
    private void setClassParameters(HttpServletRequest request) {
    	accountUuid = StringUtils.trimToEmpty(request.getParameter("accountUuid"));
    	clientUrl = StringUtils.trimToEmpty(request.getParameter("clientUrl"));
        
    }

    /**
     * Place all the received parameters in our class HashMap.
     *
     */
    private void initParamHash() {
        paramHash = new HashMap<>();

        paramHash.put("clienturl", clientUrl);
      
    }

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 *             , IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		// response.sendRedirect("../index.jsp");
	}
}

/*
 * * Local Variables:* mode: java* c-basic-offset: 2* tab-width: 2*
 * indent-tabs-mode: nil* End:** ex: set softtabstop=2 tabstop=2 expandtab:*
 */
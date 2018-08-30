package com.impalapay.airtel.servlet.admin.accounts;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.impalapay.airtel.accountmgmt.admin.SessionConstants;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.util.SecurityUtil;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

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
public class AddAccount extends HttpServlet {

	final String ERROR_NO_FIRSTNAME = "Please provide a First Name.";
	final String ERROR_NO_USERNAME = "Please provide a Username.";
	final String ERROR_NO_APIUSERNAME = "Please provide the api Username.";
	final String ERROR_INVALID_EMAIL = "Please provide a valid email address.";
	final String ERROR_NO_LOGIN_PASSWD = "Please provide a website login password.";
	final String ERROR_LOGIN_PASSWD_MISMATCH = "The website login passwords that you have provided do not match.";
	final String ERROR_NO_API_PASSWD = "Please provide an API password.";
	final String ERROR_API_PASSWD_MISMATCH = "The API passwords that you have provided do not match.";
	final String ERROR_UNIQUENAME_EXISTS = "The Unique Name provided already exists in the system.";
	final String ERROR_EMAIL_EXISTS = "The email provided already exists in the system.";

	private String firstName, lastName, username, email, loginPasswd,
			loginPasswd2,apiUsername, apiPasswd, apiPasswd2, phone, accountStatusUuid,
			countryUuid;

	// This is used to store parameter names and values from the form.
	private HashMap<String, String> paramHash;
	private EmailValidator emailValidator;

	private AccountDAO accountDAO;

	private CacheManager cacheManager;
	private HttpSession session;

	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		emailValidator = EmailValidator.getInstance();

		accountDAO = AccountDAO.getInstance();

		cacheManager = CacheManager.getInstance();
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
		session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_PARAMETERS,
				paramHash);

		// No First Name provided
		if (StringUtils.isBlank(firstName)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_NO_FIRSTNAME);

			// No Unique Name provided
		} else if (StringUtils.isBlank(username)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_NO_USERNAME);

			// An invalid email provided
		} else if (!emailValidator.isValid(email)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_INVALID_EMAIL);

			// No website login password provided
		} else if (StringUtils.isBlank(loginPasswd)
				|| StringUtils.isBlank(loginPasswd2)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_NO_LOGIN_PASSWD);

			// The website login passwords provided do not match
		} else if (!StringUtils.equals(loginPasswd, loginPasswd2)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_LOGIN_PASSWD_MISMATCH);
        
		    // No api username provided
		} else if (StringUtils.isBlank(apiUsername)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					 ERROR_NO_APIUSERNAME);
						
			// No API password provided
		} else if (StringUtils.isBlank(apiPasswd)
				|| StringUtils.isBlank(apiPasswd2)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_NO_API_PASSWD);

			// The API passwords provided do not match
		} else if (!StringUtils.equals(apiPasswd, apiPasswd2)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_API_PASSWD_MISMATCH);

			// The Unique Name already exists in the system
		} else if (existsUniqueName(username)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_UNIQUENAME_EXISTS);

			// The email already exists in the system
		} else if (existsEmail(email)) {
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					ERROR_EMAIL_EXISTS);

		} else {
			// If we get this far then all parameter checks are ok.
			session.setAttribute(
					SessionConstants.ADMIN_ADD_ACCOUNT_SUCCESS_KEY, "s");

			// Reduce our session data
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_PARAMETERS,
					null);
			session.setAttribute(SessionConstants.ADMIN_ADD_ACCOUNT_ERROR_KEY,
					null);

			addAccount();
		}

		response.sendRedirect("addAccount.jsp");
	}

	/**
     *
     */
	private void addAccount() {
		Account a = new Account();

		a.setFirstName(firstName);
		a.setLastName(lastName);
		a.setUsername(username);
		a.setEmail(email);
		a.setLoginPasswd(SecurityUtil.getMD5Hash(loginPasswd));
		a.setApiUsername(apiUsername);
		a.setApiPasswd(apiPasswd);
		a.setPhone(phone);
		a.setAccountStatusUuid(accountStatusUuid);

		accountDAO.addAccount(a);

		a = accountDAO.getAccountEmail(email); // Ensures the account is
												// populated with the correct ID
		updateAccountCache(a);
	}

	/**
	 *
	 * @param acc
	 */
	private void updateAccountCache(Account acc) {
		cacheManager.getCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME).put(
				new Element(acc.getEmail(), acc));
		cacheManager.getCache(CacheVariables.CACHE_ACCOUNTS_BY_UUID).put(
				new Element(acc.getUuid(), acc));

	}

	 /**
     * Set the class variables that represent form parameters.
     *
     * @param request
     */
    private void setClassParameters(HttpServletRequest request) {
        firstName = StringUtils.trimToEmpty(request.getParameter("firstName"));
        lastName = StringUtils.trimToEmpty(request.getParameter("lastName"));
        username = StringUtils.trimToEmpty(request.getParameter("username"));
        email = StringUtils.trimToEmpty(request.getParameter("email"));
        loginPasswd = StringUtils.trimToEmpty(request.getParameter("loginPasswd"));
        loginPasswd2 = StringUtils.trimToEmpty(request.getParameter("loginPasswd2"));
        apiUsername = StringUtils.trimToEmpty(request.getParameter("apiUsername"));
        apiPasswd = StringUtils.trimToEmpty(request.getParameter("apiPasswd"));
        apiPasswd2 = StringUtils.trimToEmpty(request.getParameter("apiPasswd2"));
        phone = StringUtils.trimToEmpty(request.getParameter("phone"));
        accountStatusUuid = request.getParameter("accountStatus");
        
    }

    /**
     * Place all the received parameters in our class HashMap.
     *
     */
    private void initParamHash() {
        paramHash = new HashMap<>();

        paramHash.put("firstName", firstName);
        paramHash.put("lastName", lastName);
        paramHash.put("username", username);
        paramHash.put("email", email);
        paramHash.put("loginPasswd", loginPasswd);
        paramHash.put("loginPasswd2", loginPasswd2);
        paramHash.put("apiUsername", apiUsername);
        paramHash.put("apiPasswd", apiPasswd);
        paramHash.put("apiPasswd2", apiPasswd2);
        paramHash.put("phone", phone);
    }

	/**
	 *
	 * @param name
	 * @return whether or not the unique name exists in the system
	 */
	private boolean existsUniqueName(final String name) {
		boolean exists = false;

		if (accountDAO.getAccountName(name) != null) {
			exists = true;
		}

		return exists;
	}

	/**
	 *
	 * @param email
	 * @return whether or not the unique name exists in the system
	 */
	private boolean existsEmail(final String email) {
		boolean exists = false;

		if (accountDAO.getAccountEmail(email) != null) {
			exists = true;
		}

		return exists;
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
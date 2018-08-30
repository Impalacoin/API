package com.impalapay.airtel.servlet.accountmgmt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

import com.impalapay.airtel.accountmgmt.session.SessionConstants;
import com.impalapay.airtel.accountmgmt.session.SessionStatistics;
import com.impalapay.airtel.accountmgmt.session.SessionStatisticsFactory;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.logincount.LoginCount;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.persistence.accountmgmt.LoginCountDAO;
import com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalanceDAO;
import com.impalapay.airtel.persistence.accountmgmt.balance.AccountPurchaseDAO;
//import com.impalapay.airtel.persistence.accountmgmt.airtime.AirtimeBalanceDAO;
//import com.impalapay.airtel.persistence.accountmgmt.airtime.AirtimePurchaseDAO;
import com.impalapay.airtel.servlet.util.PropertiesConfig;
import com.impalapay.airtel.util.SecurityUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Called by the login page to validate a login to a user account.
 * <p>
 * Copyright (c) Impalapay ., June 12, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */

public class Login extends HttpServlet {

	private Logger logger;

	// Error message provided when incorrect captcha is submitted
	final String ACCOUNT_SIGN_IN_BAD_CAPTCHA = "Sorry, the characters you entered did not "
			+ "match those provided in the image. Please try again.";

	private BasicTextEncryptor textEncryptor;

	private String hiddenCaptchaStr = "";

	private Cache accountsCache, statisticsCache, purchasesCache,
			balancesCache;

  private AccountPurchaseDAO purchaseDAO;
  private AccountBalanceDAO balanceDAO;
  private LoginCountDAO logincountDAO;
	/**
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(PropertiesConfig
				.getConfigValue("ENCRYPT_PASSWORD"));

		CacheManager mgr = CacheManager.getInstance();

		accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);
		statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_USERNAME);
		// purchasesCache =
		// mgr.getCache(CacheVariables.CACHE_CLIENTPURCHASE_BY_ACCOUNTUUID);
		// balancesCache =
		// mgr.getCache(CacheVariables.CACHE_CLIENTBALANCE_BY_ACCOUNTUUID);

		 balanceDAO = AccountBalanceDAO.getInstance();
		 purchaseDAO = AccountPurchaseDAO.getInstance();
		 logincountDAO = LoginCountDAO.getInstance();

		logger = Logger.getLogger(this.getClass());
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
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate(); // This is in case the user had previously
									// signed
			// in and his/her session is still active.
		}

		session = request.getSession(true);

		Account account = new Account();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		hiddenCaptchaStr = request.getParameter("captchaHidden");
		String captchaAnswer = request.getParameter("captchaAnswer").trim();

		Element element;

		if ((element = accountsCache.get(username)) != null) {
			account = (Account) element.getObjectValue();
		}

		if (account != null) {
			// Check that the system generated captcha and the user input for
			// the captcha match
			if (!validateCaptcha(hiddenCaptchaStr, captchaAnswer)) {
				session.setAttribute(
						SessionConstants.ACCOUNT_SIGN_IN_ERROR_KEY,
						ACCOUNT_SIGN_IN_BAD_CAPTCHA);
				response.sendRedirect("index.jsp");

				// System.out.println("best login");

			} else {
				// Correct login
				//check to see if the account status is active
				if(StringUtils.equals(account.getAccountStatusUuid(),PropertiesConfig.getConfigValue("ACCOUNT_ACTIVE_UUID"))){
					if (StringUtils.equals(SecurityUtil.getMD5Hash(password),
							account.getLoginPasswd())) {
						session.setAttribute(SessionConstants.ACCOUNT_SIGN_IN_KEY,
								username);

						updateCache(account);
						session.setAttribute(SessionConstants.ACCOUNT_SIGN_IN_TIME,
								String.valueOf(new Date().getTime()));
                        //reset logincount
						logincountDAO.resetLoginCount(account);
						response.sendRedirect("client/index.jsp");

						// Incorrect login, password not matching
					} else {
						//check for count
			            LoginCount logincount = logincountDAO.getLoginCount(account);
			            int count = logincount.getCountlogin();
			            	if(count > 3){
			            		account.setAccountStatusUuid("0b539b9f-8ad1-4c33-910a-642d70012def");
			            		session.setAttribute(
			            		SessionConstants.ACCOUNT_SIGN_IN_ERROR_KEY,
						        SessionConstants.ACCOUNT_DEACTIVATED);
						        response.sendRedirect("index.jsp");
		                     }else{
			            		//increment count
								session.setAttribute(
								SessionConstants.ACCOUNT_SIGN_IN_ERROR_KEY,
								SessionConstants.ACCOUNT_SIGN_IN_WRONG_PASSWORD);
								logincountDAO.incrementLoginCount(account);
								response.sendRedirect("index.jsp");
		                     }
					}
					
				}else{
				//response for when the account status is inactiveeeeeee
				        session.setAttribute(
						SessionConstants.ACCOUNT_SIGN_IN_ERROR_KEY,
						SessionConstants.ACCOUNT_DEACTIVATED);
						response.sendRedirect("index.jsp");
				     }
			}

			// This is also an incorrect login whereby the email does not exist.
		} else { // end 'if(account != null)'
			
			session.setAttribute(SessionConstants.ACCOUNT_SIGN_IN_ERROR_KEY,
					SessionConstants.ACCOUNT_SIGN_IN_NO_EMAIL);
			logincountDAO.incrementLoginCount(account);
			response.sendRedirect("index.jsp");
		}
	}

	/**
	 * To be called after an account holder has successfully logged in.
	 *
	 * @param account
	 */
	protected void updateCache(Account account) {
		// Cache statistics about this account
		SessionStatistics statistics = SessionStatisticsFactory
				.getSessionStatistics(account);
		

		statisticsCache.put(new Element(account.getUsername(), statistics)); // Username
																				// as
																				// the
																				// key

		// purchasesCache.put(new Element(account.getUuid(),
		// purchaseDAO.getClientPurchases(account)));

		// balancesCache.put(new Element(account.getUuid(),
		// balanceDAO.getClientBalances(account)));

	}

	/**
	 * Checks to see that the captcha generated by the person and the captcha
	 * submitted are equal. Case is ignored.
	 *
	 * @param systemCaptcha
	 * @param userCaptcha
	 * @return boolean
	 */
	private boolean validateCaptcha(String encodedSystemCaptcha,
			String userCaptcha) {
		boolean valid = false;
		String decodedHiddenCaptcha = "";

		try {
			decodedHiddenCaptcha = textEncryptor.decrypt(URLDecoder.decode(
					encodedSystemCaptcha, "UTF-8"));

		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException while trying to validate captcha.");
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		if (StringUtils.equalsIgnoreCase(decodedHiddenCaptcha, userCaptcha)) {
			valid = true;
		}

		return valid;
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
	}
}

/*
 * * Local Variables:* mode: java* c-basic-offset: 2* tab-width: 2*
 * indent-tabs-mode: nil* End:** ex: set softtabstop=2 tabstop=2 expandtab:*
 */
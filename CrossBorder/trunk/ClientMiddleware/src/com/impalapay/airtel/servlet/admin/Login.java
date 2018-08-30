package com.impalapay.airtel.servlet.admin;

import com.impalapay.airtel.accountmgmt.admin.SessionConstants;
import com.impalapay.airtel.accountmgmt.admin.SessionStatisticsFactory;
import com.impalapay.airtel.accountmgmt.session.SessionStatistics;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalanceDAO;
import com.impalapay.airtel.persistence.accountmgmt.balance.AccountPurchaseDAO;
import com.impalapay.airtel.servlet.util.PropertiesConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Processes a login request from the admin index page. The admin username and
 * password are stored in the config.properties file.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Nov 26, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */
public class Login extends HttpServlet {

	// Error message provided when incorrect captcha is submitted
	final String ACCOUNT_SIGN_IN_BAD_CAPTCHA = "Sorry, the characters you entered did not "
			+ "match those provided in the image. Please try again.";

	private BasicTextEncryptor textEncryptor;
	private String hiddenCaptchaStr = "";

	private Cache accountsCache, statisticsCache, statisticsByUsernameCache,
			masterpurchasesCache, masterbalancesCache;

	private AccountPurchaseDAO purchaseDAO;
	private AccountBalanceDAO balanceDAO;

	private List<Account> accountsList;

	private Logger logger;

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
		statisticsCache = mgr
				.getCache(CacheVariables.CACHE_ALL_ACCOUNTS_STATISTICS);
		masterpurchasesCache = mgr
				.getCache(CacheVariables.CACHE_MASTERPURCHASE);
		masterbalancesCache = mgr.getCache(CacheVariables.CACHE_MASTERBALANCE);
		Element element;
		List keys;

		keys = accountsCache.getKeysWithExpiryCheck();
		accountsList = new ArrayList<>();

		for (Object key : keys) {
			element = accountsCache.get(key);
			accountsList.add((Account) element.getObjectValue());
		}

		balanceDAO = AccountBalanceDAO.getInstance();
		purchaseDAO = AccountPurchaseDAO.getInstance();

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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		String username = StringUtils.trimToEmpty(request
				.getParameter("username"));
		String password = StringUtils.trimToEmpty(request
				.getParameter("password"));

		hiddenCaptchaStr = request.getParameter("captchaHidden");
		String captchaAnswer = request.getParameter("captchaAnswer").trim();

		if (!validateCaptcha(hiddenCaptchaStr, captchaAnswer)) {
			session.setAttribute(SessionConstants.ADMIN_SIGN_IN_ERROR_KEY,
					ACCOUNT_SIGN_IN_BAD_CAPTCHA);
			response.sendRedirect("index.jsp");

		} // The username supplied does not match what is in the config file
		else if (!StringUtils.equals(username,
				PropertiesConfig.getConfigValue("ADMIN_USERNAME"))) {
			session.setAttribute(SessionConstants.ADMIN_SIGN_IN_ERROR_KEY,
					SessionConstants.ADMIN_SIGN_IN_ERROR_KEY);
			response.sendRedirect("index.jsp");

			// The password supplied does not match what is in the config file
		} else if (!StringUtils.equals(password,
				PropertiesConfig.getConfigValue("ADMIN_PASSWORD"))) {
			session.setAttribute(SessionConstants.ADMIN_SIGN_IN_ERROR_KEY,
					SessionConstants.ADMIN_SIGN_IN_ERROR_VALUE);
			response.sendRedirect("index.jsp");

			// The login is correct
		} else {
			session.setAttribute(SessionConstants.ADMIN_SESSION_KEY, "admin");
			session.setAttribute(SessionConstants.ADMIN_LOGIN_TIME_KEY,
					new Date());

			response.sendRedirect("dashboard.jsp");

			initCache();

		}
	}

	/**
     *
     */
	private void initCache() {
		// Cache statistics of all account
		SessionStatistics statistics = SessionStatisticsFactory
				.getSessionStatistics();

		masterpurchasesCache.put(new Element(
				CacheVariables.CACHE_MASTERPURCHASE_KEY, purchaseDAO
						.getMasterFloat()));

		masterbalancesCache.put(new Element(
				CacheVariables.CACHE_MASTERBALANCE_KEY, balanceDAO
						.getMasterAccountBalances()));

		statisticsCache.put(new Element(
				CacheVariables.CACHE_ALL_ACCOUNTS_STATISTICS_KEY, statistics));

	}

	/**
	 * Checks to see that the captcha generated by the person and the captcha
	 * submitted are equal. Case is ignored.
	 *
	 * @param systemCaptcha
	 * @param userCaptchath
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
			logger.error("UnsupportedEncodingException while validating administrator captcha.");
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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}

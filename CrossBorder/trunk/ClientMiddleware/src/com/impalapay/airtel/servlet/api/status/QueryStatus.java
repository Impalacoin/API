package com.impalapay.airtel.servlet.api.status;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.impalapay.airtel.servlet.api.APIConstants;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.util.SecurityUtil;
import com.impalapay.airtel.beans.transaction.Transaction;
import com.impalapay.airtel.beans.transaction.TransactionStatus;
import com.impalapay.airtel.persistence.transaction.TransactionDAO;
import com.impalapay.airtel.persistence.transaction.TransactionStatusDAO;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Allows for querying of status through an HTTP API.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class QueryStatus extends HttpServlet {

	private Cache accountsCache;

	private SessionLogDAO sessionlogDAO;

	private TransactionDAO transactionDAO;
	
	private TransactionStatusDAO transactionstatusDAO;
	

	/**
	 * 
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		CacheManager mgr = CacheManager.getInstance();
		accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);

		sessionlogDAO = SessionLogDAO.getInstance();
		transactionDAO = TransactionDAO.getInstance();
		transactionstatusDAO =TransactionStatusDAO.getInstance();
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
		OutputStream out = response.getOutputStream();

		response.setContentType("text/plain;charset=UTF-8");
		response.setDateHeader("Expires", new Date().getTime()); // Expiration
																	// date
		response.setDateHeader("Date", new Date().getTime()); // Date and time
																// that the
																// message was
																// sent

		out.write(checkStatus(request).getBytes());
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String checkStatus(HttpServletRequest request) throws IOException {
		Account account = null;

		// joined json string
		String join = "";
		JsonElement root = null;

		// These represent parameters received over the network
		String username = "", sessionid = "", referencenumber = "";
		
		// Get all parameters
		List<String> lines = IOUtils.readLines(request.getReader());

		// used to format/join incoming JSon string
		join = StringUtils.join(lines.toArray(), "");

		try {
			// parse the JSon string
			root = new JsonParser().parse(join);

		} catch (Exception e) {
		
			return APIConstants.COMMANDSTATUS_INVALID_PARAMETERS;
		}

		username = root.getAsJsonObject().get("api_username").getAsString();
		sessionid = root.getAsJsonObject().get("session_id").getAsString();
		referencenumber = root.getAsJsonObject().get("reference_number").getAsString();

		// instantiate the JSon
		Gson g = new Gson();
		Map<String, String> expected = new HashMap<>();

		// check for the presence of all required parameters
		if (StringUtils.isBlank(username) || StringUtils.isBlank(sessionid)
				|| StringUtils.isBlank(referencenumber)) {

			expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
			String jsonResult = g.toJson(expected);

			return jsonResult;
		}

		// Retrieve the account details then check against username and sessionid
		Element element;
		if ((element = accountsCache.get(username)) != null) {
			account = (Account) element.getObjectValue();
		}

		// unknown username
		if (account == null) {
			expected.put("command_status",APIConstants.COMMANDSTATUS_UNKNOWN_USERNAME);
			String jsonResult = g.toJson(expected);

			return jsonResult;
		}

		// test for invalid sessionid
		SessionLog sessionlog = sessionlogDAO.getValidSessionLog(account);
		String session = sessionlog.getSessionUuid();
		
		if (!StringUtils.equals(SecurityUtil.getMD5Hash(sessionid), session)) {
			expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_SESSIONID);
			String jsonResult = g.toJson(expected);

			return jsonResult;

		}

		// At this point we check to see if there is no transaction with the
		// given reference number.
		List<Transaction> transaction = transactionDAO.getTransactionstatus(referencenumber);
		
		if (transaction.size() == 0) {
			expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_REFERENCENUMBER);
			String jsonResult = g.toJson(expected);

			return jsonResult;

		}

		Transaction transactions = transactionDAO.getTransactionstatus1(referencenumber);
		String transactionref = transactions.getReferenceNumber();
		
		String transtatusuuid = transactions.getTransactionStatusUuid();
		TransactionStatus transactionstatus = transactionstatusDAO.getTransactionStatus(transtatusuuid);
		String status = transactionstatus.getStatus();

		// This means that everything is ok
		String transactionuuid = transactions.getUuid();
		double amount = transactions.getAmount();
		String finalamount = String.valueOf(amount);
		String sender = transactions.getSenderName();
		// String receivermobile = transactions.getRecipientMobile();

		expected.put("transaction_id", transactionuuid);
		expected.put("transaction_amount", finalamount);
		expected.put("sender_name", sender);
		expected.put("api_username", username);
		expected.put("transaction_status", status);
		expected.put("command_status", APIConstants.COMMANDSTATUS_OK);

		String jsonResult = g.toJson(expected);

		return jsonResult;

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

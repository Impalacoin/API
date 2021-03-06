package com.impalapay.airtel.servlet.api.forex;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.impalapay.airtel.servlet.api.APIConstants;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.util.SecurityUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

/**
 * Allows for querying of forex through an HTTP API.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class QueryForex extends HttpServlet {

	private Cache accountsCache;

	private SessionLogDAO sessionlogDAO;

	// linked hashmap containing the forex values
	public LinkedHashMap<String, Double> forex;

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
		

		// Put elements to the map
		forex = new LinkedHashMap<String, Double>();

		forex.put("KES", new Double(87.75));
		forex.put("UGX", new Double(2630.00));
		forex.put("RWF", new Double(689.00));
		forex.put("NGN", new Double(161.77));
		forex.put("XOF", new Double(488.28));
		forex.put("XAF", new Double(489.34));
		forex.put("GHS", new Double(490.22));
		forex.put("MGA", new Double(491.00));
		forex.put("MWK", new Double(3.42));
		forex.put("SCR", new Double(395.08));
		forex.put("TZS", new Double(1658.34));
		forex.put("ZWM", new Double(5251.22));
		forex.put("SLL", new Double(4270.00));
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

		out.write(checkForex(request).getBytes());
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String checkForex(HttpServletRequest request) throws IOException {
		Account account = null;

		// These represent parameters received over the network
		String username = "", sessionid = "", currencycode = "";
		String join = "";
		JsonElement root = null;

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
		currencycode = root.getAsJsonObject().get("currency_code")
				.getAsString();
		// instantiate the JSon
		Gson g = new Gson();
		Map<String, String> expected = new HashMap<>();

		// check for the presence of all required parameters
		if (StringUtils.isBlank(username) || StringUtils.isBlank(sessionid)) {

			expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
			String jsonResult = g.toJson(expected);

			return jsonResult;
		}

		// Retrieve the account details then check against username and
		// sessionid
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

		// linked hashmap containing the forex values

		// check if the sent currency code is valid

		boolean exists = forex.containsKey(currencycode);

		if (exists != true || StringUtils.isBlank(currencycode)) {
			expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_CURRENCYCODE);
			String jsonResult = g.toJson(expected);

			return jsonResult;
		}

		// This means that everything is ok
		double exchangerate = forex.get(currencycode).doubleValue();
		
		// convert from double to string
		String stringexchangerate = String.valueOf(exchangerate);
		
		// time when the response is made.
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ssXXX");
		

		expected.put("api_username", username);
		expected.put("amount", stringexchangerate);
		expected.put("server_datetime", ft.format(dNow));
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

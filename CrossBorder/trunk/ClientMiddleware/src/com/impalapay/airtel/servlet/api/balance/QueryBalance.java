package com.impalapay.airtel.servlet.api.balance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.impalapay.airtel.servlet.api.APIConstants;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.balance.ClientAccountBalanceByCountry;
import com.impalapay.airtel.beans.accountmgmt.balance.MasterAccountBalance;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.util.SecurityUtil;
import com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalanceDAO;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.io.OutputStream;
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

/**
 * Allows for querying of balance through an HTTP API.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class QueryBalance extends HttpServlet {

	private Cache accountsCache,countryCache;

	private SessionLogDAO sessionlogDAO;
	
	private AccountBalanceDAO accountbalanceDAO;
	
	private HashMap<String,String> countryHash = new HashMap<>();

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
		
		accountbalanceDAO = AccountBalanceDAO.getInstance();
		
		countryCache = mgr.getCache(CacheVariables.CACHE_COUNTRY_BY_UUID);
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

		out.write(checkBalance(request).getBytes());
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String checkBalance(HttpServletRequest request) throws IOException {
		Account account = null;
		// joined json string
		String join = "";
		JsonElement root = null;

		// These represent parameters received over the network
		String username = "", sessionid = "";
		
		// Get all parameters, the keys of the parameters are specified
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
		// instantiate the JSon
		//Note
		//The = sign is encoded to \u003d. Hence you need to use disableHtmlEscaping().
		Gson g = new GsonBuilder().disableHtmlEscaping().create();
		//Gson g = new Gson();
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
		//fetch the main float balance
		MasterAccountBalance accountbalance = accountbalanceDAO.getMasterAccountBalance(account);
		double floatbalance =accountbalance.getBalance();
		String stringfloatbalance = String.valueOf(floatbalance);
		
		//fetch balance by country
		List<ClientAccountBalanceByCountry> clientBalances = accountbalanceDAO.getClientBalanceByCountry(account);
		
			List keys;
			//fetch from cache 
			Country country;
			keys = countryCache.getKeys();
		//place country uuid and country code in a hashmap
	    for(Object key : keys){
	    	element = countryCache.get(key);
	    	country =(Country) element.getObjectValue();
	    	countryHash.put(country.getUuid(), country.getCountrycode());
	    }
		
		//convert the resultant list to a hashmap.
		
		Map<String,String> balancemap = new LinkedHashMap<>();
		 
	      for (ClientAccountBalanceByCountry balance : clientBalances)
	      balancemap.put(countryHash.get(balance.getCountryUuid()),String.valueOf(balance.getBalance()));
	      
	      //converting hashmap to string and remove {}
	      
	      String newString = balancemap.toString().replaceAll("[{}]", " ");
	      String modified = newString.replace("=", "-");
	      
		
		//ClientAccountBalance accountbalance = accountbalanceDAO.getClientBalance1(account);
		//int balance = accountbalance.getBalance();
		
		//String finalbalance = String.valueOf(balance);
		
		// This means everything is ok.
	    expected.put("api_username", username);
	    expected.put("float", stringfloatbalance);
		expected.put("balance",  modified);
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

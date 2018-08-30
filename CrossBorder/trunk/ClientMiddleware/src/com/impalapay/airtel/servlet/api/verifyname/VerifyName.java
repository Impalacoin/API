package com.impalapay.airtel.servlet.api.verifyname;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.impalapay.airtel.servlet.api.APIConstants;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.util.SecurityUtil;
import com.impalapay.airtel.util.net.PostMinusThread;
import com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalanceDAO;

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
 * Allows for verifying of subscriber details through an HTTP API.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class VerifyName extends HttpServlet {

	private PostMinusThread postMinusThread;

	private Cache accountsCache,countryCache;

	private SessionLogDAO sessionlogDAO;
	
	
	private HashMap<String,String> countryHash = new HashMap<>();
	
	private HashMap<String, String> countryIp = new HashMap<>();
	
	private Map<String,String> toairtel = new HashMap< >();
	
	private String CLIENT_URL ="";

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

		out.write(checkName(request).getBytes());
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String checkName(HttpServletRequest request) throws IOException {
		
		Account account = null;
		
		// joined json string
		String join = "";
		JsonElement root = null;

		// These represent parameters received over the network
		String username = "", sessionid = "",countrycode = "",firstname ="",secondname="",lastname="",msisdn="";
		
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
		
		countrycode = root.getAsJsonObject().get("country_code").getAsString();
		
		firstname = root.getAsJsonObject().get("first_name").getAsString();
		
		secondname = root.getAsJsonObject().get("second_name").getAsString();
		
		lastname = root.getAsJsonObject().get("last_name").getAsString();
		
		msisdn = root.getAsJsonObject().get("mobile_number").getAsString();
		
		//===========================================================================
		// instantiate the JSon
		//Note
		//The = sign is encoded to \u003d. Hence you need to use disableHtmlEscaping().
		//============================================================================
		
		Gson g = new GsonBuilder().disableHtmlEscaping().create();
		//Gson g = new Gson();
		Map<String, String> expected = new HashMap<>();

		// check for the presence of all required parameters
		if (StringUtils.isBlank(username) || StringUtils.isBlank(sessionid)|| StringUtils.isBlank(countrycode)|| StringUtils.isBlank(firstname)
				|| StringUtils.isBlank(lastname)|| StringUtils.isBlank(msisdn)) {
			expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
			String jsonResult = g.toJson(expected);

			return jsonResult;
		}
		
         //=====================================================================
		//Retrieve the account details then check against username and sessionid
		//======================================================================
		
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
		
		List keys;
		
		//fetch from cache 
		Country country;
		keys = countryCache.getKeys();
		
		//place country uuid and country code in a hashmap
		for(Object key : keys) {
		    element = countryCache.get(key);
		    country = (Country) element.getObjectValue();
		    countryHash.put(country.getCountrycode(), country.getCurrencycode());	        
		}
		
		//country and country verify beneficiary ip
	    for(Object key : keys){
	    	element = countryCache.get(key);
	    	country =(Country) element.getObjectValue();
	    	countryIp.put(country.getCountrycode(), country.getCountryverifyip());
	    }
	    
	    if (!countryHash.containsKey(countrycode)) {
 			expected.put("command_status", APIConstants.COMMANDSTATUS_INVALID_COUNTRYCODE);

 			return g.toJson(expected);
 		}
	    
	    	//retrieve the countryip to be used as URL
	  		String countryverifyip = countryIp.get(countrycode);
	  		//====================================
	  		//to be remove on live 
	  		toairtel.put("api_username", username);
	        toairtel.put("session_id", "tyte5656");
	        //=====================================
	        toairtel.put("first_name", firstname);
	        toairtel.put("second_name",secondname);
	        toairtel.put("last_name", lastname);
	        toairtel.put("msisdn", msisdn);
	        String jsonData = g.toJson(toairtel);
	  		
	  		
	  		
	  		
	   if(StringUtils.isNotEmpty(countryverifyip)) {
	  		 
	  	    CLIENT_URL = countryverifyip;
	  		        
	  		postMinusThread = new PostMinusThread(CLIENT_URL, jsonData);
	  		
	  		//capture the switch response.
	  		String responseobject = postMinusThread.doPost();
	  		
	  		
	  		//pass the returned json string
	  		JsonElement roots = new JsonParser().parse(responseobject);
	  		        
	  		//extract a specific json element from the object(status_code)
	  		String switchstatuscode = roots.getAsJsonObject().get("status_code")
						.getAsString();
	  		
	  		String switchmessage = roots.getAsJsonObject().get("status_message")
	  						.getAsString();
	  		
	  		//extract a specific json element from the object(status_code)
	  		String statusdescription = roots.getAsJsonObject().get("status_description")
	  								.getAsString();
	  		
	  		
	  		//expected.put("api_username", username);
	  		expected.put("status_code", switchstatuscode);
	  		expected.put("status_description",statusdescription);
	  		
	  		String jsonResult = g.toJson(expected);

	  		return jsonResult;
	  	}
	        
	   	expected.put("api_username", username);
	  	expected.put("command_status", APIConstants.COMMANDSTATUS_UNOPERATIONAL_COUNTRY);
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

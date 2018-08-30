package com.impalapay.airtel.servlet.api.simulation;

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

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.balance.ClientAccountBalanceByCountry;
import com.impalapay.airtel.beans.geolocation.Country;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.beans.simulation.ComvivaSimulation;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.persistence.accountmgmt.balance.AccountBalanceDAO;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.persistence.simulation.ComvivaSimulationDAO;
import com.impalapay.airtel.servlet.api.APIConstants;
import com.impalapay.airtel.util.SecurityUtil;

public class SimulateName extends HttpServlet {
	private Cache accountsCache;

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

		SessionLogDAO.getInstance();
		
		
		ComvivaSimulationDAO.getInstance();
		
		
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
		String username = "", sessionid = "" ,firstname = "",secondname = "",lastname = "";
		
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
		firstname = root.getAsJsonObject().get("first_name").getAsString();
		secondname = root.getAsJsonObject().get("second_name").getAsString();
		lastname = root.getAsJsonObject().get("last_name").getAsString();
		// instantiate the JSon
		//Note
		//The = sign is encoded to \u003d. Hence you need to use disableHtmlEscaping().
		Gson g = new GsonBuilder().disableHtmlEscaping().create();
		//Gson g = new Gson();
		Map<String, String> expected = new HashMap<>();

		// check for the presence of all required parameters
		if (StringUtils.isBlank(username) || StringUtils.isBlank(sessionid) || StringUtils.isBlank(firstname)) {
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
		
		String name = "eugene";
		
		if(firstname.equalsIgnoreCase(name)){
		
		
		 	expected.put("status_code", "400");
		    expected.put("status_message", "null");
			expected.put("status_description",  "TRUE");
			
			String jsonResult = g.toJson(expected);

			return jsonResult;
		}
		expected.put("status_code", "600");
	    expected.put("status_message", "null");
		expected.put("status_description",  "FALSE");
		
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

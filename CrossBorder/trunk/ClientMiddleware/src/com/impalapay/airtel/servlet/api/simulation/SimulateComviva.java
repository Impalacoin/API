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
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.beans.simulation.ComvivaSimulation;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.persistence.simulation.ComvivaSimulationDAO;
import com.impalapay.airtel.servlet.api.APIConstants;
import com.impalapay.airtel.util.SecurityUtil;

public class SimulateComviva extends HttpServlet {
	private Cache accountsCache;

	private SessionLogDAO sessionlogDAO;
	
	
	private ComvivaSimulationDAO comvivasimulationDAO;
	
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
		
		
		comvivasimulationDAO = ComvivaSimulationDAO.getInstance();
		
		
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
		String username = "", sessionid = "" ,phonenumber = "",transactionid="",beneficiarymsisdn="",amount="";
		
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

		username = root.getAsJsonObject().get("username").getAsString();
		sessionid = root.getAsJsonObject().get("password").getAsString();
		phonenumber = root.getAsJsonObject().get("source_msisdn").getAsString();
		transactionid = root.getAsJsonObject().get("transaction_id").getAsString();
		beneficiarymsisdn = root.getAsJsonObject().get("beneficiary_msisdn").getAsString();
		amount = root.getAsJsonObject().get("amount").getAsString();
		
		// instantiate the JSon
		//Note
		//The = sign is encoded to \u003d. Hence you need to use disableHtmlEscaping().
		Gson g = new GsonBuilder().disableHtmlEscaping().create();
		//Gson g = new Gson();
		Map<String, String> expected = new HashMap<>();

		// check for the presence of all required parameters
		if (StringUtils.isBlank(username) || StringUtils.isBlank(sessionid) || StringUtils.isBlank(phonenumber) || StringUtils.isBlank(transactionid)|| StringUtils.isBlank(amount)) {
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
		
		//fetch the specific response based on phone number.
		ComvivaSimulation testingkit = comvivasimulationDAO.getErrorphone(phonenumber);
		
		if(testingkit !=null){
		
		String statuscode = testingkit.getErrorcode();
		String errorDescription = testingkit.getErrorname();
		
		 	expected.put("am_referenceid", username);
		    expected.put("am_timestamp", statuscode);
			expected.put("status_code",  statuscode);
			expected.put("status_description", errorDescription);
			String jsonResult = g.toJson(expected);

			return jsonResult;
		}
		
		expected.put("status_code",  "9980");
		expected.put("status_description","USE_PROVIDED_MSISDN");
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

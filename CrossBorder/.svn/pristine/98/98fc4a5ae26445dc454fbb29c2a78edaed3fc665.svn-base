package com.impalapay.airtel.servlet.api.session.worldremit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.servlet.api.APIConstants;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.util.SecurityUtil;
import com.impalapay.airtel.servlet.util.PropertiesConfig;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Responsible for sending world remit a sessionid HTTPS.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class SessionIdProvider extends HttpServlet {

	private Cache accountsCache;
	
	//additions for worldremit
	private SessionLogDAO sessionLogDAO;
	
	private SessionLog session;
	
	private SessionLog oldSession;


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
		
		sessionLogDAO = SessionLogDAO.getInstance();
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
		OutputStream out = response.getOutputStream();

		response.setContentType("text/plain;charset=UTF-8");
		response.setDateHeader("Expires", new Date().getTime()); // Expiration
																	// date
		response.setDateHeader("Date", new Date().getTime()); // Date and time
																// that the
																// message was
																// sent

		out.write(getSessionId(request).getBytes());
		out.flush();
		out.close();
	}

	/**
	 * Receives an HTTP request and processes it further. In a successful case,
	 * a Transaction Id and status are returned.
	 * 
	 * @param request
	 * @return String
	 * @throws IOException
	 */
	private String getSessionId(HttpServletRequest request) throws IOException {
		String username = "", password = "";

		Account account = null;

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
		password = root.getAsJsonObject().get("api_password").getAsString();

		// instantiate the JSon
		Gson g = new Gson();
		Map<String, String> expected = new HashMap<>();
		
	
		//ip address module
		 String ip = request.getHeader("X-Forwarded-For");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }
	        
	
		// Check for the presence of all required parameters
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
			String jsonResult = g.toJson(expected);

			return jsonResult;
		}

		// Retrieve the account details then check against username and password
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

		// Invalid password for hashed password
		if (!StringUtils.equals(SecurityUtil.getMD5Hash(password),account.getApiPasswd())) {
			expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_PASSWORD);
			String jsonResult = g.toJson(expected);

			return jsonResult;

		}
		
		//compare remote address with the one stored in propertiesconfig
	        
	    if(!StringUtils.equals(ip,PropertiesConfig.getConfigValue("ALLOWED_IP"))){
	    	
	    	    expected.put("your_ip", ip);
	        	expected.put("command_status", APIConstants.COMMANDSTATUS_INVALID_IPADDRESS);
	    		String jsonResult = g.toJson(expected);

	    		return jsonResult;
	        	
	     }
	        
	        //return ip;  
		// This means that everything is ok
		//first we generate a session_id for Worldremit
		String sessionId = StringUtils
				.remove(UUID.randomUUID().toString(), '-');
        
		//we create a sessionlog object
		session = new SessionLog();
		session.setAccountUuid(account.getUuid());
		session.setValid(true);
		// We persist the hashed version of the Session Id while the plain one
		// is sent to the client on the api response
		session.setSessionUuid(SecurityUtil.getMD5Hash(sessionId));
		
		// Invalidate any previous valid Session Id, then store the new one
		oldSession = sessionLogDAO.getValidSessionLog(account);
		
		if(oldSession == null){
		sessionLogDAO.putSessionLog(session);
		
		expected.put("session was previously false but session_id", sessionId);
		expected.put("command_status", APIConstants.COMMANDSTATUS_OK);
		String jsonResult = g.toJson(expected);

		return jsonResult;
	    }
		sessionLogDAO.invalidate(oldSession);
		sessionLogDAO.putSessionLog(session);
		//expected.put("your_ip", ip);
		expected.put("session_id", sessionId);
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

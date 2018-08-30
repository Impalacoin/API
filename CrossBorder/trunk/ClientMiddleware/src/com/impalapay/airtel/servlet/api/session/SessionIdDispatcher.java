package com.impalapay.airtel.servlet.api.session;

import com.google.gson.Gson;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.sessionlog.ClientUrl;
import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;
import com.impalapay.airtel.persistence.sessionlog.ClientURLDAO;
import com.impalapay.airtel.util.SecurityUtil;
import com.impalapay.airtel.util.net.PostThread;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
//import org.json.JSONException;
//import org.json.JSONObject;

/**
 * Responsible to dispatch a new Session Id to a client URL.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class SessionIdDispatcher extends Thread {

	private Account account;

	private SessionLogDAO sessionLogDAO;
	private ClientURLDAO clientURLDAO;

	/**
	 * 
	 */
	private SessionIdDispatcher() {
	}

	/**
	 * @param account
	 */
	public SessionIdDispatcher(Account account) {
		this.account = account;

		sessionLogDAO = SessionLogDAO.getInstance();
		clientURLDAO = ClientURLDAO.getInstance();
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		String sessionId = StringUtils
				.remove(UUID.randomUUID().toString(), '-');

		SessionLog session = new SessionLog();
		session.setAccountUuid(account.getUuid());
		session.setValid(true);
		// We persist the hashed version of the Session Id while the plain one
		// is sent to the client application
		session.setSessionUuid(SecurityUtil.getMD5Hash(sessionId));

		// for key value pairs
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_username", account.getUsername());
		params.put("session_id", sessionId);

		Gson g = new Gson();
		String json = g.toJson(params);

		ClientUrl clientURL = clientURLDAO.getClientUrl(account);
		
		//real method
		if (clientURL != null) {
			PostThread postThread = new PostThread(clientURL.getUrl(), json,
					false);
			postThread.start();
		}
		

		// Invalidate any previous valid Session Id, then store the new one
		SessionLog oldSession = sessionLogDAO.getValidSessionLog(account);
		
		if(oldSession !=null){
		sessionLogDAO.invalidate(oldSession);
		sessionLogDAO.putSessionLog(session);
		System.out.println("sessionlog is not null");
		}
		sessionLogDAO.putSessionLog(session);
		
	}
}

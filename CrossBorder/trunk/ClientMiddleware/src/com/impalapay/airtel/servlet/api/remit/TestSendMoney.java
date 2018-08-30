package com.impalapay.airtel.servlet.api.remit;

import com.impalapay.airtel.servlet.api.APIConstants;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the {@link SendMoney}
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class TestSendMoney {
	
	//final String CGI_URL = "https://localhost:8443/AirtelRemittance/remit";	
	//final String CGI_URL = "https://airtel.tawi.mobi/remit";
	
	final String CGI_URL = "https://localhost:8443/AirtelRemittance2/remit";	
	

	/**
	 * Test method for
	 * {@link com.impalapay.airtel.servlet.api.status.QueryStatus#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * .
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {

		//##########################################################
		// Test by calling the URL without all required parameters
		//##########################################################
		Map<String, String> expected = new HashMap<>();
		expected.put("command_status", APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
		
		Map<String, String> user = new HashMap<>();
		user.put("api_username", " ");
		user.put("session_id", " ");
		user.put("source_country_code", "");
		user.put("sendername", " ");
		user.put("recipient_mobile", " ");
		user.put("recipient_currency_code", " ");
		user.put("recipient_country_code", " ");
		user.put("reference_number", " ");
		user.put("sendertoken", " ");
		user.put("client_datetime", "");
		user.put("amount", "200");
		
		Gson g = new Gson();
		String jsonData = g.toJson(user);
		String jsonResult = g.toJson(expected);
		//assertEquals(getResponse(CGI_URL, jsonData), jsonResult);

		
		//##########################################################
		// Test for an unknown username
		//##########################################################
		Map<String, String> expected2 = new HashMap<>();
		expected2.put("command_status", APIConstants.COMMANDSTATUS_UNKNOWN_USERNAME);
		
		Map<String, String> user2 = new HashMap<>();
		user2.put("api_username", "UNKNOWN");
		user2.put("session_id", "UNKNOWN");
		user2.put("source_country_code", "UNKNOWN");
		user2.put("sendername", "UNKNOWN");
		user2.put("recipient_mobile", "UNKNOWN");
		user2.put("recipient_currency_code", "UNKNOWN");
		user2.put("recipient_country_code", "UNKNOWN");
		user2.put("reference_number", "UNKNOWN");
		user2.put("sendertoken", "UNKNOWN");
		user2.put("client_datetime", "UNKNOWN");
		user2.put("amount", "200");

		String jsonData2 = g.toJson(user2);
		String jsresult = g.toJson(expected2);
		//assertEquals(getResponse(CGI_URL, jsonData2), jsresult);

		
		
		//##########################################################
		// Test for an invalid sessionid
		//##########################################################
		Map<String, String> expected4 = new HashMap<>();
		expected4.put("command_status", APIConstants.COMMANDSTATUS_INVALID_SESSIONID);
		
		Map<String, String> user4 = new HashMap<>();
		user4.put("api_username", "demo");
		user4.put("session_id", "UNKNOWN");
		user4.put("source_country_code", "UNKNOWN");
		user4.put("sendername", "UNKNOWN");
		user4.put("recipient_mobile", "UNKNOWN");
		user4.put("recipient_currency_code", "UNKNOWN");
		user4.put("recipient_country_code", "UNKNOWN");
		user4.put("reference_number", "UNKNOWN");
		user4.put("sendertoken", "UNKNOWN");
		user4.put("client_datetime", "UNKNOWN");
		user4.put("amount", "200");

		String jsonData4 = g.toJson(user4);
		String resultant = g.toJson(expected4);
		//assertEquals(getResponse(CGI_URL, jsonData4), resultant);

		
		
		//##########################################################
		// Test using incorrect currency code
		//##########################################################
		Map<String, String> expected5 = new LinkedHashMap<String, String>();

		expected5.put("command_status", APIConstants.COMMANDSTATUS_INVALID_CURRENCYCODE);

		Map<String, String> user5 = new HashMap<>();
		user5.put("api_username", "demo");
		user5.put("session_id", "7ec5ffa880f34d01891597a258dfbfd5");
		user5.put("source_country_code", "UNKNOWN");
		user5.put("sendername", "UNKNOWN");
		user5.put("recipient_mobile", "UNKNOWN");
		user5.put("recipient_currency_code", "UNKNOWNCURRENCYCODE");
		user5.put("recipient_country_code", "UNKNOWN");
		user5.put("reference_number", "7fdd-4def-92d7-f2b840aebe0f");
		user5.put("sendertoken", "UNKNOWN");
		user5.put("client_datetime", "UNKNOWN");
		user5.put("amount", "200");

		String json = g.toJson(user5);
		String correct = g.toJson(expected5);
		assertEquals(getResponse(CGI_URL, json), correct);

		
		
		//##########################################################
		// Test using incorrect country code code
		//##########################################################
		Map<String, String> expected6 = new HashMap<>();

		expected6.put("command_status", APIConstants.COMMANDSTATUS_INVALID_COUNTRYCODE);

		Map<String, String> user6 = new HashMap<>();
		user6.put("api_username", "demo");
		user6.put("session_id", "7ec5ffa880f34d01891597a258dfbfd5");
		user6.put("source_country_code", "UNKNOWN");
		user6.put("sendername", "UNKNOWN");
		user6.put("recipient_mobile", "UNKNOWN");
		user6.put("recipient_currency_code", "KES");
		user6.put("recipient_country_code", "UNKNOWNCOUNTRYCODE");
		user6.put("reference_number", "7fdd-4def-92d7-f2b840aebe0f");
		user6.put("sendertoken", "UNKNOWN");
		user6.put("client_datetime", "UNKNOWN");
		user6.put("amount", "200");

		String resultjson = g.toJson(user6);
		String expe = g.toJson(expected6);
		assertEquals(getResponse(CGI_URL, resultjson), expe);
		
		
		
		//##########################################################
	    //Test for invalid JSon
		//##########################################################
		 assertEquals(getResponse(CGI_URL, "&api_username=demo&session_id,c129b7caf23e405d934e698da7f92982&"), 
	        		APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
		 
		 
		 
		//########################################################## 
		// Test using correct parameters
		//##########################################################
		String transactionuuid = "eb8b-4f84-bb15-a0410dc5d33b";
		String amount = "200";
		String receivermobile = "444-499-3303";
		String username = "demo";
		String sender = "machette Machette";

		Map<String, String> expected3 = new LinkedHashMap<String, String>();
		//expected3.put("receiver_number", receivermobile );
		expected3.put("api_username", username);
		expected3.put("transaction_id", transactionuuid);
        expected3.put("command_status", APIConstants.COMMANDSTATUS_OK);
        expected3.put("remit_status", APIConstants.COMMANDSTATUS_REMIT_SUCCESS);

		Map<String, String> user3 = new HashMap<>();
		user3.put("api_username", "demo");
		user3.put("session_id", "7ec5ffa880f34d01891597a258dfbfd5");
		user3.put("source_country_code", "AU");
		user3.put("sendername", "Vallery mundal");
		user3.put("recipient_mobile", "254731987100");
		user3.put("amount", "5000");
		user3.put("recipient_currency_code", "KES");
		user3.put("recipient_country_code", "KE");
		user3.put("reference_number", "gtrriiuiuu3m");
		user3.put("sendertoken", "6656664664");
		user3.put("client_datetime", "2014-11-18T18:44:27+11:00");
		
		
		

		String jsonData3 = g.toJson(user3);
		String correctresults = g.toJson(expected3);
		assertEquals(getResponse(CGI_URL, jsonData3), correctresults);
		
		
	}
	

	/**
	 * @param httpsUrl
	 * @param args
	 */
	private String getResponse(String httpsUrl, String args) {
		URL url;
		String response = "";

		try {
			// Create a context that doesn't check certificates.
			SSLContext sslContext = SSLContext.getInstance("TLS");
			TrustManager[] trustMgr = getTrustManager();

			sslContext.init(null, // key manager
					trustMgr, // trust manager
					new SecureRandom()); // random number generator
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());

			url = new URL(httpsUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.setDoOutput(true);

			// Guard against "bad hostname" errors during handshake.
			con.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String host, SSLSession sess) {
					if (host.equals("localhost")) {
						return true;
					} else {
						return false;
					}
				}
			});

			// Send data to the output
			sendData(con, args);

			// Dump all cert info
			// printHttpsCert(con);

			// Dump all the content
			response = getContent(con);

		} catch (MalformedURLException e) {
			System.err.println("MalformedURLException");
			e.printStackTrace();

		} catch (IOException e) {
			System.err.println("IOException");
			e.printStackTrace();

		} catch (NoSuchAlgorithmException e) {
			System.err.println("NoSuchAlgorithmException");
			e.printStackTrace();

		} catch (KeyManagementException e) {
			System.err.println("KeyManagementException");
			e.printStackTrace();
		}

		return response;
	}

	
	/**
	 * Send data to the url
	 * 
	 * @param con
	 */
	private void sendData(HttpsURLConnection con, String args) {
		if (con != null) {

			try {
				// send data to output
				OutputStreamWriter writer = new OutputStreamWriter(
						con.getOutputStream());

				writer.write(args);
				writer.flush();
				writer.close();

			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * @param con
	 */
	private String getContent(HttpsURLConnection con) {
		StringBuffer buff = new StringBuffer("");

		if (con != null) {

			try {

				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String input;

				while ((input = br.readLine()) != null) {
					buff.append(input + "\n");
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}// end 'if(con != null)'

		return buff.toString().trim();
	}

	
	/**
	 * @return {@link TrustManager}
	 */
	private TrustManager[] getTrustManager() {

		TrustManager[] certs = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String t) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String t) {
			}
		} };

		return certs;
	}

	
	/**
	 * @param con
	 */
	private void printHttpsCert(HttpsURLConnection con) {
		if (con != null) {

			try {
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();

				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : "
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : "
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				System.err.println("SSLPeerUnverifiedException");
				e.printStackTrace();

			} catch (IOException e) {
				System.err.println("IOException");
				e.printStackTrace();
			}

		}// end 'if(con != null)'
	}
}

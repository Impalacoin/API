package com.impalapay.airtel.servlet.api.verifyname;

import com.google.gson.Gson;
import com.impalapay.airtel.servlet.api.APIConstants;

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
 * Tests the {@link QueryBalance}
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class TestVerifyName {
    //localhost
	//final String CGI_URL = "https://localhost:8443/AirtelRemittance/balance";
	final String CGI_URL = "https://localhost:8443/AirtelRemittance2/verifybeneficiary";
	
	//live
	//final String CGI_URL = "https://airtel.tawi.mobi/balance";

	/**
	 * Test method for
	 * {@link com.impalapay.airtel.servlet.api.balance.QueryBalane#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * .
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {
		
		//test for invalid parameters
		Map<String, String> expected = new HashMap<>();
		expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
		
		Map<String, String> user = new HashMap<>();
		user.put("api_username", " ");
		user.put("session_id", "a94ce3f96134410c96d254dbc3d50fb8");
		user.put("country_code","KE");
		user.put("first_name","eugene");
		user.put("second_name","chimita");
		user.put("last_name","michael");
		user.put("mobile_number","254733254565");


		Gson g = new Gson();
		String jsonData = g.toJson(user);
		String jsonResult = g.toJson(expected);

		// System.out.println(getResponse(CGI_URL, jsonData));
		assertEquals(getResponse(CGI_URL, jsonData), jsonResult);

		
		// test for unknown username
		Map<String, String> expected2 = new HashMap<>();
		expected2.put("command_status",APIConstants.COMMANDSTATUS_UNKNOWN_USERNAME);
		
		Map<String, String> user2 = new HashMap<>();
		user2.put("api_username", "UNKNOWN_USERNAME");
		user2.put("session_id", "4d4d8de9fd6e49f785a0a713769c336c");
		user2.put("country_code","KE");
		user2.put("first_name","eugene");
		user2.put("second_name","chimita");
		user2.put("last_name","michael");
		user2.put("mobile_number","254733254565");
		
		String jsonData2 = g.toJson(user2);
		String jsresult = g.toJson(expected2);
		assertEquals(getResponse(CGI_URL, jsonData2), jsresult);
		
		
		//test for invalid sessionid
		Map<String, String> expected4 = new HashMap<>();
		expected4.put("command_status",APIConstants.COMMANDSTATUS_INVALID_SESSIONID);
		
		Map<String, String> user4 = new HashMap<>();
		user4.put("api_username", "demo");
		user4.put("session_id", "d2c3093ce5ae488aab150f85d9d987a3");
		user4.put("country_code","KE");
		user4.put("first_name","eugene");
		user4.put("second_name","chimita");
		user4.put("last_name","michael");
		user4.put("mobile_number","254733254565");

		String jsonData4 = g.toJson(user4);
		String resultant = g.toJson(expected4);
		assertEquals(getResponse(CGI_URL, jsonData4), resultant);
		

		// test using correct parameters
		Map<String, String> expected3 = new HashMap<>();
		String balance = "100000";
		expected3.put("status_description", "TRUE");
		expected3.put("status_code", "400");

		Map<String, String> user3 = new HashMap<>();
		user3.put("api_username", "demo");
		user3.put("session_id", "7ec5ffa880f34d01891597a258dfbfd5");
		user3.put("country_code","KE");
		user3.put("first_name","eugene");
		user3.put("second_name","chimita");
		user3.put("last_name","michael");
		user3.put("mobile_number","254733254565");
		
		String jsonData3 = g.toJson(user3);
		String correctresults = g.toJson(expected3);
		assertEquals(getResponse(CGI_URL, jsonData3), correctresults);
		
		//Test for incorrect Json.
		 assertEquals(getResponse(CGI_URL, "&api_username=demo&session_id=254728123456"), 
	        		APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
	        

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

			// Guard against "bad hostname" errors during handshake.replace with localhost for testing locally
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

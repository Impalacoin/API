package com.impalapay.airtel.servlet.api.session;

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
 * Tests the {@link SessionIdProvider}
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 31, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class TestSessionIdProvide{
	
	final String CGI_URL = "https://localhost:8443/AirtelRemittance2/sessionid";
	
	//for live
    //final String CGI_URL = "https://airtel.tawi.mobi/sessionid";

	/**
	 * Test method for
	 * {@link com.impalapay.airtel.servlet.api.session.SessionIdProvider#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
	 * .
	 */
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() {

		// Test by calling the URL without all required parameters
		Map<String, String> expected = new HashMap<>();
		expected.put("command_status",APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
		
		Map<String, String> user = new HashMap<>();
		user.put("api_username", " ");
		user.put("api_password", " ");

		Gson g = new Gson();
		String jsonData = g.toJson(user);
		String jsonResult = g.toJson(expected);
		assertEquals(getResponse(CGI_URL, jsonData), jsonResult);

		// Test for an unknown username
		Map<String, String> expected2 = new HashMap<>();
		expected2.put("command_status",APIConstants.COMMANDSTATUS_UNKNOWN_USERNAME);
		
		Map<String, String> user2 = new HashMap<>();
		user2.put("api_username", "UNKNOWN_USERNAME");
		user2.put("api_password", "demo");

		String jsonData2 = g.toJson(user2);
		String jsresult = g.toJson(expected2);
		assertEquals(getResponse(CGI_URL, jsonData2), jsresult);

		// Test for a wrong password
		Map<String, String> expected3 = new HashMap<>();
		expected3.put("command_status",APIConstants.COMMANDSTATUS_INVALID_PASSWORD);
		Map<String, String> user3 = new HashMap<>();
		user3.put("api_username", "demo");
		user3.put("api_password", "6565");

		String jsonData3 = g.toJson(user3);
		String results = g.toJson(expected3);
		assertEquals(getResponse(CGI_URL, jsonData3), results);

		// Test using correct parameters
		Map<String, String> correct = new HashMap<String, String>();
		correct.put("command_status", APIConstants.COMMANDSTATUS_OK);
		Map<String, String> logdetail = new HashMap<>();
		logdetail.put("api_username", "demo");
		logdetail.put("api_password", "demo");

		String urlparameters = g.toJson(logdetail);
		String expectedresult = g.toJson(correct);
		assertEquals(getResponse(CGI_URL, urlparameters), expectedresult);
		
		System.out.println(getResponse(CGI_URL, urlparameters));
		
		//test for invalid json object
		 //assertEquals(getResponse(CGI_URL, "&api_username=chimita&api_password=chimita"), 
	        		//APIConstants.COMMANDSTATUS_INVALID_PARAMETERS);
		

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

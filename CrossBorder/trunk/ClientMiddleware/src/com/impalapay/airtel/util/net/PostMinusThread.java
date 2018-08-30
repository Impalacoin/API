package com.impalapay.airtel.util.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.impalapay.airtel.servlet.util.PropertiesConfig;

/**
 * can send POST text data, to HTTP
 * <p>
 * Copyright (c) ImpalapayLtd.,Sep 13, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class PostMinusThread {

	private String url;
	private String params;
	boolean retry;
	private Logger logger;
	private String dbName = "";

	public PostMinusThread() {
		dbName = PropertiesConfig.getConfigValue("DATABASE_NAME");
		logger = Logger.getLogger(this.getClass());
	}

	public PostMinusThread(String url, String params) {
		super();
		this.url = url;
		this.params = params;

	}

	public String doPost() {

		//CloseableHttpClient httpclient = HttpClients.createDefault();
		StringEntity entity;
		String out = "";
		String usernames ="";

		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom()
					.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
					.build();
			
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();
			
			entity = new StringEntity(params);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
            /*
			// for debugging
			System.out.println(entity.getContentType());
			System.out.println(entity.getContentLength());
			System.out.println(EntityUtils.toString(entity));
			System.out.println(EntityUtils.toByteArray(entity).length);

			// System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			System.out.println(url);
            */
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			out = rd.readLine();

			JsonElement root = new JsonParser().parse(out);

			//usernames = root.getAsJsonObject().get("status_code").getAsString();

			//System.out.println(usernames);
			
			 String line = ""; while ((line = rd.readLine()) != null) {
			 System.out.println(line); }
			 
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException for URL: '" + url + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException for URL: '" + url + "'");
			logger.error(ExceptionUtils.getStackTrace(e));

		} catch (IOException e) {
			logger.error("IOException for URL: '" + url + "'");
			logger.error(ExceptionUtils.getStackTrace(e));
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
		//return usernames;
	}

}

package com.impalapay.airtel.util.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.apache.log4j.Logger;

/**
 * Utility to perform HTTP POSTs.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 14, 2014  
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);
	
	
	
	/**
	 * @param url
	 * @param params
	 * @return an {@link HttpEntity} based on the server response
	 */
	public static HttpEntity getPostEntity(URL url, Map<String,String> params) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		UrlEncodedFormEntity entity = null;
		
		Iterator<String> iter = params.keySet().iterator();
		String key;
		
		while(iter.hasNext()) {
			key = iter.next();
			formparams.add(new BasicNameValuePair(key, params.get(key)));
		}
		
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost httppost = new HttpPost(url.toString());
            httppost.setEntity(entity);
            HttpResponse response = httpclient.execute(httppost);
            return response.getEntity();
						
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException for URL: '" + url + "'");
			logger.error(ExceptionUtils.getStackTrace(e));
				
			
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException for URL: '" + url + "'");
			logger.error(ExceptionUtils.getStackTrace(e));
			
			
		} catch (IOException e) {
			logger.error("IOException for URL: '" + url + "'");
			logger.error(ExceptionUtils.getStackTrace(e));			
		}
		
		return entity;
	}

}


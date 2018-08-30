package com.impalapay.airtel.tests.http;


import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
 
/**
 *  <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * A simple Java REST GET example using the Apache HTTP library.
 * This executes a call against the Yahoo Weather API service, which is
 *
 * Apache HttpClient
 */
public class HttpComponentsHTTP {
 
  public static void main(String[] args) throws IOException {
   // DefaultHttpClient client = new DefaultHttpClient();
  // solution
	  
	  CloseableHttpClient client = HttpClientBuilder.create().build();
    try {
      // specify the host, protocol, and port
      HttpHost target = new HttpHost("weather.yahooapis.com", 80, "http");
       
      // specify the get request
      HttpGet getRequest = new HttpGet("/forecastrss?p=80020&u=f");
 
      System.out.println("executing request to " + target);
 
      HttpResponse httpResponse = client.execute(target, getRequest);
      HttpEntity entity = httpResponse.getEntity();
 
      System.out.println("----------------------------------------");
      System.out.println(httpResponse.getStatusLine());
      Header[] headers = httpResponse.getAllHeaders();
      for (int i = 0; i < headers.length; i++) {
        System.out.println(headers[i]);
      }
      System.out.println("----------------------------------------");
 
      if (entity != null) {
        System.out.println(EntityUtils.toString(entity));
      }
 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // When HttpClient instance is no longer needed,
      // shut down the connection manager to ensure
      // immediate deallocation of all system resources
       client.close();
    }
  }
}
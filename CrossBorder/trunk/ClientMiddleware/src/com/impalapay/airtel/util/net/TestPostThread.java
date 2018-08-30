package com.impalapay.airtel.util.net;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;

/**
 * Test our POSTing class.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Sep 19, 2014  
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class TestPostThread {

	// The PHP script mentioned in these URLs prints out client POST data
	final String CLIENT_URL_HTTP = "http://localhost/testjson/", // Ordinary URL
			CLIENT_URL_HTTPS = "https://localhost/testjson/";	// Secure URL
	
		
	/**
	 * Test method for {@link mobi.tawi.airtimegw.util.net.PostThread#PostThread(java.lang.String, java.util.Map, boolean)}.
	 */
	@Test
	public void testPostThread() {
		
    Map<String,String> user=new HashMap<>();
        user.put("api_username", "eugene");
        user.put("session_id", "abc123");
        
        Gson g = new Gson();
        String jsonData = g.toJson(user);
		
		PostThread postThread;
		
		postThread = new PostThread(CLIENT_URL_HTTP,jsonData, false);		
		postThread.run();	// Use this when testing. However use 'postThread.start()' when
							// running in an application server.
		
		
		postThread = new PostThread(CLIENT_URL_HTTPS,jsonData, false);		
		postThread.run();
	} 

}

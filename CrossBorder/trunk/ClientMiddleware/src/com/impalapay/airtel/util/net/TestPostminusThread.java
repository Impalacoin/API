package com.impalapay.airtel.util.net;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class TestPostminusThread {
	
	public boolean vevexcan(){
		return true;
	}
	public static void main(String args[]) {
		final String CLIENT_URL ="http://196.216.73.150:9990/switch-rest/request/checkForex";
		
		final String CLIENT_URL2 = "https://localhost:8443/AirtelRemittance/comviva";
		
		final String CLIENT_URL3 = "https://localhost:8443/AirtelRemittance2/comvivaverify";
		
		
		Map<String,String> toairtel = new HashMap< >();
        toairtel.put("username", "Airtel_KE");
        toairtel.put("password", "password");
        toairtel.put("sessionId", "eugene5544542");
        //toairtel.put("applicationId", "1234567890");
        toairtel.put("sourceCountryCode", "254");
        toairtel.put("destinationCountryCode", "256");
        toairtel.put("sourceAmount", "1000");
        
        Date now = new Date();
        
        Map<String,String> remit = new HashMap< >();
        remit.put("username", "Airtel_KE");
        remit.put("password", "password");
        remit.put("transactionRef", "uyeggeygeywy");
        remit.put("sourceCountryCode", "254");
        remit.put("destinationCountryCode", "256");
        remit.put("sessionId", "eugene5542");
        remit.put("applicationId", "1234567890");
        remit.put("sourceAddress", "25476366");
        remit.put("sourceAmount", "1000");
        remit.put("destinationAddress", "254733565664");
        remit.put("dateTime", now.toString());
        
        Map<String,String> toairtel2 = new HashMap< >();
        toairtel2.put("api_username", "demo");
        toairtel2.put("session_id", "tyte5656");
        toairtel2.put("phone_number", "254733456160");
        
        Map<String,String> toairtel3 = new HashMap< >();
       
        toairtel3.put("api_username", "demo");
        toairtel3.put("session_id", "09d183d78f83403d9db318597e43ebd8");
        toairtel3.put("first_name", "eugene");
        toairtel3.put("second_name", "chimita");
        toairtel3.put("last_name", "chimita");
        
     
      
        
        Gson g = new Gson();
        String jsonData = g.toJson(toairtel3);
		
		PostMinusThread veve = new PostMinusThread(CLIENT_URL3, jsonData);
		
		//doing a post will return a json object containing status code and status description
		String omo = veve.doPost();
		
		//pass the returned json string
		JsonElement root = new JsonParser().parse(omo);
        
		//exctract a specific json element from the object
		String usernames = root.getAsJsonObject().get("status_description")
				.getAsString();
	    System.out.println(usernames);
    }

}

package com.impalapay.airtel.tests.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class JsonStringObject {
	
	public static void main(String[] args){
		 String username ="eugene";
	        String exchangerate = "500";
	        String date ="wewe";
	        String ok = "okeyyy";
	        
	        //String invalid ="eugene = chimita";
		String json ="{\n" + " \"api_username\": \"" + username + "\",\n"
				+ " \"amount\": \"" + exchangerate + "\",\n"
				+ " \"client_datetime\": \"" + date + "\",\n"
				+ " \"command_status\": \"" + ok + "\"\n" + "}";
		try{
		JsonElement root = new JsonParser().parse(json);
		
		String usernames =root.getAsJsonObject().get("amount").getAsString();
		
		String command = root.getAsJsonObject().get("command_status").getAsString();
		System.out.println(usernames);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("invalid json formart");
		}
	

}
}
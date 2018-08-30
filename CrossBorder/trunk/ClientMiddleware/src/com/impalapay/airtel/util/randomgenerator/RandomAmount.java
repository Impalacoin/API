package com.impalapay.airtel.util.randomgenerator;
/**
 * used for generating random amount between a range
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RandomAmount {
	
	public static void main(String[] args){
	
	try {
			
			
	     
	            
	            
			
			 
			String content = "This is the content to write into file";
 
			File file = new File("/home/eugene/Desktop/amounts.txt");
			
		
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
			file.createNewFile();
		}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//writting to file
			
			 String finalvalue;
		     double value;
		     double lowerLimit, upperLimit;
		    
		     String output;
		     
		     lowerLimit = 200.00;
		     upperLimit =10000.00;
			
		for(int i = 0;i<10000;i++){
		  
	         value  = lowerLimit + ((upperLimit - lowerLimit) * Math.random());
	         
	         //use decimal fomart to formart to two decimal places
	         
	         
	       DecimalFormat twoDForm = new DecimalFormat("#.00");
	       
	        finalvalue = twoDForm.format(value);
	       bw.write(finalvalue + "\n");
			
			
		}
			bw.close();
		
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}



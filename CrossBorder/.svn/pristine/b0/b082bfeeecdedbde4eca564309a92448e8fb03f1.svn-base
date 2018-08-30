/**
 * 
 */
package com.impalapay.airtel.util.randomgenerator;
/**
 * used for generating random phone numbers and writting to a file.
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
import java.util.ArrayList;
import java.util.List;
/**
 * @author eugene
 *
 */
import java.util.Random;
	 
	public class PhoneNumber
	{
	 
	    public static void main(String args[]) throws IOException
	     
	    {
	     
	    	try {
	    		File file = new File("/home/eugene/Desktop/phone.txt");
	    		 
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				for(int j=0; j<10000; j++) {
					
					  Random generator = new Random();
				        int num1,num2,num3;
				        
				        num1 = generator.nextInt(9) + 3 ; // first group may not start with 0 or a 1
				        num1 = generator.nextInt(7) + 2;
				        num1 = generator.nextInt(6) + 2;
				        num2 = generator.nextInt(999) - 2 ; //must form an even number between 000-998
				        num3 = generator.nextInt(6000); //must be between 0000-5999
				 
			        String phoneNumber = String.format("%d%d%d%03d%04d", num1, num1, num1, num2, num3);
					bw.write(phoneNumber);
					bw.write("\n");
				}
				System.out.println("done...creatin phone numbers");
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
			}
	
	
	
	    
	}
	 
        
	    	
	    	
	        
	       
	    
	
	
	
	


	
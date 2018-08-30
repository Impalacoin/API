/**
 * 
 */
package com.impalapay.airtel.util.randomgenerator;

/**
 * used for generating  country source code,currency code e.t.c
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.math3.random.RandomDataGenerator;

/**
 * @author eugene
 *
 */
public class Transactions {

	/**
	 * @param args
	 */
	final static int DATE_COUNT = 10000;
		
	
	public static void main(String[] args) {
		
		try{
		
	
		 List<String> countries = new ArrayList();
		 
         countries.add("Kenya");
         countries.add("Uganda");
         countries.add("Rwanda");
         countries.add("Nigeria");
         countries.add("Niger");
         countries.add("Chad");
         countries.add("Burkina Faso");
         countries.add("Democratic Republic of Congo");
         countries.add("Gabon");
         countries.add("Ghana");
         countries.add("Madagascar");
         countries.add("Malawi");
         countries.add("Republic of Congo");
         countries.add("Seychelles");
         countries.add("Tanzania");
         countries.add("Zambia");
         countries.add("Sierra Leone");
         
  //for country code
  List<String> code = new ArrayList<>();
  		code.add("d4a676822f4546a0bee789e83070f788");
  		code.add("977f6e8fceed43e0a3c1716750171442");
  		code.add("a02dea8fd3c94ecfb2535ae2c698880a");
  		code.add("193a12b854234e30aa43090d8b4ee810");
  		code.add("2693501bcda940b9b4be8c73a4c3771f");
  		code.add("84ca739b54f14e76a6b2515be2f111f3");
  		code.add("1e0ea2bfe4294e78b63fdd1b27ea3b1d");
  		code.add("91978aa6e9c44e298cce946320ff7639");
  		code.add("11316c574b734c1f89165f886bd37fe");
  		code.add("c688bba9ded04073b57def62d6c2004d");
  		code.add("0c115d1bc7804592b2fdae320476b960");
  		code.add("a0f47fb210f44a93800c2b0873f74874");
  		code.add("ddde4a29553348aba3b80a8d9e6b3201");
  		code.add("ed0cd3cd0f5246ef83f90721f8d38105");
  		code.add("cc197dbc5cf8469bb38fc1cf5cbf54d9");
  		code.add("5db5fa02790e4ee0a8d7a538b4df820a");
  		code.add("5332267ba08a46e685cc8d5ddbd68f41");
  		
  
  List <String> currency = new ArrayList<>();
     currency.add("KES");
     currency.add("UGX");
     currency.add("RWF");
     currency.add("NGN");
     currency.add("XOF");
     currency.add("XAF");
     currency.add("XOF");
     currency.add("XAF");
     currency.add("XAF");
     currency.add("GHS");
     currency.add("MGA");
     currency.add("MWK");
     currency.add("XAF");
     currency.add("SCR");
     currency.add("TZS");
     currency.add("ZWM");
     currency.add("SLL");
     
  List <Double> Exchangerate = new ArrayList<Double>();
      Exchangerate.add(87.75);
      Exchangerate.add(2630.00);
      Exchangerate.add(689.00);
      Exchangerate.add(161.77);
      Exchangerate.add(488.28);
      Exchangerate.add(489.28);
      Exchangerate.add(488.28);
      Exchangerate.add(490.28);
      Exchangerate.add(491.28);
      Exchangerate.add(3.42);
      Exchangerate.add(2442.00);
      Exchangerate.add(395.00);
      Exchangerate.add(488.28);
      Exchangerate.add(12.31);
      Exchangerate.add(1658.50);
      Exchangerate.add(5251.88);
      Exchangerate.add(4370.00);
      
      
		
		
    
      //used for generating random number for calculating amount
      
        String finalvaluedisplay;
        String finalresultdisplay;
	     double value;
	     double lowerLimit, upperLimit;
	     String testcountry = "";
	     String output;
	     String account ="9756f889-811a-4a94-b13d-1c66c7655a7f";
	     
	     lowerLimit = 200.00;
	     upperLimit =700.00;
	     
	   
     
		
	for(int i = 0;i<20;i++){
	  
      
      
      
      //use decimal fomart to formart to two decimal places
      
     DecimalFormat twoDForm = new DecimalFormat("#.00");
    
    
     
     RandomDataGenerator generator = new RandomDataGenerator();
     int index;
     
     //used for creating files for output
     
     File file = new File("/home/eugene/Desktop/transaction2.txt");
     
  // if file doesnt exists, then create it
  			if (!file.exists()) {
  			file.createNewFile();
  		}
   
  			FileWriter fw = new FileWriter(file.getAbsoluteFile());
  			BufferedWriter bw = new BufferedWriter(fw);
     
     
     for(int j=0; j<10000; j++) {
    	 
    	 //to generate uuids
    	 //uuid.random()
    	 
    	  //to generate token numbers
	     Random tokengenerators = new Random();
	        int num1,num2,num3;
	        
	        num1 = tokengenerators.nextInt(9) + 3 ; // first group may not start with 0 or a 1
	        num1 = tokengenerators.nextInt(7) + 2;
	        num1 = tokengenerators.nextInt(6) + 2;
	        num2 = tokengenerators.nextInt(999) - 2 ; //must form an even number between 000-998
	        num3 = tokengenerators.nextInt(6000); //must be between 0000-5999
	 
     String sendertoken = String.format("%d%d%d%03d%04d", num1, num1, num1, num2, num3);

         //to generate phone numbers
         Random dategenerator = new Random();
         int num4,num5,num6;
         
         num4 = dategenerator.nextInt(9) + 3 ; // first group may not start with 0 or a 1
         num4 = dategenerator.nextInt(7) + 2;
         num4 = dategenerator.nextInt(6) + 2;
         num5 = dategenerator.nextInt(999) - 2 ; //must form an even number between 000-998
         num6 = dategenerator.nextInt(6000); //must be between 0000-5999

     String phoneNumber = String.format("%d%d%d-%03d-%04d", num4, num4, num4, num5, num6);
         
    	//random recipient country
     for(int c =0; c<16;c++){
    	 
    	 testcountry = code.get(c);
    	 
     }
    	 
    	 
    	 //System.out.println("Random number is: " + generator.nextInt(0, 16));
    	 value  = lowerLimit + ((upperLimit - lowerLimit) * Math.random());
    	 
    	 index = generator.nextInt(0, 16);
    	 
    	 Double exchange =Exchangerate.get(index);
    	 Double amount = value;
    	 
    	 Double result= exchange * amount;
    	 
    	 finalvaluedisplay = twoDForm.format(amount);
    	 finalresultdisplay =twoDForm.format(result);
    	 
    	 //System.out.println("Line data is: " +
    			 //countries.get(index) + " " +
    			 //code.get(index) + " " +
    			 //currency.get(index) + "\t" + "\t" + Exchangerate.get(index)+"\t"+finalvaluedisplay+"\t"+finalresultdisplay);
    	 
    	 //bw.write("    " +UUID.randomUUID()+"|" + account + "|" +code.get(index)+"|"+"|"+ phoneNumber + "|" +finalresultdisplay+ "|"+currency.get(index)+"|" + sendertoken + "|"+"\n");
    	 bw.write(code.get(index) + "\n");
    	 //System.out.println("Line data is: " +UUID.randomUUID()+"|" + account + "|" +code.get(index)+"|"+"|"+ phoneNumber + "|" +finalresultdisplay+ "|"+currency.get(index)+"|" + sendertoken + "|"+ "\t" );
    	 
    	
     }
     
     bw.close();
	}
     
	}catch (Exception e) {
		
		e.printStackTrace();
		// TODO: handle exception
	}
     
     
		/*
		
		// TODO Auto-generated method stub
		try {
			
			//test
			//RandomDataGenerator randomDataImpl = new RandomDataGenerator();
			 List<String> li = new ArrayList();
			 
	            li.add("Kenya");
	            li.add("Uganda");
	            li.add("Rwanda");
	            li.add("Nigeria");
	            li.add("Niger");
	            li.add("Chad");
	            li.add("Burkina Faso");
	            li.add("Democratic Republic of Congo");
	            li.add("Gabon");
	            li.add("Ghana");
	            li.add("Madagascar");
	            li.add("Malawi");
	            li.add("Republic of Congo");
	            li.add("Seychelles");
	            li.add("Tanzania");
	            li.add("Zambia");
	            li.add("Sierra Leone");
	            
	     //for country code
	     List<String> code = new ArrayList<>();
	     		code.add("KE");
	     		code.add("UG");
	     		code.add("RW");
	     		code.add("NG");
	     		code.add("NE");
	     		code.add("TD");
	     		code.add("BF");
	     		code.add("CD");
	     		code.add("GA");
	     		code.add("GH");
	     		code.add("MG");
	     		code.add("MW");
	     		code.add("CG");
	     		code.add("SC");
	     		code.add("TZ");
	     		code.add("ZM");
	     		code.add("SL");
	     
	     List <String> currency = new ArrayList<>();
	        currency.add("kES");
	        currency.add("UGX");
	        currency.add("RWF");
	        currency.add("NGN");
	        currency.add("XOF");
	        currency.add("XAF");
	        currency.add("XOF");
	        currency.add("XAF");
	        currency.add("XAF");
	        currency.add("GHS");
	        currency.add("MGA");
	        currency.add("MWK");
	        currency.add("XAF");
	        currency.add("SCR");
	        currency.add("TZS");
	        currency.add("ZWM");
	        currency.add("SLL");
	     
	            
	            
			
	        
			 
			String content = "This is the content to write into file";
 
			File file = new File("/home/eugene/Desktop/codes.txt");
			
			//test
			

			
			
		//	for(int j=0; j<15; j++) {
				//FileUtils.write(file,  li  + "\n", // smsTime						
					//	true); // Append to file
				
				//+ "|"	+ code + "|"
                              
                               
			//}
			
			
			//test
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
			file.createNewFile();
		}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			//writting to file
			
		for(int i = 0;i<10000;i++){
		  
			for(String str: currency){
				
				
				
				bw.write(str +  "\n");
				
				
				
				
			}
			
			
		}
			bw.close();
		
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		} */

	}

}


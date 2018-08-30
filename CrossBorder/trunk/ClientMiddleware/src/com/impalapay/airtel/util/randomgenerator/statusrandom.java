package com.impalapay.airtel.util.randomgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

public class statusrandom {
	
	public static void main(String[] args) throws IOException{
		//generate a list of random transactionstatus
		
		 List<String> status = new ArrayList();
		 
		 status.add("3b6edb35-654d-4049-b7ea-0f1db29c6e77");
		 status.add("e5998ec1-57b3-43ba-ad19-f875e3efbeac");
		 status.add("4a991e99-ffa2-4fcd-91ed-27e6ce078832");
		 status.add("796e797d-6721-4f5e-938f-241b4b4c5f6c");
		 status.add("4f3d09eb-10e0-428a-b8c1-38f476a4ab31");
		 status.add("9454a86a-c1ac-410c-882e-d9a6e9048c81");
		 status.add("397554ce-6935-4699-8b99-727574b6a49f");
		 status.add("857d1e0b-df0d-487c-b7bb-6e9d8c3eb212");
		 status.add("ea430328-ef8b-4459-95c6-7102ba16ddc5");
		 status.add("b3dd378b-10c8-4e77-a80b-d5da6d7b9c08");
		
		 
		 RandomDataGenerator generator = new RandomDataGenerator();
	     int index;
	     
	    int  count = 0;
	    
	    //used for creating files for output
        
        File file = new File("/tmp/status.txt");
        
     // if file doesnt exists, then create it
     			if (!file.exists()) {
     			file.createNewFile();
     		}
      
     			FileWriter fw = new FileWriter(file.getAbsoluteFile());
     			BufferedWriter bw = new BufferedWriter(fw);
	     
	     
	     for(int j=0; j<10000; j++){
	    	 
	    	 index = generator.nextInt(0,9);
	    	 
	    	 String value = status.get(index);
	    	 
	    	 //write to file
	    	 

	        
	      			
	      			
	      			bw.write(value + "\n");
	    	 
	    	System.out.println(j);
	    	 
	     }
		 
		
		 
	
	}

}

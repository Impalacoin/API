package com.impalapay.airtel.util;



public class TestDateUtil {
	
	public static void main(String[] args) {
		 
	    DateUtil test = new DateUtil();
	 
	    System.out.println(test.isValidDate("2014-11-09"));
	    System.out.println(test.isValidDate("2014-11-314T01:28:39±01:28"));
	  }

}

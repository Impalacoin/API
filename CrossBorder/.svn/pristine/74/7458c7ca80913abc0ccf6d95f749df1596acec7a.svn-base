package com.impalapay.airtel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public boolean isValidDate(String inDate) {
		 
	    if (inDate == null)
	      return false;
	    Date date = null;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss±hh:mm");
		
		try {
			date = dateFormat.parse(inDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return false;
		}

	    return true;
	  }

}

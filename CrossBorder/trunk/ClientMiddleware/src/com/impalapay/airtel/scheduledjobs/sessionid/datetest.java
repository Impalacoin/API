package com.impalapay.airtel.scheduledjobs.sessionid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;





public class datetest {
	
	
	public static void main(String[] args) throws ParseException{
		final int SESSIONID_MINUTES_ALIVE = 30;
		
		DateTime dateTime = new DateTime().minusMinutes(SESSIONID_MINUTES_ALIVE);
		
		//2015-03-23T18:04:28.267+03:00
		//2015-03-82T06:11:18±06:11
		//2015-03-23 14:11:41.708+03
		//2015-03-23 06:12:43±06:12
		//Mon Mar 23 18:05:09 EAT 2015
		
		//dateTime.toGregorianCalendar().getTime()
		
		//System.out.println(dateTime.toGregorianCalendar().getTime());
		
		 DateTime dt = new DateTime();
		 org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd hh:mm:ss");
		 String str = fmt.print(dateTime);
		 
		 SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		 
		 Date date = formatter.parse(dateTime.toString());
		 
		 
		 //DateTimeFormatter fmts = DateTimeFormat.forPattern("yyy-mm-dd hh:mm:ss");
		 //DateTime dts = fmt.parseDateTime(str);
		 
		 System.out.println(date);
	}

}

package com.impalapay.airtel.util.randomgenerator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex {
	public static void main(String[] args){
		String[] valid = {
		        "Ab124ty67#",
		      "te123@"
		};
		//Pattern p = Pattern.compile("^[\\w\\d ._#$@-]{8,255}$");
		Pattern p2 = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%_]).{8,255}.*$");
		for (String s: valid) {
		    Matcher m = p2.matcher(s);
		    System.out.printf("\"%s\" is valid? %b %n", s, m.matches());

		}
	}

}

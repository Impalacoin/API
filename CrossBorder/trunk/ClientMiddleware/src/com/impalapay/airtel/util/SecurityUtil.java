package com.impalapay.airtel.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 * Security utilities.
 * <p>
 * Copyright (c) ImapalaPay Ltd., Sep 13, 2014  
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class SecurityUtil {

	private static Logger logger = Logger.getLogger(SecurityUtil.class);
	
	
	/**
	 * Return the MD5 hahs of a String. It will work correctly for most 
	 * strings. A word which does not work correctly is "michael" (check 
	 * against online MD5 hash tools).	
	 *
	 * @param toHash plain text string to encryption
	 * @return an md5 hashed string
	 */
	public static String getMD5Hash(String toHash) {	
	    String md5Hash = "";
	
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        
	        md.update(toHash.getBytes(), 0, toHash.length());
	
	        md5Hash = new BigInteger(1, md.digest()).toString(16);
			
	    } catch (NoSuchAlgorithmException e) {
	    	logger.error("NoSuchAlgorithmException while getting MD5 hash of '" + toHash + "'");
			logger.error(ExceptionUtils.getStackTrace(e));
	    }
	
	    return md5Hash;
	}

}

/*
** Local Variables:
**   mode: java
**   c-basic-offset: 2
**   tab-width: 2
**   indent-tabs-mode: nil
** End:
**
** ex: set softtabstop=2 tabstop=2 expandtab:
**
*/
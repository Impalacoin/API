package com.impalapay.airtel.servlet.init;





import org.junit.Test;

import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;

/**
 * Description of class.
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Sep 20, 2013
 *
 * @author <a href="mailto:anthonym@shujaa.co.ke">Anthony Wafula</a>
 * @version %I%, %G%
 */
public class TestCacheInit {

	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
	final int DB_PORT = 5432;
	/**
	 * Test method for {@link com.impalapay.airtel.servlet.init.CacheInit#initCache()}.
	 */
	@Test
	public void testInitCache() {
		CacheInit init = new CacheInit();
		
		AccountDAO storage = new AccountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
		init.accountDAO = storage;
		
		 init.initCache();
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
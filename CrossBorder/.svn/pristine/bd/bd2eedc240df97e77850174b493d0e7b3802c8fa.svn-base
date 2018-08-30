package com.impalapay.airtel.persistence;


import org.junit.Test;

import java.sql.Connection;

/**
 * Test our class with database credentials.
 * <p>
 * Copyright (c) Impalapay Ltd., June 20,2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */

public class TestDBCredentials {
    
	final String DB_NAME = "airteldb";
	final String DB_HOST = "localhost"; 
	final String DB_USERNAME = "airtel"; 
	final String DB_PASSWD = "LignuAv7";
	final int DB_PORT = 5432;
    
    private DBCredentials dbCredentials;

    /**
     * Test method for
     * {@link com.impalapay.airtel.persistence.DBCredentials#getConnection()}.
     */
    @Test
    public void testGetConnection() {
        dbCredentials = new DBCredentials(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);

        Connection conn;

        //for(int j=0; j<100; j++) {
        //try {
        conn = dbCredentials.getConnection();
        System.out.println("Connection is: " + conn);
        //System.out.println("Live connection " + j + " is: " + conn);
        //conn.close();

        //} catch(SQLException e) {
        //	System.out.println("SQL exception while trying to test live connections");
        //	e.printStackTrace();
        //}			
        //}


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
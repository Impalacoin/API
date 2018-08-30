package com.impalapay.airtel.persistence.accountmgmt;

import static org.junit.Assert.*;

import org.junit.Test;

import com.impalapay.airtel.beans.accountmgmt.AccountStatus;

import java.util.List;

/**
 * Test our {@link com.impalapay.airtel.persistence.accountmgmt.AccountStatusDAO}
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class TestAccountStatusDAO {

        final String DB_NAME = "airteldb";
        final String DB_HOST = "localhost"; 
        final String DB_USERNAME = "airtel"; 
        final String DB_PASSWD = "LignuAv7";
        final int DB_PORT = 5432;
        
        final String ACCOUNT_STATUS_UUID = "acecb9fa-7e21-455d-8abb-c61a840cdbec";
        final String ACCOUNT_STATUS_DESC = "Active";
        
        final int ACCOUNT_STATUS_COUNT = 5;
        
        private AccountStatusDAO storage;
        
        
        
        /**
         * Test method for {@link com.impalapay.airtel.persistence.accountmgmt.AccountStatusDAO#getAccountStatus(String)}.
         */
        @Test
        public void testGetAccountStatus() {
                storage = new AccountStatusDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
                
                AccountStatus a = storage.getAccountStatus(ACCOUNT_STATUS_UUID);
                assertEquals(a.getUuid(), ACCOUNT_STATUS_UUID);
                assertEquals(a.getDescription(), ACCOUNT_STATUS_DESC);
        }
        
        
        /**
         * Test method for {@link com.impalapay.airtel.persistence.accountmgmt.AccountStatusDAO#getAllAccountStatus()}.
         */
        @Test
        public void testGetAllAccountStatus() {
                storage = new AccountStatusDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
                
                List<AccountStatus> list = storage.getAllAccountStatus();
                assertEquals(list.size(), ACCOUNT_STATUS_COUNT);
                
                /*
                for(int j=0; j<ACCOUNT_STATUS_COUNT; j++) {
                        System.out.println(list.get(j));
                }*/
        }

}
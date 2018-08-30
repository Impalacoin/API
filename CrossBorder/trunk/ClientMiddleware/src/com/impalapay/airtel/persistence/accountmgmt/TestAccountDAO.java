package com.impalapay.airtel.persistence.accountmgmt;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.impalapay.airtel.beans.accountmgmt.Account;

/**
 * Test our 
 * {@link com.impalapay.airtel.persistence.accountmgmt.AccountDAO}
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */
public class TestAccountDAO {

        final String DB_NAME = "airteldb2";
        final String DB_HOST = "localhost"; 
        final String DB_USERNAME = "airtel2"; 
        final String DB_PASSWD = "ThejKoyb3";
        final int DB_PORT = 5432;
             
        final String ACCOUNT_UUID = "9756f889-811a-4a94-b13d-1c66c7655a7f", ACCOUNT_UUID2 = "81bf3079-4495-4bec-a50d-c91a7c512d78";
          
        final String ACCOUNT_STATUS_UUID = "acecb9fa-7e21-455d-8abb-c61a840cdbec", 
                        ACCOUNT_STATUS_UUID2 = "0b539b9f-8ad1-4c33-910a-642d70012def";
     
        final String ACCOUNT_FIRST_NAME = "demo", ACCOUNT_FIRST_NAME2 = "eugene", ACCOUNT_FIRST_NAME3 = "James";
     
        final String ACCOUNT_LAST_NAME = "demo", ACCOUNT_LAST_NAME2 = "chimita", ACCOUNT_LAST_NAME3 = "Bond";
     
        final String ACCOUNT_USERNAME = "demo", ACCOUNT_USERNAME2 = "eugenechi", ACCOUNT_USERNAME3 = "jamesbond";
     
        final String ACCOUNT_LOGIN_PASSWD = "fe01ce2a7fbac8fafaed7c982a04e229", ACCOUNT_LOGIN_PASSWD2 = "euge", ACCOUNT_LOGIN_PASSWD3 = "golden";
        
        final String ACCOUNT_API_USERNAME = "airtel1", ACCOUNT_API_USERNAME2 ="euge",  ACCOUNT_API_USERNAME3 ="euge";
     
        final String ACCOUNT_API_PASSWD = "fe01ce2a7fbac8fafaed7c982a04e229", ACCOUNT_API_PASSWD2 = "chim", ACCOUNT_API_PASSWD3 = "eye";
     
        final String ACCOUNT_EMAIL = "demo@demo.com", ACCOUNT_EMAIL2 = "info@eugene.com", ACCOUNT_EMAIL3 = "info@007.com";
     
        final String ACCOUNT_PHONE = "+254 020 250 9260", ACCOUNT_PHONE2 = "+256 20 231 1478", ACCOUNT_PHONE3 = "+1 254 987 2345";
     
        //final int ACCOUNT_COUNTRY_ID = 130, ACCOUNT_COUNTRY_ID2 = 130, ACCOUNT_COUNTRY_ID3 = 131;
     
        //final int ACCOUNT_LANGUAGE_ID = 170, ACCOUNT_LANGUAGE_ID2 = 170, ACCOUNT_LANGUAGE_ID3 = 171;
        
        final int ACCOUNT_COUNT = 5;

        private AccountDAO storage;
        
                
        /**
         * Test method for {@link com.impalapay.airtel.persistence.accountmgmt.AccountDAO#getAccount(int)}.
         */
        @Ignore
        @Test
        public void testGetAccountUuid() {
                storage = new AccountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
                
                Account a = storage.getAccount(ACCOUNT_UUID);
                
                assertEquals(a.getUuid(), ACCOUNT_UUID);
                assertEquals(a.getAccountStatusUuid(), ACCOUNT_STATUS_UUID);
                assertEquals(a.getFirstName(), ACCOUNT_FIRST_NAME);
                assertEquals(a.getLastName(), ACCOUNT_LAST_NAME);
                assertEquals(a.getUsername(), ACCOUNT_USERNAME);
                assertEquals(a.getLoginPasswd(), ACCOUNT_LOGIN_PASSWD);
                assertEquals(a.getApiUsername(),ACCOUNT_API_USERNAME);
                assertEquals(a.getApiPasswd(), ACCOUNT_API_PASSWD);
                assertEquals(a.getEmail(), ACCOUNT_EMAIL);
                assertEquals(a.getPhone(), ACCOUNT_PHONE);
        }

        
        /**
         * Test method for {@link com.impalapay.airtel.persistence.accountmgmt.AccountDAO#getAccountName(java.lang.String)}.
         */
        @Ignore
        @Test
        public void testGetAccountName() {
                storage = new AccountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
                
                Account a = storage.getAccountName(ACCOUNT_USERNAME);
                
                assertEquals(a.getUuid(), ACCOUNT_UUID);
                assertEquals(a.getAccountStatusUuid(), ACCOUNT_STATUS_UUID);
                assertEquals(a.getFirstName(), ACCOUNT_FIRST_NAME);
                assertEquals(a.getLastName(), ACCOUNT_LAST_NAME);
                assertEquals(a.getUsername(), ACCOUNT_USERNAME);
                assertEquals(a.getLoginPasswd(), ACCOUNT_LOGIN_PASSWD);
                assertEquals(a.getApiPasswd(), ACCOUNT_API_PASSWD);
                assertEquals(a.getEmail(), ACCOUNT_EMAIL);
                //assertEquals(a.getPhone(), ACCOUNT_PHONE);            
        }

        
        /**
         * Test method for {@link com.impalapay.airtel.persistence.accountmgmt.AccountDAO#getAccountEmail(java.lang.String)}.
         */
        @Ignore
        @Test
        public void testGetAccountStringEmail() {
                storage = new AccountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
                
                Account a = storage.getAccountEmail(ACCOUNT_EMAIL);
                                
                assertEquals(a.getUuid(), ACCOUNT_UUID);
                assertEquals(a.getAccountStatusUuid(), ACCOUNT_STATUS_UUID);
                assertEquals(a.getFirstName(), ACCOUNT_FIRST_NAME);
                assertEquals(a.getLastName(), ACCOUNT_LAST_NAME);
                assertEquals(a.getUsername(), ACCOUNT_USERNAME);
                assertEquals(a.getLoginPasswd(), ACCOUNT_LOGIN_PASSWD);
                assertEquals(a.getApiPasswd(), ACCOUNT_API_PASSWD);
                assertEquals(a.getEmail(), ACCOUNT_EMAIL);
                //assertEquals(a.getPhone(), ACCOUNT_PHONE);                    
        }
        
        
        /**
         * Test method for {@link com.impalapay.airtel.persistence.accountmgmt.AccountDAO#getAllAccounts()}.
         */
        @Ignore
        @Test
        public void testGetAllAccounts() {
                storage = new AccountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
                
                List<Account> list = storage.getAllAccounts();
                //assertEquals(list.size(), ACCOUNT_COUNT);
                
                System.out.println(list.get(1));
                System.out.println(list.get(2));
        }
        

        /**
         * Test method for {@link com.impalapay.airtel.persistence.accountmgmt.AccountDAO#addAccount(com.impalapay.airtel.beans.accountmgmt.Account)}.
     */
    @Ignore
    @Test
        public void testAddAccount() {
                storage = new AccountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
                
                Account a = new Account();              
                a.setUuid(ACCOUNT_UUID2);
                a.setAccountStatusUuid(ACCOUNT_STATUS_UUID2);
                a.setFirstName(ACCOUNT_FIRST_NAME2);
                a.setLastName(ACCOUNT_LAST_NAME2);
                a.setUsername(ACCOUNT_USERNAME2);
                a.setLoginPasswd(ACCOUNT_LOGIN_PASSWD2);
                a.setApiUsername(ACCOUNT_API_USERNAME2);
                a.setApiPasswd(ACCOUNT_API_PASSWD2);
                a.setEmail(ACCOUNT_EMAIL2);
                a.setPhone(ACCOUNT_PHONE2);
                                
                assertTrue(storage.addAccount(a));
                
                //List<Account> list = storage.getAllAccounts();
                //assertEquals(list.size(), ACCOUNT_COUNT + 1);           
        }
                

        /**
         * Test method for {@link AccountDAO#updateAccount(java.lang.String, com.impalapay.airtel.beans.accountmgmt.Account) }.
         */ 
        @Ignore
        @Test
        public void testUpdateAccount() {
                storage = new AccountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
                
                Account a = new Account();
                a.setUuid(ACCOUNT_UUID2);
                a.setAccountStatusUuid(ACCOUNT_STATUS_UUID);
                
                a.setFirstName(ACCOUNT_FIRST_NAME3);
                a.setLastName(ACCOUNT_LAST_NAME3);
                a.setUsername(ACCOUNT_USERNAME3);
                a.setLoginPasswd(ACCOUNT_LOGIN_PASSWD3);
                a.setApiUsername(ACCOUNT_API_USERNAME3);
                a.setApiPasswd(ACCOUNT_API_PASSWD3);
                a.setEmail(ACCOUNT_EMAIL3);
                a.setPhone(ACCOUNT_PHONE3);             
                
                assertTrue(storage.updateAccount(ACCOUNT_UUID2, a));
        }
        

}
 
package com.impalapay.airtel.persistence.accountmgmt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.logincount.LoginCount;

public class TestLoginCountDAO {
	
	final String DB_NAME = "airteldb";
    final String DB_HOST = "localhost"; 
    final String DB_USERNAME = "airtel"; 
    final String DB_PASSWD = "LignuAv7";
    final int DB_PORT = 5432;
         
    final String LOGINCOUNT_UUID = "7691160b2eb346a88d0100249dba8ae6";
    
    final int LOGIN_COUNT = 5;

    private LoginCountDAO storage;
    
    @Ignore
    @Test
    public void testGetLoginCountUuid() {
            storage = new LoginCountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
            
            LoginCount a = storage.getLoginCount(LOGINCOUNT_UUID);
            
            assertEquals(a.getUuid(),LOGINCOUNT_UUID);
           
    }
    @Ignore
    @Test
    public void testGetLoginCountUuidAccount() {
            storage = new LoginCountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
            
            Account account = new Account();
            
          account.setUuid("9756f889-811a-4a94-b13d-1c66c7655a7f");
          
          
            
            LoginCount a = storage.getLoginCount(account);
            
            System.out.println(a.getCountlogin());
            
            //assertEquals(a.getUuid(),LOGINCOUNT_UUID);
           
    }
    @Ignore
    @Test
    public void testUpdateLoginCount(){
    	 storage = new LoginCountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
    	 Account account = new Account();
    	 
    	 account.setUuid("9756f889-811a-4a94-b13d-1c66c7655a7f");
    	 
    	 assertTrue(storage.incrementLoginCount(account));
    	
    }
    @Ignore
    @Test
    public void testresetLoginCount(){
    	 storage = new LoginCountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
    	 Account account = new Account();
    	 
    	 account.setUuid("9756f889-811a-4a94-b13d-1c66c7655a7f");
    	 
    	 assertTrue(storage.resetLoginCount(account));
    	
    }
    
    //@Ignore
    @Test
    public void testaddLogincount(){
    	storage = new LoginCountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
    	
         Account account = new Account();
    	 
    	 account.setUuid("7967107d-d61c-43dd-bc5b-aa12fd08497b");
    	 
    	 assertTrue(storage.addLoginCount(account));
    	
    	
    }
    @Ignore
    @Test
     public void testGetAllLoginCount(){
    	storage = new LoginCountDAO(DB_NAME, DB_HOST, DB_USERNAME, DB_PASSWD, DB_PORT);
    	
    	List<LoginCount> list = storage.getAllLoginCounts();
    	
    	 System.out.println(list.get(0));
    	
    }
	

}

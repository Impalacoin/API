package com.impalapay.airtel.accountmgmt.admin;

/**
 * Constants which are used in session management of the Administrator account. 
 * <p>
 * Copyright (c) ImpalaPay  Ltd., June 23, 2014  
 * 
 * @author <a href="mailto:michael@impalapay.com">Michael Wakahe</a>
 * @version %I%, %G%
 * 
 */
public class SessionConstants {

	final public static int SESSION_TIMEOUT = 500;  // Number of seconds for which a session is active.

	
	final public static String ADMIN_SESSION_KEY = "Admin Session Key";
	final public static String ADMIN_SIGN_IN_ERROR_KEY = "Admin Error Login";
	final public static String ADMIN_SIGN_IN_ERROR_VALUE = "Sorry, the administrator username and/or " +
			"password are incorrect. Please try again.";
	final public static String ADMIN_LOGIN_TIME_KEY = "Admin login time key";
	
	
	final public static String ADMIN_ADD_ACCOUNT_ERROR_KEY = "Admin Add Account Error";
	final public static String ADMIN_ADD_ACCOUNT_SUCCESS_KEY = "Admin Add Account Success";
	final public static String ADMIN_ADD_ACCOUNT_PARAMETERS = "Admin Add Account Parameters";
		
	
	final public static String ADMIN_ADD_MASTER_FLOAT_ERROR_KEY = "Admin Add Master Float Error";
	final public static String ADMIN_ADD_MASTER_FLOAT_SUCCESS_KEY = "Admin Add Master Float Success";
	final public static String ADMIN_ADD_MASTER_FLOAT_PARAMETERS = "Admin Add Master Credit Parameters";

	
	final public static String ADMIN_ADD_COUNTRY_FLOAT_ERROR_KEY = "Admin Add Balance By Country Error";
	final public static String ADMIN_ADD_COUNTRY_FLOAT_SUCCESS_KEY = "Admin Add Balance By Country Success";
	final public static String ADMIN_ADD_COUNTRY_FLOAT_PARAMETERS = "Admin Add balance Parameters";
	
	final public static String ADMIN_ADD_COUNTRY_MSISDN_ERROR_KEY = "Admin Add MSISDN By Country Error";
	final public static String ADMIN_ADD_COUNTRY_MSISDN_SUCCESS_KEY = "Admin Add MSISDN By Country Success";
	final public static String ADMIN_ADD_COUNTRY_MSISDN_PARAMETERS = "Admin Add Msisdn Parameters";
    
	final public static String ADMIN_ADD_ACCOUNT_URL_ERROR_KEY ="admin add client url error";
	final public static String ADMIN_ADD_ACCOUNT_URL_SUCCESS_KEY ="admin add client url Success";
	final public static String ADMIN_ADD_ACCOUNT_URL_PARAMETERS ="admin add client url parameters";
    
    
    
    //Constants relating to quartz job scheduler
    final public static String ADMIN_QUARTZ_ADD_JOB_KEY="Admin add quartz job";
     //A key corresponding to the email address of the recipient
    final public static String ADMIN_RECIPIENT_KEY="Admin recpient key holding recipient address";
    final public static String ADMIN_MESSAGE_TO_SEND_KEY="Admin message to be sent";
    final public static String ADMIN_JOB_EXISTS = "Sorry,email notification already set";
    final public static String JOB_DELETE_SUCCESSFUL="delete successful!";
    //group name associated with a job instance
    final public static String GROUP_NAME_DEFAULT="SMS Balance Default Notification";
    //trigger quartz job 15th of every month at 9 a.m
    final public static String ADMIN_CRON_EXPRESSION="0 00 09 15 * ? *";
    
    
    
    
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
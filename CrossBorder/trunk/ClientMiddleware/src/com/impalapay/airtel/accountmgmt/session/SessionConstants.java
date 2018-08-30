package com.impalapay.airtel.accountmgmt.session;

/**
 * Constants which are used in session management of the airtel api.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * @version %I%, %G%
 */
public class SessionConstants {

    public static int SESSION_TIMEOUT = 500;  // Number of seconds for which a session is active.
    final public static String ACCOUNT_SIGN_IN_KEY = "Account Signin Key";
    
    // The value associated with the following key is the Unix time in seconds when the user logged in.
    final public static String ACCOUNT_SIGN_IN_TIME = "Account Signin Time";
    
    final public static String ACCOUNT_SIGN_IN_ERROR_KEY = "Error Login";
    final public static String ACCOUNT_SIGN_IN_NO_EMAIL = "Sorry, there is no user with that username. Please try again.";
    final public static String ACCOUNT_SIGN_IN_WRONG_PASSWORD = "Sorry, the username and password do not match. Please try again.";
    final public static String ACCOUNT_CHANGE_PASSWORD_KEY = "Account change password key";
    final public static String ERROR_INVALID_EMAIL = "Invalid username,try again.";
    
    //messages associated with passwords;
    final public static String INCORRECT_PASSWORD = "The password you gave is incorrect.";
    final public static String CORRECT_PASSWORD = "Password Changed";
    final public static String MISMATCHED_PASSWORD = "The passwords supplied do not match";
    
    final public static String ACCOUNT_EDIT_CODE_ID = "Account Edit Code Id";
    
    //account associated with deactivated account.
    final public static String ACCOUNT_DEACTIVATED ="Your account has been dectivated.Please contact the system Administrator";
    
    
    
    
    
    //constants used by email notifications/quartz job scheduler
    final public static String SESSION_EMAIL_KEY = "Session Email";
    final public static String JOB_EXISTS = "Sorry,email notification already set";
    final public static String JOB_NOT_EXISTS = "Sorry, email notification does not exist";
    //final public static String GROUP_NAME_BALANCE_THRESHOLD = "Balance Threshold";
    //A key corresponding to the email address of the recipient
    final public static String RECIPIENT_KEY="RECIPIENT_KEY";
  
    
    //A key relating quartz job scheduler error messages
    final public static String QUARTZ_ADD_JOB_KEY = "Error key for adding quartz jobs"; 
    final public static String QUARTZ_UPDATE_JOB_KEY = "Error key for updating balance notification jobs";
    
    
    
    final public static String MESSAGE_TO_SEND_KEY="message to be sent";
    
    final public static String DELETE_SUCCESSFUL="Email notification deleted successfully !";
    final public static String ADD_BALANCE_THRESHOLD = "Add Balance Threshold";
    final public static String VALIDATE_EMAIL_ADDRESS="Validate email address";
    final public static String BALANCE_THRESHOLD_KEY="balance threshold";
    final public static String QUARTZ_ADD_THRESHOLD_KEY = "Error key for adding balance threshold jobs"; 
    final public static String QUARTZ_UPDATE_THRESHOLD_KEY="Error key for updating balance threshold jobs";
    final public static String CHECK_BALANCE_CRONEXPRESSION= "0 0/1 * * * ? *";
    
   
   
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
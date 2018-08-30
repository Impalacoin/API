package com.impalapay.airtel.persistence.sessionlog;

import com.impalapay.airtel.beans.accountmgmt.Account;

import com.impalapay.airtel.beans.sessionlog.ClientUrl;
/**
 * Persistence for ClientUrl.
 * <p>
 * Copyright (c) Imapalapay Ltd., August 29, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */


public interface AirtelClientURL {
	
	/**
	 * @param clienturl
	 * @return whether the action was successful or not
	 */
	public boolean putClientUrl(ClientUrl clienturl);
	
	/**
	 * 
	 * @param uuid
	 * @return whether the persisted{@link ClientUrl} with a matching uuid 
	 * is active or not
	 */
	public boolean isActive(String uuid);
	
	/**
	 * make a persisted{@link ClientUrl} with the same {@link java.util.UUID}
	 * 
	 * as the one in the argument to be inactive
	 * @param clienturl
	 * @return
	 */
	public boolean deactivate(ClientUrl clienturl); // Should be 'deactivate'
	
	
	/**
	 * @param account
	 * @return a {link ClientUrl}
	 */
	public ClientUrl getClientUrl(Account account);

}

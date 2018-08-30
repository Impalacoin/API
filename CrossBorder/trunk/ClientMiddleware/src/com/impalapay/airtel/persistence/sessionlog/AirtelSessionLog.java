package com.impalapay.airtel.persistence.sessionlog;



import java.util.Date;
import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.sessionlog.SessionLog;
/**
 * Persistence for {@link SessionLog}.
 * <p>
 * Copyright (c) ImapalaPay Ltd. August 29, 2014
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */

public interface AirtelSessionLog {
	
	
	/**
	 * 
	 * @param sessionlog
	 * @return whether the action was successful or not
	 */
	public boolean putSessionLog(SessionLog sessionlog);
	
	
	/**
	 * 
	 * @param sessionlog
	 * @return whether the session is valid or not
	 */
	public boolean isValid(String uuid);
	
	
	/**
	 * 
	 * @param sessionlog
	 * @return whether session invalidation was successful
	 */
	public boolean invalidate(SessionLog sessionlog);
	
	
	/**
     * Gets a {@link SessionLog} request(s) that belong to a particular account
     * holder.
     *
     * @param account
     * @return {@link SessionLog} SessionLog request(s)
     */
	public SessionLog getValidSessionLog(Account account);
	
	
	/**
	 * Expire {@link SessionLog}s whose creation time is older than the 
	 * specified {@link Date}.
	 * 
	 * @param date
	 */
	public void expireSessionLogs(Date date);
	

}

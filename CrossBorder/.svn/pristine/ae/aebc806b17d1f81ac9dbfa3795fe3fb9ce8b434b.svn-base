package com.impalapay.airtel.persistence.accountmgmt;

import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.logincount.LoginCount;

public interface AirtelLoginCountDAO {
	/**
	 *
	 * @param uuid
	 * @return LoginCount
	 */
	public LoginCount getLoginCount(String uuid);
	
	/**
	 *
	 * @param uuid
	 * @return account
	 */
	public LoginCount getLoginCount(Account account);


	/**
	 *
	 * @return List<LoginCount>
	 */
	public List<LoginCount> getAllLoginCounts();

	/**
	 *
	 * @param logincount
	 * @return boolean whether the logincount was added successfully or not.
	 */
	public boolean addLoginCount(Account account);

	/**
	 *
	 * @param accountUuid
	 *            the account uuid of the logincount to be modified
	 * 
	 * 
	 * @return boolean whether the increment was successful.
	 */
	public boolean updateLoginCount(String accountUuid);
	
	public boolean incrementLoginCount(Account account);

	public boolean resetLoginCount(Account account);

}

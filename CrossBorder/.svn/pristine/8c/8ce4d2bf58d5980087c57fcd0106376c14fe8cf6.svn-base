package com.impalapay.airtel.persistence.topup;

import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.topup.Topup;

/**
 * Persistence description for {@link Topup}
 * <p>
 * Copyright (c) ImpalaPay LTD., Sep 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */

public interface AccountTopUp {

	/**
	 * Gets all {@link Topup}s which have an UUID matching the argument. The
	 * list returned is not arranged in any particular order.
	 * 
	 * @param uuid
	 * @return account topup request(s)
	 */
	public List<Topup> getTopups(String uuid);

	/**
	 * Gets all account topup request(s) that belong to a particular account
	 *
	 * @param account
	 * @return List<{@link Topup}> account topup request(s)
	 */
	public List<Topup> getTopups(Account account);

	/**
	 * Gets all account-topup requests
	 *
	 * @return List<{@link Topup}> all account-topup requests
	 */
	public List<Topup> getAllTopups();

	/**
	 * Gets all account-topup requests between the specified fromIndex,
	 * inclusive, and toIndex, exclusive.
	 *
	 * @param fromIndex
	 * @param toIndex
	 * 
	 * @return List<{@link Topup}> all account-topup requests
	 */
	public List<Topup> getAllTopups(int fromIndex, int toIndex);

	/**
	 *
	 * @param topup
	 * @return boolean status of insertion
	 */
	public boolean addTopup(Topup topup);

	/**
	 * Returns a view of the portion of an Account's Top up activity between the
	 * specified fromIndex, inclusive, and toIndex, exclusive.
	 *
	 * @param account
	 * @param fromIndex
	 * @param toIndex
	 * @return List<{@link Topup}> all account-topup requests
	 */
	public List<Topup> getTopup(Account account, int fromIndex, int toIndex);

	/**
	 * Returns a view of the portion of an Account's Top up activity between the
	 * specified fromIndex, inclusive, and toIndex, exclusive. The {@link Topup}
	 * s in the list have an UUID matching to that given as a parameter in this
	 * method.
	 *
	 * @param account
	 * @param uuid
	 * @param fromIndex
	 * @param toIndex
	 * 
	 * @return List<{@link Topup}> all account-topup requests matching the uuid
	 */
	public List<Topup> getTopupByUuid(Account account, String uuid,
			int fromIndex, int toIndex);

	/**
	 * Returns all Top up activity between the specified fromIndex, inclusive,
	 * and toIndex, exclusive matching the uuid.
	 *
	 * @param uuid
	 * @param fromIndex
	 * @param toIndex
	 * @return List<{@link Topup}> all account-topup requests matching the uuid
	 */
	public List<Topup> getAllTopupByUuid(String uuid, int fromIndex, int toIndex);

}

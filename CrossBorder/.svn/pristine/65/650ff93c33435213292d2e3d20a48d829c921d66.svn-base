package com.impalapay.airtel.persistence.accountmgmt.balance;

import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.accountmgmt.balance.AccountPurchaseByCountry;
import com.impalapay.airtel.beans.accountmgmt.balance.MasterAccountFloatPurchase;
import com.impalapay.airtel.beans.geolocation.Country;

public interface AccountPurchase {
	/**
	 * 
	 * @param uuid
	 * @return the {@link MasterAccountFloatPurchase} with a corresponding UUID
	 */
	public MasterAccountFloatPurchase getMasterFloat(String uuid);

	/**
	 * To be called when the client master account float is debited.
	 * 
	 * @param purchase
	 * @return whether or not the purchase action was successful
	 */
	public boolean putMasterFloat(MasterAccountFloatPurchase purchase);

	/**
	 * This method is to be used infrequently, for example when there is an
	 * accidental entry of a master Float.
	 * 
	 * @param uuid
	 * @return whether or not the delete action was successful
	 */
	public boolean deleteMasterFloat(String uuid);

	/**
	 * Gets all float update made on the master accounts
	 * 
	 * @return a list of purchases made by the master account.
	 */
	public List<MasterAccountFloatPurchase> getMasterFloat();

	/**
	 * Gets all float update made on the master accounts belonging to this
	 * account
	 * 
	 * @return a list of purchases made by the master account.
	 */
	public List<MasterAccountFloatPurchase> getMasterFloat(Account account);

	/**
	 * @param uuid
	 * @return
	 */
	public AccountPurchaseByCountry getByCountryPurchase(String uuid);

	/**
	 * To be called when a client redeem/recharge float on a country.
	 * 
	 * @param purchase
	 * @return whether or not the recharge action was successful
	 */
	public boolean putClientPurchaseByCountry(
			AccountPurchaseByCountry purchase);
	
	public boolean putClientPurchaseByCountry2(
			AccountPurchaseByCountry purchase);

	/**
	 * This method is to be used infrequently, for example when there is an
	 * accidental entry of a float in a country.
	 * 
	 * @param uuid
	 * @return whether or not the delete action was successful
	 */
	public boolean deleteClientPurchaseByCountry(String uuid);

	/**
	 * Gets a list of {@link AccountPurchaseByCountry}s belonging to this
	 * account holder.
	 * 
	 * @param account
	 * @return a list of recharge done by this client account.
	 */
	public List<AccountPurchaseByCountry> getClientPurchasesByCountry(
			Account account);

	/**
	 * Returns a list of all {@link AccountPurchaseByCountry}s.
	 * 
	 * @return a list of recharge done by all client accounts.
	 */
	public List<AccountPurchaseByCountry> getAllClientPurchasesByCountry();

	/**
	 * Gets a list of {@link AccountPurchaseByCountry}s belonging to this
	 * account holder.
	 * 
	 * @param account
	 * @return a list of recharge done by this client account.
	 */
	public List<AccountPurchaseByCountry> getClientPurchasesByCountry(
			Account account, Country couuntry);
	
	

}

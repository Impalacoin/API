package com.impalapay.airtel.persistence.accountmgmt;

import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;


/**
 * Persistence description for {@link Account}
 * <p>
* Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 *
 */
public interface AirtelAccountDAO {

    
    /**
     *
     * @param uuid
     * @return Account
     */
    public Account getAccount(String uuid);

    
    /**
     *
     * @param username
     * @return Account
     */
    public Account getAccountName(String username);

    
    /**
     *
     * @param email
     * @return Account
     */
    public Account getAccountEmail(String email);

    /**
     *
     * @return List<Account>
     */
    public List<Account> getAllAccounts();

    
    /**
     *
     * @param account
     * @return boolean whether the account was added successfully or not.
     */
    public boolean addAccount(Account account);

    
    /**
     *
     * @param accountUuid the uuid of the account to be modified
     * 
     * @param newAccount the settings of the modifications. Some details such as
     * creation date cannot be modified.
     * 
     * @return boolean whether the account was updated successfully or not.
     */
    public boolean updateAccount(String accountUuid, Account newAccount);
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
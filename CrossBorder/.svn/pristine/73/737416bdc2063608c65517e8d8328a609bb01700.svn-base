package com.impalapay.airtel.servlet.init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.SizeOfPolicyConfiguration;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.impalapay.airtel.beans.StorableBean;
import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.persistence.accountmgmt.AccountDAO;
import com.impalapay.airtel.persistence.geolocation.CountryDAO;
import com.impalapay.airtel.persistence.transaction.TransactionDAO;
import com.impalapay.airtel.persistence.transaction.TransactionStatusDAO;
//import com.impalapay.airtel.persistence.topup.TopupStatusDAO;
import com.impalapay.airtel.servlet.util.PropertiesConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Brings certain frequently accessed variables into cache.
 * <br />
 * Ehcache objects have to be serializable to allow for off-disk storage.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @author <a href="mailto:mike@impalapay.com">Michael Wakahe</a>
 */
public class CacheInit extends HttpServlet {

    protected AccountDAO accountDAO;
    protected CountryDAO countryDAO;
    protected TransactionStatusDAO transactionStatusDAO;
    protected TransactionDAO transactionDAO;
    
    private CacheManager cacheManager;
    
    private Logger logger;
    
    private SizeOfPolicyConfiguration sizeOfPolicyConfiguration;

    
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        accountDAO = AccountDAO.getInstance();
        countryDAO = CountryDAO.getInstance();
        transactionStatusDAO = TransactionStatusDAO.getInstance();
        transactionDAO =TransactionDAO.getInstance();

        sizeOfPolicyConfiguration = new SizeOfPolicyConfiguration();
        sizeOfPolicyConfiguration.setMaxDepthExceededBehavior("abort");

        logger = Logger.getLogger(this.getClass());
        
        logger.info("Starting to initialize cache");
        initCache();
        logger.info("Have finished initializing cache");
    }

    
    /**
     *
     */
    protected void initCache() {
        DiskStoreConfiguration diskConfig = new DiskStoreConfiguration();
        diskConfig.setPath(System.getProperty("java.io.tmpdir") + File.separator + "ehcache" +
        		File.separator + "AirtelRemittance");

        Configuration config = (new Configuration()).diskStore(diskConfig);
        config.setMaxBytesLocalHeap(Long.parseLong(PropertiesConfig.getConfigValue("MAX_BYTES_LOCAL_HEAP")));
        config.setMaxBytesLocalDisk(Long.parseLong(PropertiesConfig.getConfigValue("MAX_BYTES_LOCAL_DISK")));
        config.setUpdateCheck(false);

        cacheManager = CacheManager.create(config);
        
        initAccountsCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);

        List<? extends StorableBean> objectList;

        objectList = accountDAO.getAllAccounts();
        initCacheByUuid(CacheVariables.CACHE_ACCOUNTS_BY_UUID, objectList);

        objectList = countryDAO.getAllCountries();
        initCacheByUuid(CacheVariables.CACHE_COUNTRY_BY_UUID, objectList);

        objectList = transactionStatusDAO.getAllTransactionStatus();
        initCacheByUuid(CacheVariables.CACHE_TRANSACTIONSTATUS_BY_UUID, objectList);
        
        initAccountsCache(CacheVariables.CACHE_ACCOUNTS_BY_USERNAME);
        
        initGenericCache(CacheVariables.CACHE_STATISTICS_BY_USERNAME);
        initGenericCache(CacheVariables.CACHE_ALL_ACCOUNTS_STATISTICS);

        initGenericCache(CacheVariables.CACHE_FLOATPURCHASEPERCOUNTRY_BY_ACCOUNTUUID);
        initGenericCache(CacheVariables.CACHE_BALANCEPERCOUNTRY_BY_ACCOUNTUUID);

        initGenericCache(CacheVariables.CACHE_MASTERBALANCE);
        initGenericCache(CacheVariables.CACHE_MASTERPURCHASE);
    }
    
    /**
    *
    * @param cacheName
    */
   private void initAccountsCache(String cacheName) {
	   
	   

       if (!cacheManager.cacheExists(cacheName)) {
           CacheConfiguration cacheConfig = new CacheConfiguration().sizeOfPolicy(sizeOfPolicyConfiguration);
           cacheConfig.setCopyOnRead(false); // Whether the Cache should copy elements it returns
           cacheConfig.setCopyOnWrite(false); // Whether the Cache should copy elements it gets
           cacheConfig.setEternal(true); // Sets whether elements are eternal.    	
           cacheConfig.setName(cacheName); // Sets the name of the cache.

           Cache accountsCache = new Cache(cacheConfig);
           cacheManager.addCacheIfAbsent(accountsCache);
           if (accountsCache.getStatus() == Status.STATUS_UNINITIALISED) {
               accountsCache.initialise();
           }

           List<Account> allAccounts = accountDAO.getAllAccounts();
           
           if (StringUtils.equals(cacheName, CacheVariables.CACHE_ACCOUNTS_BY_USERNAME)) {
               for (Account a : allAccounts) {
                   accountsCache.put(new Element(a.getUsername(), a)); 	// Username as the key
               }

           } else if (StringUtils.equals(cacheName, CacheVariables.CACHE_ACCOUNTS_BY_UUID)) {
               for (Account a : allAccounts) {
                   accountsCache.put(new Element(a.getUuid(), a));		// Uuid as the key
               }

           }
       }
   }


    /**
     *
     * @param cacheName
     * @param objList
     */
    private void initCacheByUuid(String cacheName, List<? extends StorableBean> objList) {
        if (!cacheManager.cacheExists(cacheName)) {
            CacheConfiguration cacheConfig = new CacheConfiguration().sizeOfPolicy(sizeOfPolicyConfiguration);
            cacheConfig.setCopyOnRead(false); // Whether the Cache should copy elements it returns
            cacheConfig.setCopyOnWrite(false); // Whether the Cache should copy elements it gets
            cacheConfig.setEternal(true); // Sets whether elements are eternal.    	
            cacheConfig.setName(cacheName); // Sets the name of the cache.

            Cache cache = new Cache(cacheConfig);
            cacheManager.addCacheIfAbsent(cache);
            if (cache.getStatus() == Status.STATUS_UNINITIALISED) {
                cache.initialise();
            }

            for (StorableBean b : objList) {
                cache.put(new Element(b.getUuid(), b));	// UUID as the key
            }
        }
    }

    
    
    
    /**
     *
     * @param cacheName
     */
    private void initGenericCache(String cacheName) {
        if (!cacheManager.cacheExists(cacheName)) {
            CacheConfiguration cacheConfig = new CacheConfiguration().sizeOfPolicy(sizeOfPolicyConfiguration);
            cacheConfig.setCopyOnRead(false); // Whether the Cache should copy elements it returns
            cacheConfig.setCopyOnWrite(false); // Whether the Cache should copy elements it gets
            cacheConfig.setEternal(true); // Sets whether elements are eternal.    	
            cacheConfig.setName(cacheName); // Sets the name of the cache.

            Cache cache = new Cache(cacheConfig);
            cacheManager.addCacheIfAbsent(cache);
            if (cache.getStatus() == Status.STATUS_UNINITIALISED) {
                cache.initialise();
            }
        }
    }


    /**
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();

        CacheManager.getInstance().shutdown();
    }
}

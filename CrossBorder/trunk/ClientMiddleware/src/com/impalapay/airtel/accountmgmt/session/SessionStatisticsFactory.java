package com.impalapay.airtel.accountmgmt.session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.impalapay.airtel.beans.accountmgmt.Account;
import com.impalapay.airtel.beans.transaction.TransactionStatus;
import com.impalapay.airtel.cache.CacheVariables;
import com.impalapay.airtel.persistence.geolocation.CountryDAO;
import com.impalapay.airtel.persistence.transaction.TransactionStatusDAO;
import com.impalapay.airtel.persistence.util.CountUtils;
import com.impalapay.airtel.servlet.report.chart.bar.TransactionBarDay;
import com.impalapay.airtel.beans.geolocation.Country;








//import com.impalapay.airtel..servlet.report.chart.bar.IncomingSMSBarDay;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Hours;


/**
 * Creates statistics that are to be cached in the session of a user.
 * <p>
 * Copyright (c) ImpalaPay Ltd., July 31, 2014  
 * 
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 * 
 */
public class SessionStatisticsFactory {

	private static CountUtils countUtils;	
	private static CountryDAO countryDAO;
	private static TransactionStatusDAO transactionStatusDAO;
	
	static {
		countUtils = CountUtils.getInstance();	
		
		countryDAO = CountryDAO.getInstance();
		
		transactionStatusDAO = TransactionStatusDAO.getInstance();
		
	}
	
	
	/**
	 * Some refactoring with reflection can be applied here.
	 * 
	 * @param account
	 * @return {@link SessionStatistics}
	 */
	public static SessionStatistics getSessionStatistics (Account account) {
		SessionStatistics stats = new SessionStatistics();
		TransactionStatus acceptedTransactionStatus,successTransactionStatus;
	    List<Country> countryList;
	    int count;
	    double amount;
		
	    
	    stats.setTransactionCountTotal(countUtils.getTransactionCount(account));
		
	
	    // Set up data for the pie charts
	    
	  //Set total number of transactions attempts or requests
        acceptedTransactionStatus = transactionStatusDAO.getTransactionStatus(TransactionStatus.SUCCESS);
        stats.setTransactionCountTotal(countUtils.getTransactionCount(account, acceptedTransactionStatus));
       

        countryList = countryDAO.getAllCountries();
        
        //Set total number of transaction requests per country
        // Set up data for the pie charts
        for (Country country : countryList) {
            count = countUtils.getTransactionCount(account, country,acceptedTransactionStatus);
            if (count > 0) {
                stats.addCountryTransactionCountTotal(country, count);
            }
            
        }
        
        
        //Set total value of  transaction requests per country
        //that have been successful
        successTransactionStatus = transactionStatusDAO.getTransactionStatus(TransactionStatus.TRANSACTION_SUCCESS);
        //stats.setTopupCountTotal(countUtils.getTopupCount(account, successTopupStatus));
        
        for (Country country : countryList) {
            count = countUtils.getTransactionCount(account, country,successTransactionStatus);
            amount=countUtils.getTransactionAmount(account, country, successTransactionStatus);
            if (count > 0) {
                stats.addCountryTransactionCountSuccess(country, count);
                
            }
            
            if (amount > 0) {
                stats.addCountryTransactionAmountSuccess(country, amount);
                
            }
        }
		
        
        //country counts
        
        
        
        
        //country count ends
        /**
        // Set up data for the bar charts
        DateMidnight dateMidnightStart = DateMidnight.now().minus(Hours.hours(24 * (TransactionBarDay.DAY_COUNT - 1)));
        DateMidnight dateMidnightEnd = dateMidnightStart.plus(Hours.hours(24));
        int numDays = 0;
        do {
            for (Country country : countryList) {
               
                count = countUtils.getTransactionAmount(account, country,acceptedTransactionStatus,
                        new Date(dateMidnightStart.getMillis()), new Date(dateMidnightEnd.getMillis()));

                if (count > 0) {
                    stats.addCountryTransactionAmountDay(new SimpleDateFormat("MMM d").format(new Date(dateMidnightStart.getMillis())),
                            country, count);
                }
            }

            dateMidnightStart = dateMidnightStart.plus(Hours.hours(24));
            dateMidnightEnd = dateMidnightEnd.plus(Hours.hours(24));
            numDays++;
        } while (numDays < TransactionBarDay.DAY_COUNT);
        **/
        for (Country country : countryList) {
            
            count = countUtils.getTransactionAmounts(account, country,acceptedTransactionStatus);

            if (count > 0) {
                stats.addCountryTransactionAmountDays(country, count);
            }
        }

        return stats;
		
	}
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
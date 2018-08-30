package com.impalapay.airtel.accountmgmt.admin;

import com.impalapay.airtel.accountmgmt.session.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.impalapay.airtel.accountmgmt.admin.persistence.util.CountUtils;





import com.impalapay.airtel.persistence.geolocation.CountryDAO;

import org.joda.time.DateMidnight;
import org.joda.time.Hours;

/**
 * Factory class to create transaction statistics from accounts. These will be used 
 * during the sessions while a user is logged in.
 * <p>
 * Copyright (c) ImpalaPay Ltd., Dec 20, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class SessionStatisticsFactory {

    private static CountUtils countUtils;
    private static CountryDAO countryDAO;

    static {
        countUtils = CountUtils.getInstance();


       countryDAO = CountryDAO.getInstance();
    }
    

    /**
     * Gets statistics relating to {@link ke.co.shujaa.airtimegw.server.beans.topup.Topup} activity.
     * from all accounts
     *
     * @return SessionStatistics
    */
    public static SessionStatistics getSessionStatistics() {
        SessionStatistics stats = new SessionStatistics();
        
        int count;
        
       // stats.setAllTransactionCount(countUtils.getAllTransactionCount());
        /**
        TopupStatus acceptedTopupStatus,successTopupStatus;
        List<Network> networkList;
        int count,amount;


        //set total number of top up attempts or airtime requests 
        acceptedTopupStatus = topupStatusDAO.getTopupStatus(TopupStatus.ACCEPTED_FOR_DELIVERY);
        stats.setTopupCountTotal(countUtils.getAllTopupCount(acceptedTopupStatus));
       

        networkList = networkDAO.getAllNetworks();

        //set total number of top up attempts or airtime requests per network operator
        for (Network network : networkList) {
            count = countUtils.getAllTopupCount(network,acceptedTopupStatus);
            if (count > 0) {
                stats.addNetworkTopupCountTotal(network, count);
            }
            
        }
        
        //Set total value of top up attempts or airtime requests per network operator
        //that have been successful
        successTopupStatus = topupStatusDAO.getTopupStatus(TopupStatus.TOPUP_SUCCESS);
        //stats.setTopupCountTotal(countUtils.getAllTopupCount(successTopupStatus));
        
        for (Network network : networkList) {
            count = countUtils.getAllTopupCount(network,successTopupStatus);
            amount=countUtils.getAllTopupAmount( network, successTopupStatus);
            if (count > 0) {
                stats.addNetworkTopupCountSuccess(network, count);
                
            }
            
            if (amount > 0) {
                stats.addNetworkTopupAmountSuccess(network, amount);
                
            }
        }
        // Set up data for the bar charts
        DateMidnight dateMidnightStart = DateMidnight.now().minus(Hours.hours(24 * (TopupBarDay.DAY_COUNT - 1)));
        DateMidnight dateMidnightEnd = dateMidnightStart.plus(Hours.hours(24));
        int numDays = 0;
        do {
            for (Network network : networkList) {
               
                count = countUtils.getAllTopupAmount(network, acceptedTopupStatus,
                        new Date(dateMidnightStart.getMillis()), new Date(dateMidnightEnd.getMillis()));
               
                if (count > 0) {
                    stats.addNetworkTopupAmountDay(new SimpleDateFormat("MMM d").format(new Date(dateMidnightStart.getMillis())),
                            network, count);
                }
            }

            dateMidnightStart = dateMidnightStart.plus(Hours.hours(24));
            dateMidnightEnd = dateMidnightEnd.plus(Hours.hours(24));
            numDays++;
        } while (numDays < TopupBarDay.DAY_COUNT);
**/

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
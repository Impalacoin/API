package com.impalapay.airtel.scheduledjobs.sessionid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

//import static org.quartz.JobBuilder.*;
//import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * Description of class.
 * <p>
 * Copyright (c) ImpalaPay LTD., Sep 24, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */

public class SessionMgmtLauncher extends Thread {
	
	private Logger logger;

	/**
	 * 
	 */
	public SessionMgmtLauncher() {
		logger = Logger.getLogger(this.getClass());
	}

	
	/**
	 * 
	 */
	@Override
	public void run() {
		try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


         // define the job and tie it to our HelloJob class
            JobDetail job = newJob(ExpireSessionJob.class)
                .withIdentity("myJob", "group1")
                .build();

            // Trigger the job to run now, and then every 5 seconds
            Trigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever())
                .build();
            
            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            
            // and start it off
            scheduler.start();
            
            //Thread.sleep(30000);
            //scheduler.shutdown();

        } catch (SchedulerException se) {
            //se.printStackTrace();
            logger.error(ExceptionUtils.getStackTrace(se));
           
        }
	}

}

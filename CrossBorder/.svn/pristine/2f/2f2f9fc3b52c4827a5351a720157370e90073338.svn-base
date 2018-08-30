package com.impalapay.airtel.tests.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobScheduler {

	public static void main(String[] args) {

		try {

			// specify the job' s details..
			// (Created TestJob class is linked with a JobDetail object)
			JobDetail job = JobBuilder.newJob(TestJob.class)
					.withIdentity("testJob").build();

			// specify the running period of the job
			// A new trigger is created as below. Trigger Class specifies
			// running period of the job which will be scheduled.
			// There are two kind of Quartz Triggers as :
			// Trigger : specifies start time, end time, running period of the
			// job.
			// CronTrigger : specifies start time, end time, running period of
			// the job according to Unix cron expression.
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withSchedule(
							SimpleScheduleBuilder.simpleSchedule()
									.withIntervalInSeconds(30).repeatForever())
					.build();
			/**
			 * // CronTrigger the job to run on the every 20 seconds CronTrigger
			 * cronTrigger = TriggerBuilder.newTrigger()
			 * .withIdentity("crontrigger","crontriggergroup1")
			 * .withSchedule(CronScheduleBuilder.cronSchedule("10 * * * * ?"))
			 * .build();
			 */
             //A new SchedulerFactory is created and a Scheduler object is gotten from SchedulerFactory Class.
			// schedule the job
			SchedulerFactory schFactory = new StdSchedulerFactory();
			Scheduler sch = schFactory.getScheduler();
			
			//Scheduler Object is started.
			sch.start();
			//TestJob is scheduled:
			sch.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}

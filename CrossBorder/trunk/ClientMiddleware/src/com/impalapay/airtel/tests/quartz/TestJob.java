package com.impalapay.airtel.tests.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

   //private Logger log = Logger.getLogger(TestJob.class);

   public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {
      System.out.println("TestJob run successfully...");
   }

}
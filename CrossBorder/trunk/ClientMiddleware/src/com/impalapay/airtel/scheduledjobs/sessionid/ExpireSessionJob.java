package com.impalapay.airtel.scheduledjobs.sessionid;

import com.impalapay.airtel.persistence.sessionlog.SessionLogDAO;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Description of class.
 * <p>
 * Copyright (c) ImpalaPay LTD., Sep 24, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * 
 */

public class ExpireSessionJob implements Job {

	/**
     * Empty constructor for job initilization
     * <p>
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
	// The number of minutes a Session Id is allowed to be active
	final int SESSIONID_MINUTES_ALIVE = 30;
		
	private SessionLogDAO sessionLogDAO;
		
	public ExpireSessionJob() {
		// TODO Auto-generated constructor stub
	}

	
	/**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     * 
     * @throws JobExecutionException
     *             if there is an exception while executing the job.
     */
	@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

		//DateTime dateTime = new DateTime().minusMinutes(SESSIONID_MINUTES_ALIVE);
		
		//org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd hh:mm:ss");
		///String str = fmt.print(dateTime);
		
    	//sessionLogDAO.expireSessionLogs(dateTime.toGregorianCalendar().getTime());
		//System.out.println("expire session");
    }
}

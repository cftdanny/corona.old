/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.corona.context.ContextManagerFactory;
import com.corona.context.annotation.Inject;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class QuartzScheduler implements Scheduler {

	/**
	 * the current context manager factory
	 */
	@Inject private ContextManagerFactory contextManagerFactory;
	
	/**
	 * the schedule name
	 */
	private String name;
	
	/**
	 * the Quartz scheduler
	 */
	private org.quartz.Scheduler scheduler;
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.async.Scheduler#schedule(com.corona.async.JobDescriptor)
	 */
	@Override
	public void schedule(final JobDescriptor descriptor) throws AsyncException {
		
		JobDetail detail = JobBuilder.newJob(Dispatcher.class).build();
		Trigger trigger = TriggerBuilder.newTrigger().forJob(detail).build();
		
		try {
			this.scheduler.scheduleJob(detail, trigger);
		} catch (SchedulerException e) {
			throw new AsyncException("");
		}
	}
}

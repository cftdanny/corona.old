/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.corona.context.ContextManager;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This dispatcher class is used to execute asynchronous job method in component </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Dispatcher implements Job {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(Dispatcher.class);
	
	/**
	 * @param context the job execution context from Quartz
	 * @return the job descriptor
	 */
	private JobDescriptor getJobDescriptor(final JobExecutionContext context) {
		return (JobDescriptor) context.getJobDetail().getJobDataMap().get(JobDescriptor.class.getName());
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {

		// find job descriptor to build job execution environment
		JobDescriptor descriptor = this.getJobDescriptor(context);
		
		// create context manager factory in order to execute job in asynchronous method
		ContextManager contextManager = descriptor.getContextManagerFactory().create();
		
		// create component with asynchronous job method
		Object component = contextManager.get(descriptor.getType(), descriptor.getName());
		
		// execute asynchronous job method with data store in Quartz job context
		try {
			descriptor.getMethod().invoke(component, descriptor.getArguments());
		} catch (Exception e) {
			
			this.logger.error("Fail to execute method [{0}] with Quartz job", e, descriptor.getMethod().getName());
			throw new JobExecutionException(
					"Fail to execute method " + descriptor.getMethod().getName() + " with Quartz job", e
			);
		}
	}
}

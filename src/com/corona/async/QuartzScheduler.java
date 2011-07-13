/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.util.Properties;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.corona.context.Closeable;
import com.corona.context.ContextManagerFactory;
import com.corona.context.annotation.Create;
import com.corona.context.annotation.Inject;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.StringUtil;

/**
 * <p>This scheduler that is implemented by Quartz </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class QuartzScheduler implements Scheduler, Closeable {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(QuartzScheduler.class);
	
	/**
	 * the current context manager factory
	 */
	@Inject private ContextManagerFactory contextManagerFactory;
	
	/**
	 * the properties to initial Quartz scheduler
	 */
	private Properties properties = new Properties();
	
	/**
	 * the Quartz scheduler
	 */
	private org.quartz.Scheduler scheduler;
	
	/**
	 * how many seconds will delay after start
	 */
	private int startDelayedSecond = 0;
	
	/**
	 * set default properties for Quartz scheduler configuration
	 */
	public QuartzScheduler() {
		
		this.properties.setProperty("org.quartz.scheduler.rmi.export", "false");
		this.properties.setProperty("org.quartz.scheduler.rmi.proxy", "false");
		this.properties.setProperty("org.quartz.scheduler.wrapJobExecutionInUserTransaction", "false");
		
		this.properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		this.properties.setProperty("org.quartz.threadPool.threadCount", "3");
		this.properties.setProperty("org.quartz.threadPool.threadPriority", "5");
		this.properties.setProperty(
				"org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true"
		);

		this.properties.setProperty("org.quartz.jobStore.misfireThreshold", "60000");
		this.properties.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
		
		this.properties.setProperty("org.quartz.scheduler.skipUpdateCheck", "false");
	}
	
	/**
	 * @return the Quartz scheduler name
	 */
	public String getName() {
		return this.properties.getProperty("org.quartz.scheduler.instanceName");
	}
	
	/**
	 * @param name the Quartz scheduler name
	 */
	public void setName(final String name) {
		this.properties.setProperty("org.quartz.scheduler.instanceName", name);
	}
	
	/**
	 * @return the thread count for Quartz jobs
	 */
	public int getThreadCount() {
		return Integer.parseInt(this.properties.getProperty("org.quartz.threadPool.threadCount"));
	}

	/**
	 * @param threadCount the thread count for Quartz jobs
	 */
	public void setThreadCount(final int threadCount) {
		this.properties.setProperty("org.quartz.threadPool.threadCount", Integer.toString(threadCount));
	}
	
	/**
	 * @return the thread priority for Quartz jobs
	 */
	public int getThreadPriority() {
		return Integer.parseInt(this.properties.getProperty("org.quartz.threadPool.threadPriority"));
	}
	
	/**
	 * @param threadPriority the thread priority for Quartz jobs
	 */
	public void setThreadPriority(final int threadPriority) {
		this.properties.setProperty("org.quartz.threadPool.threadPriority", Integer.toString(threadPriority));
	}

	/**
	 * @return the properties to initial Quartz scheduler
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to initial Quartz scheduler to set
	 */
	public void setProperties(final Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * @return how many seconds will delay after start
	 */
	public int getStartDelayedSecond() {
		return startDelayedSecond;
	}
	
	/**
	 * @param startDelayedSecond how many seconds will delay after start to set
	 */
	public void setStartDelayedSecond(final int startDelayedSecond) {
		this.startDelayedSecond = startDelayedSecond;
	}

	/**
	 * initial this scheduler
	 */
	@Create public void init() {
		
		// create scheduler according to Quartz scheduler configuration properties
		try {
			this.scheduler = new StdSchedulerFactory(this.properties).getScheduler();
		} catch (SchedulerException e) {
			this.logger.error("Fail to initial Quartz scheduler [{0}], just skip to create it", e, this.getName());
		}
		
		// start scheduler in order to run jobs
		try {
			if (this.scheduler != null) {
				this.scheduler.startDelayed(this.startDelayedSecond);
			}
		} catch (SchedulerException e) {
			this.logger.error("Fail to start Quartz scheduler [{0}], just skip this error", e, this.getName());
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Closeable#close()
	 */
	@Override
	public void close() {
		
		// shutdown Quartz scheduler
		try {
			if (this.scheduler != null) {
				this.scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			this.logger.error("Fail to shutdown Quartz scheduler [{0}], just skip this error", e, this.getName());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.async.Scheduler#schedule(com.corona.async.JobDescriptor)
	 */
	@Override
	public void schedule(final JobDescriptor descriptor) throws AsyncException {
		
		// create job in order to schedule job for Quartz scheduler
		JobBuilder jobBuilder = JobBuilder.newJob(Dispatcher.class);

		// set job group, name and description information
		Job jobRemark = descriptor.getMethod().getAnnotation(Job.class);
		if (!StringUtil.isBlank(jobRemark.value())) {
			jobBuilder.withIdentity(jobRemark.value(), jobRemark.group());
		}
		if (!StringUtil.isBlank(jobRemark.description())) {
			jobBuilder.withDescription(jobRemark.description());
		}
		
		// prepare job data in order to execute job later
		JobDataMap jobData = new JobDataMap();
		
		descriptor.setContextManagerFactory(this.contextManagerFactory);
		jobData.put(JobDescriptor.class.getName(), descriptor);

		// create job detail
		JobDetail detail = jobBuilder.usingJobData(jobData).build();
		
		
		
		// create trigger in order to trigger job
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).forJob(detail).startNow().build();
		
		// schedule job by detail and trigger
		try {
			this.scheduler.scheduleJob(detail, trigger);
		} catch (SchedulerException e) {
			
			this.logger.error("Fail to schedule job that is defined by method [{0}]", e, descriptor.getMethod());
			throw new AsyncException(
					"Fail to schedule job that is defined by method [{0}]", e, descriptor.getMethod()
			);
		}
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
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
	 * @param method the method that is defined as asynchronous method
	 * @param type the annotation type to check
	 * @return the parameter index of parameter that is annotated with argument type
	 */
	private int getParameterIndex(final Method method, final Class<? extends Annotation> type) {
		
		for (int i = 0, count = method.getParameterTypes().length; i < count; i++) {
			
			Annotation[] annotations = method.getParameterAnnotations()[i];
			if ((annotations != null) && (annotations.length > 0)) {
				for (Annotation annotation : annotations) {
					if (annotation.annotationType().equals(type)) {
						return i;
					}
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * @param method the method that is defined as asynchronous method
	 * @param arguments the arguments of method
	 * @return the Quartz schedule builder
	 * @exception Exception if fail to create schedule builder
	 */
	@SuppressWarnings("rawtypes")
	private ScheduleBuilder getScheduleBuilder(final Method method, final Object[] arguments) throws Exception {
		
		int index = this.getParameterIndex(method, Interval.class);
		if (index != -1) {
			long interval = Long.parseLong(arguments[index].toString());
			return SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(interval).repeatForever();
		}
		
		index = this.getParameterIndex(method, Cron.class);
		if (index != -1) {
			String cron = arguments[index].toString();
			return CronScheduleBuilder.cronSchedule(cron);
		}		
		
		return SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever();
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.async.Scheduler#schedule(com.corona.async.JobDescriptor)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		
		// create trigger build and schedule builder in order to trigger job
		TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().startNow();
		try {
			ScheduleBuilder scheduleBuilder = this.getScheduleBuilder(
					descriptor.getMethod(), descriptor.getArguments()
			);
			triggerBuilder.withSchedule(scheduleBuilder);
		} catch (Exception e) {
			this.logger.error("Fail to create schedule for trigger by method [{0}]", e, descriptor.getMethod());
			throw new AsyncException(
					"Fail to create schedule for trigger by method [{0}]", e, descriptor.getMethod()
			);
		}
		
		// set trigger priority if set
		if (jobRemark.priority() != -1) {
			triggerBuilder.withPriority(jobRemark.priority());
		}
		
		// find duration from job method
		int index = this.getParameterIndex(descriptor.getMethod(), Duration.class);
		if (index != -1) {
			int interval = Integer.parseInt(descriptor.getArguments()[index].toString());
			triggerBuilder.startAt(DateBuilder.futureDate(interval, DateBuilder.IntervalUnit.MILLISECOND));
		}
		
		// find expiration from job method
		index = this.getParameterIndex(descriptor.getMethod(), Expiration.class);
		if (index != -1) {
			Date expiration = (Date) descriptor.getArguments()[index];
			triggerBuilder.startAt(expiration);
		}
		
		// find termination from job method
		index = this.getParameterIndex(descriptor.getMethod(), Finish.class);
		if (index != -1) {
			Date stop = (Date) descriptor.getArguments()[index];
			triggerBuilder.endAt(stop);
		}
		
		// create trigger
		Trigger trigger = triggerBuilder.forJob(detail).build();
		
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

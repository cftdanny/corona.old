/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import com.corona.context.AbstractModule;
import com.corona.context.InjectFieldFactory;
import com.corona.context.InjectPropertyFactory;
import com.corona.context.annotation.Application;

/**
 * <p>This module is used to register asynchronous job supporting components and configuration. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AsyncSupportModule extends AbstractModule {

	/**
	 * whether create default scheduler
	 */
	private boolean createDefaultScheduler;
	
	/**
	 * construct module and create default scheduler
	 */
	public AsyncSupportModule() {
		this(true);
	}

	/**
	 * @param createDefaultScheduler whether create default scheduler
	 */
	public AsyncSupportModule(final boolean createDefaultScheduler) {
		this.createDefaultScheduler = createDefaultScheduler;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		// configure @Param injection for field and property 
		this.bindExtension(InjectFieldFactory.class).as(Async.class).to(
				new AsyncInjectFieldFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(Async.class).to(
				new AsyncInjectPropertyFactory()
		);
		
		// if create default scheduler, will create it by Quartz scheduler and bind to application scope
		if (this.createDefaultScheduler) {
			this.bind(Scheduler.class).to(QuartzScheduler.class).in(Application.class);
		}
	}
}

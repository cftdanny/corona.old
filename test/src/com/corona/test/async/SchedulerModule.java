/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.async;

import com.corona.context.AbstractModule;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SchedulerModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		this.bind(SimpleJob.class).to(SimpleJobImpl.class);
		this.bind(SimpleJobRunner.class).to(SimpleJobRunner.class);
	}
}

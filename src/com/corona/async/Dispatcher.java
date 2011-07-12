/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Dispatcher implements Job {

	/**
	 * {@inheritDoc}
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
	}
}

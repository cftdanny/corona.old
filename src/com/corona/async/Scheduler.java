/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

/**
 * <p>The scheduler that is schedule a job to be in background </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Scheduler {

	/**
	 * @param descriptor the job descriptor
	 * @throws AsyncException if fail to schedule job to run asynchronous 
	 */
	void schedule(JobDescriptor descriptor) throws AsyncException;
}

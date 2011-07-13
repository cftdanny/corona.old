/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.async;

import com.corona.async.Async;
import com.corona.async.Job;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SimpleJobRunner {

	/**
	 * the simple job
	 */
	@Async private SimpleJob simpleJob;
	
	/**
	 * run the job
	 * @exception Exception if fail
	 */
	@Job void run() throws Exception {
		this.simpleJob.execute(1, 1000);
	}
}

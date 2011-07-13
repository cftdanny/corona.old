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
	 * the simple job 1
	 */
	@Async private SimpleJob simpleJob1;
	
	/**
	 * the simple job 2
	 */
	private SimpleJob simpleJob2;
	
	/**
	 * @param simpleJob2 the simpleJob2 to set
	 */
	@Async public void setSimpleJob2(final SimpleJob simpleJob2) {
		this.simpleJob2 = simpleJob2;
	}

	/**
	 * run the job
	 * @exception Exception if fail
	 */
	@Job void run() throws Exception {
		this.simpleJob1.execute(1, 1000);
		this.simpleJob2.execute("* * * * * ?");
	}
}

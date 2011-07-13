/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.async;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface SimpleJob {

	/**
	 * execute job
	 * @param interval the interval
	 * @param duration the duration
	 * @exception Exception if fail
	 */
	void execute(long interval, long duration) throws Exception;
}

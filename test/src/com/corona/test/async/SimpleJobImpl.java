/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.async;

import java.util.Date;

import com.corona.async.Duration;
import com.corona.async.Interval;
import com.corona.async.Job;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SimpleJobImpl implements SimpleJob {

	/**
	 * the count
	 */
	private static int count = 0;
	
	/**
	 * @return the count
	 */
	public static int getCount() {
		return count;
	}
	
	/**
	 * @param count the count to set
	 */
	public static void setCount(final int count) {
		SimpleJobImpl.count = count;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.async.SimpleJob#execute(long, long)
	 */
	@Override
	@Job public void execute(@Interval final long interval, @Duration final long duration) throws Exception {
		
		count = count + 1;
		
		System.out.print("COUNT: ");
		System.out.print(count);
		System.out.print(" - ");
		System.out.println(new Date());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.async.SimpleJob#execute(java.lang.String)
	 */
	@Override
	public void execute(final String cron) throws Exception {
		System.out.println("CRON");
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.async;

import org.testng.annotations.Test;

import com.corona.async.AsyncSupportModule;
import com.corona.context.Module;
import com.corona.mock.AbstractComponentTest;

/**
 * <p>Test job and scheduler </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TestScheduler extends AbstractComponentTest {

	/**
	 * {@inheritDoc}
	 * @see com.corona.mock.AbstractComponentTest#getModules()
	 */
	@Override
	protected Module[] getModules() {
		return new Module[] {new AsyncSupportModule(), new SchedulerModule()};
	}

	/**
	 * @throws Exception if fail
	 */
	@Test public void testSimpleJob() throws Exception {
		
		SimpleJobImpl.setCount(0);
		
		SimpleJobRunner runner = this.get(SimpleJobRunner.class);
		runner.run();
		
		while (SimpleJobImpl.getCount() < 6) {
			Thread.sleep(10);
		}
	}
}

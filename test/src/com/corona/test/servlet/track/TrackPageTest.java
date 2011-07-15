/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.track;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.mock.AbstractWebsiteTest;
import com.corona.servlet.tracking.TrackManager;

/**
 * <p>TEST PAGE: /index.pdf </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TrackPageTest extends AbstractWebsiteTest {

	/**
	 * @return how many tracked count
	 */
	private int getTrackedCount() {
		return ((DemoTrackManager) this.getContextManager().get(TrackManager.class)).getCount();
	}
	
	/**
	 * @throws Exception if throws exception
	 */
	@Test public void testTrack() throws Exception {
		
		WebDriver driver = this.getWebDriver("/track");
		
		Assert.assertTrue(driver.getPageSource().startsWith("[track]"));
		Assert.assertEquals(this.getTrackedCount(), 1);
		
		for (int i = 0; i < 9; i++) {
			driver = this.getWebDriver("/track");
		}
		
		Assert.assertEquals(this.getTrackedCount(), 0);
	}
}

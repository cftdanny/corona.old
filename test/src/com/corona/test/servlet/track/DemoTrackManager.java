/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.track;

import java.util.List;

import com.corona.servlet.tracking.AbstractTrackManager;
import com.corona.servlet.tracking.Finger;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DemoTrackManager extends AbstractTrackManager {

	/**
	 * count
	 */
	private int count = 0;
	
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.tracking.AbstractTrackManager#track(com.corona.servlet.tracking.Finger)
	 */
	@Override
	public synchronized void track(final Finger finger) {
		
		this.count = this.count + 1;
		super.track(finger);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.tracking.AbstractTrackManager#save(java.util.List)
	 */
	@Override
	protected void save(final List<Finger> fingerHistory) {
		this.count = 0;
	}
}

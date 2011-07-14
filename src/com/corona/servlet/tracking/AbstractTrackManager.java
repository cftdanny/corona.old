/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.tracking;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The helper class of track manager </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractTrackManager implements TrackManager {

	/**
	 * the max NO of tracked information
	 */
	private int maxIdledFingers = 10;
	
	/**
	 * all tracked requests
	 */
	private List<Finger> fingers = new ArrayList<Finger>();

	/**
	 * @return the max NO of tracks
	 */
	public int getMaxIdledFingers() {
		return maxIdledFingers;
	}
	
	/**
	 * @param maxIdledFingers the max NO of tracks to set
	 */
	public void setMaxIdledFingers(final int maxIdledFingers) {
		this.maxIdledFingers = maxIdledFingers;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.tracking.TrackManager#track(com.corona.servlet.tracking.Finger)
	 */
	@Override
	public synchronized void track(final Finger finger) {
		
		this.fingers.add(finger);
		if (this.fingers.size() >= this.maxIdledFingers) {
			
			this.save(this.fingers);
			this.fingers = new ArrayList<Finger>();
		}
	}

	/**
	 * @param fingerHistory the tracked information to be saved
	 */
	protected abstract void save(final List<Finger> fingerHistory);
}

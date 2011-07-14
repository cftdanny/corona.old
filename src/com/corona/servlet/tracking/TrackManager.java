/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.tracking;

/**
 * <p>The track manager is used to store tracks, for example, into database </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface TrackManager {

	/**
	 * @param finger the tracked request information
	 */
	void track(Finger finger);
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.tracking;

import java.util.List;

/**
 * <p>The track manager is used to store tracks, for example, into database </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface TrackManager {

	/**
	 * @param tracks the tracks to be saved
	 */
	void save(List<Track> tracks);
}

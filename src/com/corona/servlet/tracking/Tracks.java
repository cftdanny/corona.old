/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.tracking;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This collection is used to keep tracks and return all tracks if it is over max NO of tracks </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Tracks {

	/**
	 * the max NO of tracks
	 */
	private int maxTracks = 10;
	
	/**
	 * all tracked requests
	 */
	private List<Track> tracks = new ArrayList<Track>();

	/**
	 * @return the max NO of tracks
	 */
	public synchronized int getMaxTracks() {
		return maxTracks;
	}
	
	/**
	 * @param maxTracks the max NO of tracks to set
	 */
	public synchronized void setMaxTracks(final int maxTracks) {
		this.maxTracks = maxTracks;
	}

	/**
	 * @param track the track to be added
	 */
	public synchronized void add(final Track track) {
		this.tracks.add(track);
	}
	
	/**
	 * @return if deposits tracks is larger than max track, return it
	 */
	public synchronized List<Track> getTracks() {
		
		List<Track> result = null;
		if (this.tracks.size() >= this.maxTracks) {
			result = this.tracks;
			this.tracks = new ArrayList<Track>();
		}
		return result;
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.tracking;

import java.util.Date;

/**
 * <p>This class is used to track request information </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Track {

	/**
	 * the request path
	 */
	private String path;
	
	/**
	 * when user access this path
	 */
	private Date time;
	
	/**
	 * who access this path
	 */
	private String username;
	
	/**
	 * the error if fail to process this request
	 */
	private Throwable error;
	
	/**
	 * @return the request path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param path the request path to set
	 */
	public void setPath(final String path) {
		this.path = path;
	}
	
	/**
	 * @return when user request this path
	 */
	public Date getTime() {
		return time;
	}
	
	/**
	 * @param time when user request this path
	 */
	public void setTime(final Date time) {
		this.time = time;
	}
	
	/**
	 * @return the user name who request this path
	 */
	public String getUsername() {
		return username;
	}

	
	/**
	 * @param username the user name who request this path 
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * @return the error
	 */
	public Throwable getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(final Throwable error) {
		this.error = error;
	}
}

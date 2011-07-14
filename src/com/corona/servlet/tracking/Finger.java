/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.tracking;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class is used to track request information </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Finger {

	/**
	 * the track code
	 */
	private String code;
	
	/**
	 * the request path
	 */
	private String path;
	
	/**
	 * when user access this path
	 */
	private Date before = new Date();
	
	/**
	 * whether request is finished
	 */
	private Date after;
	
	/**
	 * who access this path
	 */
	private String username;
	
	/**
	 * the error if fail to process this request
	 */
	private Throwable error;
	
	/**
	 * the tracked request parameters
	 */
	private Map<String, String> parameters = new HashMap<String, String>();
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

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
	public Date getBefore() {
		return before;
	}
	
	/**
	 * @param before when user begin to request this path
	 */
	public void setBefore(final Date before) {
		this.before = before;
	}
	
	/**
	 * @return the after
	 */
	public Date getAfter() {
		return after;
	}
	
	/**
	 * @param after the after to set
	 */
	public void setAfter(final Date after) {
		this.after = after;
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

	/**
	 * @return the tracked request parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}
}

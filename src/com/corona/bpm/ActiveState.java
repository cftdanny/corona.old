/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ActiveState {

	/**
	 * the process is active, can signal to next
	 */
	public static final int ACTIVE = 1;
	
	/**
	 * waiting child process to finish
	 */
	public static final int WAITING = 2;
	
	/**
	 * the process has been finished
	 */
	public static final int FINISHED = 3;
	
	/**
	 * the process has been cancelled
	 */
	public static final int CANCELLED = 4;

	/**
	 * the state code
	 */
	private int code = ACTIVE;

	/**
	 * the current activity
	 */
	private Activity activity;
	
	/**
	 * @return the state code
	 */
	public int getCode() {
		return this.code;
	}
	
	/**
	 * @param code the state code
	 */
	public void setCode(final int code) {
		this.code = code;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return this.activity.getName();
	}
	
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return this.activity.getRemark();
	}
	
	/**
	 * @param activity the current activity
	 */
	public void setActivity(final Activity activity) {
		this.activity = activity;
	}
}

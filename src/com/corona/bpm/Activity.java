/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm;

import java.util.Map;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Activity {

	/**
	 * @return the name
	 */
	String getName();
	
	/**
	 * @return the remark
	 */
	String getRemark();
	
	/**
	 * @param activities all activities
	 * @exception ProcessException if fail to find child activity
	 */
	void setChildren(Map<String, Activity> activities) throws ProcessException;
	
	/**
	 * @param activity the parent activity
	 */
	void addParent(Activity activity);
	
	/**
	 * @param context the context
	 * @throws ProcessException if fail to enter activity
	 */
	void enter(Context context) throws ProcessException;
	
	/**
	 * @param context the context
	 * @throws ProcessException if fail to exit activity
	 */
	void exit(Context context) throws ProcessException;
}

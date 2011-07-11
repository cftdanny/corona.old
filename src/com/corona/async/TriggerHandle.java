/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface TriggerHandle {

	String getName();
	
	void cancel();
	
	void resume();
	
	void pause();
}

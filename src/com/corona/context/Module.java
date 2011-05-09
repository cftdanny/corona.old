/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>The <b>Module</b> is used to configure context manager factory runtime environment. An application
 * can own more than one modules to configure context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Module {

	/**
	 * <p>This method is used to configure component elements for an application. All components in 
	 * a function can be grouped to a module. </p>
	 * 
	 * @param binder the binder
	 */
	void configure(Binder binder);
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>This interface is used to set configuration value to component just after it is created </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Configuration {

	/**
	 * @return the property name of component to configure
	 */
	String getPropertyName();
	
	/**
	 * @param contextManager the current context manager
	 * @return the value to configure component property
	 */
	Object getValue(ContextManager contextManager);
}

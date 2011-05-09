/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>This inspector is used to inspect all component configuration in context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Visitor {

	/**
	 * <p>Inspect all component configurations that are loaded in context manager factory by key and
	 * descriptor pair.
	 * </p>
	 * 
	 * @param key the component key
	 * @param descriptor the component descriptor
	 */
	void visit(Key<?> key, Descriptor<?> descriptor);
}

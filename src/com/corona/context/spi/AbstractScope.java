/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.ContextManager;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.context.Scope;

/**
 * <p>A helper abstract class of Scope that is used to get context manager factory, find component descriptor 
 * by component key, etc. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractScope implements Scope {

	/**
	 * <p>Return the implementation instance of context manager factory by current context manager. </p>
	 *  
	 * @param contextManager the context manager
	 * @return the implementation of context manager factory
	 */
	private ContextManagerFactoryImpl getContextManagerFactory(final ContextManager contextManager) {
		return (ContextManagerFactoryImpl) contextManager.getContextManagerFactory();
	}
	
	/**
	 * <p>Find component descriptor by its key from current context manager. If does not exists, 
	 * return <code>null</code>.
	 * </p>
	 * 
	 * @param <T> the injection type
	 * @param contextManager the context manager
	 * @param key the component key
	 * @return the component descriptor or <code>null</code> if does not exists
	 */
	protected <T> Descriptor<T> getDescriptor(final ContextManager contextManager, final Key<T> key) {
		return this.getContextManagerFactory(contextManager).getDescriptors().get(key);
	}
}

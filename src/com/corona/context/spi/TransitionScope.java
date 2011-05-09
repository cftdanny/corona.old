/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>A scope that creates component every time when it tries to get component from its scope. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TransitionScope extends AbstractScope {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(TransitionScope.class);
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Scope#get(com.corona.context.ContextManager, com.corona.context.Key)
	 */
	@Override
	public <T> T get(final ContextManager contextManager, final Key<T> key) {
		
		Descriptor<T> descriptor = this.getDescriptor(contextManager, key);
		if (descriptor == null) {
			this.logger.error("Component with key [{0}] is not registered", key.toString());
			throw new ConfigurationException("Component with key [{0}] is not registered", key.toString());
		}
		return descriptor.getValue(contextManager);
	}
}

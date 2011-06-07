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
 * <p>Context scope is used to create or resolve component that is defined as context scope. If component 
 * is defined as context scope, only same context manager will share single instance when it is
 * resolved from scope container. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ContextScope extends AbstractScope {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ContextScope.class);
	
	/**
	 * @param contextManager the context manager
	 * @return the implementation of context manager
	 */
	private ContextManagerImpl getContextManager(final ContextManager contextManager) {
		return (ContextManagerImpl) contextManager;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Scope#get(com.corona.context.ContextManager, com.corona.context.Key)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(final ContextManager contextManager, final Key<T> key) {
		
		this.logger.info("Try to resolve component from cached context repository with key [{0}]", key);
		T component = (T) this.getContextManager(contextManager).getComponents().get(key);
		if (component != null) {
			return component;
		}
		
		this.logger.debug("Component with key [{0}] is not cached in context repository, will create it", key);
		Descriptor<T> descriptor = this.getDescriptor(contextManager, key);
		if (descriptor == null) {
			this.logger.error("Component with key [{0}] is not registered, can not create this component", key);
			throw new ConfigurationException(
					"Component with key [{0}] is not registered, can not create this component", key
			);
		}

		this.logger.debug("Try to create component with key [{0}], descriptor [{1}]", key, descriptor);
		component = descriptor.getValue(contextManager);
		this.getContextManager(contextManager).getComponents().put(key, component);
		this.logger.info("Component with key [{0}] has been create and cached to context repository", key);
		
		return component;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Scope#close(java.lang.Object)
	 */
	@Override
	public void close(final Object context) {
		// do nothing
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.util.HashMap;
import java.util.Map;

import com.corona.context.Closeable;
import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>Application scope is used to create or resolve component that is defined as application scope. If 
 * component is defined as application scope, all context manager will share single instance when it is
 * resolved from scope container. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ApplicationScope extends AbstractScope {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ApplicationScope.class);
	
	/**
	 * the component repository (key and instance) for application scope
	 */
	private Map<Key<?>, Object> components = new HashMap<Key<?>, Object>();
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Scope#get(com.corona.context.ContextManager, com.corona.context.Key)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(final ContextManager contextManager, final Key<T> key) {
		
		this.logger.info("Try to resolve component from cached application repository with key [{0}]", key);
		T component = (T) this.components.get(key);
		if (component != null) {
			return component;
		} 

		this.logger.debug("Component with key [{0}] is not cached in application repository, will create it", key);
		Descriptor<T> descriptor = this.getDescriptor(contextManager, key);
		if (descriptor == null) {
			this.logger.error("Component with key [{0}] is not registered, can not create this component", key);
			throw new ConfigurationException(
					"Component with key [{0}] is not registered, can not create this component", key
			);
		}
		
		this.logger.debug("Try to create component with key [{0}], descriptor [{1}]", key, descriptor);
		component = descriptor.getValue(contextManager);
		this.components.put(key, component);
		this.logger.info("Component with key [{0}] has been create and cached to application repository", key);
		
		return component;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Scope#close(java.lang.Object)
	 */
	@Override
	public void close(final Object context) {
		
		// close all components belongs to this context if it implements Closeable interface
		for (Map.Entry<Key<?>, Object> item : this.components.entrySet()) {
			
			if (item.getValue() instanceof Closeable) {
				try {
					((Closeable) item.getValue()).close();
				} catch (Exception e) {
					this.logger.error("Fail to close component [{0}, {1}]", item.getKey(), item.getValue());
				}
			}
		}
		
		// set local variable to null to release resources
		this.components = null;
	}
}

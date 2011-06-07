/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.Configuration;
import com.corona.context.ContextManager;

/**
 * <p>This configuration will link property of component to another component </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LinkedConfiguration implements Configuration {

	/**
	 * the property name to be configured in component
	 */
	private String property;
	
	/**
	 * to be injected component type
	 */
	private Class<?> type;
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * @param property the property name to be configured in component
	 * @param protocolType component type to be injected
	 * @param componentName the component name
	 */
	LinkedConfiguration(final String property, final Class<?> protocolType, final String componentName) {
		this.property = property;
		this.type = protocolType;
		this.name = componentName;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Configuration#getPropertyName()
	 */
	@Override
	public String getPropertyName() {
		return this.property;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Configuration#getValue(com.corona.context.ContextManager)
	 */
	@Override
	public Object getValue(final ContextManager contextManager) {
		return contextManager.get(this.type, this.name);
	}
}

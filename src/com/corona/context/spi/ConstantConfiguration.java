/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.Configuration;
import com.corona.context.ContextManager;

/**
 * <p>This configuration is used to set constant value to property of component </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ConstantConfiguration implements Configuration {

	/**
	 * the property name
	 */
	private String property;
	
	/**
	 * the value that will set to component property
	 */
	private Object value;
	
	/**
	 * @param property the property name of component
	 * @param value the value that will set to component property
	 */
	ConstantConfiguration(final String property, final Object value) {
		this.property = property;
		this.value = value;
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
		return this.value;
	}
}

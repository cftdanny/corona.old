/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.Builder;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This builder is used to set constant value to component by setter method. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type of component
 */
public class ConfigurationBuilder<T> implements Builder<T> {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(ConfigurationBuilder.class);
	
	/**
	 * the protocol type of component
	 */
	private Class<T> configuredComponentType;
	
	/**
	 * the component name
	 */
	private String configuredComponentName = null;
	
	/**
	 * the property name of component to be configured
	 */
	private String configuredPropertyName;
	
	/**
	 * the constant value to set to component property
	 */
	private Object value;
	
	/**
	 * the component to be linked to component property
	 */
	private Class<?> linkedComponentType = null;
	
	/**
	 * the component name of linked component
	 */
	private String linkedComponentName = null;
	
	/**
	 * @param protocolType the injection type of component
	 */
	public ConfigurationBuilder(final Class<T> protocolType) {
		this.configuredComponentType = protocolType;
	}

	/**
	 * @param componentName the component name
	 * @return this builder
	 */
	public ConfigurationBuilder<T> as(final String componentName) {
		this.configuredComponentName = componentName;
		return this;
	}

	/**
	 * @param propertyName the name of setting
	 * @return this builder
	 */
	public ConfigurationBuilder<T> property(final String propertyName) {
		this.configuredPropertyName = propertyName;
		return this;
	}

	/**
	 * @param configurationValue the value to configure component property
	 * @return this builder
	 */
	public ConfigurationBuilder<T> value(final Object configurationValue) {
		this.value = configurationValue;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Builder#build(com.corona.context.ContextManagerFactory)
	 */
	@Override
	public void build(final ContextManagerFactory contextManagerFactory) {

		Key<T> key = new Key<T>(this.configuredComponentType, this.configuredComponentName);
		Descriptor<T> descriptor = contextManagerFactory.getComponentDescriptor(key);
		if (descriptor != null) {
			
			if (this.linkedComponentType == null) {
				descriptor.configure(new ConstantConfiguration(this.configuredPropertyName, this.value));
			} else {
				descriptor.configure(new LinkedConfiguration(
						this.configuredPropertyName, this.linkedComponentType, this.linkedComponentName
				));
			}
		} else {
			this.logger.warn("Component with key [{0}] does not exist, just skip it", key); 
		}
	}
}

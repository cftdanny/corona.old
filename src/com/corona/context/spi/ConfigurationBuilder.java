/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.Builder;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.context.Setting;
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
	private Class<T> type;
	
	/**
	 * the component name
	 */
	private String name = null;
	
	/**
	 * the setting name
	 */
	private String setting;
	
	/**
	 * the setting value
	 */
	private Object value;
	
	/**
	 * @param protocolType the injection type of component
	 */
	public ConfigurationBuilder(final Class<T> protocolType) {
		this.type = protocolType;
	}

	/**
	 * @param componentName the component name
	 * @return this builder
	 */
	public ConfigurationBuilder<T> as(final String componentName) {
		this.name = componentName;
		return this;
	}

	/**
	 * @param settingName the name of setting
	 * @return this builder
	 */
	public ConfigurationBuilder<T> setting(final String settingName) {
		this.setting = settingName;
		return this;
	}

	/**
	 * @param settingValue the value of setting
	 * @return this builder
	 */
	public ConfigurationBuilder<T> value(final Object settingValue) {
		this.value = settingValue;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Builder#build(com.corona.context.ContextManagerFactory)
	 */
	@Override
	public void build(final ContextManagerFactory contextManagerFactory) {

		Descriptor<T> descriptor = contextManagerFactory.getComponentDescriptor(new Key<T>(this.type, this.name));
		if (descriptor != null) {
			descriptor.configure(new Setting(this.setting, this.value));
		} else {
			this.logger.warn(
					"Component with key [{0}] and name [{1}] does not exist, skip it", this.type, this.name
			);
		}
	}
}

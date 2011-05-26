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
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type of component
 */
public class SettingBuilder<T> implements Builder<T> {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(SettingBuilder.class);
	
	/**
	 * the protocol type of component
	 */
	private Class<T> type;
	
	/**
	 * the component name
	 */
	private String name;
	
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
	public SettingBuilder(final Class<T> protocolType) {
		this.type = protocolType;
	}

	/**
	 * @param componentName the component name
	 * @return this builder
	 */
	public SettingBuilder<T> as(final String componentName) {
		this.name = componentName;
		return this;
	}

	/**
	 * @param settingName the name of setting
	 * @return this builder
	 */
	public SettingBuilder<T> to(final String settingName) {
		this.setting = settingName;
		return this;
	}

	/**
	 * @param settingValue the value of setting
	 * @return this builder
	 */
	public SettingBuilder<T> with(final Object settingValue) {
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
			descriptor.register(new Setting(this.setting, this.value));
		} else {
			this.logger.warn(
					"Component with key [{0}] and name [{1}] does not exist, skip it", this.type, this.name
			);
		}
	}
}

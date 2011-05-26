/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import static java.util.Locale.ENGLISH;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.corona.context.ConfigurationException;
import com.corona.context.Setting;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This descriptor is used to set value to component property by setting. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SettingDescriptor {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(SettingDescriptor.class);
	
	/**
	 * the method for setting to set value
	 */
	private Method method;
	
	/**
	 * the setting value
	 */
	private Object value;
	
	/**
	 * @param implementationClass the implementation class
	 * @param setting the setting
	 */
	SettingDescriptor(final Class<?> implementationClass, final Setting setting) {
		
		// find property by property name and implementation class
		PropertyDescriptor descriptor = null;
		try {
			descriptor = new PropertyDescriptor(
					setting.getName(), implementationClass, null, "set" + this.capitalize(setting.getName())
			);
		} catch (Exception e) {
			
			this.logger.error("Property [{0}] does not exist in component class [{1}]", 
					e, setting.getName(), implementationClass
			);
			throw new ConfigurationException("Property [{0}] does not exist in component class [{1}]", 
					e, setting.getName(), implementationClass
			);
		}
		
		// if write method in implementation class does not exist, will throw error
		if (descriptor.getWriteMethod() == null) {
			
			this.logger.error("Write method [{0}] does not exist in component class [{1}]", 
					setting.getName(), implementationClass
			);
			throw new ConfigurationException("Write method [{0}] does not exist in component class [{1}]", 
					setting.getName(), implementationClass
			);
		}
		
		// store writer method and value, for later to set component value
		this.method = descriptor.getWriteMethod();
		this.value = setting.getValue();
	}

	/**
	 * @param name the string
	 * @return the capitalized string
	 */
    public String capitalize(final String name) {
    	
    	if (name == null || name.length() == 0) {
    		return name;
    	}
    	return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

	/**
	 * @param component the component to set its setting value
	 */
	void setValue(final Object component) {
		
		try {
			this.method.invoke(component, this.value);
		} catch (Exception e) {
			
			this.logger.error("Fail to set component value by mehtod [{0}]", e, method);
			throw new ConfigurationException("Fail to set component value by mehtod [{0}]", e, method);
		}
	}
}

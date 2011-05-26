/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>This class is used to set configuration value to component just after it is created </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Setting {

	/**
	 * the property name, usually, the setter name
	 */
	private String name;
	
	/**
	 * the value passes to component by setter
	 */
	private Object value;
	
	/**
	 * default constructor
	 */
	public Setting() {
		// do nothing
	}
	
	/**
	 * @param name the setting name
	 * @param value the setting value
	 */
	public Setting(final String name, final Object value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(final Object value) {
		this.value = value;
	}
}

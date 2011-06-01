/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.constant;

/**
 * <p>Some constants values </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Constant {

	/**
	 * the constant component name
	 */
	public static final String NAME = "constant";
	
	/**
	 * the constant name
	 */
	private String name;
	
	/**
	 * the constant value
	 */
	private String value;
	
	/**
	 * @return the constant name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the constant name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the constant value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param value the constant value to set
	 */
	public void setValue(final String value) {
		this.value = value;
	}
}

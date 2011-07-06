/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.request;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Address {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the street
	 */
	private String street;
	
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
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * @param street the street to set
	 */
	public void setStreet(final String street) {
		this.street = street;
	}
}

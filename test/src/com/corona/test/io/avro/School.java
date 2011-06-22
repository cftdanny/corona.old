/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.io.avro;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class School {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the address
	 */
	private String address;

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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * @param address the address to set
	 */
	public void setAddress(final String address) {
		this.address = address;
	}
}

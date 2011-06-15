/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

/**
 * <p>The descriptor for array token </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ArrayTokenDescriptor implements TokenDescriptor {

	/**
	 * the token name
	 */
	private String name;
	
	/**
	 * the array index
	 */
	private int index;
	
	/**
	 * @param name the token name
	 * @param index the index
	 */
	ArrayTokenDescriptor(final String name, final int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * @return name the token name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the array index
	 */
	public int getIndex() {
		return index;
	}
}

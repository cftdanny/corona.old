/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.param;

/**
 * <p>The descriptor for object token </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ObjectTokenDescriptor implements TokenDescriptor {

	/**
	 * the token name
	 */
	private String name;
	
	/**
	 * @param name the token name
	 */
	ObjectTokenDescriptor(final String name) {
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.param.TokenDescriptor#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}
}

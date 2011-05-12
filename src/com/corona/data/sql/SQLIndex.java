/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.annotation.Entity;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLIndex<E> implements com.corona.data.Index<E> {

	/**
	 * 
	 */
	private String select;
	
	/**
	 * 
	 */
	private String delete;
	
	public SQLIndex(Entity entity, com.corona.data.annotation.Index index) {
		
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Index#list(java.lang.Object[])
	 */
	@Override
	public E[] list(Object... values) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Index#delete(java.lang.Object[])
	 */
	@Override
	public int delete(Object... values) {
		return 0;
	}
}

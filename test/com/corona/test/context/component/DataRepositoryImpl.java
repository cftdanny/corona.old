/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

import java.util.HashMap;
import java.util.Map;

import com.corona.context.annotation.Context;
import com.corona.context.annotation.Name;

/**
 * <p>The data repository implementation </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Context
@Name("default")
public class DataRepositoryImpl implements DataRepository {

	/**
	 * the data for find
	 */
	private Map<Integer, String> repository = new HashMap<Integer, String>();
	
	/**
	 * create data repository
	 */
	public DataRepositoryImpl() {
		
		this.repository.put(1, "A");
		this.repository.put(2, "B");
		this.repository.put(3, "C");
		this.repository.put(4, "D");
		this.repository.put(5, "E");
		this.repository.put(6, "F");
		this.repository.put(7, "G");
		this.repository.put(8, "H");
		this.repository.put(9, "I");
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.test.context.component.DataRepository#find(int)
	 */
	@Override
	public String find(final int id) {
		return this.repository.get(id);
	}
}

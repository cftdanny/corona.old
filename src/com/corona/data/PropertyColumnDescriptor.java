/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This descriptor is used to map column of data source to a field in entity class </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public class PropertyColumnDescriptor<E> implements ColumnDescriptor<E> {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#getName()
	 */
	@Override
	public String getName() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#getType()
	 */
	@Override
	public Class<?> getType() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#get(java.lang.Object)
	 */
	@Override
	public Object get(final E entity) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(final E entity, final Object value) {
	}
}

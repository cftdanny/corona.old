/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This descriptor is used to map column of data source to field or property of entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface ColumnDescriptor<E> {

	/**
	 * @return the column name
	 */
	String getName();
	
	/**
	 * @return the type of field or property in entity class
	 */
	Class<?> getType();
	
	/**
	 * @param entity the entity instance
	 * @return the value of column that get from entity instance
	 */
	Object get(E entity);
	
	/**
	 * @param entity the entity instance
	 * @param value the value that set to entity instance for this column
	 */
	void set(E entity, Object value);
}

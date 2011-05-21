/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This unique key is used to find or delete entity with values of unique key. It implements <b>exists</b>, 
 * <b>get</b> and <b>delete</b> methods for every unique key. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public interface UniqueKey<E> {

	/**
	 * close all resources that allocated for unique key
	 */
	void close();
	
	/**
	 * <p>Test whether an entity exists in data source or not by values of columns in unique key. </p>
	 * 
	 * @param values the values of columns in unique key
	 * @return <code>true</code> if entity exists in data source
	 */
	boolean exists(Object... values);
	
	/**
	 * <p>Find an entity instance from data source by values of columns in unique key. </p> 
	 * 
	 * @param values the values of columns in unique key
	 * @return entity instance or <code>null</code> if does not exists
	 */
	E get(Object... values);
	
	/**
	 * <p>Delete an entity from data source by values of columns in unique key. </p>
	 * 
	 * @param values the values of columns in unique key
	 * @return <code>true</code> if entity has been deleted from data source
	 */
	boolean delete(Object... values);
}

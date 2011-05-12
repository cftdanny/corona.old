/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This unique key is used to manage table entity with unique key </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of table entity
 */
public interface UniqueKey<E> {

	/**
	 * <p>Try to test whether an entity instance (table record) exists in database or not by 
	 * unique key. </p>
	 * 
	 * @param values the values of the unique key
	 * @return <code>true</code> if entity with primary key exists
	 */
	boolean exists(Object... values);
	
	/**
	 * <p>Find an entity instance (table record) from database by unique key. </p> 
	 * 
	 * @param values the values of the unique key
	 * @return the entity instance or <code>null</code> if does not exists
	 */
	E get(Object... values);
	
	/**
	 * <p>Delete an entity instance (table record) from database by unique key. </p>
	 * 
	 * @param values the value of unique key
	 * @return <code>true</code> if this entity has been deleted
	 */
	boolean delete(Object... values);
}

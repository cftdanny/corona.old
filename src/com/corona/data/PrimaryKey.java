/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This primary key class is used to manage entity in table with primary key </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of table entity
 */
public interface PrimaryKey<E> {

	/**
	 * <p>test whether an entity instance (table record) exists in database or not by 
	 * primary key (argument k). </p>
	 * 
	 * @param keys the value of the primary key
	 * @return <code>true</code> if entity with primary key exists
	 */
	boolean exists(Object... keys);
	
	/**
	 * <p>Find an entity instance (table record) from database by primary key (argument k). </p> 
	 * 
	 * @param keys the value of the primary key
	 * @return the entity instance or <code>null</code> if does not exists
	 */
	E get(Object... keys);

	/**
	 * <p>Delete an entity instance (table record) from database by primary key (argument k). </p>
	 * 
	 * @param keys the value of primary key
	 * @return <code>true</code> if this entity has been deleted
	 */
	boolean delete(Object... keys);
	
	/**
	 * <p>Update an changed entity instance to data source by entity instance and primary key. </p>
	 * 
	 * @param e the instance of entity
	 * @return whether entity has been save to data source
	 */
	boolean update(E e);
}

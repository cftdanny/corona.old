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
 * @param <K> the type of primary key
 */
public interface PrimaryKey<K, E> {

	/**
	 * <p>Try to test whether an entity instance (table record) exists in database or not by 
	 * primary key (argument k). </p>
	 * 
	 * @param value the value of the primary key
	 * @return <code>true</code> if entity with primary key exists
	 */
	boolean exists(K value);
	
	/**
	 * <p>Find an entity instance (table record) from database by primary key (argument k). </p> 
	 * 
	 * @param value the value of the primary key
	 * @return the entity instance or <code>null</code> if does not exists
	 */
	E get(K value);

	/**
	 * <p>Delete an entity instance (table record) from database by primary key (argument k). </p>
	 * 
	 * @param value the value of primary key
	 * @return <code>true</code> if this entity has been deleted
	 */
	boolean delete(K value);
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This definition is used to store and create primary key information. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface PrimaryKeyDescriptor<E> {

	/**
	 * @param connectionManager the current connection manager
	 * @return the new primary key
	 */
	PrimaryKey<E> createPrimaryKey(ConnectionManager connectionManager);
}

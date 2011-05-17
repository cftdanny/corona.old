/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This definition is used to store and create unique key information. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface UniqueKeyDescriptor<E> {

	/**
	 * @return the id of unique key
	 */
	int getId();
	
	/**
	 * @param connectionManager the connection manager
	 * @return the command to query entity by primary key
	 */
	Query<E> getSelectQuery(ConnectionManager connectionManager);
	
	/**
	 * @param connectionManager the connection manager
	 * @return the command to delete entity by primary key
	 */
	Command getDeleteCommand(ConnectionManager connectionManager);
}
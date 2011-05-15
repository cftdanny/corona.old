/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>The index definition </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface IndexDescriptor<E> {

	/**
	 * @return the id of index
	 */
	int getId();
	
	/**
	 * @param connectionManager the connection manager
	 * @return the query to search by index 
	 */
	Command getSearchQuery(ConnectionManager connectionManager);
	
	/**
	 * @param connectionManager the connection manager
	 * @return the command to delete by index
	 */
	Command getDeleteCommand(ConnectionManager connectionManager);
}

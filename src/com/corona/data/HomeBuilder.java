/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This builder is used to build query and command that will be used in data access object {@link Home}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface HomeBuilder {

	/**
	 * @param <E> the type of entity class
	 * @param connectionManager the current connection manager
	 * @param config the entity configuration
	 * @return the command is used to insert entity to data source
	 */
	<E> Command createInsertCommand(ConnectionManager connectionManager, EntityMetaData<E> config);
	
	/**
	 * @param <E> the type of entity class
	 * @param connectionManager the current connection manager
	 * @param config the entity configuration
	 * @param filter the filter
	 * @return the query is used to count rows in data source by filter
	 */
	<E> Query<Long> createCountQuery(ConnectionManager connectionManager, EntityMetaData<E> config, String filter);
	
	/**
	 * @param <E> the type of entity class
	 * @param connectionManager the current connection manager
	 * @param config the entity configuration
	 * @param filter the filter
	 * @return the query to list rows in data source by filter
	 */
	<E> Query<E> createListQuery(ConnectionManager connectionManager, EntityMetaData<E> config, String filter);
	
	/**
	 * @param connectionManager the connection manager
	 * @param config the entity configuration
	 * @param filter the filter
	 * @return the DELETE command
	 */
	Command createDeleteCommand(ConnectionManager connectionManager, EntityMetaData<?> config, String filter);
	
	/**
	 * @param connectionManager the connection manager
	 * @param config the entity configuration
	 * @param filter the filter
	 * @return the UPDATE command
	 */
	Command createUpdateCommand(ConnectionManager connectionManager, EntityMetaData<?> config, String filter);
}

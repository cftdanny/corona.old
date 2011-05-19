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
}

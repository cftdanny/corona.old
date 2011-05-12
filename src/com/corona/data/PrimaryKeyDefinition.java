/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This definition is used to store and create primary key information. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface PrimaryKeyDefinition {

	/**
	 * @param connectionManager the connection manager
	 * @return the command to delete entity by primary key
	 */
	Command getDeleteCommand(ConnectionManager connectionManager);
}

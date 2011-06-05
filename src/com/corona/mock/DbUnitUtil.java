/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import java.sql.Connection;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;

import com.corona.data.ConnectionManager;

/**
 * <p>The utility class that is used to create DatabaseConnection of DbUnit by ConnectionManager </p>
 *
 * @author $Author$
 * @version $Id$
 */
final class DbUnitUtil {

	/**
	 * utility class
	 */
	private DbUnitUtil() {
		// do nothing
	}

	/**
	 * @param connectionManager the connection manager
	 * @return the database connection
	 * @exception Exception if fail to create database connection
	 */
	static DatabaseConnection create(final ConnectionManager connectionManager) throws Exception {
		return create(connectionManager, null);
	}

	/**
	 * @param connectionManager the connection manager
	 * @param schema the database schema 
	 * @return the database connection
	 * @exception Exception if fail to create database connection
	 */
	static DatabaseConnection create(final ConnectionManager connectionManager, final String schema) throws Exception {
		
		DatabaseConnection connection = new DatabaseConnection((Connection) connectionManager.getSource(), schema);
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new SQLDataTypeFactory());
		return connection;
	}
}

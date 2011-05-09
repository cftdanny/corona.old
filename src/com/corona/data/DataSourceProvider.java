/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.Properties;

/**
 * <p>The data source provider is used to create {@link ConnectionManagerFactory} by database family. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface DataSourceProvider {

	/**
	 * @return the database family (MySQL, SQL Server, DB2, etc)
	 */
	String getFamily();
	
	/**
	 * @param url the a database URL of the form jdbc:subprotocol:subname
	 * @param username the user name to log in database server
	 * @param password the password for user
	 * @return the new created {@link ConnectionManagerFactory}
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	ConnectionManagerFactory create(
			final String url, final String username, final String password) throws DataException;
	
	/**
	 * @param url the a database URL of the form jdbc:subprotocol:subname
	 * @param properties a list of arbitrary string tag/value pairs as connection arguments
	 * @return the new created {@link ConnectionManagerFactory}
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	ConnectionManagerFactory create(final String url, final Properties properties) throws DataException;
}

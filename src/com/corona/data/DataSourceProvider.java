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
	 * the property name for JNDI data source
	 */
	String JNDI = "jndi";
	
	/**
	 * the a database URL of the form jdbc:subprotocol:subname
	 */
	String URL = "url";
	
	/**
	 * the user name to log in database server
	 */
	String USER = "user";
	
	/**
	 * the password for user
	 */
	String PASSWORD = "password";
	
	/**
	 * @return the database family (MySQL, SQL Server, DB2, etc)
	 */
	String getFamily();
	
	/**
	 * <p>Create {@link ConnectManagerFactory} by data source configuration. Configurations is stored in 
	 * argument properties, the key can be JNDI data source, JDBC connection URL, database log in user, 
	 * password for user, etc
	 * </p>
	 * 
	 * @param properties a list of arbitrary string tag/value pairs as connection arguments
	 * @return the new created {@link ConnectionManagerFactory}
	 * @exception DataException if fail to create {@link ConnectionManagerFactory}
	 */
	ConnectionManagerFactory create(final Properties properties) throws DataException;
}

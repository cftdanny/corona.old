/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>The connection manager is used to manage data source, for example: query, update or delete data
 * in data source. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ConnectionManager {

	/**
	 * @return the parent connection manager factory
	 */
	ConnectionManagerFactory getConnectionManagerFactory();
	
	/**
	 * @return the dialect for the data source
	 */
	Dialect getDialect();

	/**
	 * @return the session for specified data source
	 */
	Object getSource();
	
	/**
	 * @return <code>true</code> if closed from data source
	 */
	boolean isClosed();
	
	/**
	 * close from data source
	 */
	void close();
	
	/**
	 * 
	 * @param <E>
	 * @param clazz
	 * @param query the query statement for specified data source
	 * @return
	 */
	<E> Query<E> createQuery(Class<E> clazz, String query);
	
	/**
	 * <p>Create command by command string. For example, if data source is SQL, it will be DELETE, UPDATE, INSERT
	 * statement.
	 * </p>
	 * 
	 * @param command the command string
	 * @return the created command
	 */
	Command createCommand(String command);
	
	/**
	 * <p>Create command by a command annotation in class. For example, if data source is SQL, it will be DELETE, 
	 * UPDATE, INSERT statement.
	 * </p>
	 *  
	 * @param commandClass the class that is annotated with command annotation
	 * @return the created command
	 */
	Command createCommand(Class<?> commandClass);
	
	/**
	 * <p>Create command by a command annotation in class. For example, if data source is SQL, it will be DELETE, 
	 * UPDATE, INSERT statement.
	 * </p>
	 *  
	 * @param commandClass the class that is annotated with command annotation
	 * @param name the command name
	 * @return the created command
	 */
	Command createCommand(Class<?> commandClass, String name);
}

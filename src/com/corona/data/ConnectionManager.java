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
	 * <p>Create query by script and map query result to bean instance of result class. For example, if data source is
	 * SQL, query script will be SQL SELECT statement. </p>
	 * </>
	 * 
	 * @param <E> the type of result class
	 * @param resultClass the result class
	 * @param query the query script
	 * @return the new created query
	 */
	<E> Query<E> createQuery(Class<E> resultClass, String query);

	/**
	 * <p>Create query by script and map query result to bean instance of result class. For example, if data source is
	 * SQL, query script will be SQL SELECT statement. </p>
	 * </>
	 * 
	 * @param <E> the type of result class
	 * @param resultHandler the result handler
	 * @param query the query script
	 * 
	 * @return the new query
	 */
	<E> Query<E> createQuery(ResultHandler<E> resultHandler, String query);

	/**
	 * <p>Create query by a NamedQuery annotation is result class. It also support multiple data source family 
	 * query script by NamedQuery annotation.
	 * </p>
	 * 
	 * @param <E> the type of result class
	 * @param resultClass the result class
	 * @return the new query
	 */
	<E> Query<E> createNamedQuery(Class<E> resultClass);
	
	/**
	 * <p>Create query by a NamedQuery annotation is result class. It also support multiple data source family 
	 * query script by NamedQuery annotation.
	 * </p>
	 * 
	 * @param <E> the type of result class
	 * @param resultClass the result class
	 * @param name the named query name
	 * @return the new query
	 */
	<E> Query<E> createNamedQuery(Class<E> resultClass, String name);
	
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
	Command createNamedCommand(Class<?> commandClass);
	
	/**
	 * <p>Create command by a command annotation in class. For example, if data source is SQL, it will be DELETE, 
	 * UPDATE, INSERT statement.
	 * </p>
	 *  
	 * @param commandClass the class that is annotated with command annotation
	 * @param name the command name
	 * @return the created command
	 */
	Command createNamedCommand(Class<?> commandClass, String name);
}

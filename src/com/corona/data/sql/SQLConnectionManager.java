/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.DataException;
import com.corona.data.DataRuntimeException;
import com.corona.data.Query;
import com.corona.data.BeanResultHandler;
import com.corona.data.ResultHandler;
import com.corona.data.Transaction;
import com.corona.data.annotation.NamedCommand;
import com.corona.data.annotation.NamedCommands;
import com.corona.data.annotation.NamedQueries;
import com.corona.data.annotation.NamedQuery;
import com.corona.data.annotation.Statement;
import com.corona.expression.Template;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The base and helper connection manager for SQL database. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class SQLConnectionManager implements ConnectionManager, 
		SQLCommandCloseListener, SQLQueryCloseListener {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(SQLConnectionManager.class);
	
	/**
	 * the SQL connection manager factory
	 */
	private SQLConnectionManagerFactory connectionManagerFactory;
	
	/**
	 * the opened JDBC connection
	 */
	private Connection connection;

	/**
	 * the listener support for querying connection manager close event
	 */
	private SQLConnectionManagerCloseListenerSupport closeListenerSupport;
	
	/**
	 * the pooled commands
	 */
	private Map<Key, SQLCommand> pooledCommands = new HashMap<Key, SQLCommand>();
	
	/**
	 * all opened commands, will close them when manager is closed
	 */
	private List<SQLCommand> commands = new ArrayList<SQLCommand>();
	
	/**
	 * the pooled queries
	 */
	private Map<Key, SQLQuery<?>> pooledQueries = new HashMap<Key, SQLQuery<?>>();
	
	/**
	 * all opened queries, will close them when manager is closed
	 */
	private List<SQLQuery<?>> queries = new ArrayList<SQLQuery<?>>();
	
	/**
	 * @param connectionManagerFactory the SQL connection manager factory
	 * @exception DataException if fail to create connection manager factory
	 */
	public SQLConnectionManager(final SQLConnectionManagerFactory connectionManagerFactory) throws DataException {
		this.connectionManagerFactory = connectionManagerFactory;
		this.connection = this.connectionManagerFactory.getConnection();
		
		this.closeListenerSupport = new SQLConnectionManagerCloseListenerSupport();
	}

	/**
	 * @param listener the listener to listen SQL connection manager close event
	 */
	public void addCloseListener(final SQLConnectionManagerCloseListener listener) {
		this.closeListenerSupport.add(listener);
	}

	/**
	 * @param listener the listener to listen SQL connection manager close event
	 */
	public void removeCloseListener(final SQLConnectionManagerCloseListener listener) {
		this.closeListenerSupport.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getConnectionManagerFactory()
	 */
	@Override
	public ConnectionManagerFactory getConnectionManagerFactory() {
		return this.connectionManagerFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getSource()
	 */
	@Override
	public Connection getSource() {
		return this.connection;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#isClosed()
	 */
	@Override
	public boolean isClosed() {
		
		try {
			return this.connection.isClosed();
		} catch (Exception e) {
			this.logger.error(
					"Fail to test whether JDBC connection is closed, just treat as closed.", e
			);
			return true;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.sql.SQLCommandCloseListener#close(com.corona.data.sql.SQLCommandCloseEvent)
	 */
	@Override
	public void close(final SQLCommandCloseEvent event) {
		this.commands.remove(event.getSource());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.sql.SQLQueryCloseListener#close(com.corona.data.sql.SQLQueryCloseEvent)
	 */
	@Override
	public void close(final SQLQueryCloseEvent event) {
		this.queries.remove(event.getSource());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Closeable#close()
	 */
	@Override
	public void close() {

		// fire closing event to tell all listeners, connection manager will close
		SQLConnectionManagerCloseEvent event = new SQLConnectionManagerCloseEvent(this);
		this.closeListenerSupport.fire(event);

		// if connection manager is closed, don't need close again
		if (this.isClosed()) {
			return;
		}
		
		// close all commands if it is not closed yet
		for (SQLCommand command : this.commands.toArray(new SQLCommand[0])) {
			try {
				command.close();
			} catch (Exception e) {
				this.logger.error("Fail to close command [{0}], ignore this exception", e, command);
			}
		}
		this.commands.clear();
		
		// close all queries if it is not closed yet
		for (SQLQuery<?> query : this.queries.toArray(new SQLQuery<?>[0])) {
			try {
				query.close();
			} catch (Exception e) {
				this.logger.error("Fail to close query [{0}], ignore this exception", e, query);
			}
		}
		this.queries.clear();
		
		// if all listeners don't want to cache and reuse this client, will close it
		if (!event.isCancel()) {
			// close JDBC connection
			try {
				this.connection.close();
			} catch (Exception e) {
				
				this.logger.error("Fail to close opened JDBC connection", e);
				throw new DataRuntimeException("Fail to close opened JDBC connection", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getTransaction()
	 */
	@Override
	public Transaction getTransaction() {
		return new SQLLocalTransaction(this);
	}

	/**
	 * @param type the class type of query result
	 * @param name the query name
	 * @param query the query
	 */
	void addPooledQuery(final Class<?> type, final String name, final SQLQuery<?> query) {
		
		// remove query from to auto-closed query list if added before
		this.queries.remove(query);
		
		// put query to pooled query map, allow to find it later
		this.pooledQueries.put(new Key(type, name), query);
	}

	/**
	 * @param type the class type of query result or it defines the command
	 * @param name the command name
	 * @param command the command
	 */
	void addPooledCommand(final Class<?> type, final String name, final SQLCommand command) {
		
		// remove command from to auto-closed command list if added before
		this.pooledCommands.remove(command);
		
		// put command to pooled command map, allow to find it later
		this.pooledCommands.put(new Key(type, name), command);
	}
	
	/**
	 * @param type the class type of query result or it defines the command
	 * @param name the command name
	 * @return the pooled command or <code>null</code> if does not exist
	 */
	SQLCommand getPooledCommand(final Class<?> type, final String name) {
		return this.pooledCommands.get(new Key(type, name));
	}

	/**
	 * @param <E> the class type of query result
	 * @param type the class type of query result
	 * @param name the query name
	 * @return the pooled query or <code>null</code> if does not exist
	 */
	@SuppressWarnings("unchecked")
	<E> SQLQuery<E> getPooledQuery(final Class<E> type, final String name) {
		return (SQLQuery<E>) this.pooledQueries.get(new Key(type, name));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createQuery(java.lang.Class, java.lang.String)
	 */
	@Override
	public <E> Query<E> createQuery(final Class<E> resultClass, final String query) {
		return createQuery(new BeanResultHandler<E>(resultClass), query);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createQuery(com.corona.data.ResultHandler, java.lang.String)
	 */
	@Override
	public <E> Query<E> createQuery(final ResultHandler<E> resultHandler, final String query) {
		
		SQLQuery<E> sqlQuery = null;
		try {
			sqlQuery = new SQLQuery<E>(this, resultHandler, query);
		} catch (DataException e) {
			
			this.logger.error("Fail to create query by SQL [{0}]", e, query);
			throw new DataRuntimeException("Fail to create query by SQL [{0}]", e, query);
		}
		
		this.queries.add(sqlQuery);
		sqlQuery.addCloseListener(this);
		
		return sqlQuery;
	}
	
	/**
	 * @param <E> the type of result class
	 * @param resultClass the result class
	 * @param namedQuery the named query annotation
	 * @param variableMap the variable map
	 * @return the new query
	 */
	private <E> Query<E> createNamedQuery(
			final Class<E> resultClass, final NamedQuery namedQuery, final Map<String, ?> variableMap) {
		
		// get data source family and default query script from NamedQuery annotation
		String family = this.getConnectionManagerFactory().getDataSourceProvider().getFamily();
		
		// if any statement is set to specified data source family, will use it as query script
		String query = namedQuery.value();
		for (Statement statement : namedQuery.statements()) {
			
			if (family.equals(statement.family())) {
				query = statement.statement();
				break;
			}
		}
		
		// if there is binding parameters in statement, try to bind it to normal statement
		if (variableMap != null) {
			query = new Template(query).execute(variableMap);
		}
		
		// create query by query statement
		return this.createQuery(resultClass, query);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedQuery(java.lang.Class)
	 */
	@Override
	public <E> Query<E> createNamedQuery(final Class<E> resultClass) {
		
		NamedQuery namedQuery = resultClass.getAnnotation(NamedQuery.class);
		if (namedQuery == null) {
			this.logger.error("Can not find @NamedQuery annotation in class [{0}]", resultClass);
			throw new DataRuntimeException("Can not find @NamedQuery annotation in class [{0}]", resultClass);
		}
		return this.createNamedQuery(resultClass, namedQuery, null);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedQuery(java.lang.Class, java.util.Map)
	 */
	@Override
	public <E> Query<E> createNamedQuery(final Class<E> resultClass, final Map<String, ?> bindings) {
		
		NamedQuery namedQuery = resultClass.getAnnotation(NamedQuery.class);
		if (namedQuery == null) {
			this.logger.error("Can not find @NamedQuery annotation in class [{0}]", resultClass);
			throw new DataRuntimeException("Can not find @NamedQuery annotation in class [{0}]", resultClass);
		}
		return this.createNamedQuery(resultClass, namedQuery, bindings);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedQuery(java.lang.Class, java.lang.String)
	 */
	@Override
	public <E> Query<E> createNamedQuery(final Class<E> resultClass, final String name) {
		
		NamedQueries namedQueries = resultClass.getAnnotation(NamedQueries.class);
		if (namedQueries == null) {
			this.logger.error("Can not find @NamedQueries annotation in class [{0}]", resultClass);
			throw new DataRuntimeException("Can not find @NamedQueries annotation in class [{0}]", resultClass);
		}
		
		for (NamedQuery namedQuery : namedQueries.value()) {
			if (namedQuery.name().equals(name)) {
				this.createNamedQuery(resultClass, namedQuery, null);
			}
		}
		
		this.logger.error("Can not find @NamedQuery [{0}] in class [{1}]", name, resultClass);
		throw new DataRuntimeException("Can not find @NamedQuery [{0}] in class [{1}]", name, resultClass);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedQuery(java.lang.Class, java.lang.String, java.util.Map)
	 */
	@Override
	public <E> Query<E> createNamedQuery(final Class<E> resultClass, final String name, final Map<String, ?> bindings) {
		
		NamedQueries namedQueries = resultClass.getAnnotation(NamedQueries.class);
		if (namedQueries == null) {
			this.logger.error("Can not find @NamedQueries annotation in class [{0}]", resultClass);
			throw new DataRuntimeException("Can not find @NamedQueries annotation in class [{0}]", resultClass);
		}
		
		for (NamedQuery namedQuery : namedQueries.value()) {
			if (namedQuery.name().equals(name)) {
				this.createNamedQuery(resultClass, namedQuery, bindings);
			}
		}
		
		this.logger.error("Can not find @NamedQuery [{0}] in class [{1}]", name, resultClass);
		throw new DataRuntimeException("Can not find @NamedQuery [{0}] in class [{1}]", name, resultClass);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createCommand(java.lang.String)
	 */
	@Override
	public Command createCommand(final String command) {
		
		SQLCommand sqlCommand = null;
		try {
			sqlCommand = new SQLCommand(this, command);
		} catch (SQLException e) {
			
			this.logger.error("Fail to create SQL command by [{0}]", e, command);
			throw new DataRuntimeException("Fail to create SQL command by [{0}]", e, command);
		}
		
		this.commands.add(sqlCommand);
		sqlCommand.addCloseListener(this);
		
		return sqlCommand;
	}

	/**
	 * @param namedCommand the named command annotation
	 * @param variableMap the variable map
	 * @return the new command
	 */
	private Command createNamedCommand(final NamedCommand namedCommand, final Map<String, ?> variableMap) {
		
		// get data source family and default command script from NamedCommand annotation
		String family = this.getConnectionManagerFactory().getDataSourceProvider().getFamily();

		// if any statement is set to specified data source family, will use it as command script
		String command = namedCommand.value();
		for (Statement statement : namedCommand.statements()) {
			
			if (family.equals(statement.family())) {
				command = statement.statement();
				break;
			}
		}
		
		// if there is binding parameters in statement, try to bind it to normal statement
		if (variableMap != null) {
			command = new Template(command).execute(variableMap);
		}

		// create query by query statement
		return this.createCommand(command);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedCommand(java.lang.Class)
	 */
	@Override
	public Command createNamedCommand(final Class<?> commandClass) {
		
		NamedCommand namedCommand = commandClass.getAnnotation(NamedCommand.class);
		if (namedCommand == null) {
			this.logger.error("Can not find @NamedCommand annotation in class [{0}]", commandClass);
			throw new DataRuntimeException("Can not find @NamedCommand annotation in class [{0}]", commandClass);
		}
		return this.createNamedCommand(namedCommand, null);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedCommand(java.lang.Class, java.util.Map)
	 */
	@Override
	public Command createNamedCommand(final Class<?> commandClass, final Map<String, ?> bindings) {

		NamedCommand namedCommand = commandClass.getAnnotation(NamedCommand.class);
		if (namedCommand == null) {
			this.logger.error("Can not find @NamedCommand annotation in class [{0}]", commandClass);
			throw new DataRuntimeException("Can not find @NamedCommand annotation in class [{0}]", commandClass);
		}
		return this.createNamedCommand(namedCommand, bindings);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedCommand(java.lang.Class, java.lang.String)
	 */
	@Override
	public Command createNamedCommand(final Class<?> commandClass, final String name) {
		
		NamedCommands namedCommands = commandClass.getAnnotation(NamedCommands.class);
		if (namedCommands == null) {
			this.logger.error("Can not find @NamedCommands annotation in class [{0}]", commandClass);
			throw new DataRuntimeException("Can not find @NamedCommands annotation in class [{0}]", commandClass);
		}
		
		for (NamedCommand namedCommand : namedCommands.value()) {
			if (namedCommand.name().equals(name)) {
				this.createNamedCommand(namedCommand, null);
			}
		}
		
		this.logger.error("Can not find @NamedCommands [{0}] in class [{1}]", name, commandClass);
		throw new DataRuntimeException("Can not find @NamedCommands [{0}] in class [{1}]", name, commandClass);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedCommand(java.lang.Class, java.lang.String, java.util.Map)
	 */
	@Override
	public Command createNamedCommand(final Class<?> commandClass, final String name, final Map<String, ?> bindings) {

		NamedCommands namedCommands = commandClass.getAnnotation(NamedCommands.class);
		if (namedCommands == null) {
			this.logger.error("Can not find @NamedCommands annotation in class [{0}]", commandClass);
			throw new DataRuntimeException("Can not find @NamedCommands annotation in class [{0}]", commandClass);
		}
		
		for (NamedCommand namedCommand : namedCommands.value()) {
			if (namedCommand.name().equals(name)) {
				this.createNamedCommand(namedCommand, bindings);
			}
		}
		
		this.logger.error("Can not find @NamedCommands [{0}] in class [{1}]", name, commandClass);
		throw new DataRuntimeException("Can not find @NamedCommands [{0}] in class [{1}]", name, commandClass);
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The base and helper connection manager for SQL database. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class SQLConnectionManager implements ConnectionManager {

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
	 * all opened commands, will close them when manager is closed
	 */
	private List<SQLCommand> commands = new ArrayList<SQLCommand>();
	
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
			this.logger.error("Fail to test whether JDBC connection is closed", e);
			throw new DataRuntimeException("Fail to test whether JDBC connection is closed", e);
		}
	}

	/**
	 * @param query the new opened query, register it to connection manager
	 */
	void register(final SQLQuery<?> query) {
		this.queries.add(query);
	}
	
	/**
	 * @param command the new opened query, register it to connection manager
	 */
	void register(final SQLCommand command) {
		this.commands.add(command);
	}
/**
	 * @param query the query to be closed, unregister from connection manager
	 */
	void unregister(final SQLQuery<?> query) {
		this.queries.remove(query);
	}
	
	/**
	 * @param command the command to be closed, unregister from connection manager
	 */
	void unregister(final SQLCommand command) {
		this.commands.remove(command);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#close()
	 */
	@Override
	public void close() {
		
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
		
		// close JDBC connection
		try {
			this.connection.close();
		} catch (Exception e) {
			
			this.logger.error("Fail to close opened JDBC connection", e);
			throw new DataRuntimeException("Fail to close opened JDBC connection", e);
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
		
		try {
			return new SQLQuery<E>(this, resultHandler, query);
		} catch (DataException e) {
			
			this.logger.error("Fail to create query by SQL [{0}]", e, query);
			throw new DataRuntimeException("Fail to create query by SQL [{0}]", e, query);
		}
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
		return this.createNamedQuery(resultClass, namedQuery);
	}

	/**
	 * @param <E> the type of result class
	 * @param resultClass the result class
	 * @param namedQuery the named query annotation
	 * @return the new query
	 */
	private <E> Query<E> createNamedQuery(final Class<E> resultClass, final NamedQuery namedQuery) {
		
		// get data source family and default query script from NamedQuery annotation
		String family = this.getConnectionManagerFactory().getDataSourceProvider().getFamily();
		String query = namedQuery.value();
		
		// if any statement is set to specified data source family, will use it as query script
		for (Statement statement : namedQuery.statements()) {
			
			if (family.equals(statement.family())) {
				query = statement.statement();
				break;
			}
		}
		return this.createQuery(resultClass, query);
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
				this.createNamedQuery(resultClass, namedQuery);
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
		
		try {
			return new SQLCommand(this, command);
		} catch (SQLException e) {
			
			this.logger.error("Fail to create SQL command by [{0}]", e, command);
			throw new DataRuntimeException("Fail to create SQL command by [{0}]", e, command);
		}
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
		return this.createNamedCommand(namedCommand);
	}

	/**
	 * @param namedCommand the named command annotation
	 * @return the new command
	 */
	private Command createNamedCommand(final NamedCommand namedCommand) {
		
		// get data source family and default command script from NamedCommand annotation
		String family = this.getConnectionManagerFactory().getDataSourceProvider().getFamily();
		String command = namedCommand.value();

		// if any statement is set to specified data source family, will use it as command script
		for (Statement statement : namedCommand.statements()) {
			
			if (family.equals(statement.family())) {
				command = statement.statement();
				break;
			}
		}
		return this.createCommand(command);
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
				this.createNamedCommand(namedCommand);
			}
		}
		
		this.logger.error("Can not find @NamedCommands [{0}] in class [{1}]", name, commandClass);
		throw new DataRuntimeException("Can not find @NamedCommands [{0}] in class [{1}]", name, commandClass);
	}
}

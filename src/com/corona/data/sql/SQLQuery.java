/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.corona.data.DataException;
import com.corona.data.DataRuntimeException;
import com.corona.data.Query;
import com.corona.data.ResultHandler;
import com.corona.data.BeanResultHandler;

/**
 * <p>This query is used to execute SQL query for database and transfer the query result to list of entities. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public class SQLQuery<E> implements Query<E> {

	/**
	 * the parent connection manager
	 */
	private SQLConnectionManager parent;
	
	/**
	 * the query request handler
	 */
	private ResultHandler<E> resultHandler;
	
	/**
	 * the prepared and named parameter SQL statement
	 */
	private ParametricStatement statement;

	/**
	 * @param connectionManager the connection manager
	 * @param sql the SQL query statement
	 * @param resultClass the class that will map row of query result to
	 * @throws DataException if fail to create this query
	 */
	public SQLQuery(
			final SQLConnectionManager connectionManager, final Class<E> resultClass, final String sql
	) throws DataException {
		this(connectionManager, new BeanResultHandler<E>(resultClass), sql);
	}

	/**
	 * @param connectionManager the connection manager
	 * @param sql the SQL query statement
	 * @param resultHandler the handler that is used to map row of query result to bean instance
	 * @throws DataException if fail to create this query
	 */
	public SQLQuery(
			final SQLConnectionManager connectionManager, final ResultHandler<E> resultHandler, final String sql
	) throws DataException {
		
		// register this query to connection manager, enable close query if manager is closed
		this.parent = connectionManager;
		connectionManager.register(this);
		
		// prepare SQL statement
		try {
			this.statement = new ParametricStatement(connectionManager.getSource(), sql);
		} catch (SQLException e) {
			throw new DataException("Fail to prepare SQL query statement [{0}]", e, sql);
		}
		this.resultHandler = resultHandler;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.statement.toString();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Query#getSource()
	 */
	@Override
	public Object getSource() {
		return this.statement;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Query#close()
	 */
	@Override
	public void close() {
		
		this.parent.unregister(this);
		try {
			this.statement.close();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to close prepared SQL statement", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Query#setMaxResult(int)
	 */
	@Override
	public Query<E> setMaxResult(final int maxResult) {
		
		try {
			this.statement.setMaxRows(maxResult);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to set max return rows prepared SQL statement", e);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Query#get(java.lang.Object[])
	 */
	@Override
	public E get(final Object... args) {
		
		// set max result rows for query to 1 in order to improve query performance
		int maxRows = 0;
		try {
			maxRows = this.statement.getMaxRows();
			this.statement.setMaxRows(1);
		} catch (SQLException e) {
			maxRows = -1;
		}
		
		// query rows from data source and just return first row if has return
		try {
			List<E> outcome = this.list(args);
			if (outcome.size() == 0) {
				return null;
			} else {
				return outcome.get(0);
			}
		} catch (Throwable e) {
			
			throw new DataRuntimeException("Fail to fetch first row by SQL query [{0}]", e, this.toString());
		} finally {
			
			if (maxRows >= 0) {
				this.setMaxResult(maxRows);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Query#get(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public E get(final String[] names, final Object[] args) {

		// set max result rows for query to 1 in order to improve query performance
		int maxRows = 0;
		try {
			maxRows = this.statement.getMaxRows();
			this.statement.setMaxRows(1);
		} catch (SQLException e) {
			maxRows = -1;
		}
		
		// query rows from data source and just return first row if has return
		try {
			List<E> outcome = this.list(names, args);
			if (outcome.size() == 0) {
				return null;
			} else {
				return outcome.get(0);
			}
		} catch (Throwable e) {
			
			throw new DataRuntimeException("Fail to fetch first row by SQL query [{0}]", e, this.toString());
		} finally {
			
			if (maxRows >= 0) {
				this.setMaxResult(maxRows);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Query#list(java.lang.Object[])
	 */
	@Override
	public List<E> list(final Object... args) {
		
		// set parameter value to query statement before execute query
		int i = 0;
		try {
			this.statement.clearParameters();
			for (int count = args.length; i < count; i++) {
				this.statement.setObject(i + 1, args[i]);
			}
			this.statement.clearWarnings();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to set parameter [{i}] value to SQL query [{0}]", 
					e, i, this.toString()
			);
		}
		
		// execute query and transfer query result to entity list
		ResultSet resultset = null;
		try {
			resultset = this.statement.executeQuery();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to execute SQL query [{0}]", e, this.toString());
		}
		
		return this.resultHandler.toList(new SQLResultHolder(resultset));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Query#list(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public List<E> list(final String[] names, final Object[] args) {
		
		// set named parameter value to query statement before execute query
		int i = 0;
		try {
			this.statement.clearParameters();
			for (int count = names.length; i < count; i++) {
				this.statement.setObject(names[i], args[i]);
			}
			this.statement.clearWarnings();
		} catch (Exception e) {
			throw new DataRuntimeException("Fail to set parameter [{0}] value to SQL query [{1}]", 
					e, names[i], this.toString()
			);
		}

		// execute query and transfer query result to entity list
		ResultSet resultset = null;
		try {
			resultset = this.statement.executeQuery();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to execute SQL query [{0}]", e, this.toString());
		}
		
		return this.resultHandler.toList(new SQLResultHolder(resultset));
	}
}

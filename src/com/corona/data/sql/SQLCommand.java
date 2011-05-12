/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.SQLException;
import java.sql.Statement;

import com.corona.data.Command;
import com.corona.data.DataRuntimeException;

/**
 * <p>An abstract command that is used to batch DELETE, UPDATE or INSERT data to database server by SQL. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLCommand implements Command {

	/**
	 * the prepared and named parameter SQL statement
	 */
	private NamedParameterStatement statement;
	
	/**
	 * @param connectionManager the connection manager for SQL database 
	 * @param sql the SQL statement
	 * @throws SQLException if fail to prepare SQL statement
	 */
	public SQLCommand(final SQLConnectionManager connectionManager, final String sql) throws SQLException {
		this.statement = new NamedParameterStatement(connectionManager.getSource(), sql);
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
	 * @see com.corona.data.Command#getSource()
	 */
	@Override
	public Statement getSource() {
		return this.statement;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#close()
	 */
	@Override
	public void close() {
		
		try {
			this.statement.close();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to close SQL statement [{0}]", this.statement);
		}
	}

	/**
	 * @param args the parameter values
	 */
	private void setPrameters(final Object[] args) {
		
		int i = 0;
		try {
			this.statement.clearParameters();
			for (int count = args.length; i < count; i++) {
				this.statement.setObject(i + 1, args[i]);
			}
			this.statement.clearWarnings();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to set parameter [{i}] value to SQL statement", e, i);
		}
	}

	/**
	 * @param names the parameter names
	 * @param args the parameter values
	 */
	private void setParameters(final String[] names, final Object[] args) {
		
		int i = 0;
		try {
			this.statement.clearParameters();
			for (int count = names.length; i < count; i++) {
				this.statement.setObject(names[i], args[i]);
			}
			this.statement.clearWarnings();
		} catch (Exception e) {
			throw new DataRuntimeException("Fail to set parameter [{0}] value to SQL statement", e, names[i]);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#delete(java.lang.Object[])
	 */
	@Override
	public int delete(final Object... args) {
		
		this.setPrameters(args);
		try {
			return this.statement.executeUpdate();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to execte DELETE SQL [{0}]", e, this.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#delete(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int delete(final String[] names, final Object[] args) {
		
		this.setParameters(names, args);
		try {
			return this.statement.executeUpdate();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to execte DELETE SQL [{0}]", e, this.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#update(java.lang.Object[])
	 */
	@Override
	public int update(final Object... args) {

		this.setPrameters(args);
		try {
			return this.statement.executeUpdate();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to execte UPDATE SQL [{0}]", e, this.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#update(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int update(final String[] names, final Object[] args) {
		
		this.setParameters(names, args);
		try {
			return this.statement.executeUpdate();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to execte UPDATE SQL [{0}]", e, this.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#insert(java.lang.Object[])
	 */
	@Override
	public int insert(final Object... args) {

		this.setPrameters(args);
		try {
			return this.statement.executeUpdate();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to execte INSERT SQL [{0}]", e, this.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#insert(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int insert(final String[] names, final Object[] args) {

		this.setParameters(names, args);
		try {
			return this.statement.executeUpdate();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to execte INSERT SQL [{0}]", e, this.toString());
		}
	}
}

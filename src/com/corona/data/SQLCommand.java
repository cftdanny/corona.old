/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>An abstract command that is used to batch DELETE, UPDATE or INSERT data to database server by SQL. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLCommand implements Command {

	/**
	 * the prepared statement
	 */
	private PreparedStatement statement;

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
		
		try {
			
			this.statement.clearParameters();
			for (int i = 0, count = args.length; i < count; i++) {
				this.statement.setObject(i + 1, args[i]);
			}
			this.statement.clearWarnings();
			
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to set parameter value to SQL statement", e);
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
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#update(java.lang.Object[])
	 */
	@Override
	public int update(Object... args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#update(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int update(String[] names, Object[] args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#insert(java.lang.Object[])
	 */
	@Override
	public int insert(Object... args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#insert(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int insert(String[] names, Object[] args) {
		return 0;
	}
}

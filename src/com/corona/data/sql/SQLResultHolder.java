/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.corona.data.DataRuntimeException;
import com.corona.data.ResultHolder;

/**
 * <p>This extractor is used to extract value by column from JDBC SQL ResultSet. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLResultHolder implements ResultHolder {

	/**
	 * the ResultSet that returns from query
	 */
	private ResultSet resultset;
	
	/**
	 * @param resultset the ResultSet that returns from query
	 */
	SQLResultHolder(final ResultSet resultset) {
		this.resultset = resultset;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		
		try {
			return this.resultset.getMetaData().getColumnCount();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get column count from query result", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getColumnLabels()
	 */
	@Override
	public String[] getColumnLabels() {
		
		try {
			ResultSetMetaData metadata = this.resultset.getMetaData();
			String[] columnLabels = new String[metadata.getColumnCount()];
			
			for (int i = 1, count = metadata.getColumnCount(); i <= count; i++) {
				columnLabels[i - 1] = metadata.getColumnLabel(i);
			}
			return columnLabels;
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get column labels from query result", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#next()
	 */
	@Override
	public boolean next() {
		
		try {
			return this.resultset.next();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to navigate next row in query result", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#get(java.lang.String)
	 */
	@Override
	public Object get(final String column) {
		
		try {
			return this.resultset.getObject(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#get(int)
	 */
	@Override
	public Object get(final int column) {

		try {
			return this.resultset.getObject(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getString(java.lang.String)
	 */
	@Override
	public String getString(final String column) {

		try {
			return this.resultset.getString(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get string value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getString(int)
	 */
	@Override
	public String getString(final int column) {

		try {
			return this.resultset.getString(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get string value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(final String column) {

		try {
			return this.resultset.getInt(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get integer value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getInteger(int)
	 */
	@Override
	public Integer getInteger(final int column) {

		try {
			return this.resultset.getInt(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get integer value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getLong(java.lang.String)
	 */
	@Override
	public Long getLong(final String column) {
		
		try {
			return this.resultset.getLong(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get long value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getLong(int)
	 */
	@Override
	public Long getLong(final int column) {
		
		try {
			return this.resultset.getLong(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get long value from query result by column [{0}]", column, e);
		}
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

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
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getSource()
	 */
	@Override
	public Object getSource() {
		return this.resultset;
	}

	/**
	 * @param resultset the ResultSet that returns from query
	 */
	SQLResultHolder(final ResultSet resultset) {
		this.resultset = resultset;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getCount()
	 */
	@Override
	public int getCount() {
		
		try {
			return this.resultset.getMetaData().getColumnCount();
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get column count from query result", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getColumns()
	 */
	@Override
	public String[] getColumns() {
		
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
	 * @see com.corona.data.ResultHolder#getObject(java.lang.String)
	 */
	@Override
	public Object getObject(final String column) {
		
		try {
			return this.resultset.getObject(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getObject(int)
	 */
	@Override
	public Object getObject(final int column) {

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
	 * @see com.corona.data.ResultHolder#getByte(java.lang.String)
	 */
	@Override
	public Byte getByte(final String column) {

		try {
			return this.resultset.getByte(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get byte value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getByte(int)
	 */
	@Override
	public Byte getByte(final int column) {

		try {
			return this.resultset.getByte(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get byte value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getShort(int)
	 */
	@Override
	public Short getShort(final int column) {

		try {
			return this.resultset.getShort(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get short value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getShort(java.lang.String)
	 */
	@Override
	public Short getShort(final String column) {

		try {
			return this.resultset.getShort(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get short value from query result by column [{0}]", column, e);
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

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getFloat(java.lang.String)
	 */
	@Override
	public Float getFloat(final String column) {

		try {
			return this.resultset.getFloat(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get float value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getFloat(int)
	 */
	@Override
	public Float getFloat(final int column) {

		try {
			return this.resultset.getFloat(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get float value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getDouble(java.lang.String)
	 */
	@Override
	public Double getDouble(final String column) {

		try {
			return this.resultset.getDouble(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get double value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getDouble(int)
	 */
	@Override
	public Double getDouble(final int column) {

		try {
			return this.resultset.getDouble(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get double value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getBoolean(java.lang.String)
	 */
	@Override
	public Boolean getBoolean(final String column) {

		try {
			return this.resultset.getBoolean(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get boolean value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getBoolean(int)
	 */
	@Override
	public Boolean getBoolean(final int column) {

		try {
			return this.resultset.getBoolean(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get boolean value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getDate(java.lang.String)
	 */
	@Override
	public Date getDate(final String column) {

		try {
			return this.resultset.getDate(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get date value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getDate(int)
	 */
	@Override
	public Date getDate(final int column) {

		try {
			return this.resultset.getDate(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get date value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getTimestamp(java.lang.String)
	 */
	@Override
	public Timestamp getTimestamp(final String column) {

		try {
			return this.resultset.getTimestamp(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get timestamp value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getTimestamp(int)
	 */
	@Override
	public Timestamp getTimestamp(final int column) {

		try {
			return this.resultset.getTimestamp(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get timestamp value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getTime(java.lang.String)
	 */
	@Override
	public Time getTime(final String column) {

		try {
			return this.resultset.getTime(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get time value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getTime(int)
	 */
	@Override
	public Time getTime(final int column) {

		try {
			return this.resultset.getTime(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get time value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getURL(java.lang.String)
	 */
	@Override
	public URL getURL(final String column) {

		try {
			return this.resultset.getURL(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get URL value from query result by column [{0}]", column, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getURL(int)
	 */
	@Override
	public URL getURL(final int column) {

		try {
			return this.resultset.getURL(column);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get URL value from query result by column [{0}]", column, e);
		}
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			throw new DataRuntimeException("Fail to get column count from metadata of query result", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#getColumnLabels()
	 */
	@Override
	public String[] getColumnLabels() {
		
		List<String> labels = new ArrayList<String>();
		try {
			ResultSetMetaData metadata = this.resultset.getMetaData();
			for (int i = 1, count = metadata.getColumnCount(); i <= count; i++) {
				labels.add(metadata.getColumnLabel(i));
			}
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get column labels from metadata of query result", e);
		}
		return labels.toArray(new String[0]);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#get(java.lang.String)
	 */
	@Override
	public Object get(final String columnLabel) {
		
		try {
			return this.resultset.getObject(columnLabel);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get value from SQL Result Set by column [{0}]", columnLabel, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHolder#get(int)
	 */
	@Override
	public Object get(final int columnIndex) {

		try {
			return this.resultset.getObject(columnIndex);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get value from SQL Result Set by column [{0}]", columnIndex, e);
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
			throw new DataRuntimeException("Fail to navigate SQL Result Set to next row", e);
		}
	}
}

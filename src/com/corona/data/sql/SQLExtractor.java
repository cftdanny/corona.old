/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.corona.data.DataRuntimeException;
import com.corona.data.Extractor;

/**
 * <p>This extractor is used to extract value by column from JDBC SQL ResultSet. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLExtractor implements Extractor {

	/**
	 * the ResultSet that returns from query
	 */
	private ResultSet resultset;
	
	/**
	 * @param resultset the ResultSet that returns from query
	 */
	SQLExtractor(final ResultSet resultset) {
		this.resultset = resultset;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Extractor#get(java.lang.String)
	 */
	@Override
	public Object get(final String name) {
		
		try {
			return this.resultset.getObject(name);
		} catch (SQLException e) {
			throw new DataRuntimeException("Fail to get value from SQL Result Set by column [{0}]", name, e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Extractor#next()
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

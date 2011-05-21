/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.ResultSet;

import com.corona.data.DataRuntimeException;
import com.corona.data.Dialect;
import com.corona.data.HomeBuilder;
import com.corona.data.ResultHolder;

/**
 * <p>The abstract dialect for SQL database. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class SQLDialect implements Dialect {

	/**
	 * the home builder
	 */
	private SQLHomeBuilder homeBuilder = new SQLHomeBuilder();
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getResultHolder(java.lang.Object)
	 */
	@Override
	public ResultHolder getResultHolder(final Object result) {
		
		if (!(result instanceof ResultSet)) {
			throw new DataRuntimeException("SQL extractor can only extract value from ResultSet");
		}
		return new SQLResultHolder((ResultSet) result);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getHomeBuilder()
	 */
	@Override
	public HomeBuilder getHomeBuilder() {
		return this.homeBuilder;
	}
}

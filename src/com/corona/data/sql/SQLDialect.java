/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.ResultSet;

import com.corona.data.ConnectionManager;
import com.corona.data.Dialect;
import com.corona.data.EntityMetaData;
import com.corona.data.StatementBuilder;
import com.corona.data.ResultHolder;

/**
 * <p>The abstract dialect for SQL database. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class SQLDialect implements Dialect {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#createResultHolder(java.lang.Object)
	 */
	@Override
	public ResultHolder createResultHolder(final Object result) {
		return new SQLResultHolder((ResultSet) result);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#createStatementBuilder(
	 * 	com.corona.data.ConnectionManager, com.corona.data.EntityMetaData
	 * )
	 */
	@Override
	public <E> StatementBuilder<E> createStatementBuilder(
			final ConnectionManager connectionManager, final EntityMetaData<E> metadata
	) {
		return new SQLStatementBuilder<E>(connectionManager, metadata);
	}
}

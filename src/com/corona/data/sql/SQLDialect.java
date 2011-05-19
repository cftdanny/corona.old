/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.sql.ResultSet;

import com.corona.data.DataRuntimeException;
import com.corona.data.Dialect;
import com.corona.data.EntityDeleteBuilder;
import com.corona.data.EntityQueryBuilder;
import com.corona.data.EntityUpdateBuilder;
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
	 * the entity builder builder
	 */
	private SQLEntityQueryBuilder entityQueryBuilder;
	
	/**
	 * the entity delete builder
	 */
	private SQLEntityDeleteBuilder entityDeleteBuilder;

	/**
	 * the entity update builder
	 */
	private SQLEntityUpdateBuilder entityUpdateBuilder;

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

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getEntityQueryBuilder()
	 */
	@Override
	public EntityQueryBuilder getEntityQueryBuilder() {
		
		if (this.entityQueryBuilder == null) {
			this.entityQueryBuilder = new SQLEntityQueryBuilder();
		}
		return this.entityQueryBuilder;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getEntityDeleteBuilder()
	 */
	@Override
	public EntityDeleteBuilder getEntityDeleteBuilder() {
		
		if (this.entityDeleteBuilder == null) {
			this.entityDeleteBuilder = new SQLEntityDeleteBuilder();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getEntityUpdateBuilder()
	 */
	@Override
	public EntityUpdateBuilder getEntityUpdateBuilder() {
		
		if (this.entityUpdateBuilder == null) {
			this.entityUpdateBuilder = new SQLEntityUpdateBuilder();
		}
		return this.entityUpdateBuilder;
	}
}

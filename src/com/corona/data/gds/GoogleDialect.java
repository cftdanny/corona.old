/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.gds;

import java.sql.Timestamp;
import java.util.Date;

import com.corona.data.Command;
import com.corona.data.Dialect;
import com.corona.data.EntityDeleteBuilder;
import com.corona.data.EntityQueryBuilder;
import com.corona.data.EntityUpdateBuilder;
import com.corona.data.HomeBuilder;
import com.corona.data.ResultHolder;

/**
 * <p>This dialect is used for Google Datastore Service. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class GoogleDialect implements Dialect {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getEntityQueryBuilder()
	 */
	@Override
	public EntityQueryBuilder getEntityQueryBuilder() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getEntityDeleteBuilder()
	 */
	@Override
	public EntityDeleteBuilder getEntityDeleteBuilder() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getEntityUpdateBuilder()
	 */
	@Override
	public EntityUpdateBuilder getEntityUpdateBuilder() {
		return null;
	}

	/**
	 * the Google Datastore connection manager
	 */
	private GoogleConnectionManager connectionManager;
	
	/**
	 * @param connectionManager the Google Datastore connection manager
	 */
	GoogleDialect(final GoogleConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getCurrentTime()
	 */
	@Override
	public Timestamp getCurrentTime() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getGeneratedKeys(com.corona.data.Command)
	 */
	@Override
	public Object[] getGeneratedKeys(final Command command) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getResultHolder(java.lang.Object)
	 */
	@Override
	public ResultHolder getResultHolder(Object result) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getHomeBuilder()
	 */
	@Override
	public HomeBuilder getHomeBuilder() {
		return null;
	}
}

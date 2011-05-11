/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.gds;

import java.sql.Timestamp;
import java.util.Date;

import com.corona.data.Command;
import com.corona.data.Dialect;

/**
 * <p>This dialect is used for Google Datastore Service. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class GoogleDialect implements Dialect {

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
		return new Timestamp(new Date().getTime());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getGeneratedKeys(com.corona.data.Command)
	 */
	@Override
	public Object[] getGeneratedKeys(final Command command) {
		return null;
	}
}

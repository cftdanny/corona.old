/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.gds;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.Query;
import com.google.appengine.api.datastore.DatastoreService;

/**
 * <p>This connection manager is used to manage data in Google Datastore Service. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class GoogleConnectionManager implements ConnectionManager {

	/**
	 * the connection manager factory for Google Datastore
	 */
	private GoogleConnectionManagerFactory connectionManagerFactory;
	
	/**
	 * the Google Datastore dialect 
	 */
	private GoogleDialect dialect = new GoogleDialect(this);
	
	/**
	 * the Google Datastore Service
	 */
	private DatastoreService datastore;
	
	/**
	 * @param factory the connection manager factory for Google Datastore
	 * @param datastore the Google Datastore Service
	 */
	GoogleConnectionManager(final GoogleConnectionManagerFactory factory, final DatastoreService datastore) {
		this.connectionManagerFactory = factory;
		this.datastore = datastore;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getConnectionManagerFactory()
	 */
	@Override
	public GoogleConnectionManagerFactory getConnectionManagerFactory() {
		return this.connectionManagerFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getDialect()
	 */
	@Override
	public GoogleDialect getDialect() {
		return this.dialect;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getSource()
	 */
	@Override
	public Object getSource() {
		return this.datastore;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#close()
	 */
	@Override
	public void close() {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createQuery(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T> Query<T> createQuery(Class<? extends T> entityClass, String query) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedQuery(java.lang.Class)
	 */
	@Override
	public <T> Query<T> createNamedQuery(Class<? extends T> entityClass) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createCommand()
	 */
	@Override
	public Command createCommand() {
		return null;
	}
}

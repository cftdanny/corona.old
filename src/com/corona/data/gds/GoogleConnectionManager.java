/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.gds;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;
import com.corona.data.Dialect;
import com.corona.data.Query;
import com.corona.data.ResultHandler;
import com.corona.data.Transaction;
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
	public ConnectionManagerFactory getConnectionManagerFactory() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getDialect()
	 */
	@Override
	public Dialect getDialect() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getSource()
	 */
	@Override
	public Object getSource() {
		return null;
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
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#getTransaction()
	 */
	@Override
	public Transaction getTransaction() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createQuery(java.lang.Class, java.lang.String)
	 */
	@Override
	public <E> Query<E> createQuery(Class<E> resultClass, String query) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createQuery(com.corona.data.ResultHandler, java.lang.String)
	 */
	@Override
	public <E> Query<E> createQuery(ResultHandler<E> resultHandler, String query) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedQuery(java.lang.Class)
	 */
	@Override
	public <E> Query<E> createNamedQuery(Class<E> resultClass) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedQuery(java.lang.Class, java.lang.String)
	 */
	@Override
	public <E> Query<E> createNamedQuery(Class<E> resultClass, String name) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createCommand(java.lang.String)
	 */
	@Override
	public Command createCommand(String command) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedCommand(java.lang.Class)
	 */
	@Override
	public Command createNamedCommand(Class<?> commandClass) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ConnectionManager#createNamedCommand(java.lang.Class, java.lang.String)
	 */
	@Override
	public Command createNamedCommand(Class<?> commandClass, String name) {
		return null;
	}
}

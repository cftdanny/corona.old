/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.gds;

import com.corona.data.Command;
import com.google.appengine.api.datastore.DatastoreService;

/**
 * <p>The command for Google Datastore Service that can be used to execute batch DELETE, UPDATE command. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class GoogleCommand implements Command {

	/**
	 * the Google Datastore Service
	 */
	private DatastoreService datastore;
	
	/**
	 * @param datastore the Google Datastore Service
	 */
	GoogleCommand(final DatastoreService datastore) {
		this.datastore = datastore;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#getSource()
	 */
	@Override
	public Object getSource() {
		return this.datastore;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#close()
	 */
	@Override
	public void close() {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#delete(java.lang.Object[])
	 */
	@Override
	public int delete(final Object... args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#delete(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int delete(String[] names, Object[] args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#update(java.lang.Object[])
	 */
	@Override
	public int update(Object... args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#update(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int update(String[] names, Object[] args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#insert(java.lang.Object[])
	 */
	@Override
	public int insert(Object... args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#insert(java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int insert(String[] names, Object[] args) {
		return 0;
	}
}

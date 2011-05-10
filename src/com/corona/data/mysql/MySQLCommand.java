/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import java.sql.PreparedStatement;
import java.sql.Statement;

import com.corona.data.Command;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
class MySQLCommand implements Command {

	/**
	 * the prepared MySQL statement
	 */
	private PreparedStatement preparedStatement;

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#getSource()
	 */
	@Override
	public PreparedStatement getSource() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#close()
	 */
	@Override
	public void close() {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Command#delete(java.lang.Object[])
	 */
	@Override
	public int delete(Object... args) {
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

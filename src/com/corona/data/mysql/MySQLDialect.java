/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.mysql;

import java.sql.Timestamp;

import com.corona.data.Dialect;

/**
 * <p>The dialect for MySQL Server. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class MySQLDialect implements Dialect {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getGeneratedId()
	 */
	@Override
	public Object getGeneratedId() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Dialect#getTime()
	 */
	@Override
	public Timestamp getTime() {
		return null;
	}
}

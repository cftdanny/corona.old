/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.EventObject;

/**
 * <p>The event to indicate a SQL query will be closed </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLQueryCloseEvent extends EventObject {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2759686146679396891L;

	/**
	 * @param query the SQL query
	 */
	public SQLQueryCloseEvent(final SQLQuery<?> query) {
		super(query);
		
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.EventObject#getSource()
	 */
	@Override
	public SQLQuery<?> getSource() {
		return (SQLQuery<?>) super.getSource();
	}
}

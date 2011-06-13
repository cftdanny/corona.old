/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.EventObject;

/**
 * <p>This event is used to indicate an SQLCommand will be closed </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLCommandCloseEvent extends EventObject {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 4394371432268032260L;

	/**
	 * @param command the SQL command to be closed
	 */
	public SQLCommandCloseEvent(final SQLCommand command) {
		super(command);
	}

	/**
	 * {@inheritDoc}
	 * @see java.util.EventObject#getSource()
	 */
	@Override
	public SQLCommand getSource() {
		return (SQLCommand) super.getSource();
	}
}

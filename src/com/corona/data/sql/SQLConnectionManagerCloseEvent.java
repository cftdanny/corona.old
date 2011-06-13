/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.EventObject;

/**
 * <p>This event is used to query whether SQL connection manager can be closed or not. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLConnectionManagerCloseEvent extends EventObject {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1798598888361982326L;

	/**
	 * whether allow SQL connection manager to close 
	 */
	private boolean cancel = false;
	
	/**
	 * @param connectionManager the SQL connection manager
	 */
	SQLConnectionManagerCloseEvent(final SQLConnectionManager connectionManager) {
		super(connectionManager);
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.util.EventObject#getSource()
	 */
	@Override
	public SQLConnectionManager getSource() {
		return (SQLConnectionManager) super.getSource();
	}

	/**
	 * @return whether allow SQL connection manager to close
	 */
	public boolean isCancel() {
		return cancel;
	}
	
	/**
	 * @param cancel whether allow SQL connection manager to close
	 */
	public void setCancel(final boolean cancel) {
		this.cancel = cancel;
	}
}

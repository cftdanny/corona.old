/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.Vector;

/**
 * <p>This class is used to support listener for SQL connection manager. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SQLQueryCloseListenerSupport {

	/**
	 * all listeners
	 */
	private Vector<SQLQueryCloseListener> listeners = new Vector<SQLQueryCloseListener>();
	
	/**
	 * @param listener the listener to be added
	 */
	void add(final SQLQueryCloseListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * @param listener the listener to be removed
	 */
	void remove(final SQLQueryCloseListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * @param event the event to be fired
	 */
	void fire(final SQLQueryCloseEvent event) {
		
		for (SQLQueryCloseListener listener : this.listeners) {
			listener.close(event);
		}
	}
}

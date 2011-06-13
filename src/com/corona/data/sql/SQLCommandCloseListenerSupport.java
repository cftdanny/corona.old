/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.Vector;

/**
 * <p>The support class for SQL command closing listener </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SQLCommandCloseListenerSupport {

	/**
	 * all listeners
	 */
	private Vector<SQLCommandCloseListener> listeners = new Vector<SQLCommandCloseListener>();
	
	/**
	 * @param listener the listener to be added
	 */
	void add(final SQLCommandCloseListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * @param listener the listener to be removed
	 */
	void remove(final SQLCommandCloseListener listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * @param event the event to be fired
	 */
	void fire(final SQLCommandCloseEvent event) {
		
		for (SQLCommandCloseListener listener : this.listeners) {
			listener.close(event);
		}
	}
}

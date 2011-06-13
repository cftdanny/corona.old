/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

/**
 * <p>Listener to listen SQL query will be closed </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface SQLQueryCloseListener {

	/**
	 * @param event the event that SQL query will be closed
	 */
	void close(SQLQueryCloseEvent event);
}

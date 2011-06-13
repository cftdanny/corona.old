/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.EventListener;

/**
 * <p>This listener is used to listen SQL command will be closed </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface SQLCommandCloseListener extends EventListener {

	/**
	 * @param event the event that SQL command will be closed
	 */
	void close(SQLCommandCloseEvent event);
}

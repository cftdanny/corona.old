/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.EventListener;

/**
 * <p>This listener is used to listen the event about SQL connection will be closed. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface SQLConnectionManagerCloseListener extends EventListener {

	/**
	 * @param event the event
	 */
	void close(final SQLConnectionManagerCloseEvent event);
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>This interface is used to indicate that component should be closed when its scope is closed. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Closeable {

	/**
	 * close this component when its parent is closed
	 */
	void close();
}

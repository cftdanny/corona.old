/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This factory is used to create {@link ConnectionManager}. Normally, it is created by {@DataSourceManager}
 * with data source configuration.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ConnectionManagerFactory {

	/**
	 * @return the dialect of data source
	 */
	Dialect getDialect();
	
	/**
	 * @return the new {@link ConnectionManager}
	 * @throws DataException if fail to create connection manager
	 */
	ConnectionManager open() throws DataException;
}

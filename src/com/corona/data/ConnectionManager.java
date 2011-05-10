/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>The connection manager is used to manage data source, for example: query, update or delete data
 * in data source. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ConnectionManager {

	<T> Query<T> createQuery(Class<? extends T> entityClass, String query);

	<T> Query<T> createNamedQuery(Class<? extends T> entityClass);
	
	Command createCommand();
}

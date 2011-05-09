/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ConnectionManager {

	<T> Query<T> createQuery(Class<? extends T> entityClass, String query);

	<T> Query<T> createNamedQuery(Class<? extends T> entityClass);
	
	Command createCommand();
}

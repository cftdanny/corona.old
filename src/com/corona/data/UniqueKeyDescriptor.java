/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This definition is used to store and create unique key information. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface UniqueKeyDescriptor<E> {

	/**
	 * @return the id of unique key
	 */
	Integer getId();
	
	/**
	 * @param connectionManager the current connection manager
	 * @return the new unique key
	 */
	UniqueKey<E> createUniqueKey(ConnectionManager connectionManager);
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This descriptor is used to store index configuration about an entity. It is also used to 
 * create {@link UniqueKey}.
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface IndexDescriptor<E> {

	/**
	 * @return the id of index
	 */
	Integer getId();
	
	/**
	 * @param connectionManager current connection manager
	 * @return the new index
	 */
	Index<E> create(final ConnectionManager connectionManager);
}

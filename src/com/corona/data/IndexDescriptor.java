/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>The index descriptor for an entity class that is defined by {@link Index} annotation. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface IndexDescriptor<E> {

	/**
	 * @return the id of index
	 */
	int getId();
	
	/**
	 * @param connectionManager current connection manager
	 * @return the new index
	 */
	Index<E> createIndex(final ConnectionManager connectionManager);
}

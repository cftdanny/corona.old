/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This descriptor is used to store unique key configuration about an entity. It is also used to 
 * create {@link UniqueKey}.
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public interface UniqueKeyDescriptor<E> {

	/**
	 * @return the id of unique key
	 */
	Integer getId();
	
	/**
	 * <p>create {@link UniqueKey} by connection manager and this unique key descriptor.
	 * </p>
	 * 
	 * @param connectionManager the current connection manager
	 * @return the new unique key
	 */
	UniqueKey<E> create(ConnectionManager connectionManager);
}

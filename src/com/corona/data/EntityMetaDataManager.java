/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This manager is used to get {@link EntityMetaData} configuration by entity class. In order to
 * improve performance, the created {@link EntityMetaData} can be cached. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface EntityMetaDataManager {

	/**
	 * @param <E> the type of entity class
	 * @param entityClass the entity class
	 * @return the {@link EntityMetaData}
	 */
	<E> EntityMetaData<E> getEntityMetaData(Class<E> entityClass);
}

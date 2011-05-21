/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.HashMap;
import java.util.Map;

import com.corona.data.EntityMetaData;
import com.corona.data.EntityMetaDataRepository;

/**
 * <p>The helper class for {@link EntityMetaDataRepository}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLEntityMetaDataManager implements EntityMetaDataRepository {

	/**
	 * the cached storage for {@link EntityMetaData}
	 */
	private Map<Class<?>, EntityMetaData<?>> storage = new HashMap<Class<?>, EntityMetaData<?>>();
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityMetaDataRepository#getEntityMetaData(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> EntityMetaData<E> getEntityMetaData(final Class<E> entityClass) {
		
		EntityMetaData<E> config = (EntityMetaData<E>) this.storage.get(entityClass);
		if (config == null) {
			config = new SQLEntityMetaData<E>(entityClass);
			this.storage.put(entityClass, config);
		}
		return config;
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.HashMap;
import java.util.Map;

import com.corona.data.AbstractEntityDescriptor;
import com.corona.data.IndexDescriptor;
import com.corona.data.PrimaryKeyDescriptor;
import com.corona.data.UniqueKeyDescriptor;

/**
 * <p>This class is used to store table definition according to annotation that is annotated in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
class SQLEntityDescriptor<E> extends AbstractEntityDescriptor<E> {

	/**
	 * the primary key
	 */
	private SQLPrimaryKeyDescriptor<E> primaryKey;
	
	/**
	 * all unique keys
	 */
	private Map<Integer, SQLUniqueKeyDescriptor<E>> uniqueKeys = new HashMap<Integer, SQLUniqueKeyDescriptor<E>>();

	/**
	 * all unique keys
	 */
	private Map<Integer, SQLIndexDescriptor<E>> indexes = new HashMap<Integer, SQLIndexDescriptor<E>>();

	/**
	 * @param entityClass the entity class
	 */
	SQLEntityDescriptor(final Class<E> entityClass) {
		super(entityClass);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDescriptor#getPrimarykey()
	 */
	@Override
	public PrimaryKeyDescriptor<E> getPrimarykey() {
		return this.primaryKey;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDescriptor#getUniqueKey(int)
	 */
	@Override
	public UniqueKeyDescriptor<E> getUniqueKey(final int id) {
		return this.uniqueKeys.get(id);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDescriptor#getIndex(int)
	 */
	@Override
	public IndexDescriptor<E> getIndex(final int id) {
		return this.indexes.get(id);
	}
}

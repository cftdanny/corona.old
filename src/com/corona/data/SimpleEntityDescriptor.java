/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public class SimpleEntityDescriptor<E> extends AbstractEntityDescriptor<E> {

	/**
	 * @param entityClass the entity class
	 */
	public SimpleEntityDescriptor(final Class<E> entityClass) {
		super(entityClass);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDescriptor#getPrimarykey()
	 */
	@Override
	public PrimaryKeyDescriptor<E> getPrimarykey() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDescriptor#getUniqueKey(int)
	 */
	@Override
	public UniqueKeyDescriptor<E> getUniqueKey(final int id) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDescriptor#getIndex(int)
	 */
	@Override
	public IndexDescriptor<E> getIndex(final int id) {
		return null;
	}
}

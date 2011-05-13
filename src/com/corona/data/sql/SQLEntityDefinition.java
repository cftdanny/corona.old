/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.PrimaryKeyDefinition;
import com.corona.data.EntityDefinition;
import com.corona.data.annotation.Entity;

/**
 * <p>This class is used to store table definition according to annotation that is annotated in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
class SQLEntityDefinition<E> implements EntityDefinition<E> {

	/**
	 * the entity class
	 */
	private Entity entity;
	
	/**
	 * the primary key
	 */
	private SQLPrimaryKeyDefinition<E> primaryKey;
	
	/**
	 * @param entity the entity class
	 */
	SQLEntityDefinition(final Entity entity) {
		this.entity = entity;
		
		this.primaryKey = new SQLPrimaryKeyDefinition<E>(this.entity, entity.primaryKey());
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDefinition#getPrimarykey()
	 */
	@Override
	public PrimaryKeyDefinition<E> getPrimarykey() {
		return this.primaryKey;
	}

}

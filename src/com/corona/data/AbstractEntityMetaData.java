/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import com.corona.data.annotation.Entity;

/**
 * <p>This helper class will find all fields and properties that can map to columns of query result. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public abstract class AbstractEntityMetaData<E> extends AbstractResultMetaData<E> implements EntityMetaData<E> {

	/**
	 * the name, if SQL data source, it is table name
	 */
	private String name = "";
	
	/**
	 * @param entityClass the entity class
	 */
	public AbstractEntityMetaData(final Class<E> entityClass) {

		// create parent result meta data first
		super(entityClass);
		
		// find Entity annotation that is annotated to entity class
		Entity entity = this.getMappingClass().getAnnotation(Entity.class);
		if (entity != null) {
			this.name = entity.name();
		}
		
		// if entity name is empty, will use entity class name
		if (this.name.trim().length() == 0) {
			this.name = this.getMappingClass().getSimpleName();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityMetaData#getName()
	 */
	public String getName() {
		return name;
	}
}

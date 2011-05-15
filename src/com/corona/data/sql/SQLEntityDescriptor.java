/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.corona.data.ColumnDescriptor;
import com.corona.data.DataRuntimeException;
import com.corona.data.FieldColumnDescriptor;
import com.corona.data.IndexDescriptor;
import com.corona.data.PrimaryKeyDescriptor;
import com.corona.data.EntityDescriptor;
import com.corona.data.UniqueKeyDescriptor;
import com.corona.data.annotation.Column;
import com.corona.data.annotation.Entity;
import com.corona.data.annotation.Entity.MappingBy;
import com.corona.data.annotation.Index;
import com.corona.data.annotation.UniqueKey;

/**
 * <p>This class is used to store table definition according to annotation that is annotated in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
class SQLEntityDescriptor<E> implements EntityDescriptor<E> {

	/**
	 * the schema name
	 */
	private String schema;
	
	/**
	 * the entity name
	 */
	private String name;
	
	/**
	 * the identity descriptor
	 */
	private ColumnDescriptor<E> identity;
	
	/**
	 * all columns
	 */
	private Map<String, ColumnDescriptor<E>> columns = new HashMap<String, ColumnDescriptor<E>>();
	
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
	 * @param clazz the entity class
	 * @param entity the annotation that is annotated to entity class
	 */
	SQLEntityDescriptor(final Class<E> clazz, final Entity entity) {
		
		this.schema = entity.schema();
		this.name = (entity.name().trim().length() == 0) ? clazz.getSimpleName() : entity.name();
		
		// find primary key, unique keys and indexes that are defined for entity
		this.primaryKey = new SQLPrimaryKeyDescriptor<E>(clazz, entity, entity.primaryKey());
		for (UniqueKey uniqueKey : entity.uniqueKeys()) {
			
			SQLUniqueKeyDescriptor<E> descriptor = new SQLUniqueKeyDescriptor<E>(clazz, entity, uniqueKey);
			if (this.uniqueKeys.containsKey(descriptor.getId())) {
				throw new DataRuntimeException("Unique key [{0}] defined for entity [{0}] already exists",
						descriptor.getId(), clazz
				);
			}
			this.uniqueKeys.put(descriptor.getId(), descriptor);
		}
		for (Index index : entity.indexes()) {
			
			SQLIndexDescriptor<E> descriptor = new SQLIndexDescriptor<E>(entity, index);
			if (this.indexes.containsKey(descriptor.getId())) {
				throw new DataRuntimeException("Index [{0}] defined for entity [{0}] already exists",
						descriptor.getId(), clazz
				);
			}
			this.indexes.put(descriptor.getId(), descriptor);
		}
		
		// find all mapping fields in entity class
		for (Field field : clazz.getDeclaredFields()) {
		
			if ((entity.mappingBy() == MappingBy.FIELD) || field.isAnnotationPresent(Column.class)) {
				ColumnDescriptor<E> column = new FieldColumnDescriptor<E>(field);
				if (this.columns.containsKey(column.getName())) {
					throw new DataRuntimeException("Column [{0}] defined in field [{0}] already exists", 
							column.getName(), field
					);
				}
				this.columns.put(column.getName(), column);
			}
		}
		
		// check whether there is an ID column
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

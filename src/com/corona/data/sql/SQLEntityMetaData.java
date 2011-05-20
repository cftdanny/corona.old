/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.corona.data.AbstractEntityMetaData;
import com.corona.data.ColumnDescriptor;
import com.corona.data.DataRuntimeException;
import com.corona.data.IndexDescriptor;
import com.corona.data.PrimaryKeyDescriptor;
import com.corona.data.UniqueKeyDescriptor;
import com.corona.data.annotation.Entity;
import com.corona.data.annotation.Id;
import com.corona.data.annotation.Index;
import com.corona.data.annotation.UniqueKey;

/**
 * <p>This class is used to store table definition according to annotation that is annotated in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
class SQLEntityMetaData<E> extends AbstractEntityMetaData<E> {

	/**
	 * the identity column descriptor
	 */
	private ColumnDescriptor<E> identityDescriptor;
	
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
	SQLEntityMetaData(final Class<E> entityClass) {
		super(entityClass);
		
		// get Entity annotation from entity class
		Entity entity = this.getType().getAnnotation(Entity.class);
		if (entity == null) {
			throw new DataRuntimeException("Class [{0)] should be annotated with @Entity", this.getType());
		}
		
		// create primary key descriptor, unique key descriptors and index descriptor
		this.primaryKey = new SQLPrimaryKeyDescriptor<E>(this, entity.primaryKey());
		
		for (UniqueKey uniqueKey : entity.uniqueKeys()) {
			
			SQLUniqueKeyDescriptor<E> descriptor = new SQLUniqueKeyDescriptor<E>(this, uniqueKey);
			if (this.uniqueKeys.containsKey(descriptor.getId())) {
				throw new DataRuntimeException("Unique key [{0}] for entity [{1}] already exists", 
						descriptor.getId(), this.getType()
				);
			}
			this.uniqueKeys.put(descriptor.getId(), descriptor);
		}
		
		for (Index index : entity.indexes()) {
			
			SQLIndexDescriptor<E> descriptor = new SQLIndexDescriptor<E>(this, index);
			if (this.indexes.containsKey(descriptor.getId())) {
				throw new DataRuntimeException("Index [{0}] for entity [{1}] already exists", 
						descriptor.getId(), this.getType()
				);
			}
			this.indexes.put(descriptor.getId(), descriptor);
		}
		
		// find identity column if it is defined in all columns descriptor
		for (ColumnDescriptor<E> descriptor : this.getColumnDescriptors().values()) {
			if (descriptor.getAnnotation(Id.class) != null) {
				if (this.identityDescriptor != null) {
					throw new DataRuntimeException("More than one Id column is defined in entity [{0}]", 
							this.getType()
					);
				}
				this.identityDescriptor = descriptor;
			}
		}
		
		// if there is identity column, check primary key is identity column
		if (this.identityDescriptor != null) {
			
			List<ColumnDescriptor<E>> descriptors = this.primaryKey.getPrimaryKeyColumnDescriptors();
			if ((descriptors.size() != 1) || (!this.identityDescriptor.equals(descriptors.get(0)))) {
				throw new DataRuntimeException("Primary key must be same as identity column in entity [{0}]", 
						this.getType()
				);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityMetaData#getIdentityDescriptor()
	 */
	@Override
	public ColumnDescriptor<E> getIdentityDescriptor() {
		return this.identityDescriptor;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityMetaData#getPrimarykey()
	 */
	@Override
	public PrimaryKeyDescriptor<E> getPrimarykey() {
		return this.primaryKey;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityMetaData#getUniqueKey(int)
	 */
	@Override
	public UniqueKeyDescriptor<E> getUniqueKey(final int id) {
		return this.uniqueKeys.get(id);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityMetaData#getIndex(int)
	 */
	@Override
	public IndexDescriptor<E> getIndex(final int id) {
		return this.indexes.get(id);
	}
}

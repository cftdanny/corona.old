/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This class is used to store table definition by an annotation that is annotated in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public interface EntityDescriptor<E> {

	/**
	 * @return the entity class
	 */
	Class<E> getEntityClass();
	
	/**
	 * @return all columns descriptors for entity
	 */
	ColumnDescriptor<E>[] getColumnDescriptors(); 
	
	/**
	 * @return the primary key definition
	 */
	PrimaryKeyDescriptor<E> getPrimarykey(); 
	
	/**
	 * @param id the unique key name
	 * @return the unique key
	 */
	UniqueKeyDescriptor<E> getUniqueKey(int id);
	
	/**
	 * @param id the index name
	 * @return the index
	 */
	IndexDescriptor<E> getIndex(int id);
}

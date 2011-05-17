/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This class is used to store Meta Data information in order to map rows of query result from data source
 * to instances of mapping to result class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public interface ResultMetaData<E> {

	/**
	 * @return the class that query result will map to
	 */
	Class<E> getMappingClass();
	
	/**
	 * @return the column descriptor if it is annotated with Id annotation
	 */
	ColumnDescriptor<E> getIdColumnDescriptor();
	
	/**
	 * @return the map to column descriptors that are defined in result class
	 */
	ColumnDescriptor<E>[] getColumnDescriptors(); 
}

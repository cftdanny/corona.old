/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.lang.annotation.Annotation;

/**
 * <p>This descriptor is used to map column of data source to field or property of entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public interface ColumnDescriptor<E> {

	/**
	 * @return the column name
	 */
	String getName();
	
	/**
	 * @return the type of field or property in entity class
	 */
	Class<?> getType();
	
	/**
	 * @param <A> the type of annotation
	 * @param annotationType the annotation type
	 * @return the annotation
	 */
	<A extends Annotation> A getAnnotation(Class<A> annotationType);
	
	/**
	 * @param entity the entity instance
	 * @return the value of column that get from entity instance
	 */
	Object get(E entity);
	
	/**
	 * @param entity the entity instance
	 * @param value the value to be set to entity
	 */
	void set(E entity, Object value);
	
	/**
	 * @param entity the entity instance
	 * @param resultHolder the query result
	 */
	void set(E entity, ResultHolder resultHolder);
}

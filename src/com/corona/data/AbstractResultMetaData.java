/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.corona.data.annotation.Column;
import com.corona.data.annotation.Entity;
import com.corona.data.annotation.Transient;
import com.corona.data.annotation.Entity.MappingBy;

/**
 * <p>This helper class is used to find mapping configuration from a class by annotation in it. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of result class that query result can be mapped to
 */
public abstract class AbstractResultMetaData<E> implements ResultMetaData<E> {

	/**
	 * the result class
	 */
	private Class<E> resultClass;
	
	/**
	 * all column descriptors
	 */
	private List<ColumnDescriptor<E>> columnDescriptors;
	
	/**
	 * @param resultClass the result class that query result can be mapped to
	 */
	public AbstractResultMetaData(final Class<E> resultClass) {
		
		// find whether map query result to field or property by Entity annotation, default is property
		this.resultClass = resultClass;
		Entity entity = this.resultClass.getAnnotation(Entity.class);
		
		MappingBy mapping = MappingBy.PROPERTY; 
		if (entity != null) {
			mapping = entity.mappingBy();
		}
		
		// get bean info by entity class
		BeanInfo beaninfo = null;
		try {
			beaninfo = Introspector.getBeanInfo(this.resultClass);
		} catch (IntrospectionException e) {
			throw new DataRuntimeException("Fail to get bean info with entity class [{0}]", e, this.resultClass);
		}

		// find all properties (getter and setter) if it will map to column in query result
		for (PropertyDescriptor property : beaninfo.getPropertyDescriptors()) {
			
			if (this.isMappingColumn(property, mapping)) {
				MethodColumnDescriptor<E> descriptor = new MethodColumnDescriptor<E>(property);
				this.columnDescriptors.add(descriptor);
			}
		}

		// find all fields if it will map to column in query result
		for (Field field : this.resultClass.getDeclaredFields()) {
			
			if (this.isMappingColumn(field, mapping)) {
				FieldColumnDescriptor<E> descriptor = new FieldColumnDescriptor<E>(field);
				this.columnDescriptors.add(descriptor);
			}
		}
	}
	
	/**
	 * @param property the property
	 * @param mapping map column to field or property
	 * @return whether this property is used to map column of query result
	 */
	private boolean isMappingColumn(final PropertyDescriptor property, final MappingBy mapping) {
		
		// FALSE - if it is index property, do not support for query result mapping
		if (property instanceof IndexedPropertyDescriptor) {
			return false;
		}
		
		// FALSE - if there is not read method for this property, do not support this pattern 
		Method readMethod = property.getReadMethod();
		if (readMethod == null) {
			return false;
		}
		// TRUE - if read method is annotated with Column annotation, use it as mapping
		if (readMethod.isAnnotationPresent(Column.class)) {
			return true;
		}
		// FALSE - if read method is annotated with Transient, do not use it as mapping
		if (property.getReadMethod().isAnnotationPresent(Transient.class)) {
			return false;
		}
		
		// according to default and Entity annotation, test whether can be used as mapping
		return MappingBy.PROPERTY == mapping;
	}
	
	/**
	 * @param field the field 
	 * @param mapping map column to field or property
	 * @return whether this field is used to map column of query result
	 */
	private boolean isMappingColumn(final Field field, final MappingBy mapping) {
		
		// TRUE - if field is annotated with Column, use it as mapping
		if (field.isAnnotationPresent(Column.class)) {
			return true;
		}
		// FALSE - if field is annotated with Transient, do not use it as mapping
		if (field.isAnnotationPresent(Transient.class)) {
			return false;
		}
		
		// according to default and Entity annotation, test whether can be used as mapping
		return MappingBy.FIELD == mapping;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultMetaData#getResultClass()
	 */
	@Override
	public Class<E> getResultClass() {
		return this.resultClass;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultMetaData#getColumnDescriptors()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ColumnDescriptor<E>[] getColumnDescriptors() {
		return (ColumnDescriptor<E>[]) this.columnDescriptors.toArray();
	}
}

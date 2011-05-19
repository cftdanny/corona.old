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
import java.util.HashMap;
import java.util.Map;

import com.corona.data.annotation.Column;
import com.corona.data.annotation.Entity;
import com.corona.data.annotation.Id;
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
	 * the class that is used to map row of query result
	 */
	private Class<E> type;
	
	/**
	 * all column descriptors
	 */
	private Map<String, ColumnDescriptor<E>> columnDescriptors = new HashMap<String, ColumnDescriptor<E>>();
	
	/**
	 * @param resultClass the result class that query result can be mapped to
	 */
	public AbstractResultMetaData(final Class<E> resultClass) {
		
		// find whether map query result to field or property by Entity annotation, default is property
		this.type = resultClass;
		Entity entity = this.type.getAnnotation(Entity.class);
		
		// find how to map column to bean property or field
		MappingBy mapping = (entity != null) ? entity.mappingBy() : MappingBy.PROPERTY; 
		
		// get bean info by entity class
		BeanInfo beaninfo = null;
		try {
			beaninfo = Introspector.getBeanInfo(this.type);
		} catch (IntrospectionException e) {
			throw new DataRuntimeException("Fail to get bean info with entity [{0}]", e, this.type);
		}

		// find all properties (getter and setter) if it will map to column in query result
		for (PropertyDescriptor property : beaninfo.getPropertyDescriptors()) {
			
			if (this.isMappingColumn(property, mapping)) {
				PropertyColumnDescriptor<E> descriptor = new PropertyColumnDescriptor<E>(property);
				this.columnDescriptors.put(descriptor.getName(), descriptor);
			}
		}

		// find all fields if it will map to column in query result
		for (Field field : this.type.getDeclaredFields()) {
			
			if (this.isMappingColumn(field, mapping)) {
				FieldColumnDescriptor<E> descriptor = new FieldColumnDescriptor<E>(field);
				this.columnDescriptors.put(descriptor.getName(), descriptor);
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
		// TRUE - if read method is annotated with Column or Id annotation, use it as mapping
		if (readMethod.isAnnotationPresent(Column.class)) {
			return true;
		}
		if (readMethod.isAnnotationPresent(Id.class)) {
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
		
		// TRUE - if field is annotated with Column or Id annotation, use it as mapping
		if (field.isAnnotationPresent(Column.class)) {
			return true;
		}
		if (field.isAnnotationPresent(Id.class)) {
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
	 * @see com.corona.data.ResultMetaData#getType()
	 */
	@Override
	public Class<E> getType() {
		return this.type;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultMetaData#getColumnDescriptor(java.lang.String)
	 */
	@Override
	public ColumnDescriptor<E> getColumnDescriptor(final String columnLabel) {
		return this.columnDescriptors.get(columnLabel.toUpperCase());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultMetaData#getColumnDescriptors()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ColumnDescriptor<E>[] getColumnDescriptors() {
		return (ColumnDescriptor<E>[]) this.columnDescriptors.values().toArray();
	}
}

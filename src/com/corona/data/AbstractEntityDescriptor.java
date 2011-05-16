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
import com.corona.data.annotation.Entity.MappingBy;
import com.corona.data.annotation.Id;
import com.corona.data.annotation.Transient;

/**
 * <p>This helper class will find all fields and properties that can map to columns of query result. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public abstract class AbstractEntityDescriptor<E> implements EntityDescriptor<E> {

	/**
	 * the entity class
	 */
	private Class<E> entityClass;

	/**
	 * the schema, if SQL data source, it is database schema 
	 */
	private String schema = "";
	
	/**
	 * the name, if SQL data source, it is table name
	 */
	private String name = "";
	
	/**
	 * the identity descriptor
	 */
	private ColumnDescriptor<E> identityColumnDescriptor;
	
	/**
	 * all columns
	 */
	private Map<String, ColumnDescriptor<E>> columnDescriptors = new HashMap<String, ColumnDescriptor<E>>();

	/**
	 * @param entityClass the entity class
	 */
	public AbstractEntityDescriptor(final Class<E> entityClass) {

		this.entityClass = entityClass;

		// find Entity annotation that is annotated to entity class
		Entity entity = this.entityClass.getAnnotation(Entity.class);
		if (entity != null) {
			this.schema = entity.schema();
			this.name = entity.name();
		}
		
		// if entity name is empty, will use entity class name
		if (this.name.trim().length() == 0) {
			this.name = this.entityClass.getSimpleName();
		}
		
		// find map query result to field or property, default is property
		MappingBy mapping = MappingBy.PROPERTY; 
		if (entity != null) {
			mapping = entity.mappingBy();
		}
		
		// get bean info by entity class
		BeanInfo beaninfo = null;
		try {
			beaninfo = Introspector.getBeanInfo(entityClass);
		} catch (IntrospectionException e) {
			throw new DataRuntimeException("Fail to get bean info with entity class [{0}]", e, entityClass);
		}

		// find all properties (getter and setter) if it will map to column in query result
		for (PropertyDescriptor property : beaninfo.getPropertyDescriptors()) {
			if (this.isMappingColumn(property, mapping)) {
				
				MethodColumnDescriptor<E> descriptor = new MethodColumnDescriptor<E>(property);
				this.columnDescriptors.put(descriptor.getName(), descriptor);
				if (property.getReadMethod().isAnnotationPresent(Id.class)) {
					this.identityColumnDescriptor = descriptor;
				}
			}
		}

		// find all fields if it will map to column in query result
		for (Field field : this.entityClass.getDeclaredFields()) {
			
			if (this.isMappingColumn(field, mapping)) {
				
				FieldColumnDescriptor<E> descriptor = new FieldColumnDescriptor<E>(field);
				this.columnDescriptors.put(descriptor.getName(), descriptor);
				if (field.isAnnotationPresent(Id.class)) {
					this.identityColumnDescriptor = descriptor;
				}
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
	 * @return the schema
	 */
	protected String getSchema() {
		return schema;
	}
	
	/**
	 * @return the name
	 */
	protected String getName() {
		return name;
	}
	
	/**
	 * @return the identity column descriptor
	 */
	protected ColumnDescriptor<E> getIdentityColumnDescriptor() {
		return identityColumnDescriptor;
	}
	
	/**
	 * @return all column descriptors
	 */
	protected Map<String, ColumnDescriptor<E>> getColumnDescriptors() {
		return this.columnDescriptors;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.EntityDescriptor#getEntityClass()
	 */
	@Override
	public Class<E> getEntityClass() {
		return this.entityClass;
	}
}

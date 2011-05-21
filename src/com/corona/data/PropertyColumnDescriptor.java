/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.corona.data.annotation.Column;
import com.corona.util.StringUtil;

/**
 * <p>This descriptor is used to map column of query result to a property of entity class </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public class PropertyColumnDescriptor<E> implements ColumnDescriptor<E> {

	/**
	 * the column name
	 */
	private String name;
	
	/**
	 * the write method
	 */
	private Method writeMethod;
	
	/**
	 * the read method
	 */
	private Method readMethod;
	
	/**
	 * the resolver to get column value from query result
	 */
	private DataType resolver;
	
	/**
	 * @param property the property descriptor of java bean
	 */
	public PropertyColumnDescriptor(final PropertyDescriptor property) {
		
		this.readMethod = property.getReadMethod();
		this.writeMethod = property.getWriteMethod();
		
		// try to find mapped to column name by Column annotation in getter or setter method
		if (this.readMethod.isAnnotationPresent(Column.class)) {
			this.name = this.readMethod.getAnnotation(Column.class).name();
		}
		
		// if column name is not defined by annotation, will use property name instead
		if (StringUtil.isBlank(this.name)) {
			this.name = property.getName();
		}
		this.name = this.name.toUpperCase();
		
		// find column value resolver for this column
		this.resolver = DataTypeRepository.getInstance().find(this.getType());
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#getType()
	 */
	@Override
	public Class<?> getType() {
		return this.readMethod.getReturnType();
	}

	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#getAnnotation(java.lang.Class)
	 */
	@Override
	public <A extends Annotation> A getAnnotation(final Class<A> annotationClass) {
		return this.readMethod.getAnnotation(annotationClass);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#get(java.lang.Object)
	 */
	@Override
	public Object get(final E entity) {
		
		try {
			return this.readMethod.invoke(entity);
		} catch (Throwable e) {
			throw new DataRuntimeException("Fail to get value by method [{0}] for column [{1}]", 
					e, this.readMethod, this.name
			);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(final E entity, final Object value) {

		try {
			this.writeMethod.invoke(entity, value);
		} catch (Throwable e) {
			throw new DataRuntimeException("Fail to set value by method [{0}] for column [{1}]", 
					e, this.writeMethod, this.name
			);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#set(java.lang.Object, com.corona.data.ResultHolder)
	 */
	@Override
	public void set(final E entity, final ResultHolder resultHolder) {
		this.set(entity, this.resolver.get(resultHolder, this.name));
	}
}

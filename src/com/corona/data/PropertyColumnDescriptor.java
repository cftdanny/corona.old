/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.corona.data.annotation.Column;

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
	 * @param property the property descriptor of java bean
	 */
	public PropertyColumnDescriptor(final PropertyDescriptor property) {
		
		this.readMethod = property.getReadMethod();
		this.writeMethod = property.getWriteMethod();
		
		// try to find mapped to column name by Column annotation in getter or setter method
		if ((this.readMethod != null) && (this.readMethod.isAnnotationPresent(Column.class))) {
			this.name = this.readMethod.getAnnotation(Column.class).name();
		} else if ((this.writeMethod != null) && (this.writeMethod.isAnnotationPresent(Column.class))) {
			this.name = this.writeMethod.getAnnotation(Column.class).name();
		}
		
		// if column name is not defined by annotation, will use property name instead
		if (this.name.trim().length() == 0) {
			this.name = property.getName();
		}
		this.name = this.name.toUpperCase();
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
		return (this.readMethod != null) ? this.readMethod.getReturnType() : this.writeMethod.getParameterTypes()[0];
	}

	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#getAnnotation(java.lang.Class)
	 */
	@Override
	public <A extends Annotation> A getAnnotation(final Class<A> annotationClass) {
		
		if (this.readMethod != null) {
			return this.readMethod.getAnnotation(annotationClass);
		} else {
			return this.writeMethod.getAnnotation(annotationClass);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#get(java.lang.Object)
	 */
	@Override
	public Object get(final E entity) {
		
		if (this.readMethod == null) {
			throw new NullPointerException("Getter method is not defined for column [" + this.name + "]");
		}
		
		try {
			return this.readMethod.invoke(entity);
		} catch (Throwable e) {
			throw new DataRuntimeException("Fail to invoke getter method for column [{0}]", e, this.name);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(final E entity, final Object value) {
		
		if (this.writeMethod == null) {
			throw new NullPointerException("Setter method is not defined for column [" + this.name + "]");
		}
		
		try {
			this.writeMethod.invoke(entity, value);
		} catch (Throwable e) {
			throw new DataRuntimeException("Fail to invoke setter method for column [{0}]", e, this.name);
		}
	}
}

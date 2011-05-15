/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.lang.reflect.Field;

import com.corona.data.annotation.Column;

/**
 * <p>This descriptor is used to map column of data source to a field in entity class </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public class FieldColumnDescriptor<E> implements ColumnDescriptor<E> {

	/**
	 * the column name
	 */
	private String name;
	
	/**
	 * the field in entity class
	 */
	private Field field;
	
	/**
	 * @param field the field in entity class that is annotated with {@link Column}
	 */
	public FieldColumnDescriptor(final Field field) {
		
		this.field = field;
		this.field.setAccessible(true);
		
		Column column = this.field.getAnnotation(Column.class);
		if ((column == null) || ("".endsWith(column.name()))) {
			this.name = this.field.getName();
		} else {
			this.name = column.name();
		}
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
		return this.field.getType();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#get(java.lang.Object)
	 */
	@Override
	public Object get(final E entity) {
		
		try {
			return (entity == null) ? null : this.field.get(entity);
		} catch (Throwable e) {
			throw new DataRuntimeException("Fail to get value by field [{0}]", e, this.field);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ColumnDescriptor#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void set(final E entity, final Object value) {
		
		if (entity != null) {
			try {
				this.field.set(entity, value);
			} catch (Throwable e) {
				throw new DataRuntimeException("Fail to set value to field [{0}]", e, this.field);
			}
		}
	}
}

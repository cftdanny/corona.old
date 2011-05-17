/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This handler is used to transfer query result into map to entity instance. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public class BeanResultHandler<E> extends AbstractResultHandler<E> {

	/**
	 * the column descriptors about an entity class
	 */
	private Map<String, ColumnDescriptor<E>> columnDescriptors = new HashMap<String, ColumnDescriptor<E>>();
	
	/**
	 * the entity class
	 */
	private Class<E> entityClass;
	
	/**
	 * @param resultMetaData the meta data for mapping row of query result to bean
	 */
	public BeanResultHandler(final ResultMetaData<E> resultMetaData) {
		
		this.entityClass = resultMetaData.getMappingClass();
		for (ColumnDescriptor<E> columnDescriptor : resultMetaData.getColumnDescriptors()) {
			this.columnDescriptors.put(columnDescriptor.getName(), columnDescriptor);
		}
	}
	
	/**
	 * @param entityClass the entity class that map query result to 
	 */
	public BeanResultHandler(final Class<E> entityClass) {
		this(new BeanResultMetaData<E>(entityClass));
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.ResultHandler#get(com.corona.data.ResultHolder)
	 */
	@Override
	public E get(final ResultHolder result) {
		return this.get(result, this.getDescriptors(result));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.AbstractResultHandler#toList(com.corona.data.ResultHolder)
	 */
	@Override
	public List<E> toList(final ResultHolder result) {
		
		// find all column descriptors that both exists in query result and entity class
		List<ColumnDescriptor<E>> descriptors = this.getDescriptors(result);

		// convert all rows in query result into list of entity instance
		List<E> outcome = new ArrayList<E>();
		while (result.next()) {
			outcome.add(this.get(result, descriptors));
		}
		
		return outcome;
	}

	/**
	 * @param result the query result
	 * @return all column descriptors that both exists in query result and entity class
	 */
	private List<ColumnDescriptor<E>> getDescriptors(final ResultHolder result) {
		
		List<ColumnDescriptor<E>> descriptors = new ArrayList<ColumnDescriptor<E>>();
		for (String columnLabel : result.getColumnLabels()) {
			
			ColumnDescriptor<E> descriptor = this.columnDescriptors.get(columnLabel);
			if (descriptor != null) {
				descriptors.add(descriptor);
			}
		}
		return descriptors;
	}
	
	/**
	 * @param result the query result
	 * @param descriptors the field or column descriptor
	 * @return the new entity instance that is mapped from current row in query result
	 */
	private E get(final ResultHolder result, final List<ColumnDescriptor<E>> descriptors) {
		
		// create entity instance with default constructor
		E entity = null;
		try {
			entity = this.entityClass.newInstance();
		} catch (Throwable e) {
			throw new DataRuntimeException(
					"Fail to create instance for entity class [{0}], default constructor is needed", 
					e, this.entityClass.toString()
			);
		}
		
		// transfer column value from query result to new created entity instance
		for (ColumnDescriptor<E> descriptor : descriptors) {
			descriptor.set(entity, result.get(descriptor.getName()));
		}
		
		return entity;
	}
}

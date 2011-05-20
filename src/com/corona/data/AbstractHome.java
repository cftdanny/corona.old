/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>the helper class of data access object ({@link Home}) for an entity </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public class AbstractHome<E> implements Home<E> {

	/**
	 * the entity configuration
	 */
	private EntityMetaData<E> entityMetaData;
	
	/**
	 * the current entity manager
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the primary key
	 */
	private PrimaryKey<E> primarykey;
	
	/**
	 * all unique keys
	 */
	private Map<Integer, UniqueKey<E>> uniqueKeys;
	
	/**
	 * the INSERT entity command
	 */
	private Command insertCommand;
	
	/**
	 * @param connectionManager current connection manager
	 * @param entityClass the entity class
	 */
	public AbstractHome(final ConnectionManager connectionManager, final Class<E> entityClass) {
		
		this.connectionManager = connectionManager;
		
		// find entity configuration for entity class
		EntityMetaDataManager entityMetaDataManager = this.getConnectionManagerFactory().getEntityMetaDataManager();
		this.entityMetaData = entityMetaDataManager.getEntityMetaData(entityClass);
	}
	
	/**
	 * @return the current connection manager
	 */
	private ConnectionManagerFactory getConnectionManagerFactory() {
		return this.connectionManager.getConnectionManagerFactory();
	}
	
	/**
	 * @return the primary key
	 */
	private PrimaryKey<E> getPrimaryKey() {
		
		if (this.primarykey == null) {
			PrimaryKeyDescriptor<E> descriptor = this.entityMetaData.getPrimarykey();
			this.primarykey = descriptor.createPrimaryKey(this.connectionManager);
		}
		return this.primarykey;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#getUniqueKey(int)
	 */
	@Override
	public UniqueKey<E> getUniqueKey(final int id) {
		
		if (this.uniqueKeys == null) {
			this.uniqueKeys = new HashMap<Integer, UniqueKey<E>>();
		}
		
		// if unique key about id has been created, just return
		UniqueKey<E> uniqueKey = this.uniqueKeys.get(id);
		if (uniqueKey != null) {
			return uniqueKey;
		}
		
		// if unique key about id is not defined, just return null
		UniqueKeyDescriptor<E> descriptor = this.entityMetaData.getUniqueKey(id);
		if (descriptor == null) {	
			return null;
		}
		
		// if defined, create unique and store it to cache
		uniqueKey = descriptor.createUniqueKey(this.connectionManager);
		this.uniqueKeys.put(id, uniqueKey);
		return uniqueKey;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(final Object... keys) {
		return this.getPrimaryKey().exists(keys);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#get(java.lang.Object[])
	 */
	@Override
	public E get(final Object... keys) {
		return this.getPrimaryKey().get(keys);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#update(java.lang.Object)
	 */
	@Override
	public boolean update(final E e) {
		return this.getPrimaryKey().update(e);
	}

	/**
	 * @return the home builder
	 */
	private HomeBuilder getBuilder() {
		return this.connectionManager.getDialect().getHomeBuilder();
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#insert(java.lang.Object)
	 */
	@Override
	public void insert(final E e) {
		
		// if INSERT command is not created before, we will create it by dialect and HomeBuilder
		if (this.insertCommand == null) {
			this.insertCommand = this.getBuilder().createInsertCommand(
					this.connectionManager, this.entityMetaData
			);
		}
		
		// get id column descriptor for entity
		ColumnDescriptor<E> identity = this.entityMetaData.getIdentityDescriptor();
		
		// get values of columns in order to insert into data source
		List<Object> arguments = new ArrayList<Object>();
		for (ColumnDescriptor<E> descriptor : this.entityMetaData.getColumnDescriptors().values()) {
			if ((identity == null) || (!descriptor.equals(identity))) {
				arguments.add(descriptor.get(e));
			}
		}
		
		// after execute INSERT command, return NO of inserted row should be 1
		if (this.insertCommand.insert(arguments.toArray()) != 1) {
			throw new DataRuntimeException("More than one rows are inserted to data source, but should be 1");
		}
		
		// if there is ID column, will get generated ID from data source and pass to entity instance
		if (identity != null) {
			Object[] keys = this.connectionManager.getDialect().getGeneratedKeys(this.insertCommand);
			if ((keys != null) && (keys.length > 0)) {
				identity.set(e, keys[0]);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#delete(java.lang.Object[])
	 */
	@Override
	public boolean delete(final Object... keys) {
		return this.getPrimaryKey().delete(keys);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#list()
	 */
	@Override
	public List<E> list() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#list(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<E> list(String sql, Object... args) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#list(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public List<E> list(String sql, String[] names, Object[] args) {
		return null;
	}

	/**
	 * @param filter the filter
	 * @return the SELECT COUNT(*) SQL query
	 */
	private Query<Long> createCountQuery(final String filter) {
		return this.getBuilder().createCountQuery(connectionManager, entityMetaData, filter);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count()
	 */
	@Override
	public long count() {
		return this.createCountQuery("").get();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count(java.lang.String, java.lang.Object[])
	 */
	@Override
	public long count(final String filter, final Object... args) {
		return this.createCountQuery(filter).get(args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public long count(final String filter, final String[] names, final Object... args) {
		return this.createCountQuery(filter).get(names, args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#update(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int update(String sql, Object... args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#update(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int update(String sql, String[] names, Object[] args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#delete(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int delete(String sql, Object... args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#delete(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int delete(String sql, String[] names, Object[] args) {
		return 0;
	}
}

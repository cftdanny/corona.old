/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>the helper class of data access object ({@link Home}) for an entity </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <K> the type of primary key class
 * @param <E> the type of entity class
 */
public class AbstractHome<K, E> implements Home<K, E> {

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
	private PrimaryKey<K, E> primarykey;
	
	/**
	 * all unique keys
	 */
	private Map<Integer, UniqueKey<E>> uniqueKeys;
	
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
	private PrimaryKey<K, E> getPrimaryKey() {
		
		if (this.primarykey == null) {
			this.primarykey = this.entityMetaData.getPrimarykey().createPrimaryKey(this.connectionManager);
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
	 * @see com.corona.data.Home#exists(java.lang.Object)
	 */
	@Override
	public boolean exists(final K key) {
		return this.getPrimaryKey().exists(key);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#get(java.lang.Object)
	 */
	@Override
	public E get(final K key) {
		return this.getPrimaryKey().get(key);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#update(java.lang.Object, java.lang.String[])
	 */
	@Override
	public boolean update(final E e, final String... columns) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#insert(java.lang.Object)
	 */
	@Override
	public void insert(final E e) {
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(final K key) {
		return this.getPrimaryKey().delete(key);
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
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count(java.lang.String, java.lang.Object[])
	 */
	@Override
	public long count(String sql, Object... args) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public long count(String sql, String[] names, Object... args) {
		return 0;
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

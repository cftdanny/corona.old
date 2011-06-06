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
public abstract class AbstractHome<E> implements Home<E> {

	/**
	 * the entity configuration
	 */
	private EntityMetaData<E> entityMetaData;
	
	/**
	 * the current entity manager
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the builder that is used to create statement
	 */
	private StatementBuilder<E> statementBuilder;
	
	/**
	 * the primary key
	 */
	private PrimaryKey<E> primarykey;
	
	/**
	 * all unique keys
	 */
	private Map<Integer, UniqueKey<E>> uniqueKeys;

	/**
	 * all unique keys
	 */
	private Map<Integer, Index<E>> indexes;

	/**
	 * the INSERT entity command
	 */
	private Command insert;
	
	/**
	 * @param connectionManager current connection manager
	 * @param entityClass the entity class
	 */
	public AbstractHome(final ConnectionManager connectionManager, final Class<E> entityClass) {
		
		this.connectionManager = connectionManager;
		
		// find entity runtime configuration according to entity class and connection manager
		EntityMetaDataRepository entityMetaDataManager = this.getEntityMetaDataRepository();
		this.entityMetaData = entityMetaDataManager.getEntityMetaData(entityClass);
		this.statementBuilder = this.connectionManager.getDialect().createStatementBuilder(
				this.connectionManager, this.entityMetaData
		);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#close()
	 */
	@Override
	public void close() {
		
		if (this.primarykey != null) {
			this.primarykey.close();
		}
		if (this.uniqueKeys != null) {
			for (UniqueKey<E> uniqueKey : this.uniqueKeys.values()) {
				uniqueKey.close();
			}
		}
		if (this.indexes != null) {
			for (Index<E> index : this.indexes.values()) {
				index.close();
			}
		}
		if (this.insert != null) {
			this.insert.close();
		}
	}

	/**
	 * @return the repository for entity configuration
	 */
	private EntityMetaDataRepository getEntityMetaDataRepository() {
		return this.getConnectionManagerFactory().getEntityMetaDataRepository();
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
			this.primarykey = descriptor.create(this.connectionManager);
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
		uniqueKey = descriptor.create(this.connectionManager);
		this.uniqueKeys.put(id, uniqueKey);
		return uniqueKey;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#getIndex(int)
	 */
	@Override
	public Index<E> getIndex(final int id) {
		
		if (this.indexes == null) {
			this.indexes = new HashMap<Integer, Index<E>>();
		}
		
		// if index about id has been created before, just return
		Index<E> index = this.indexes.get(id);
		if (index != null) {
			return index;
		}
		
		// if index descriptor about id is not defined, just return null
		IndexDescriptor<E> descriptor = this.entityMetaData.getIndex(id);
		if (descriptor == null) {
			return null;
		}
		
		// if defined, create index and store it to cache
		index = descriptor.create(this.connectionManager);
		this.indexes.put(id, index);
		return index;
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
	 * {@inheritDoc}
	 * @see com.corona.data.Home#insert(java.lang.Object)
	 */
	@Override
	public void insert(final E e) {
		
		// if INSERT command is not created before, we will create it by dialect and HomeBuilder
		if (this.insert == null) {
			this.insert = this.statementBuilder.createInsertCommand();
		}
		
		// get id column descriptor for entity
		ColumnDescriptor<E> identity = this.entityMetaData.getIdentityDescriptor();
		
		// get values of columns in order to insert into data source
		List<Object> arguments = new ArrayList<Object>();
		for (ColumnDescriptor<E> descriptor : this.entityMetaData.getColumns().values()) {
			if ((identity == null) || (!descriptor.equals(identity))) {
				arguments.add(descriptor.get(e));
			}
		}
		
		// after execute INSERT command, return NO of inserted row should be 1
		if (this.insert.insert(arguments.toArray()) != 1) {
			throw new DataRuntimeException("More than one rows are inserted to data source, but should be 1");
		}
		
		// if there is ID column, will get generated ID from data source and pass to entity instance
		if (identity != null) {
			Object[] keys = this.connectionManager.getDialect().getGeneratedKeys(this.insert);
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
	 * @see com.corona.data.Home#getStatementBuilder()
	 */
	@Override
	public StatementBuilder<E> getStatementBuilder() {
		return this.statementBuilder;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#list()
	 */
	@Override
	public List<E> list() {
		return this.statementBuilder.createListQuery("").list();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#list(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<E> list(final String sql, final Object... args) {
		return this.statementBuilder.createListQuery(sql).list(args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#list(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public List<E> list(final String sql, final String[] names, final Object[] args) {
		return this.statementBuilder.createListQuery(sql).list(names, args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count()
	 */
	@Override
	public long count() {
		return this.statementBuilder.createCountQuery("").get();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count(java.lang.String, java.lang.Object[])
	 */
	@Override
	public long count(final String filter, final Object... args) {
		return this.statementBuilder.createCountQuery(filter).get(args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#count(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public long count(final String filter, final String[] names, final Object... args) {
		return this.statementBuilder.createCountQuery(filter).get(names, args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#update(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int update(final String sql, final Object... args) {
		return this.statementBuilder.createUpdateCommand(sql).update(args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#update(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int update(final String sql, final String[] names, final Object[] args) {
		return this.statementBuilder.createUpdateCommand(sql).update(names, args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#delete(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int delete(final String sql, final Object... args) {
		return this.statementBuilder.createDeleteCommand(sql).delete(args);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Home#delete(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public int delete(final String sql, final String[] names, final Object[] args) {
		return this.statementBuilder.createDeleteCommand(sql).delete(names, args);
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.ArrayList;
import java.util.List;

import com.corona.data.ColumnDescriptor;
import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.PrimaryKey;
import com.corona.data.Query;

/**
 * <p>This primary key is used to SELECT single entity or DELETE single entity from database by SQL
 * statement. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 * @param <K> the type of primary key class
 */
class SQLPrimaryKey<K, E> implements PrimaryKey<K, E> {

	/**
	 * the primary key descriptor
	 */
	private SQLPrimaryKeyDescriptor<E> parent;
	
	/**
	 * the connection manager factory
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the query that can be used to query by single entity in data source by primary key
	 */
	private Query<E> selectQuery;
	
	/**
	 * the command that can be used to delete entity from data source by primary key
	 */
	private Command deleteCommand;

	/**
	 * the UPDATE command that is used to update value of all columns by entity instance
	 */
	private Command updateCommand;

	/**
	 * @param connectionManager the current connection manager
	 * @param parent the primary key descriptor
	 */
	SQLPrimaryKey(final ConnectionManager connectionManager, final SQLPrimaryKeyDescriptor<E> parent) {
		this.connectionManager = connectionManager;
		this.parent = parent;
	}
	
	/**
	 * @return the SELECT query according to primary key
	 */
	private Query<E> getSelectQuery() {
		
		if (this.selectQuery == null) {
			this.selectQuery = this.parent.createSelectQuery(this.connectionManager);
		}
		return this.selectQuery;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#exists(java.lang.Object)
	 */
	@Override
	public boolean exists(final K value) {
		return this.get(value) != null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#get(java.lang.Object)
	 */
	@Override
	public E get(final K value) {
		return this.getSelectQuery().get(value);
	}

	/**
	 * @return the DELETE command according to primary key
	 */
	private Command getDeleteCommand() {

		if (this.deleteCommand == null) {
			this.deleteCommand = this.parent.createDeleteCommand(this.connectionManager);
		}
		return this.deleteCommand;
	}
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(final K value) {

		int count = this.getDeleteCommand().delete(value);
		if (count > 1) {
			throw new DataRuntimeException(
					"Primary key is invalid, please check this DELETE SQL [{0}]", this.getDeleteCommand()
			);
		}
		return count == 1;
	}

	/**
	 * @return the UPDATE SQL command according to primary key
	 */
	private Command getUpdateCommand() {
		
		if (this.updateCommand == null) {
			this.updateCommand = this.parent.createUpdateCommand(this.connectionManager);
		}
		return this.updateCommand;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#update(java.lang.Object)
	 */
	@Override
	public boolean update(final E e) {
		
		// prepare arguments by entity instance for UPDATE SQL statement
		List<Object> arguments = new ArrayList<Object>();
		for (ColumnDescriptor<E> descriptor : this.parent.getUpdatableColumnDescriptors()) {
			arguments.add(descriptor.get(e));
		}
		for (ColumnDescriptor<E> descriptor : this.parent.getPrimaryKeyColumnDescriptors()) {
			arguments.add(descriptor.get(e));
		}
		
		// execute UPDATE SQL statement, the affect rows in database should be 0 or 1
		int count = this.getUpdateCommand().update(arguments.toArray());
		if (count > 1) {
			throw new DataRuntimeException(
					"Primary key is invalid, please check this UPDATE SQL [{0}]", this.getUpdateCommand()
			);
		}
		return count == 1;
	}
}

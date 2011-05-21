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
 */
class SQLPrimaryKey<E> implements PrimaryKey<E> {
	
	/**
	 * the current connection manager
	 */
	private ConnectionManager connectionManager;

	/**
	 * the primary key descriptor
	 */
	private SQLPrimaryKeyDescriptor<E> parent;
	
	/**
	 * the query that can be used to query by single entity in data source by primary key
	 */
	private Query<E> select;
	
	/**
	 * the command that can be used to delete entity from data source by primary key
	 */
	private Command delete;

	/**
	 * the UPDATE command that is used to update value of all columns by entity instance
	 */
	private Command update;

	/**
	 * @param connectionManager the current connection manager
	 * @param parent the primary key descriptor
	 */
	SQLPrimaryKey(final ConnectionManager connectionManager, final SQLPrimaryKeyDescriptor<E> parent) {
		this.connectionManager = connectionManager;
		this.parent = parent;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#close()
	 */
	@Override
	public void close() {
		
		if (this.select != null) {
			this.select.close();
		}
		if (this.delete != null) {
			this.delete.close();
		}
		if (this.update != null) {
			this.update.close();
		}
	}

	/**
	 * @return the SELECT query according to primary key
	 */
	private Query<E> getSelectQuery() {
		
		if (this.select == null) {
			this.select = this.parent.createSelectQuery(this.connectionManager);
		}
		return this.select;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(final Object... keys) {
		return this.get(keys) != null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#get(java.lang.Object[])
	 */
	@Override
	public E get(final Object... keys) {
		return this.getSelectQuery().get(keys);
	}

	/**
	 * @return the DELETE command according to primary key
	 */
	private Command getDeleteCommand() {

		if (this.delete == null) {
			this.delete = this.parent.createDeleteCommand(this.connectionManager);
		}
		return this.delete;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#delete(java.lang.Object[])
	 */
	@Override
	public boolean delete(final Object... keys) {

		int count = this.getDeleteCommand().delete(keys);
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
		
		if (this.update == null) {
			this.update = this.parent.createUpdateCommand(this.connectionManager);
		}
		return this.update;
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
					"Primary key is invalid, please check UPDATE SQL [{0}]", this.getUpdateCommand()
			);
		}
		return count == 1;
	}
}

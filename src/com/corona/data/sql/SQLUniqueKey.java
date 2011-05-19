/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.Query;
import com.corona.data.UniqueKey;

/**
 * <p>This unique key is used to SELECT single entity or DELETE single entity from database by SQL
 * statement. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public class SQLUniqueKey<E> implements UniqueKey<E> {

	/**
	 * the current connection manager
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the parent unique key descriptor
	 */
	private SQLUniqueKeyDescriptor<E> parent;
	
	/**
	 * the query that can be used to query by single entity in data source by unique key
	 */
	private Query<E> selectQuery;
	
	/**
	 * the command that can be used to delete entity from data source by unique key
	 */
	private Command deleteCommand;

	/**
	 * @param connectionManager the current connection manager
	 * @param parent the parent unique key descriptor
	 */
	SQLUniqueKey(final ConnectionManager connectionManager, final SQLUniqueKeyDescriptor<E> parent) {
		this.connectionManager = connectionManager;
		this.parent = parent;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKey#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(final Object... values) {
		return this.get(values) != null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKey#get(java.lang.Object[])
	 */
	@Override
	public E get(final Object... values) {
		
		if (this.selectQuery == null) {
			this.selectQuery = this.parent.createSelectQuery(this.connectionManager);
		}
		return this.selectQuery.get(values);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKey#delete(java.lang.Object[])
	 */
	@Override
	public boolean delete(final Object... values) {

		if (this.deleteCommand == null) {
			this.deleteCommand = this.parent.createDeleteCommand(this.connectionManager);
		}
		
		int count = this.deleteCommand.delete(values);
		if (count > 1) {
			throw new DataRuntimeException(
					"Unique key [{0}] is invalid, please check DELETE SQL [{0}]", 
					this.parent.getId(), this.deleteCommand
			);
		}
		return count == 1;
	}
}

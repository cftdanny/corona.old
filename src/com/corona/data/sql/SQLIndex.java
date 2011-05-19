/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.List;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.Index;
import com.corona.data.Query;

/**
 * <p>This class is used to control resources in database by SELECT and DELETE with table index. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public class SQLIndex<E> implements Index<E> {

	/**
	 * the current connection manager
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the index descriptor
	 */
	private SQLIndexDescriptor<E> parent;
	
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
	 * @param parent the index descriptor
	 */
	SQLIndex(final ConnectionManager connectionManager, final SQLIndexDescriptor<E> parent) {
		this.connectionManager = connectionManager;
		this.parent = parent;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Index#list(java.lang.Object[])
	 */
	@Override
	public List<E> list(final Object... values) {
		
		if (this.selectQuery == null) {
			this.selectQuery = this.parent.createSelectQuery(this.connectionManager);
		}
		return this.selectQuery.list(values);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Index#delete(java.lang.Object[])
	 */
	@Override
	public int delete(final Object... values) {
		
		if (this.deleteCommand == null) {
			this.deleteCommand = this.parent.createDeleteCommand(this.connectionManager);
		}
		return this.deleteCommand.delete(values);
	}
}

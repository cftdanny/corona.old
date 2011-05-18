/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.Command;
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
	 * the query that can be used to query by single entity in data source by primary key
	 */
	private Query<E> query;
	
	/**
	 * the command that can be used to delete entity from data source by primary key
	 */
	private Command command;

	/**
	 * @param query the query that can be used to query by single entity in data source by primary key
	 * @param command the command that can be used to delete entity from data source by primary key
	 */
	SQLPrimaryKey(final Query<E> query, final Command command) {
		this.query = query;
		this.command = command;
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
		return this.query.get(value);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKey#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(final K value) {

		int count = this.command.delete(value);
		if (count > 1) {
			throw new DataRuntimeException(
					"Primary key is invalid, please check this DELETE SQL [{0}]", this.command
			);
		}
		
		return count == 1;
	}
}

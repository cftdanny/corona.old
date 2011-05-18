/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.Command;
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
	 * the query that can be used to query by single entity in data source by unique key
	 */
	private Query<E> query;
	
	/**
	 * the command that can be used to delete entity from data source by unique key
	 */
	private Command command;

	/**
	 * @param query the query that can be used to query by single entity in data source by unique key
	 * @param command the command that can be used to delete entity from data source by unique key
	 */
	SQLUniqueKey(final Query<E> query, final Command command) {
		this.query = query;
		this.command = command;
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
		return this.query.get(values);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKey#delete(java.lang.Object[])
	 */
	@Override
	public boolean delete(final Object... values) {

		int count = this.command.delete(values);
		if (count > 1) {
			throw new DataRuntimeException(
					"Unique key is invalid, please check this DELETE SQL [{0}]", this.command
			);
		}
		return count == 1;
	}
}

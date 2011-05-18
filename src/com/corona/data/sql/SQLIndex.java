/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.List;

import com.corona.data.Command;
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
	 * the query that can be used to query by single entity in data source by unique key
	 */
	private Query<E> query;
	
	/**
	 * the command that can be used to delete entity from data source by unique key
	 */
	private Command command;

	/**
	 * @param query the query that can be used to query by entities in data source by index
	 * @param command the command that can be used to delete entities from data source by index
	 */
	SQLIndex(final Query<E> query, final Command command) {
		this.query = query;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Index#list(java.lang.Object[])
	 */
	@Override
	public List<E> list(final Object... values) {
		return this.query.list(values);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.Index#delete(java.lang.Object[])
	 */
	@Override
	public int delete(final Object... values) {
		return this.command.delete(values);
	}
}

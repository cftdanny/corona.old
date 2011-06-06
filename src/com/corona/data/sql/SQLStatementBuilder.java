/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.regex.Pattern;

import com.corona.data.BeanResultHandler;
import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.EntityMetaData;
import com.corona.data.StatementBuilder;
import com.corona.data.Query;
import com.corona.data.ResultHandler;
import com.corona.util.StringUtil;

/**
 * <p>This builder is used to build query and command for {@link Home} for SQL database </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
class SQLStatementBuilder<E> implements StatementBuilder<E> {

	/**
	 * the pattern to match whether SQL statement starts with WHERE
	 */
	private static final Pattern WHERE = Pattern.compile("(\\s*[w|W][h|H][e|E][r|R][e|E]\\s+).*");

	/**
	 * the pattern to match whether SQL statement starts with WHERE
	 */
	private static final Pattern SET = Pattern.compile("(\\s*[s|S][e|E][t|T]\\s+).*");

	/**
	 * the current connection manager
	 */
	private ConnectionManager connectionManager;
	
	/**
	 * the meta data of entity class
	 */
	private EntityMetaData<E> metadata;
	
	/**
	 * @param connectionManager the current connection manager
	 * @param metadata the meta data of entity class
	 */
	SQLStatementBuilder(final ConnectionManager connectionManager, final EntityMetaData<E> metadata) {
		this.connectionManager = connectionManager;
		this.metadata = metadata;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.StatementBuilder#createInsertCommand()
	 */
	@Override
	public Command createInsertCommand() {
		SQLPrimaryKeyDescriptor<E> descriptor = (SQLPrimaryKeyDescriptor<E>) this.metadata.getPrimarykey();
		return descriptor.createInsertCommand(this.connectionManager);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.StatementBuilder#createCountQuery(java.lang.String)
	 */
	@Override
	public Query<Long> createCountQuery(final String filter) {
		
		SQLSelectBuilder builder = new SQLSelectBuilder(this.metadata, filter);
		return this.connectionManager.createQuery(ResultHandler.LONG, builder.getStatement("SELECT COUNT(*)"));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.StatementBuilder#createListQuery(java.lang.String)
	 */
	@Override
	public Query<E> createListQuery(final String filter) {

		SQLSelectBuilder builder = new SQLSelectBuilder(this.metadata, filter);
		return connectionManager.createQuery(
				new BeanResultHandler<E>(this.metadata), builder.getStatement("SELECT *")
		);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.StatementBuilder#createDeleteCommand(java.lang.String)
	 */
	@Override
	public Command createDeleteCommand(final String filter) {

		String sql = "DELETE FROM " + this.metadata.getName();
		if (WHERE.matcher(filter).matches()) {
			sql = sql + " " + filter;
		} else if (!StringUtil.isBlank(filter)) {
			sql = sql + " WHERE " + filter;
		}
		return this.connectionManager.createCommand(sql);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.StatementBuilder#createUpdateCommand(java.lang.String)
	 */
	@Override
	public Command createUpdateCommand(final String filter) {
		
		String sql = "UPDATE " + this.metadata.getName();
		if (SET.matcher(filter).matches()) {
			sql = sql + " " + filter;
		} else {
			sql = sql + " SET " + filter;
		}
		return connectionManager.createCommand(sql);
	}
}

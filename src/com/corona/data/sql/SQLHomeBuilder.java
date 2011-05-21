/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.regex.Pattern;

import com.corona.data.BeanResultHandler;
import com.corona.data.ColumnDescriptor;
import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.EntityMetaData;
import com.corona.data.HomeBuilder;
import com.corona.data.Query;
import com.corona.data.ResultHandler;
import com.corona.util.StringUtil;

/**
 * <p>This builder is used to build query and command for {@link Home} for SQL database </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLHomeBuilder implements HomeBuilder {

	/**
	 * the pattern to match whether SQL statement starts with WHERE
	 */
	private static final Pattern WHERE = Pattern.compile("(\\s*[w|W][h|H][e|E][r|R][e|E]\\s+).*");

	/**
	 * the pattern to match whether SQL statement starts with WHERE
	 */
	private static final Pattern SET = Pattern.compile("(\\s*[s|S][e|E][t|T]\\s+).*");

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.HomeBuilder#createInsertCommand(
	 * 	com.corona.data.ConnectionManager, com.corona.data.EntityMetaData
	 * )
	 */
	@Override
	public <E> Command createInsertCommand(final ConnectionManager connectionManager, final EntityMetaData<E> config) {
		
		String columns = "", params = "";
		for (ColumnDescriptor<E> descriptor : config.getColumns().values()) {
			if ((config.getIdentityDescriptor() == null) || (!descriptor.equals(config.getIdentityDescriptor()))) {
				columns = columns + (columns.length() == 0 ? "" : ", ") + descriptor.getName();
				params = params + (params.length() == 0 ? "" : ", ") + "?";
			}
		}
		
		String sql = "INSERT INTO " + config.getName() + " (" + columns + ") VALUES (" + params + ")";
		return connectionManager.createCommand(sql);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.HomeBuilder#createCountQuery(
	 * 	com.corona.data.ConnectionManager, com.corona.data.EntityMetaData, java.lang.String
	 * )
	 */
	@Override
	public <E> Query<Long> createCountQuery(
			final ConnectionManager connectionManager, final EntityMetaData<E> config, final String filter) {
		
		SQLSelectBuilder builder = new SQLSelectBuilder(config, filter);
		return connectionManager.createQuery(ResultHandler.LONG, builder.getStatement("SELECT COUNT(*)"));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.HomeBuilder#createListQuery(
	 * 	com.corona.data.ConnectionManager, com.corona.data.EntityMetaData, java.lang.String
	 * )
	 */
	@Override
	public <E> Query<E> createListQuery(
			final ConnectionManager connectionManager, final EntityMetaData<E> config, final String filter) {

		SQLSelectBuilder builder = new SQLSelectBuilder(config, filter);
		return connectionManager.createQuery(new BeanResultHandler<E>(config), builder.getStatement("SELECT *"));
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.HomeBuilder#createDeleteCommand(
	 * 	com.corona.data.ConnectionManager, com.corona.data.EntityMetaData, java.lang.String
	 * )
	 */
	@Override
	public Command createDeleteCommand(
			final ConnectionManager connectionManager, final EntityMetaData<?> config, final String filter) {

		String sql = "DELETE FROM " + config.getName();
		if (WHERE.matcher(filter).matches()) {
			sql = sql + " " + filter;
		} else if (!StringUtil.isBlank(filter)) {
			sql = sql + " WHERE " + filter;
		}
		return connectionManager.createCommand(sql);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.HomeBuilder#createUpdateCommand(
	 * 	com.corona.data.ConnectionManager, com.corona.data.EntityMetaData, java.lang.String
	 * )
	 */
	@Override
	public Command createUpdateCommand(
			final ConnectionManager connectionManager, final EntityMetaData<?> config, final String filter) {
		
		String sql = "UPDATE " + config.getName();
		if (SET.matcher(filter).matches()) {
			sql = sql + " " + filter;
		} else {
			sql = sql + " SET " + filter;
		}
		return connectionManager.createCommand(sql);
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.ColumnDescriptor;
import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.EntityMetaData;
import com.corona.data.HomeBuilder;
import com.corona.data.Query;
import com.corona.data.ResultHandler;

/**
 * <p>This builder is used to build query and command for {@link Home} for SQL database </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLHomeBuilder implements HomeBuilder {

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.HomeBuilder#createInsertCommand(
	 * 	com.corona.data.ConnectionManager, com.corona.data.EntityMetaData
	 * )
	 */
	@Override
	public <E> Command createInsertCommand(final ConnectionManager connectionManager, final EntityMetaData<E> config) {
		
		String columns = "", params = "";
		for (ColumnDescriptor<E> descriptor : config.getColumnDescriptors().values()) {
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
			final ConnectionManager connectionManager, final EntityMetaData<E> config, final String filter
	) {
		SQLSelectBuilder builder = new SQLSelectBuilder(config, filter);
		return connectionManager.createQuery(ResultHandler.LONG, builder.getStatement("SELECT COUNT(*)"));
	}
}

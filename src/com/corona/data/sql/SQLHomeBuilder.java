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
		
		String values = "", params = "";
		for (ColumnDescriptor<E> descriptor : config.getColumnDescriptors()) {
			
			if (values.length() == 0) {
				values = values + descriptor.getName();
				params = params + "?";
			} else {
				values = values + ", " + descriptor.getName();
				params = params + ", ?";
			}
		}
		
		String sql = "INSERT INTO " + config.getName() + "(" + values + ") VALUES (" + params + ")";
		return connectionManager.createCommand(sql);
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.IndexDefinition;
import com.corona.data.annotation.Entity;

/**
 * <p>This class is used to store the index configuration in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLIndexDefinition implements IndexDefinition {

	/**
	 * the index name
	 */
	private String name;
	
	/**
	 * the SELECT SQL according this index
	 */
	private String selectSql;
	
	/**
	 * the DELETE SQL according to this index
	 */
	private String deleteSql;
	
	/**
	 * @param entity the entity annotation
	 * @param index the index annotation in entity class
	 */
	public SQLIndexDefinition(final Entity entity, final com.corona.data.annotation.Index index) {
		
		if (index.fields().length == 0) {
			throw new DataRuntimeException("Columns or fileds must be defined for index");
		}
		
		String where = "", orderby = "";
		for (String field : index.fields()) {
			where += ((where.length() == 0) ? field : (" AND " + field)) + " = ?";
			orderby += ((where.length() == 0) ? field : (", " + field));
		}
		
		this.name = index.name();
		this.selectSql = "SELECT * FROM " + entity.value() + " WHERE " + where + " " + orderby;
		this.deleteSql = "DELETE * FROM " + entity.value() + " WHERE " + where;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.IndexDefinition#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.IndexDefinition#getSearchQuery(com.corona.data.ConnectionManager)
	 */
	@Override
	public Command getSearchQuery(final ConnectionManager connectionManager) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.IndexDefinition#getDeleteCommand(com.corona.data.ConnectionManager)
	 */
	@Override
	public Command getDeleteCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.deleteSql);
	}
}

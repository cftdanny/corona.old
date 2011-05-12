/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.PrimaryKeyDefinition;
import com.corona.data.annotation.Entity;

/**
 * <p>This definition is used to store and create primary key configuration. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SQLPrimaryKeyDefinition implements PrimaryKeyDefinition {

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
	 * @param key the primary key annotation in entity class
	 */
	public SQLPrimaryKeyDefinition(final Entity entity, final com.corona.data.annotation.PrimaryKeyDefinition key) {
		
		this.selectSql = "SELECT * FROM " + entity.value() + " WHERE " + key.value() + " = ?";
		this.deleteSql = "DELETE * FROM " + entity.value() + " WHERE " + key.value() + " = ?";
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

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.Query;
import com.corona.data.UniqueKeyDescriptor;
import com.corona.data.annotation.Entity;
import com.corona.data.annotation.UniqueKey;

/**
 * <p>This class is used to create QUERY and COMMAND for unique key in a entity. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
class SQLUniqueKeyDescriptor<E> implements UniqueKeyDescriptor<E> {

	/**
	 * the unique key name
	 */
	private int id;
	
	/**
	 * the SELECT SQL script
	 */
	private String select;
	
	/**
	 * the DELETE SQL script
	 */
	private String delete;
	
	/**
	 * @param clazz the entity class that is annotated with {@link Entity} annotation
	 * @param entity the entity annotation
	 * @param uniqueKey the unique key annotation
	 */
	SQLUniqueKeyDescriptor(final Class<E> clazz, final Entity entity, final UniqueKey uniqueKey) {
		
		// check whether primary key for entity is empty or not
		if (uniqueKey.columns().length == 0) {
			throw new DataRuntimeException("Primary key for entity [{0}] is empty", clazz);
		}
		this.id = uniqueKey.id();
		
		// find table name by class that is annotated entity class
		String table = clazz.getSimpleName();
		if (entity.name().trim().length() != 0) {
			table = entity.name();
		}
		
		// find id (primary key) column by annotated field 
		String where = "";
		for (String column : uniqueKey.columns()) {
			
			if (where.length() == 0) {
				where = where + "(" + column + " = ?)";
			} else {
				where = where + " AND (" + column + " = ?)";
			}
		}
		
		this.select = "SELECT * FROM " + table + " WHERE " + where + " = ?";
		this.delete = "DELETE * FROM " + table + " WHERE " + where + " = ?";
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKeyDescriptor#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKeyDescriptor#getSelectQuery(com.corona.data.ConnectionManager)
	 */
	@Override
	public Query<E> getSelectQuery(final ConnectionManager connectionManager) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKeyDescriptor#getDeleteCommand(com.corona.data.ConnectionManager)
	 */
	@Override
	public Command getDeleteCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.delete);
	}
}

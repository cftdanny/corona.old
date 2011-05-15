/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.PrimaryKeyDescriptor;
import com.corona.data.Query;
import com.corona.data.annotation.Entity;
import com.corona.data.annotation.PrimaryKey;

/**
 * <p>This definition is used to store and create primary key configuration. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public class SQLPrimaryKeyDescriptor<E> implements PrimaryKeyDescriptor<E> {

	/**
	 * the SELECT SQL according this index
	 */
	private String selectSql;
	
	/**
	 * the DELETE SQL according to this index
	 */
	private String deleteSql;
	
	/**
	 * @param clazz the entity class that is annotated with {@link Entity} annotation
	 * @param entity the {@link Entity} annotation in entity class
	 * @param primaryKey the primary key that is annotated in entity class with {@link PrimaryKey} annotation
	 */
	public SQLPrimaryKeyDescriptor(final Class<E> clazz, final Entity entity, final PrimaryKey primaryKey) {
		
		// check whether primary key for entity is empty or not
		if (primaryKey.value().length == 0) {
			throw new DataRuntimeException("Primary key for entity [{0}] is empty", clazz);
		}
		
		// find table name by class that is annotated entity class
		String table = clazz.getSimpleName();
		if (entity.name().trim().length() != 0) {
			table = entity.name();
		}
		
		// find id (primary key) column by annotated field 
		String where = "";
		for (String column : primaryKey.value()) {
			
			if (where.length() == 0) {
				where = where + "(" + column + " = ?)";
			} else {
				where = where + " AND (" + column + " = ?)";
			}
		}
		
		this.selectSql = "SELECT * FROM " + table + " WHERE " + where + " = ?";
		this.deleteSql = "DELETE * FROM " + table + " WHERE " + where + " = ?";
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKeyDescriptor#getSelectQuery(com.corona.data.ConnectionManager)
	 */
	@Override
	public Query<E> getSelectQuery(final ConnectionManager connectionManager) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.IndexDescriptor#getDeleteCommand(com.corona.data.ConnectionManager)
	 */
	@Override
	public Command getDeleteCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.deleteSql);
	}
}

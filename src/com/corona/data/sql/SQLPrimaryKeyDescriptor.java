/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.BeanResultHandler;
import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.EntityMetaData;
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
	 * the parent entity MetaData
	 */
	private EntityMetaData<E> parent;
	
	/**
	 * the SELECT SQL according this index
	 */
	private String query;
	
	/**
	 * the DELETE SQL according to this index
	 */
	private String command;
	
	/**
	 * @param parent the parent entity MetaData
	 * @param entity the {@link Entity} annotation in entity class
	 * @param primaryKey the primary key that is annotated in entity class with {@link PrimaryKey} annotation
	 */
	public SQLPrimaryKeyDescriptor(final EntityMetaData<E> parent, final Entity entity, final PrimaryKey primaryKey) {

		this.parent = parent;

		// check whether primary key for entity is empty or not
		if (primaryKey.value().length == 0) {
			throw new DataRuntimeException(
					"Columns of primary key for entity [{0}] is empty", this.parent.getMappingClass()
			);
		}
		
		// find table name by class that is annotated entity class
		String table = parent.getMappingClass().getSimpleName();
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
		
		this.query = "SELECT * FROM " + table + " WHERE " + where + " = ?";
		this.command = "DELETE * FROM " + table + " WHERE " + where + " = ?";
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKeyDescriptor#createPrimaryKey(com.corona.data.ConnectionManager)
	 */
	@Override
	public <K> com.corona.data.PrimaryKey<K, E> createPrimaryKey(final ConnectionManager connectionManager) {
		return new SQLPrimaryKey<K, E>(this.getQuery(connectionManager), this.getCommand(connectionManager));
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new query for SELECT by primary key
	 */
	private Query<E> getQuery(final ConnectionManager connectionManager) {
		return connectionManager.createQuery(new BeanResultHandler<E>(this.parent), this.query);
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new command for DELETE by primary key
	 */
	private Command getCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.command);
	}
}

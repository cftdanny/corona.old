/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.BeanResultHandler;
import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.EntityMetaData;
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
	 * the parent entity MetaData
	 */
	private EntityMetaData<E> parent;

	/**
	 * the unique key name
	 */
	private int id;
	
	/**
	 * the SELECT SQL script
	 */
	private String query;
	
	/**
	 * the DELETE SQL script
	 */
	private String command;
	
	/**
	 * @param parent the parent entity MetaData
	 * @param entity the entity annotation
	 * @param uniqueKey the unique key annotation
	 */
	SQLUniqueKeyDescriptor(final EntityMetaData<E> parent, final Entity entity, final UniqueKey uniqueKey) {
		
		this.parent = parent;

		// check whether primary key for entity is empty or not
		if (uniqueKey.columns().length == 0) {
			throw new DataRuntimeException("Primary key for entity [{0}] is empty", this.parent.getMappingClass());
		}
		this.id = uniqueKey.id();
		
		// find table name by class that is annotated entity class
		String table = this.parent.getMappingClass().getSimpleName();
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
		
		this.query = "SELECT * FROM " + table + " WHERE " + where + " = ?";
		this.command = "DELETE * FROM " + table + " WHERE " + where + " = ?";
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
	 * @see com.corona.data.UniqueKeyDescriptor#createUniqueKey(com.corona.data.ConnectionManager)
	 */
	@Override
	public com.corona.data.UniqueKey<E> createUniqueKey(final ConnectionManager connectionManager) {
		return new SQLUniqueKey<E>(this.getQuery(connectionManager), this.getCommand(connectionManager));
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new query by SELECT SQL for unique key
	 */
	private Query<E> getQuery(final ConnectionManager connectionManager) {
		return connectionManager.createQuery(new BeanResultHandler<E>(this.parent), this.query);
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new query by DELETE SQL for unique key
	 */
	private Command getCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.command);
	}
}

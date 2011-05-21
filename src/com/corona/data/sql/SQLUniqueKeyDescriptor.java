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
import com.corona.data.annotation.UniqueKey;

/**
 * <p>The {@link UniqueKeyDescriptor} implementation for SQL database. </p>
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
	private Integer id;
	
	/**
	 * the SELECT SQL script
	 */
	private String selectSql;
	
	/**
	 * the DELETE SQL script
	 */
	private String deleteSql;
	
	/**
	 * @param parent the parent entity MetaData
	 * @param uniqueKey the unique key annotation
	 */
	SQLUniqueKeyDescriptor(final EntityMetaData<E> parent, final UniqueKey uniqueKey) {
		
		// check whether primary key for entity is empty or not
		this.parent = parent;
		this.id = uniqueKey.id();
		
		if (uniqueKey.columns().length == 0) {
			throw new DataRuntimeException("Unique key [{0}] in entity [{1}] is empty", 
					this.id, this.parent.getType()
			);
		}
		
		// find id (primary key) column by annotated field 
		String where = "";
		for (String column : uniqueKey.columns()) {
			where = where + ((where.length() == 0) ? "" : " AND ") + "(" + column.toUpperCase() + " = ?)";
		}
		
		this.selectSql = "SELECT * FROM " + this.parent.getName() + " WHERE " + where;
		this.deleteSql = "DELETE FROM " + this.parent.getName() + " WHERE " + where;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKeyDescriptor#getId()
	 */
	@Override
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.UniqueKeyDescriptor#create(com.corona.data.ConnectionManager)
	 */
	@Override
	public com.corona.data.UniqueKey<E> create(final ConnectionManager connectionManager) {
		return new SQLUniqueKey<E>(connectionManager, this);
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new query by SELECT SQL for unique key
	 */
	Query<E> createSelectQuery(final ConnectionManager connectionManager) {
		return connectionManager.createQuery(new BeanResultHandler<E>(this.parent), this.selectSql);
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new query by DELETE SQL for unique key
	 */
	Command createDeleteCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.deleteSql);
	}
}

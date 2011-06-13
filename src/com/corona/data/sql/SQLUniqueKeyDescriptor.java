/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.text.MessageFormat;

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
	 * the pooled SELECT query name
	 */
	private static final String POOLED_SELECT_NAME = "@UK{0}.SELECT@";

	/**
	 * the pooled DELETE command name
	 */
	private static final String POOLED_DELETE_NAME = "@UK{0}.DELETE@";
	
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
	 * @param connectionManager the connection manager
	 * @return the SQL connection manager
	 */
	private SQLConnectionManager getSQLConnectionManager(final ConnectionManager connectionManager) {
		return (SQLConnectionManager) connectionManager;
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new query by SELECT SQL for unique key
	 */
	Query<E> createSelectQuery(final ConnectionManager connectionManager) {
		
		// create query name by unique key id
		String name = MessageFormat.format(POOLED_SELECT_NAME, new Integer(this.id));
		
		// find query from connection manager pool first, if find, just return
		SQLQuery<E> query = this.getSQLConnectionManager(connectionManager).getPooledQuery(
				this.parent.getType(), name
		);
		if (query != null) {
			return query;
		}

		// if can not find query in pool, will create and register it
		query = (SQLQuery<E>) connectionManager.createQuery(
				new BeanResultHandler<E>(this.parent), this.selectSql
		);
		this.getSQLConnectionManager(connectionManager).addPooledQuery(
				this.parent.getType(), name, query
		);
		return query;
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new query by DELETE SQL for unique key
	 */
	Command createDeleteCommand(final ConnectionManager connectionManager) {

		// create query name by unique key id
		String name = MessageFormat.format(POOLED_DELETE_NAME, new Integer(this.id));

		// find command from connection manager pool first, if find, just return
		SQLCommand command = this.getSQLConnectionManager(connectionManager).getPooledCommand(
				this.parent.getType(), name
		); 
		if (command != null) {
			return command;
		}

		// if can not find command in pool, will create and register it
		command = (SQLCommand) connectionManager.createCommand(this.deleteSql);
		this.getSQLConnectionManager(connectionManager).addPooledCommand(
				this.parent.getType(), name, command
		);
		return command;
	}
}

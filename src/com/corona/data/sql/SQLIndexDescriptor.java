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
import com.corona.data.IndexDescriptor;
import com.corona.data.Query;
import com.corona.data.annotation.Index;

/**
 * <p>The {@link IndexDescriptor} implementation for SQL database. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public class SQLIndexDescriptor<E> implements IndexDescriptor<E> {

	/**
	 * the pooled SELECT query name
	 */
	private static final String POOLED_SELECT_NAME = "@IX{0}.SELECT@";

	/**
	 * the pooled DELETE command name
	 */
	private static final String POOLED_DELETE_NAME = "@IX{0}.DELETE@";

	/**
	 * the parent entity MetaData
	 */
	private EntityMetaData<E> parent;

	/**
	 * the index name
	 */
	private Integer id;
	
	/**
	 * the SELECT SQL according this index
	 */
	private String selectSql;
	
	/**
	 * the DELETE SQL according to this index
	 */
	private String deleteSql;
	
	/**
	 * @param parent the parent entity MetaData
	 * @param index the index annotation in entity class
	 */
	public SQLIndexDescriptor(final EntityMetaData<E> parent, final Index index) {
		
		// check whether columns of index is empty or not
		this.parent = parent;
		this.id = index.id();
		
		if (index.columns().length == 0) {
			throw new DataRuntimeException("Index [{0}] for entity [{1}] is empty", 
					this.id, this.parent.getType()
			);
		}
		
		// make where and order by SQL by table index definition
		String where = "", orderby = "";
		for (String column : index.columns()) {
			where = where + ((where.length() == 0) ? "" : " AND ") + "(" + column.toUpperCase() + " = ?)";
			orderby = orderby + ((orderby.length() == 0) ? "" : ", ") + column;
		}
		
		this.selectSql = "SELECT * FROM " + this.parent.getName() + " WHERE " + where + " ORDER BY " + orderby;
		this.deleteSql = "DELETE FROM " + this.parent.getName() + " WHERE " + where;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.IndexDescriptor#getId()
	 */
	@Override
	public Integer getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.IndexDescriptor#create(com.corona.data.ConnectionManager)
	 */
	@Override
	public com.corona.data.Index<E> create(final ConnectionManager connectionManager) {
		return new SQLIndex<E>(connectionManager, this);
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

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.BeanResultHandler;
import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.EntityMetaData;
import com.corona.data.IndexDescriptor;
import com.corona.data.Query;
import com.corona.data.annotation.Entity;
import com.corona.data.annotation.Index;

/**
 * <p>This class is used to store the index configuration in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public class SQLIndexDescriptor<E> implements IndexDescriptor<E> {

	/**
	 * the parent entity MetaData
	 */
	private EntityMetaData<E> parent;

	/**
	 * the index name
	 */
	private int id;
	
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
	 * @param entity the entity annotation
	 * @param index the index annotation in entity class
	 */
	public SQLIndexDescriptor(final EntityMetaData<E> parent, final Entity entity, final Index index) {
		
		this.parent = parent;
		
		// check whether columns of index is empty or not
		if (index.columns().length == 0) {
			throw new DataRuntimeException("Index for entity [{0}] is empty", this.parent.getMappingClass());
		}
		this.id = index.id();
		
		// find table name by class that is annotated entity class
		String table = this.parent.getMappingClass().getSimpleName();
		if (entity.name().trim().length() != 0) {
			table = entity.name();
		}

		// make where and order by SQL by table index definition
		String where = "", orderby = "";
		for (String field : index.columns()) {
			where += ((where.length() == 0) ? field : (" AND " + field)) + " = ?";
			orderby += ((where.length() == 0) ? field : (", " + field));
		}
		
		this.query = "SELECT * FROM " + table + " WHERE " + where + " " + orderby;
		this.command = "DELETE * FROM " + table + " WHERE " + where;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.IndexDescriptor#getId()
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.data.IndexDescriptor#createIndex(com.corona.data.ConnectionManager)
	 */
	@Override
	public com.corona.data.Index<E> createIndex(final ConnectionManager connectionManager) {
		return new SQLIndex<E>(this.getQuery(connectionManager), this.getCommand(connectionManager));
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

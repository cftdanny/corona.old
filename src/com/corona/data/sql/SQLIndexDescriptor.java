/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.IndexDescriptor;
import com.corona.data.annotation.Entity;

/**
 * <p>This class is used to store the index configuration in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public class SQLIndexDescriptor<E> implements IndexDescriptor<E> {

	/**
	 * the index name
	 */
	private int id;
	
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
	public SQLIndexDescriptor(final Entity entity, final com.corona.data.annotation.Index index) {
		
		// check whether columns of index is empty or not
		if (index.columns().length == 0) {
			throw new DataRuntimeException("Columns of index is empty");
		}
		
		String where = "", orderby = "";
		for (String field : index.columns()) {
			where += ((where.length() == 0) ? field : (" AND " + field)) + " = ?";
			orderby += ((where.length() == 0) ? field : (", " + field));
		}
		
		this.id = index.name();
		this.selectSql = "SELECT * FROM " + entity.name() + " WHERE " + where + " " + orderby;
		this.deleteSql = "DELETE * FROM " + entity.name() + " WHERE " + where;
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
	 * @see com.corona.data.IndexDescriptor#getSearchQuery(com.corona.data.ConnectionManager)
	 */
	@Override
	public Command getSearchQuery(final ConnectionManager connectionManager) {
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

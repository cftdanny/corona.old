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
			throw new DataRuntimeException(
					"Index [{0}] for entity [{1}] is empty", this.id, this.parent.getType()
			);
		}
		
		// make where and order by SQL by table index definition
		String where = "", orderby = "";
		for (String column : index.columns()) {
			where = where + ((where.length() == 0) ? "" : " AND ") + "(" + column.toUpperCase() + " = ?)";
			orderby = orderby + ((where.length() == 0) ? "" : ", ") + column;
		}
		
		this.selectSql = "SELECT * FROM " + this.parent.getName() + " WHERE " + where + " " + orderby;
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
	 * @see com.corona.data.IndexDescriptor#createIndex(com.corona.data.ConnectionManager)
	 */
	@Override
	public com.corona.data.Index<E> createIndex(final ConnectionManager connectionManager) {
		return new SQLIndex<E>(connectionManager, this);
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

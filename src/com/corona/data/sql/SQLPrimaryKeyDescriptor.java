/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

import java.util.ArrayList;
import java.util.List;

import com.corona.data.BeanResultHandler;
import com.corona.data.ColumnDescriptor;
import com.corona.data.Command;
import com.corona.data.ConnectionManager;
import com.corona.data.DataRuntimeException;
import com.corona.data.EntityMetaData;
import com.corona.data.PrimaryKeyDescriptor;
import com.corona.data.Query;
import com.corona.data.annotation.PrimaryKey;

/**
 * <p>This descriptor is used to SELECT, UPDATE, DELETE a single row by SQL statement in database 
 * by primary key. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity
 */
public class SQLPrimaryKeyDescriptor<E> implements PrimaryKeyDescriptor<E> {

	/**
	 * the entity configuration that defines this primary key
	 */
	private EntityMetaData<E> parent;
	
	/**
	 * all column descriptors for primary key
	 */
	private List<ColumnDescriptor<E>> updatableColumnDescriptors;
	
	/**
	 * all column descriptors for non-primary key 
	 */
	private List<ColumnDescriptor<E>> primaryKeyColumnDescriptors;
	
	/**
	 * the SELECT SQL and WHERE clause is created according to primary key
	 */
	private String selectSql;
	
	/**
	 * the DELETE SQL and WHERE clause is created according to primary key
	 */
	private String deleteSql;
	
	/**
	 * the UPDATE SQL and WHERE clause is created according to primary key
	 */
	private String updateSql = "";
	
	/**
	 * @param parent the parent entity MetaData
	 * @param primaryKey the primary key that is annotated in entity class with {@link PrimaryKey} annotation
	 */
	public SQLPrimaryKeyDescriptor(final EntityMetaData<E> parent, final PrimaryKey primaryKey) {

		// check whether primary key for entity is empty or not
		this.parent = parent;
		if (primaryKey.value().length == 0) {
			throw new DataRuntimeException("Primary key columns for entity [{0}] is empty", 
					this.parent.getMappingClass()
			);
		}
		
		// find all column descriptors that are defined in primary key
		this.primaryKeyColumnDescriptors = new ArrayList<ColumnDescriptor<E>>();
		for (String columnLabel : primaryKey.value()) {
			ColumnDescriptor<E> descriptor = this.parent.getColumnDescriptor(columnLabel);
			if (descriptor == null) {
				throw new DataRuntimeException("Column [{0}] is not defined in entity [{1}]", 
						columnLabel, this.parent.getMappingClass()
				);
			}
			this.primaryKeyColumnDescriptors.add(descriptor);
		}
		
		// create WHERE clause by primary key for SELECT, DELETE and UPDATE statement
		String where = "";
		for (ColumnDescriptor<E> descriptor : this.primaryKeyColumnDescriptors) {
			where = where + ((where.length() == 0) ? "" : " AND ") + "(" + descriptor.getName() + " = ?)";
		}
		
		// create UPDATE SQL statement by all column descriptors in entity and primary key
		this.updatableColumnDescriptors = new ArrayList<ColumnDescriptor<E>>();
		for (ColumnDescriptor<E> descriptor : this.parent.getColumnDescriptors()) {
			
			if (!this.primaryKeyColumnDescriptors.contains(descriptor)) {
				this.updateSql = this.updateSql + ((this.updateSql.length() == 0) ? "" : ", ");
				this.updateSql = this.updateSql + descriptor.getName() + " = ?";

				this.updatableColumnDescriptors.add(descriptor);
			}
		}

		// create UPDATE, DELETE, SELECT SQL statement for primary key
		this.updateSql = "UPDATE " + this.parent.getName() + " SET " + this.updateSql + " WHERE " + where;
		this.selectSql = "SELECT * FROM " + this.parent.getName() + " WHERE " + where;
		this.deleteSql = "DELETE FROM " + this.parent.getName() + " WHERE " + where;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKeyDescriptor#createPrimaryKey(com.corona.data.ConnectionManager)
	 */
	@Override
	public com.corona.data.PrimaryKey<E> createPrimaryKey(final ConnectionManager connectionManager) {
		return new SQLPrimaryKey<E>(connectionManager, this);
	}
	
	/**
	 * @return the updatableColumnDescriptors
	 */
	List<ColumnDescriptor<E>> getUpdatableColumnDescriptors() {
		return updatableColumnDescriptors;
	}

	/**
	 * @return the primaryKeyColumnDescriptors
	 */
	List<ColumnDescriptor<E>> getPrimaryKeyColumnDescriptors() {
		return primaryKeyColumnDescriptors;
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new query for SELECT by primary key
	 */
	Query<E> createSelectQuery(final ConnectionManager connectionManager) {
		return connectionManager.createQuery(new BeanResultHandler<E>(this.parent), this.selectSql);
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new command for DELETE by primary key
	 */
	Command createDeleteCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.deleteSql);
	}
	
	/**
	 * @param connectionManager the current connection manager
	 * @return the new command for UPDATE by primary key
	 */
	Command createUpdateCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.updateSql);
	}
}

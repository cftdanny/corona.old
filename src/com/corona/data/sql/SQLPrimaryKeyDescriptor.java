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
	 * all descriptors about column that its value can be updated by primary key
	 */
	private List<ColumnDescriptor<E>> updatableColumnDescriptors;
	
	/**
	 * all column descriptors for primary key 
	 */
	private List<ColumnDescriptor<E>> primaryKeyColumnDescriptors;
	
	/**
	 * the WHRER SQL according to primary key
	 */
	private String where = "";
	
	/**
	 * the SELECT SQL according this index
	 */
	private String selectStatement;
	
	/**
	 * the DELETE SQL according to this index
	 */
	private String deleteStatement;
	
	/**
	 * the update SQL
	 */
	private String updateStatement = "";
	
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
		for (ColumnDescriptor<E> descriptor : this.primaryKeyColumnDescriptors) {
			this.where = this.where + ((this.where.length() == 0) ? "" : " AND ");
			this.where = this.where + "(" + descriptor.getName() + " = ?)";
		}
		
		// create UPDATE SQL statement by all column descriptors in entity and primary key
		this.updatableColumnDescriptors = new ArrayList<ColumnDescriptor<E>>();
		for (ColumnDescriptor<E> descriptor : this.parent.getColumnDescriptors()) {
			
			if (!this.primaryKeyColumnDescriptors.contains(descriptor)) {
				this.updateStatement = this.updateStatement + ((this.updateStatement.length() == 0) ? "" : ", ");
				this.updateStatement = this.updateStatement + descriptor.getName() + " = ?";

				this.updatableColumnDescriptors.add(descriptor);
			}
		}

		// create UPDATE, DELETE, SELECT SQL statement for primary key
		this.updateStatement = "UPDATE " + this.parent.getName() + " SET " + this.updateStatement + " WHERE " + where;
		this.selectStatement = "SELECT * FROM " + this.parent.getName() + " WHERE " + where;
		this.deleteStatement = "DELETE FROM " + this.parent.getName() + " WHERE " + where;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.data.PrimaryKeyDescriptor#createPrimaryKey(com.corona.data.ConnectionManager)
	 */
	@Override
	public <K> com.corona.data.PrimaryKey<K, E> createPrimaryKey(final ConnectionManager connectionManager) {
		return new SQLPrimaryKey<K, E>(connectionManager, this);
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
		return connectionManager.createQuery(new BeanResultHandler<E>(this.parent), this.selectStatement);
	}

	/**
	 * @param connectionManager the current connection manager
	 * @return the new command for DELETE by primary key
	 */
	Command createDeleteCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.deleteStatement);
	}
	
	/**
	 * @param connectionManager the current connection manager
	 * @return the new command for UPDATE by primary key
	 */
	Command createUpdateCommand(final ConnectionManager connectionManager) {
		return connectionManager.createCommand(this.updateStatement);
	}
}

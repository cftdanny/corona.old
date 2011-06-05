/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import org.dbunit.dataset.ITable;

import com.corona.data.ConnectionManager;

/**
 * <p>A table from database or file </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Table {

	/**
	 * the table name
	 */
	private String name;
	
	/**
	 * the table metadata
	 */
	private TableMetaData metadata;
	
	/**
	 * the table data
	 */
	private ITable table;
	
	/**
	 * @param name the table name
	 * @param table the table data
	 */
	Table(final String name, final ITable table) {
		
		this.name = name;
		this.table = table;
		this.metadata = new TableMetaData(this.table.getTableMetaData());
	}
	
	/**
	 * @param connectionManager the current connection manager
	 * @param tableName the table name
	 * @exception Exception if fail
	 */
	public Table(final ConnectionManager connectionManager, final String tableName) throws Exception {
		this(connectionManager, null, tableName);
	}

	/**
	 * @param connectionManager the current connection manager
	 * @param schema the database schema
	 * @param tableName the table name
	 * @exception Exception if fail
	 */
	public Table(
			final ConnectionManager connectionManager, final String schema, final String tableName) throws Exception {
		
		this.name = tableName;
		this.table = DbUnitUtil.create(connectionManager, schema).createTable(tableName);
		this.metadata = new TableMetaData(this.table.getTableMetaData());
	}

	/**
	 * @return the loaded table
	 */
	ITable getSource() {
		return this.table;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the table metadata
	 */
	public TableMetaData getTableMetaData() {
		return this.metadata;
	}
	
	/**
	 * @return how many rows in this table
	 */
	int getCount() {
		return this.table.getRowCount();
	}
	
	/**
	 * @param row The row index, starting with 0
	 * @param column The name of the column
	 * @return the value
	 * @throws Exception if fail to get value
	 */
	public Object getValue(final int row, final String column) throws Exception {
		return this.table.getValue(row, column);
	}
}

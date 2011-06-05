/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.ITableMetaData;

/**
 * <p>The table metadata </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TableMetaData {

	/**
	 * the meta data
	 */
	private ITableMetaData metadata;
	
	/**
	 * @param metadata the meta data
	 */
	TableMetaData(final ITableMetaData metadata) {
		this.metadata = metadata;
	}
	
	/**
	 * @return the table name
	 */
	public String getTableName() {
		return this.metadata.getTableName();
	}
	
	/**
	 * @param columnName the column name
	 * @return the column index
	 * @throws Exception if fail
	 */
	public int getColumnIndex(final String columnName) throws Exception {
		return this.metadata.getColumnIndex(columnName);
	}
	
	/**
	 * @return all column names
	 * @throws Exception if fail
	 */
	public List<String> getColumnNames() throws Exception {
		
		List<String> names = new ArrayList<String>();
		for (Column column : this.metadata.getColumns()) {
			names.add(column.getColumnName());
		}
		return names;
	}
}

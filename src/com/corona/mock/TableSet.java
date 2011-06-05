/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.mock;

import java.util.HashMap;
import java.util.Map;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import com.corona.data.ConnectionManager;
import com.corona.data.ConnectionManagerFactory;

/**
 * <p>A group of tables from database or a resource file. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TableSet {

	/**
	 * the data set
	 */
	private IDataSet dataset;
	
	/**
	 * all tables in data set
	 */
	private Map<String, Table> tables = new HashMap<String, Table>();
	
	/**
	 * @param clazz the class that is used to load resource
	 * @param resource the resource file
	 * @exception Exception if fail to load resource
	 */
	public TableSet(final Class<?> clazz, final String resource) throws Exception {
		
		if (resource.endsWith(".xml")) {
			this.dataset = new XmlDataSet(clazz.getResourceAsStream(resource));
		} else {
			this.dataset = new XlsDataSet(clazz.getResourceAsStream(resource));
		}
		
		for (String name : this.dataset.getTableNames()) {
			this.add(new Table(name, this.dataset.getTable(name)));
		}
	}
	
	/**
	 * @param connectionManager the connection manager
	 * @param tableNames the table names
	 * @exception Exception if fail to get data from database
	 */
	public TableSet(final ConnectionManager connectionManager, final String... tableNames) throws Exception {
		this(connectionManager, null, tableNames);
	}

	/**
	 * @param connectionManager the connection manager
	 * @param schema the database schema
	 * @param tableNames the table names
	 * @exception Exception if fail to get data from database
	 */
	public TableSet(
			final ConnectionManager connectionManager, final String schema, final String... tableNames
	) throws Exception {
		
		this.dataset = DbUnitUtil.create(connectionManager, schema).createDataSet(tableNames);
		for (String name : this.dataset.getTableNames()) {
			this.add(new Table(name, this.dataset.getTable(name)));
		}
	}

	/**
	 * @return the loaded data set
	 */
	IDataSet getSource() {
		return this.dataset;
	}
	
	/**
	 * @param table the table
	 */
	private void add(final Table table) {
		this.tables.put(table.getName(), table);
	}
	
	/**
	 * @param name the table name
	 * @return the table
	 */
	public Table getTable(final String name) {
		return this.tables.get(name);
	}
	
	/**
	 * @param connectionManagerFactory the current connection manager factory
	 * @throws Exception the exception
	 */
	public void load(final ConnectionManagerFactory connectionManagerFactory) throws Exception {
		this.load(connectionManagerFactory, null);
	}

	/**
	 * @param connectionManagerFactory the current connection manager factory
	 * @param schema the database schema
	 * @throws Exception the exception
	 */
	public void load(final ConnectionManagerFactory connectionManagerFactory, final String schema) throws Exception {
		
		ConnectionManager connectionManager = connectionManagerFactory.open();
		try {
			DatabaseOperation.CLEAN_INSERT.execute(DbUnitUtil.create(connectionManager, schema), this.dataset);
		} catch (Exception e) {
			throw e;
		} finally {
			connectionManager.close();
		}
	}
}

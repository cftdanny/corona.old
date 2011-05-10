/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>The command is used to batch DELETE, INSERT or UPDATE data in data source. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Command {

	/**
	 * @return the command of specified data source
	 */
	Object getSource();
	
	/**
	 * <p>Close this command. For example, in SQL, it can be used to close prepared statement. </p>
	 */
	void close();

	/**
	 * <p>Batch delete records from database with statement and parameter values. Also return how many 
	 * records has been deleted. </p>
	 *
	 * @param args the value of parameter
	 * @return how many records have been deleted
	 */
	int delete(Object... args);

	/**
	 * <p>Batch delete records from database with statement, parameter names and parameter values. 
	 * Also return how many records has been deleted. </p>
	 *
	 * @param names the parameter names
	 * @param args the parameter values 
	 * @return how many records have been deleted
	 */
	int delete(String[] names, Object[] args);

	/**
	 * <p>Batch update records from database with statement and parameter values. Also return how many 
	 * records has been updated. </p>
	 *
	 * @param args the parameter values
	 * @return how many records has been updated
	 */
	int update(Object... args);

	/**
	 * <p>Batch update records from database with statement, parameter names and parameter values. 
	 * Also return how many records has been updated. </p>
	 *
	 * @param names the parameter names
	 * @param args the parameter values
	 * @return how many records has been updated
	 */
	int update(String[] names, Object[] args);

	/**
	 * <p>Insert a new records to database with statement and parameter values. Also return how many 
	 * records has been inserted. </p>
	 *
	 * @param args the parameter values
	 * @return how many records has been inserted
	 */
	int insert(Object... args);

	/**
	 * <p>Insert a new records to database with statement and parameter values. Also return how many 
	 * records has been inserted. </p>
	 *
	 * @param names the parameter names
	 * @param args the parameter values
	 * @return how many records has been inserted
	 */
	int insert(String[] names, Object[] args);
}

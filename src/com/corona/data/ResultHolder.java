/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This class is used to extract value from query result by column name. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ResultHolder {

	/**
	 * @return how many columns in query result
	 */
	int getColumnCount();
	
	/**
	 * @return all column labels in query result
	 */
	String[] getColumnLabels();
	
	/**
	 * @return whether there is row in query result
	 */
	boolean next();
	
	/**
	 * @param column the column label
	 * @return the value of column in current row
	 */
	Object get(String column);
	
	/**
	 * @param column the column index
	 * @return the value of column in current row
	 */
	Object get(int column);
	
	/**
	 * @param column the column label
	 * @return the long value of column in current row
	 */
	String getString(String column);
	
	/**
	 * @param column the column index
	 * @return the long value of column in current row
	 */
	String getString(int column);
	
	/**
	 * @param column the column label
	 * @return the long value of column in current row
	 */
	Integer getInteger(String column);
	
	/**
	 * @param column the column index
	 * @return the long value of column in current row
	 */
	Integer getInteger(int column);
	
	/**
	 * @param column the column label
	 * @return the long value of column in current row
	 */
	Long getLong(String column);
	
	/**
	 * @param column the column index
	 * @return the long value of column in current row
	 */
	Long getLong(int column);
}

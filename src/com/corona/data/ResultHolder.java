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
	 * @param columnLabel the column name or label
	 * @return the value of column in current row
	 */
	Object get(String columnLabel);
	
	/**
	 * @param columnIndex the column index
	 * @return the value of column in current row
	 */
	Object get(int columnIndex);
	
	/**
	 * @return whether there is row in query result
	 */
	boolean next();
}

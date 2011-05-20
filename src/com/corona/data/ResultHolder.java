/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * <p>This class is used to extract value from query result by column name. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ResultHolder {

	/**
	 * @return the source of query result
	 */
	Object getSource();
	
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
	Object getObject(String column);
	
	/**
	 * @param column the column index
	 * @return the value of column in current row
	 */
	Object getObject(int column);
	
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
	 * @param column the column index
	 * @return the byte value of column in current row
	 */
	Byte getByte(String column);
	
	/**
	 * @param column the column index
	 * @return the byte value of column in current row
	 */
	Byte getByte(int column);

	/**
	 * @param column the column index
	 * @return the short value of column in current row
	 */
	Short getShort(int column);
	
	/**
	 * @param column the column index
	 * @return the short value of column in current row
	 */
	Short getShort(String column);
	
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
	
	/**
	 * @param column the column index
	 * @return the float value of column in current row
	 */
	Float getFloat(String column);
	
	/**
	 * @param column the column index
	 * @return the float value of column in current row
	 */
	Float getFloat(int column);

	/**
	 * @param column the column index
	 * @return the double value of column in current row
	 */
	Double getDouble(String column);
	
	/**
	 * @param column the column index
	 * @return the double value of column in current row
	 */
	Double getDouble(int column);

	/**
	 * @param column the column index
	 * @return the boolean value of column in current row
	 */
	Boolean getBoolean(String column);
	
	/**
	 * @param column the column index
	 * @return the boolean value of column in current row
	 */
	Boolean getBoolean(int column);

	/**
	 * @param column the column index
	 * @return the date value of column in current row
	 */
	Date getDate(String column);
	
	/**
	 * @param column the column index
	 * @return the date value of column in current row
	 */
	Date getDate(int column);

	/**
	 * @param column the column index
	 * @return the timestamp value of column in current row
	 */
	Timestamp getTimestamp(String column);
	
	/**
	 * @param column the column index
	 * @return the timestamp value of column in current row
	 */
	Timestamp getTimestamp(int column);

	/**
	 * @param column the column index
	 * @return the time value of column in current row
	 */
	Time getTime(String column);
	
	/**
	 * @param column the column index
	 * @return the time value of column in current row
	 */
	Time getTime(int column);

	/**
	 * @param column the column index
	 * @return the URL value of column in current row
	 */
	URL getURL(String column);
	
	/**
	 * @param column the column index
	 * @return the URL value of column in current row
	 */
	URL getURL(int column);
}

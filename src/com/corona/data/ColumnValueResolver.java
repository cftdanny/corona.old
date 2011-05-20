/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This resolver is used to get value from query result by value type and column name. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface ColumnValueResolver {

	/**
	 * @param resultHolder the query result holder
	 * @param column the column label
	 * @return the value about this column
	 */
	Object get(ResultHolder resultHolder, String column);
}

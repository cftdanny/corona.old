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
public interface Extractor {

	/**
	 * @param name the column name
	 * @return the value for column of current row
	 */
	Object get(String name);
	
	/**
	 * @return whether there is row in query result
	 */
	boolean next();
}

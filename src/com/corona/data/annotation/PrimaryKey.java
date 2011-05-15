/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

/**
 * <p>This annotation is used to define primary key for entity of data source. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface PrimaryKey {

	/**
	 * the columns of primary key 
	 */
	String[] value();
}

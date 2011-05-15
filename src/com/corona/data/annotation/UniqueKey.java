/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

/**
 * <p>This annotation is used to define an unique key for entity in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface UniqueKey {

	/**
	 * the id of unique key  
	 */
	int id();
	
	/**
	 * all columns for unique key 
	 */
	String[] columns();
}

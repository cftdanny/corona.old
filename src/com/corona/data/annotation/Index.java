/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

/**
 * <p>This annotation is used to define an index for an entity. It will annotate an entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface Index {

	/**
	 * the index name 
	 */
	String name();
	
	/**
	 * the columns of index
	 */
	String[] columns();
}

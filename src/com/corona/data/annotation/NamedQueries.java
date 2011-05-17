/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;

/**
 * <p>This annotation is used to define named queries in a class. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface NamedQueries {

	/**
	 * all named queries 
	 */
	NamedQuery[] value();
}

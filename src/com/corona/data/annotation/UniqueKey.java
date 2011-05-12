/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.annotation;


/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface UniqueKey {

	/**
	 * the name of unique key constraint 
	 */
	String name();
	
	String[] fields();
}

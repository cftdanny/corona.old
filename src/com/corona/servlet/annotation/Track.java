/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.annotation;

/**
 * <p>This annotation is used to annotate produce method to indicate will track user access this request </p>
 *
 * @author $Author$
 * @version $Id$
 */
public @interface Track {

	/**
	 * defined track code
	 */
	String code() default "";
	
	/**
	 * the request parameter names will be stored 
	 */
	String[] value() default { };
}

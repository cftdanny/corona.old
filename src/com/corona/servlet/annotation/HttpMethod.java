/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>To check which type of HTTP request method will be matched for URL to be matched. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Selector
public @interface HttpMethod {

	/**
	 * <p>Support HTTP request method </p>
	 */
	public enum Action {
		
		/**
		 * HTTP GET method
		 */
		GET,
		
		/**
		 * HTTP GET method
		 */
		POST,
		
		/**
		 * HTTP GET method
		 */
		PUT,
		
		/**
		 * HTTP GET method
		 */
		DELETE
	}
	
	/**
	 * all supported request method 
	 */
	Action[] value();
}

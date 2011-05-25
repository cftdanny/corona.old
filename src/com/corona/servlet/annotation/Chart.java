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
 * <p>This annotation is used to annotate method to create Chart picture by JFreeChart </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Produce
public @interface Chart {

	/**
	 * the method name to create chart by outcome data 
	 */
	String value();
	
	/**
	 * the picture width 
	 */
	int width() default 200;
	
	/**
	 * the picture height
	 */
	int height() default 200;
	
	/**
	 * write picture as PNG or JPEG/JPG
	 */
	boolean png() default true;
	
	/**
	 * the image quality (0.0f to 1.0f) for JPEG/JPG picture
	 */
	float quality() default 0.75F;
	
	/**
	 * encode alpha for PNG picture
	 */
	boolean alpha() default true;
	
	/**
	 * the compression level for PNG picture 
	 */
	int compression() default 9;
}

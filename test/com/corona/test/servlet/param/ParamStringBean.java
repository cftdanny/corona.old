/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import com.corona.context.annotation.Optional;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.WebResource;

/**
 * <p>The bean to test inject value from request parameter </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class ParamStringBean {

	/**
	 * the a
	 */
	@Param @Optional private String a;

	/**
	 * @return the a
	 */
	public String getA() {
		return a;
	}
	
	/**
	 * @param a the a to set
	 */
	public void setA(final String a) {
		this.a = a;
	}
	
	/**
	 * @return this object
	 */
	@Same("/param/string.html")
	@FreeMaker("/param/string.ftl") public ParamStringBean html() {
		return this;
	}
}

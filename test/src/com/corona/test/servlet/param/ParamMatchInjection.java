/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.MatchParam;
import com.corona.servlet.annotation.Path;

/**
 * <p>This component is used to test inject match parameter </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class ParamMatchInjection {

	/**
	 * a
	 */
	private String a;
	
	/**
	 * b
	 */
	@MatchParam private Integer b;
	
	/**
	 * c
	 */
	private String c;
	
	/**
	 * @return the c
	 */
	public String getC() {
		return c;
	}
	
	/**
	 * @param c the c to set
	 */
	@MatchParam public void setC(final String c) {
		this.c = c;
	}
	
	/**
	 * @return the a
	 */
	public String getA() {
		return a;
	}
	
	/**
	 * @return the b
	 */
	public Integer getB() {
		return b;
	}

	/**
	 * @param pa b
	 * @return this
	 */
	@Path("/param/match/{a}/{b}/{c}.html")
	@FreeMaker("/param-match.ftl") 
	public ParamMatchInjection produce(@MatchParam("a") final String pa) {
		this.a = pa;
		return this;
	}
}

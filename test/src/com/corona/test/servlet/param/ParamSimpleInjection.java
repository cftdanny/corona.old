/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import java.util.List;

import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Same;

/**
 * <p>Test simple Param inject </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class ParamSimpleInjection {

	/**
	 * a
	 */
	private String a;
	
	/**
	 * b
	 */
	@Param private List<Integer> b;
	
	/**
	 * c
	 */
	private String[] c;
	
	/**
	 * d
	 */
	private Long d;

	/**
	 * @param d the d
	 */
	public ParamSimpleInjection(@Param("d") final Long d) {
		this.d = d;
	}
	
	/**
	 * @return the a
	 */
	public String getA() {
		return a;
	}
	
	/**
	 * @param a the a to set
	 */
	@Param public void setA(final String a) {
		this.a = a;
	}

	
	/**
	 * @return the b
	 */
	public List<Integer> getB() {
		return b;
	}
	
	/**
	 * @param b the b to set
	 */
	public void setB(final List<Integer> b) {
		this.b = b;
	}

	
	/**
	 * @return the c
	 */
	public String[] getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(final String[] c) {
		this.c = c;
	}
	
	/**
	 * @return the d
	 */
	public Long getD() {
		return d;
	}
	
	/**
	 * @param d the d to set
	 */
	public void setD(final Long d) {
		this.d = d;
	}

	/**
	 * @param pc c
	 * @return this
	 */
	@Same("/param/simple.html")
	@FreeMaker("/param-simple.ftl") 
	public ParamSimpleInjection produce(@Param("c") final String[] pc) {
		this.c = pc;
		return this;
	}
}

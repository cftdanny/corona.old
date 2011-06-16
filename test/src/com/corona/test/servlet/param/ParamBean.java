/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import java.util.List;

import com.corona.context.annotation.Optional;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Controller;

/**
 * <p>The bean to test inject value from request parameter </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class ParamBean {

	/**
	 * the a
	 */
	@Param @Optional private String a;

	/**
	 * the b
	 */
	@Param @Optional private Integer b;
	
	/**
	 * the c
	 */
	private Long c;
	
	/**
	 * the d
	 */
	private List<String> d;
	
	/**
	 * the e
	 */
	@Param private List<Integer> e;
	
	/**
	 * the f
	 */
	@Param private Integer[] f;
	
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
	 * @return the b
	 */
	public Integer getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(final Integer b) {
		this.b = b;
	}
	
	/**
	 * @return the c
	 */
	public Long getC() {
		return c;
	}
	
	/**
	 * @param c the c to set
	 */
	public void setC(final Long c) {
		this.c = c;
	}
	
	/**
	 * @return the d
	 */
	public List<String> getD() {
		return d;
	}
	
	/**
	 * @param d the d to set
	 */
	@Param("d") public void setD(final List<String> d) {
		this.d = d;
	}
	
	/**
	 * @return the e
	 */
	public List<Integer> getE() {
		return e;
	}
	
	/**
	 * @param e the e to set
	 */
	public void setE(final List<Integer> e) {
		this.e = e;
	}
	
	/**
	 * @return the f
	 */
	public Integer[] getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(final Integer[] f) {
		this.f = f;
	}

	/**
	 * @param ac the c
	 * @return this object
	 */
	@Same("/param/string.html")
	@FreeMaker("/param/string.ftl") 
	public ParamBean html(@Param("c") @Optional final Long ac) {
		this.c = ac;
		return this;
	}
}

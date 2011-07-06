/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.request;

import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.JsonRequest;
import com.corona.servlet.annotation.Same;

/**
 * <p>test inject by param </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class JsonRequestByFieldBean {

	/**
	 * the address
	 */
	@JsonRequest private Address address;
	
	/**
	 * @return the address
	 */
	@Same("/json/field.html")
	@FreeMaker("/json-inject.ftl")
	public Address byField() {
		return this.address;
	}
}

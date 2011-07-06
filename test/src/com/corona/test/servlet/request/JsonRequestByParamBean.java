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
public class JsonRequestByParamBean {

	/**
	 * @param address the address
	 * @return the address
	 */
	@Same("/json/param.html")
	@FreeMaker("/json-inject.ftl")
	public Address byParam(@JsonRequest final Address address) {
		return address;
	}
}

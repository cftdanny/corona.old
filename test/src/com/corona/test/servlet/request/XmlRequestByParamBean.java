/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.request;

import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.XmlRequest;

/**
 * <p>test inject by param </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class XmlRequestByParamBean {

	/**
	 * @param address the address
	 * @return the address
	 */
	@Same("/xml/param.html")
	@FreeMaker("/xml-inject.ftl")
	public Address byParam(@XmlRequest final Address address) {
		return address;
	}
}

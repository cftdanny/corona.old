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
public class JsonRequestByPropertyBean {

	/**
	 * the address
	 */
	private Address address;
	
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	
	/**
	 * @param address the address to set
	 */
	@JsonRequest public void setAddress(final Address address) {
		this.address = address;
	}

	/**
	 * @return the address
	 */
	@Same("/json/property.html")
	@FreeMaker("/json-inject.ftl")
	public Address byField() {
		return this.address;
	}
}

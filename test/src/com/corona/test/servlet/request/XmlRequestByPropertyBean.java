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
public class XmlRequestByPropertyBean {

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
	@XmlRequest public void setAddress(final Address address) {
		this.address = address;
	}

	/**
	 * @return the address
	 */
	@Same("/xml/property.html")
	@FreeMaker("/xml-inject.ftl")
	public Address byField() {
		return this.address;
	}
}

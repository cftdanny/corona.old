/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.HttpMethod;
import com.corona.servlet.annotation.HttpMethod.Action;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Controller;

/**
 * <p>This page is used to create HTML page by FreeMaker </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class IndexHtml {

	/**
	 * create HTML page by FreeMaker
	 */
	@Same("/index.html")
	@FreeMaker("/index.ftl") 
	@HttpMethod(Action.GET) public void produce() {
		// usually, collect data for page in this method
	}
}

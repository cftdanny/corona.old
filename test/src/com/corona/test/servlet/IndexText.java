/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.annotation.Inject;
import com.corona.servlet.annotation.ContentType;
import com.corona.servlet.annotation.Expiration;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Service;
import com.corona.servlet.annotation.Controller;

/**
 * <p>This page is used to test service producer </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class IndexText {

	/**
	 * the HTTP SERVLET response
	 */
	@Inject private HttpServletResponse response;

	/**
	 * @throws Exception if fail to produce output text
	 */
	@Same("/index.txt")
	@ContentType("text/plan")
	@Expiration(1000)
	@Service public void produce() throws Exception {
		this.response.getOutputStream().println("Hello, Corona!");
	}
}

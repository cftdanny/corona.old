/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.annotation.Inject;
import com.corona.servlet.annotation.ContentType;
import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Service;

/**
 * <p>This component is used to test component handler </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller("/child")
public class ComponentHandlerHtml {

	/**
	 * the HTTP SERVLET response
	 */
	@Inject private HttpServletResponse response;

	/**
	 * @throws Exception if fail to produce output text
	 */
	@Same("/1.txt")
	@ContentType("text/plan")
	@Service public void produce1() throws Exception {
		this.response.getOutputStream().println("Child Page 1!");
	}

	/**
	 * @throws Exception if fail to produce output text
	 */
	@Same("/2.txt")
	@ContentType("text/plan")
	@Service public void produce2() throws Exception {
		this.response.getOutputStream().println("Child Page 2!");
	}
}

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
 * <p>Test how to inject session variable </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller("/session")
public class SessionVariableHtml {

	/**
	 * the session variable
	 */
	@Inject private SessionVariable variable;
	
	/**
	 * the HTTP SERVLET response
	 */
	@Inject private HttpServletResponse response;

	/**
	 * @throws Exception if fail to produce output text
	 */
	@Same("/store")
	@ContentType("text/html")
	@Service public void store() throws Exception {
		this.variable.setValue("SESSION");
		this.response.getOutputStream().println(
				"<html>"
					+ "<body>"
						+ "<a id=load href=load>" + this.variable.getValue() + "</a>"
					+ "</body>"
				+ "</html>"
		);
	}

	/**
	 * @throws Exception if fail to produce output text
	 */
	@Same("/load")
	@ContentType("text/plan")
	@Service public void load() throws Exception {
		this.response.getOutputStream().println("[" + this.variable.getValue() + "]");
	}
}

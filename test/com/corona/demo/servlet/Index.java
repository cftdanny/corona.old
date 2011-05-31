/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.demo.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.annotation.Inject;
import com.corona.servlet.annotation.ContentType;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Service;
import com.corona.servlet.annotation.WebResource;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class Index {

	/**
	 * the servlet response
	 */
	@Inject private HttpServletResponse response;
	
	/**
	 * @throws IOException if fail to write HTTP
	 */
	@Same("/test")
	@ContentType("text/plain")
	@Service public void test() throws IOException {
		
		OutputStream out = this.response.getOutputStream();
		out.write("Hello, world".getBytes());
		out.flush();
	}
}

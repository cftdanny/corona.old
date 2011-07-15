/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.track;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.annotation.Inject;
import com.corona.servlet.annotation.ContentType;
import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Service;
import com.corona.servlet.annotation.Track;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class TrackPage {

	/**
	 * the HTTP SERVLET response
	 */
	@Inject private HttpServletResponse response;

	/**
	 * @throws Exception if fail to produce output text
	 */
	@Same("/track")
	@ContentType("text/plan")
	@Track
	@Service public void produce() throws Exception {
		this.response.getOutputStream().println("[track]");
	}
}

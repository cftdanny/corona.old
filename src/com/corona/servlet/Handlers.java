/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The generators will store all matchers and producers, and find matched generator by request URL. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Handlers {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(Handlers.class);
	
	/**
	 * all content resolvers
	 */
	private List<Handler> handlers = new ArrayList<Handler>();
	
	/**
	 * @param handlers all HTTP request handlers
	 */
	Handlers(final List<Handler> handlers) {
		this.handlers.addAll(handlers);
	}
	
	/**
	 * @param uri the URI to check whether it is protected
	 * @return <code>true</code> if this URI is protected
	 */
	private boolean isProtectedPages(final String uri) {
		
		if (uri.startsWith("/WEB-INF/") || uri.equals("/WEB-INF")) {
			return true;
		}
		if (uri.startsWith("/META-INF/") || uri.equals("/META-INF")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param request the HTTP SERVLET request
	 * @param response the HTTP SERVLET response
	 * @exception HandleException if fail to create HTTP response
	 */
	void handle(final HttpServletRequest request, final HttpServletResponse response) throws HandleException {
		
		// check whether request path is protected page or not
		String path = request.getPathInfo();
		if ((path == null) || this.isProtectedPages(path.toUpperCase())) {
			
			this.logger.debug("HTT request path [{0}] is protected by application", path);
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found!");
			} catch (Exception e) {
				this.logger.error("Fail to send \"Page Not Found\" command to client", e);
				throw new HandleException("Fail to send \"Page Not Found\" command to client", e);
			}
			return;
		}
		
		// test whether request path is static path or not, if yes, fill out by static resource
		InputStream in = request.getSession().getServletContext().getResourceAsStream(path);
		if (in != null) {

			try {
				OutputStream out = response.getOutputStream();
				for (int c = in.read(); c != -1; c = in.read()) {
					out.write(c);
				}
			} catch (Exception e) {
				this.logger.error("Fail to fill response stream by file [{0}]", e, path);
				throw new HandleException("Fail to fill response stream by file [{0}]", e, path);
			} finally {
				
				try {
					in.close();
				} catch (IOException e) {
					this.logger.error("Fail to close file [{0}], just skip this exception", e, path);
				}
			}
			return;
		}
		
		// try to match request path with handler by handler
		for (Handler handler : this.handlers) {
			
			MatchResult result = handler.getMatcher().match(request);
			if (result != null) {
				this.logger.debug("Create HTTP response for path [{0}] by handler [{1}]", path, handler);
				try {
					handler.handle(result, request, response);
				} catch (HandleException e) {
					this.logger.error("Fail to handle HTTP request by handler [{0}]", handler);
					throw e;
				}
				return;
			}
		}
		
		try {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found!");
		} catch (Exception e) {
			this.logger.error("Fail to send page not found command to client", e);
			throw new HandleException("Fail to send page not found command to client", e);
		}
	}
}

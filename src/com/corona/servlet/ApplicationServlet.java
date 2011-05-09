/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManagerFactory;

/**
 * <p>This SERVLET is used to create HTTP response by a group of handlers. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ApplicationServlet implements Servlet, Serializable {

	/**
	 * The Serial Version UID 
	 */
	private static final long serialVersionUID = -1259616986665281178L;

	/**
	 * the SERVLET configuration
	 */
	private transient ServletConfig servletConfig;
	
	/**
	 * The handlers to create HTTP response
	 */
	private Handlers handlers;
	
	/**
	 * {@inheritDoc}
	 * @see javax.servlet.Servlet#getServletConfig()
	 */
	@Override
	public ServletConfig getServletConfig() {
		return this.servletConfig;
	}

	/**
	 * @return the SERVLET context
	 */
	private ServletContext getServletContext() {
		return this.getServletConfig().getServletContext();
	}
	
	/**
	 * {@inheritDoc}
	 * @see javax.servlet.Servlet#getServletInfo()
	 */
	@Override
	public String getServletInfo() {
		return this.getClass().getName();
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		
		this.servletConfig = config;
		
		// find context manager factory from SERVLET context
		ContextManagerFactory contextManagerFactory = ServletUtil.getContextManagerFactory(this.getServletContext());
		if (contextManagerFactory == null) {
			this.getServletContext().log("Context manager factory is not loaded, configure and load it first");
			throw new ServletException("Context manager factory is not loaded, configure and load it first");
		}
		
		// find handlers from SERVLET context
		// initialize handler with context manager factory
		this.handlers = ServletUtil.getHandlers(this.getServletContext()); 
		if (this.handlers == null) {
			this.getServletContext().log("Application handlers is not loaded, configure and load it first");
			throw new ServletException("Application handlers is not loaded, configure and load it first");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void service(
			final ServletRequest request, final ServletResponse response) throws ServletException, IOException {
		
		try {
			this.handlers.handle((HttpServletRequest) request, (HttpServletResponse) response);
		} catch (HandleException e) {
			
			String path = ((HttpServletRequest) request).getPathInfo();
			this.getServletContext().log("Fail to create create response with request path [" + path + "]", e); 
			throw new ServletException("Fail to create create response with request path [" + path + "]", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.Servlet#destroy()
	 */
	@Override
	public void destroy() {
		// do nothing
	}
}

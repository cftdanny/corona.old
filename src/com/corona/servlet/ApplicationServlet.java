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
import com.corona.servlet.multipart.MultipartRequestImpl;
import com.corona.util.ServletUtil;
import com.corona.util.StringUtil;

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
	 * whether create temporary file to receive multipart request
	 */
	private static final String UPLOAD_ENABLE_UPLOAD_FILES = "com.corona.fileUpload.enableUploadFiles";

	/**
	 * whether create temporary file to receive multipart request
	 */
	private static final String UPLOAD_CREATE_TEMP_FILES = "com.corona.fileUpload.createTempFiles";
	
	/**
	 * the max request size for upload file multipart request
	 */
	private static final String UPLOAD_MAX_REQUEST_SIZE = "com.corona.fileUpload.maxRequestSize";
	
	/**
	 * The multipart flag
	 */
	private static final String MULTIPART = "multipart/";
	
	/**
	 * the SERVLET configuration
	 */
	private transient ServletConfig servletConfig;
	
	/**
	 * whether enable upload files
	 */
	private boolean enableUploadFiles;
	
	/**
	 * whether create temporary file to receive multipart request
	 */
	private boolean createTempFiles;
	
	/**
	 * the max request size for upload file multipart request
	 */
	private int maxRequestSize;
	
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
		
		// find handlers from SERVLET context, initialize handler with context manager factory
		this.handlers = ServletUtil.getHandlers(this.getServletContext()); 
		if (this.handlers == null) {
			this.getServletContext().log("Application handlers is not loaded, configure and load it first");
			throw new ServletException("Application handlers is not loaded, configure and load it first");
		}

		// read whether enable upload files, default is true
		try {
			String value = config.getInitParameter(UPLOAD_ENABLE_UPLOAD_FILES);
			if (StringUtil.isBlank(value)) {
				this.enableUploadFiles = true;
			} else {
				this.enableUploadFiles = Boolean.parseBoolean(value);
			}
		} catch (Exception e) {
			this.enableUploadFiles = true;
		}

		// read whether create temporary files in order to receive uploading file, default is true
		try {
			String value = config.getInitParameter(UPLOAD_CREATE_TEMP_FILES);
			if (StringUtil.isBlank(value)) {
				this.createTempFiles = true;
			} else {
				this.createTempFiles = Boolean.parseBoolean(value);
			}
		} catch (Exception e) {
			this.createTempFiles = true;
		}
		
		// the max request size for multipart request
		try {
			this.maxRequestSize = Integer.parseInt(config.getInitParameter(UPLOAD_MAX_REQUEST_SIZE));
		} catch (Exception e) {
			this.maxRequestSize = 2 * 1024 * 1024;
		}
	}

	/**
	 * @param request the request
	 * @return whether request is multipart request
	 */
	private boolean isMultipartRequest(final HttpServletRequest request) {
		
		if (!"post".equals(request.getMethod().toLowerCase())) {
			return false;
		}
	      
		String contentType = request.getContentType();
		if (contentType == null) {
			return false;
		} 
		return contentType.toLowerCase().startsWith(MULTIPART);
	}

	/**
	 * {@inheritDoc}
	 * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void service(
			final ServletRequest request, final ServletResponse response) throws ServletException, IOException {

		// check whether request is multipart request, if yes, wrap it to multipart request
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (this.enableUploadFiles && this.isMultipartRequest(httpRequest)) {
			httpRequest = new MultipartRequestImpl(httpRequest, this.createTempFiles, this.maxRequestSize);
			httpRequest.getParameterNames();
		}
		
		try {
			this.handlers.handle(httpRequest, (HttpServletResponse) response);
		} catch (Exception e) {

			String path = ((HttpServletRequest) request).getPathInfo();
			this.getServletContext().log(
					"Fail to create create response with request path [" + path + "]", e
			); 
			throw new ServletException("Internal Server Error: " + path, e);
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

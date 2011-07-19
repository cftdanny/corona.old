/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.ApplicationServlet;

/**
 * <p>The SERVLET will load logged in user from cookie and create wrapper SERVLET request by this user </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SecurityApplicationServlet extends ApplicationServlet {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -8748769396309482600L;

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(SecurityApplicationServlet.class);
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ApplicationServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		
		super.init(config);
		try {
			SimpleIdentityHelper.init(config);
		} catch (Exception e) {
			throw new ServletException("Fail to create encryptor and decryptor from SERVLET context", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.ApplicationServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	public void service(
			final ServletRequest request, final ServletResponse response) throws ServletException, IOException {
		
		User user = null;
		try {
			user = SimpleIdentityHelper.load((HttpServletRequest) request);
		} catch (Exception e) {
			this.logger.error("Fail to load logged in user information from request cookies, just skip it", e);
		}
		
		// if user logged in, will create wrapper request with logged in user information
		if (user != null) {
			super.service(new SecurityServletRequest((HttpServletRequest) request, user), response);
		} else {
			super.service(request, response);
		}
	}
}

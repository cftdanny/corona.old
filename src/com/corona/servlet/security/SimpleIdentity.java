/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.context.annotation.Inject;
import com.corona.crypto.CypherException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This identity will store logged user information to cookie </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SimpleIdentity implements Identity {
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(SimpleIdentity.class);
	
	/**
	 * the current context manager
	 */
	@Inject private ContextManager contextManager;

	/**
	 * the HTTP SERVLET request
	 */
	@Inject private HttpServletRequest request;

	/**
	 * the HTTP SERVLET response
	 */
	@Inject private HttpServletResponse response;

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.Identity#getUser()
	 */
	@Override
	public User getUser() {
		
		Principal userPrincipal = this.request.getUserPrincipal();
		if (userPrincipal instanceof SimpleUserPrincipal) {
			SimpleUserPrincipal simpleUserPrincipal = (SimpleUserPrincipal) userPrincipal;
			
			User user = new User(simpleUserPrincipal.getName());
			for (String role : simpleUserPrincipal.getRoles()) {
				user.getRoles().add(new Role(role));
			}
			return user;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.Identity#isLoggedIn()
	 */
	@Override
	public boolean isLoggedIn() {
		return null != this.request.getRemoteHost();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.Identity#login()
	 */
	@Override
	public User login() {
		
		Authenticator authenticator = this.contextManager.get(Authenticator.class);
		if (authenticator == null) {
			this.logger.error("Authenticator is not configured in context, should register it first");
			throw new ValueException("Authenticator is not configured in context, should register it first");
		}
		
		User user = authenticator.authenticate();
		if (user != null) {
			
			try {
				SimpleIdentityHelper.save(request, response, user);
			} catch (CypherException e) {
				this.logger.error("Fail to encrypt logged in user information", e);
				user = null;
			}
		}
		return user;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.Identity#logout()
	 */
	@Override
	public void logout() {
		SimpleIdentityHelper.delete(request, response);
	}
}

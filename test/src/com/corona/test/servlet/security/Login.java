/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.security;

import com.corona.context.ContextManager;
import com.corona.context.annotation.Inject;
import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.Expiration;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Redirect;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.security.Identity;
import com.corona.servlet.security.User;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class Login {

	/**
	 * the current context manager
	 */
	@Inject private ContextManager contextManager;
	
	/**
	 * display login page
	 */
	@Same("/html/login.html")
	@FreeMaker("/login.ftl")
	public void loginPage() {
		// do nothing
	}

	/**
	 * display login page
	 * @return current user
	 */
	@Same("/html/logged.html")
	@FreeMaker("/logged.ftl")
	public User loggedPage() {
		return this.contextManager.get(Identity.class).getUser();
	}

	/**
	 * @return page redirect to
	 */
	@Same("/html/login")
	@Redirect("logged.html")
	public String login() {

		Identity identity = this.contextManager.get(Identity.class);
		return (identity.login() == null) ? "html/login.html" : null;
	}
	
	/**
	 * log out
	 */
	@Expiration
	@Same("/html/logout")
	@Redirect("login.html")
	public void logout() {
		this.contextManager.get(Identity.class).logout();
	}
}

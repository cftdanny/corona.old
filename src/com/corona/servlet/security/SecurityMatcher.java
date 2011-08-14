/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import javax.servlet.http.HttpServletRequest;

import com.corona.servlet.MatchResult;
import com.corona.servlet.Matcher;

/**
 * <p>This matcher is used to check whether user is logged in before trys to access resource </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class SecurityMatcher implements Matcher {

	/**
	 * the matching priority
	 */
	private int priority = 1;
	
	/**
	 * the login page
	 */
	private String loginPage = "/login.html";
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#getPriority()
	 */
	@Override
	public int getPriority() {
		return this.priority;
	}

	/**
	 * @param priority the matching priority
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}
	
	/**
	 * @return the login page
	 */
	public String getLoginPage() {
		return loginPage;
	}
	
	/**
	 * @param loginPage the login page to set
	 */
	public void setLoginPage(final String loginPage) {
		this.loginPage = loginPage;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#match(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public MatchResult match(final String path, final HttpServletRequest request) {
		return (this.loginPage.equals(path) || (request.getRemoteUser() != null)) ? null : new MatchResult(path);
	}
}

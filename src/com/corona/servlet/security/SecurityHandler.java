/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.context.annotation.Application;
import com.corona.servlet.HandleException;
import com.corona.servlet.Handler;
import com.corona.servlet.MatchResult;
import com.corona.servlet.Matcher;

/**
 * <p>This handler is used to make sure user is logged in when trys to access some resources </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Application
public class SecurityHandler implements Handler {

	/**
	 * the matcher
	 */
	private Matcher matcher = new SecurityMatcher();
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Handler#getMatcher()
	 */
	@Override
	public Matcher getMatcher() {
		return this.matcher;
	}

	/**
	 * @param matcher the new matcher to check whether user is logged in
	 */
	public void setMatcher(final Matcher matcher) {
		this.matcher = matcher;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Handler#handle(
	 * 	com.corona.servlet.MatchResult, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse
	 * )
	 */
	@Override
	public void handle(
			final MatchResult result, final HttpServletRequest request, final HttpServletResponse response
	) throws HandleException {
		
		try {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			throw new HandleException("Fail to send unauthorized error to client");
		}
	}
}

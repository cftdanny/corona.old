/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.restricting.loggedin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.servlet.HandleException;
import com.corona.servlet.Restrictor;
import com.corona.util.StringUtil;

/**
 * <p>Only allow logged in user to access resource </p>
 *
 * @author $Author$
 * @version $Id$
 */
class LoggedInRestrictor implements Restrictor {

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Restrictor#restrict(
	 * 	java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse
	 * )
	 */
	@Override
	public boolean restrict(
			final String path, final HttpServletRequest request, final HttpServletResponse response
	) throws HandleException {
		
		
		if (StringUtil.isBlank(request.getRemoteUser())) {
			
			try {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} catch (IOException e) {
				throw new HandleException("Fail send UNAUTHORIZED error command to client", e);
			}
			return true;
		} else {
			return false;
		}
	}
}

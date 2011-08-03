/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.restricting.securedrequest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.servlet.HandleException;
import com.corona.servlet.Restrictor;

/**
 * <p>Only allow access resource by HTTPS </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SecuredRequestRestrictor implements Restrictor {

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
		
		if (!request.isSecure()) {
			
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				throw new HandleException("Fail send FORBIDDEN error command to client", e);
			}
			return true;
		} else {
			return false;
		}
	}
}

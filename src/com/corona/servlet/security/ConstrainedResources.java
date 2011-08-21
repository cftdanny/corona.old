/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;

import com.corona.servlet.MatchResult;
import com.corona.util.XmlUtil;

/**
 * <p>The resources that can't be accessed by user </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ConstrainedResources extends ResourcePatterns {

	/**
	 * the status code to send if matches any resource
	 */
	private int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
	
	/**
	 * the redirect to page if matches any resource
	 */
	private String redirectPage = null;
	
	/**
	 * @param descriptor the descriptor
	 */
	ConstrainedResources(final Element descriptor) {
		super(descriptor);
		
		Element redirect = XmlUtil.getChildElement(descriptor, "redirect-to");
		if (redirect != null) {
			this.redirectPage = redirect.getTextContent();
		} else {
			Element status = XmlUtil.getChildElement(descriptor, "status-code");
			if (status != null) {
				this.statusCode = Integer.parseInt(status.getTextContent());
			}			
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.ResourcePatterns#getMatchResult(java.lang.String)
	 */
	@Override
	public MatchResult getMatchResult(final String path) {
		
		MatchResult result = new MatchResult(path);
		if (this.redirectPage != null) {
			result.set(REDIRECT_PAGE, this.redirectPage);
		} else {
			result.set(STATUS_CODE, this.statusCode);
		}
		return result;
	}
}

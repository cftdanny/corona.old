/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import java.util.regex.Pattern;

import org.w3c.dom.Element;

/**
 * <p>This pattern is used to match request URI with REGEX expression </p>
 *
 * @author $Author$
 * @version $Id$
 */
class RegexResourcePattern implements ResourcePattern {

	/**
	 * the resource URI REGEX pattern
	 */
	private Pattern regex;
	
	/**
	 * @param descriptor the pattern descriptor
	 */
	RegexResourcePattern(final Element descriptor) {
		this.regex = Pattern.compile(descriptor.getTextContent());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.ResourcePattern#match(java.lang.String)
	 */
	@Override
	public boolean match(final String path) {
		return this.regex.matcher(path).matches();
	}
}

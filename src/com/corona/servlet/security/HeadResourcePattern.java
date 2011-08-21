/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import org.w3c.dom.Element;

/**
 * <p>This pattern is used to match request URI with head string </p>
 *
 * @author $Author$
 * @version $Id$
 */
class HeadResourcePattern implements ResourcePattern {

	/**
	 * the resource URI head
	 */
	private String head;

	/**
	 * @param descriptor the pattern descriptor
	 */
	HeadResourcePattern(final Element descriptor) {
		this.head = descriptor.getTextContent();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.security.ResourcePattern#match(java.lang.String)
	 */
	@Override
	public boolean match(final String path) {
		return path.startsWith(this.head);
	}
}

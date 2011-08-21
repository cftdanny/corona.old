/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.security;

import org.w3c.dom.Element;

/**
 * <p>The resources that can be accessed by users </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class UnconstrainedResources extends ResourcePatterns {

	/**
	 * @param descriptor the descriptor
	 */
	UnconstrainedResources(final Element descriptor) {
		super(descriptor);
	}
}

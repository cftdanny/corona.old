/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.definition;

import org.w3c.dom.Element;

/**
 * <p>The end node that indicates process will be ended </p>
 *
 * @author $Author$
 * @version $Id$
 */
class End extends Node {

	/**
	 * @param descriptor the node descriptor
	 */
	End(final Element descriptor) {
		super(descriptor);
	}
}

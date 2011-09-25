/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import org.w3c.dom.Element;

import com.corona.bpm.Activity;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Task extends TransitableActivity {

	/**
	 * @param descriptor the XML descriptor for task
	 */
	Task(final Element descriptor) {
		super(descriptor);
	}
}

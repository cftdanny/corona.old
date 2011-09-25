/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import java.util.Map;

import org.w3c.dom.Element;

import com.corona.bpm.Activity;

/**
 * <p>The end node that indicates process will be ended </p>
 *
 * @author $Author$
 * @version $Id$
 */
class End extends AbstractActivity {

	/**
	 * @param descriptor the node descriptor
	 */
	End(final Element descriptor) {
		super(descriptor);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.Activity#setChildren(java.util.Map)
	 */
	@Override
	public void setChildren(final Map<String, Activity> activities) {
	}
}

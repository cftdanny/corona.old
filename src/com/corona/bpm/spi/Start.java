/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import java.util.Map;

import org.w3c.dom.Element;

import com.corona.bpm.Context;
import com.corona.bpm.ProcessException;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Start extends TransitableActivity {

	/**
	 * @param descriptor the XML descriptor for start
	 */
	Start(final Element descriptor) {
		super(descriptor);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.bpm.spi.AbstractActivity#enter(com.corona.bpm.Context)
	 */
	@Override
	public void enter(final Context context) throws ProcessException {
		
		for (Transition transition : this.getTransitions()) {
			if (transition.match(context)) {
				
			}
		}
		throw new ProcessException("");
	}
}

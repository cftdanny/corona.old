/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm.spi;

import org.w3c.dom.Element;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Decision extends TransitableActivity {

	/**
	 * @param descriptor the XML descriptor for decision
	 */
	Decision(final Element descriptor) {
		super(descriptor);
	}
}

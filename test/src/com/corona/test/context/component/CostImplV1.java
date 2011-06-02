/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

import com.corona.context.annotation.Install;
import com.corona.context.annotation.Version;

/**
 * <p>The implementation of adder </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Install(dependencies = "java.lang.String")
@Version(1)
public class CostImplV1 implements Cost {

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.context.component.Cost#getPrice(int, int)
	 */
	@Override
	public int getPrice(final int spend, final int sell) {
		return sell - spend;
	}
}

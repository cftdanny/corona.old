/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import com.corona.servlet.WebStartModule;
import com.corona.test.servlet.json.JsonContent;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ServletTestModule extends WebStartModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		this.bind(Index.class).to(Index.class);
		
		this.bind(JsonContent.class).to(JsonContent.class);
	}
}

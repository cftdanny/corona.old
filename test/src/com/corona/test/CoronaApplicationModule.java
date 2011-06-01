/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import com.corona.servlet.Handler;
import com.corona.servlet.ResourceHandler;
import com.corona.servlet.WebStartModule;

/**
 * <p>This module is used to define all components for application, both for testing and production. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class CoronaApplicationModule extends WebStartModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		// Install JavaScript handler to match all requests for /script/* with first (1) priority
		this.bind(Handler.class).to(ResourceHandler.class).as("script");
		this.bindConfiguration(Handler.class).as("script").property("head").value("/script");
		this.bindConfiguration(Handler.class).as("script").property("priority").value(1);

		// Install CSS handler to match all requests for /style/* with first (1) priority
		this.bind(Handler.class).to(ResourceHandler.class).as("style");
		this.bindConfiguration(Handler.class).as("style").property("head").value("/style");
		this.bindConfiguration(Handler.class).as("style").property("priority").value(1);

		// Install image handler to match all requests for /image/* with first (1) priority
		this.bind(Handler.class).to(ResourceHandler.class).as("image");
		this.bindConfiguration(Handler.class).as("image").property("head").value("/image");
		this.bindConfiguration(Handler.class).as("image").property("priority").value(1);

		// Install other static resources to match other handler can't match with last priority
		this.bind(Handler.class).to(ResourceHandler.class);
		this.bindConfiguration(Handler.class).property("welcomeFileName").value("index.html");
	}
}

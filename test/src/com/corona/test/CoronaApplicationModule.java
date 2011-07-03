/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test;

import com.corona.component.cookie.CookieManager;
import com.corona.component.cookie.CookieManagerImpl;
import com.corona.remote.Server;
import com.corona.servlet.Handler;
import com.corona.servlet.WebStartModule;
import com.corona.servlet.handling.resource.ResourceHandler;
import com.corona.test.remote.DemoServer;
import com.corona.test.remote.DemoService;
import com.corona.test.servlet.ComponentHandlerHtml;
import com.corona.test.servlet.CookieHtml;
import com.corona.test.servlet.IndexExcel;
import com.corona.test.servlet.IndexHtml;
import com.corona.test.servlet.IndexJson;
import com.corona.test.servlet.IndexPdf;
import com.corona.test.servlet.IndexPng;
import com.corona.test.servlet.IndexText;
import com.corona.test.servlet.IndexXml;
import com.corona.test.servlet.SessionVariable;
import com.corona.test.servlet.SessionVariableHtml;
import com.corona.test.servlet.param.ParamBeanInjection;
import com.corona.test.servlet.param.ParamMatchInjection;
import com.corona.test.servlet.param.ParamSimpleInjection;

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
		
		// STATIC CONTENT
		this.configureStaticContent();
		
		// PRODUCER
		this.configureProducerContent();
		
		// COMPONENT
		this.configureSupportComponent();
		
		// PAGE
		this.configurePageComponent();
		
		// INJECTION
		this.configureInjection();
		
		// REMOTING
		this.configureRemoting();
	}

	/**
	 * configure static content
	 */
	private void configureStaticContent() {
		
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
	
	/**
	 * content that is produced by producer
	 */
	private void configureProducerContent() {

		// HTML: FREEMAKER
		this.bind(IndexHtml.class).to(IndexHtml.class);

		// RAW: TEXT
		this.bind(IndexText.class).to(IndexText.class);
		
		// XML
		this.bind(IndexXml.class).to(IndexXml.class);

		// JSON
		this.bind(IndexJson.class).to(IndexJson.class);

		// PDF
		this.bind(IndexPdf.class).to(IndexPdf.class);
		
		// EXCEL
		this.bind(IndexExcel.class).to(IndexExcel.class);
		
		// PNG/JPEG
		this.bind(IndexPng.class).to(IndexPng.class);
	}
	
	/**
	 * configure support component
	 */
	private void configureSupportComponent() {
		this.bind(CookieManager.class).to(CookieManagerImpl.class);
		this.bind(ComponentHandlerHtml.class).to(ComponentHandlerHtml.class);
	}
	
	/**
	 * configure page component
	 */
	private void configurePageComponent() {
		
		// test cookie manager
		this.bind(CookieHtml.class).to(CookieHtml.class);
		
		// test session scope
		this.bind(SessionVariable.class).to(SessionVariable.class);
		this.bind(SessionVariableHtml.class).to(SessionVariableHtml.class);
	}
	
	/**
	 * configure injection
	 */
	private void configureInjection() {
		this.bind(ParamSimpleInjection.class).to(ParamSimpleInjection.class);
		this.bind(ParamBeanInjection.class).to(ParamBeanInjection.class);
		this.bind(ParamMatchInjection.class).to(ParamMatchInjection.class);
	}
	
	/**
	 * configure remoting service
	 */
	private void configureRemoting() {
		this.bind(Server.class).to(DemoServer.class);
		this.bind(DemoService.class).to(DemoService.class);
	}
}
/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.demo.servlet;

import com.corona.demo.servlet.chart.CreateChartContent;
import com.corona.demo.servlet.excel.CreateExcelContent;
import com.corona.demo.servlet.json.JsonContent;
import com.corona.demo.servlet.param.ParamStringBean;
import com.corona.demo.servlet.pdf.CreatePdfContent;
import com.corona.servlet.Handler;
import com.corona.servlet.ResourceHandler;
import com.corona.servlet.WebStartModule;

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

		// Test inject by @Param
		this.bind(ParamStringBean.class).to(ParamStringBean.class);
		
		// match /script with first priority
		this.bind(Handler.class).to(ResourceHandler.class).as("script");
		this.bindConfiguration(Handler.class).as("script").property("head").value("/script");
		this.bindConfiguration(Handler.class).as("script").property("priority").value(1);

		// match other resource if have
		this.bind(Handler.class).to(ResourceHandler.class);

		// producer
		this.bind(Index.class).to(Index.class);
		
		this.bind(JsonContent.class).to(JsonContent.class);
		this.bind(CreatePdfContent.class).to(CreatePdfContent.class);
		this.bind(CreateExcelContent.class).to(CreateExcelContent.class);
		this.bind(CreateChartContent.class).to(CreateChartContent.class);
	}
}

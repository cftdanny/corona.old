/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import com.corona.context.InjectFieldFactory;
import com.corona.context.InjectParameterFactory;
import com.corona.context.InjectPropertyFactory;
import com.corona.context.annotation.Application;
import com.corona.servlet.annotation.Chart;
import com.corona.servlet.annotation.Excel;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Head;
import com.corona.servlet.annotation.Json;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Path;
import com.corona.servlet.annotation.Pdf;
import com.corona.servlet.annotation.Regex;
import com.corona.servlet.annotation.Resource;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Service;
import com.corona.servlet.annotation.Tail;
import com.corona.servlet.annotation.Xml;
import com.corona.servlet.chart.ChartProducerFactory;
import com.corona.servlet.excel.ExcelProducerFactory;
import com.corona.servlet.freemaker.FreeMakerEngineManager;
import com.corona.servlet.freemaker.FreeMakerEngineManagerImpl;
import com.corona.servlet.freemaker.FreeMakerProducerFactory;
import com.corona.servlet.resource.ResourceProducerFactory;
import com.corona.servlet.service.ServiceProducerFactory;
import com.corona.servlet.json.JsonProducerFactory;
import com.corona.servlet.matching.HeadMatcherFactory;
import com.corona.servlet.matching.PathMatcherFactory;
import com.corona.servlet.matching.RegexMatcherFactory;
import com.corona.servlet.matching.SameMatcherFactory;
import com.corona.servlet.matching.TailMatcherFactory;
import com.corona.servlet.param.ParamInjectFieldFactory;
import com.corona.servlet.param.ParamInjectParameterFactory;
import com.corona.servlet.param.ParamInjectPropertyFactory;
import com.corona.servlet.pdf.PdfProducerFactory;
import com.corona.servlet.xml.XmlProducerFactory;

/**
 * <p>This module is used to configure context manager factory for SERVLET environment. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ApplicationModule extends WebKernelModule {

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		
		// configure @Param injection for field and parameter
		this.bindExtension(InjectFieldFactory.class).as(Param.class).to(
				new ParamInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(Param.class).to(
				new ParamInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(Param.class).to(
				new ParamInjectPropertyFactory()
		);
		
		// configure built-in matcher factory for SERVLET
		this.bindExtension(MatcherFactory.class).as(Head.class).to(new HeadMatcherFactory());
		this.bindExtension(MatcherFactory.class).as(Tail.class).to(new TailMatcherFactory());
		this.bindExtension(MatcherFactory.class).as(Same.class).to(new SameMatcherFactory());
		this.bindExtension(MatcherFactory.class).as(Regex.class).to(new RegexMatcherFactory());
		this.bindExtension(MatcherFactory.class).as(Path.class).to(new PathMatcherFactory());
		
		// configure built-in producer factory for SERVLET
		this.bindExtension(ProducerFactory.class).as(Service.class).to(new ServiceProducerFactory());
		this.bindExtension(ProducerFactory.class).as(Xml.class).to(new XmlProducerFactory());
		
		// configure default JSON producer environment with dependency component and extension
		this.bind(
				com.corona.servlet.json.JsonMarshaller.class
		).to(
				com.corona.servlet.json.JsonMarshallerImpl.class
		).in(Application.class);
		this.bindExtension(ProducerFactory.class).as(Json.class).to(new JsonProducerFactory());
		
		// configure default FreeMaker producer environment with dependency component and extension
		this.bind(FreeMakerEngineManager.class).to(FreeMakerEngineManagerImpl.class).in(Application.class);
		this.bindExtension(ProducerFactory.class).as(FreeMaker.class).to(new FreeMakerProducerFactory());
		
		// configure PDF producer
		this.bindExtension(ProducerFactory.class).as(Pdf.class).to(new PdfProducerFactory());
		
		// configure Microsoft EXCEL producer
		this.bindExtension(ProducerFactory.class).as(Excel.class).to(new ExcelProducerFactory());

		// configure Microsoft CHART producer
		this.bindExtension(ProducerFactory.class).as(Chart.class).to(new ChartProducerFactory());

		// configure Resource producer
		this.bindExtension(ProducerFactory.class).as(Resource.class).to(new ResourceProducerFactory());
	}
}

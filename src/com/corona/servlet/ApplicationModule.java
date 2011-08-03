/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import com.corona.context.InjectFieldFactory;
import com.corona.context.InjectMethodFactory;
import com.corona.context.InjectParameterFactory;
import com.corona.context.InjectPropertyFactory;
import com.corona.context.annotation.Application;
import com.corona.servlet.annotation.AllowRoles;
import com.corona.servlet.annotation.Chart;
import com.corona.servlet.annotation.CookieParam;
import com.corona.servlet.annotation.DenyRoles;
import com.corona.servlet.annotation.Excel;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.HasParam;
import com.corona.servlet.annotation.Head;
import com.corona.servlet.annotation.HttpMethod;
import com.corona.servlet.annotation.Jndi;
import com.corona.servlet.annotation.Json;
import com.corona.servlet.annotation.JsonRequest;
import com.corona.servlet.annotation.LoggedIn;
import com.corona.servlet.annotation.MatchParam;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Path;
import com.corona.servlet.annotation.Pdf;
import com.corona.servlet.annotation.Redirect;
import com.corona.servlet.annotation.Regex;
import com.corona.servlet.annotation.Remote;
import com.corona.servlet.annotation.Resource;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.SecuredRequest;
import com.corona.servlet.annotation.Service;
import com.corona.servlet.annotation.Session;
import com.corona.servlet.annotation.Tail;
import com.corona.servlet.annotation.Upload;
import com.corona.servlet.annotation.Xml;
import com.corona.servlet.annotation.XmlRequest;
import com.corona.servlet.injecting.cookieparam.CookieParamInjectFieldFactory;
import com.corona.servlet.injecting.cookieparam.CookieParamInjectParameterFactory;
import com.corona.servlet.injecting.cookieparam.CookieParamInjectPropertyFactory;
import com.corona.servlet.injecting.jndi.JndiInjectFieldFactory;
import com.corona.servlet.injecting.jndi.JndiInjectParameterFactory;
import com.corona.servlet.injecting.jndi.JndiInjectPropertyFactory;
import com.corona.servlet.injecting.json.JsonRequestInjectFieldFactory;
import com.corona.servlet.injecting.json.JsonRequestInjectParameterFactory;
import com.corona.servlet.injecting.json.JsonRequestInjectPropertyFactory;
import com.corona.servlet.injecting.matchparam.MatchParamInjectFieldFactory;
import com.corona.servlet.injecting.matchparam.MatchParamInjectParameterFactory;
import com.corona.servlet.injecting.matchparam.MatchParamInjectPropertyFactory;
import com.corona.servlet.injecting.param.ParamInjectFieldFactory;
import com.corona.servlet.injecting.param.ParamInjectParameterFactory;
import com.corona.servlet.injecting.param.ParamInjectPropertyFactory;
import com.corona.servlet.injecting.upload.UploadInjectFieldFactory;
import com.corona.servlet.injecting.upload.UploadInjectParameterFactory;
import com.corona.servlet.injecting.upload.UploadInjectPropertyFactory;
import com.corona.servlet.injecting.xml.XmlRequestInjectFieldFactory;
import com.corona.servlet.injecting.xml.XmlRequestInjectParameterFactory;
import com.corona.servlet.injecting.xml.XmlRequestInjectPropertyFactory;
import com.corona.servlet.matching.HeadMatcherFactory;
import com.corona.servlet.matching.PathMatcherFactory;
import com.corona.servlet.matching.RegexMatcherFactory;
import com.corona.servlet.matching.SameMatcherFactory;
import com.corona.servlet.matching.TailMatcherFactory;
import com.corona.servlet.producing.chart.ChartProducerFactory;
import com.corona.servlet.producing.excel.ExcelProducerFactory;
import com.corona.servlet.producing.freemaker.FreeMakerEngine;
import com.corona.servlet.producing.freemaker.FreeMakerEngineImpl;
import com.corona.servlet.producing.freemaker.FreeMakerProducerFactory;
import com.corona.servlet.producing.json.JsonProducerFactory;
import com.corona.servlet.producing.pdf.PdfProducerFactory;
import com.corona.servlet.producing.redirect.RedirectProducerFactory;
import com.corona.servlet.producing.remote.RemoteInjectMethodFactory;
import com.corona.servlet.producing.remote.RemoteProducerFactory;
import com.corona.servlet.producing.resource.ResourceProducerFactory;
import com.corona.servlet.producing.service.ServiceProducerFactory;
import com.corona.servlet.producing.xml.XmlProducerFactory;
import com.corona.servlet.restricting.allowroles.AllowRolesRestrictorFactory;
import com.corona.servlet.restricting.deniedroles.DenyRolesRestrictorFactory;
import com.corona.servlet.restricting.loggedin.LoggedInRestrictorFactory;
import com.corona.servlet.restricting.securedrequest.SecuredRequestRestrictorFactory;
import com.corona.servlet.selecting.hasparam.HasParamSelectorFactory;
import com.corona.servlet.selecting.httpmethod.HttpMethodSelectorFactory;

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
		
		// configure @Session scope, allow developer to register session component
		this.bindScope(Session.class).to(new SessionScope());
		
		// configure @Param injection for field, property and parameter
		this.bindExtension(InjectFieldFactory.class).as(Param.class).to(
				new ParamInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(Param.class).to(
				new ParamInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(Param.class).to(
				new ParamInjectPropertyFactory()
		);

		// configure @MatchParam injection for field, property and parameter
		this.bindExtension(InjectFieldFactory.class).as(MatchParam.class).to(
				new MatchParamInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(MatchParam.class).to(
				new MatchParamInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(MatchParam.class).to(
				new MatchParamInjectPropertyFactory()
		);

		// configure @CookieParam injection for field, property and parameter
		this.bindExtension(InjectFieldFactory.class).as(CookieParam.class).to(
				new CookieParamInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(CookieParam.class).to(
				new CookieParamInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(CookieParam.class).to(
				new CookieParamInjectPropertyFactory()
		);

		// configure @Jndi injection for field, property and parameter
		this.bindExtension(InjectFieldFactory.class).as(Jndi.class).to(
				new JndiInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(Jndi.class).to(
				new JndiInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(Jndi.class).to(
				new JndiInjectPropertyFactory()
		);

		// configure @JsonRequest injection for field, property and parameter
		this.bindExtension(InjectFieldFactory.class).as(JsonRequest.class).to(
				new JsonRequestInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(JsonRequest.class).to(
				new JsonRequestInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(JsonRequest.class).to(
				new JsonRequestInjectPropertyFactory()
		);

		// configure @XmlRequest injection for field, property and parameter
		this.bindExtension(InjectFieldFactory.class).as(XmlRequest.class).to(
				new XmlRequestInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(XmlRequest.class).to(
				new XmlRequestInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(XmlRequest.class).to(
				new XmlRequestInjectPropertyFactory()
		);

		// configure @Upload injection for field, property and parameter
		this.bindExtension(InjectFieldFactory.class).as(Upload.class).to(
				new UploadInjectFieldFactory()
		);
		this.bindExtension(InjectParameterFactory.class).as(Upload.class).to(
				new UploadInjectParameterFactory()
		);
		this.bindExtension(InjectPropertyFactory.class).as(Upload.class).to(
				new UploadInjectPropertyFactory()
		);

		// configure built-in selector factory for SERVLET
		this.bindExtension(SelectorFactory.class).as(HttpMethod.class).to(new HttpMethodSelectorFactory());
		this.bindExtension(SelectorFactory.class).as(HasParam.class).to(new HasParamSelectorFactory());
		
		// configure built-in restrictor factory for SERVLET
		this.bindExtension(RestrictorFactory.class).as(LoggedIn.class).to(new LoggedInRestrictorFactory());
		this.bindExtension(RestrictorFactory.class).as(AllowRoles.class).to(new AllowRolesRestrictorFactory());
		this.bindExtension(RestrictorFactory.class).as(DenyRoles.class).to(new DenyRolesRestrictorFactory());
		this.bindExtension(RestrictorFactory.class).as(SecuredRequest.class).to(new SecuredRequestRestrictorFactory());
		
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
		this.bindExtension(ProducerFactory.class).as(Json.class).to(new JsonProducerFactory());
		
		// configure default FreeMaker producer environment with dependency component and extension
		this.bind(FreeMakerEngine.class).to(FreeMakerEngineImpl.class).in(Application.class);
		this.bindExtension(ProducerFactory.class).as(FreeMaker.class).to(new FreeMakerProducerFactory());
		
		// configure PDF producer
		this.bindExtension(ProducerFactory.class).as(Pdf.class).to(new PdfProducerFactory());
		
		// configure Microsoft EXCEL producer
		this.bindExtension(ProducerFactory.class).as(Excel.class).to(new ExcelProducerFactory());

		// configure JFreeChart CHART producer
		this.bindExtension(ProducerFactory.class).as(Chart.class).to(new ChartProducerFactory());

		// configure Redirect producer
		this.bindExtension(ProducerFactory.class).as(Redirect.class).to(new RedirectProducerFactory());

		// configure Resource producer
		this.bindExtension(ProducerFactory.class).as(Resource.class).to(new ResourceProducerFactory());

		// configure @Remote injection for inject method and content producer
		this.bindExtension(InjectMethodFactory.class).as(Remote.class).to(
				new RemoteInjectMethodFactory()
		);
		this.bindExtension(ProducerFactory.class).as(Remote.class).to(new RemoteProducerFactory());
	}
}

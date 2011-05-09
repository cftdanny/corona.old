/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import com.corona.context.annotation.Application;
import com.corona.servlet.annotation.Head;
import com.corona.servlet.annotation.Json;
import com.corona.servlet.annotation.Path;
import com.corona.servlet.annotation.Regex;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Service;
import com.corona.servlet.annotation.Tail;
import com.corona.servlet.annotation.Velocity;
import com.corona.servlet.annotation.Xml;
import com.corona.servlet.json.JsonProducerFactory;
import com.corona.servlet.matching.HeadMatcherFactory;
import com.corona.servlet.matching.PathMatcherFactory;
import com.corona.servlet.matching.RegexMatcherFactory;
import com.corona.servlet.matching.SameMatcherFactory;
import com.corona.servlet.matching.TailMatcherFactory;
import com.corona.servlet.producing.ServiceProducerFactory;
import com.corona.servlet.velocity.VelocityProducerFactory;
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
				com.corona.servlet.json.Marshaller.class
		).to(
				com.corona.servlet.json.MarshallerImpl.class
		).in(Application.class);
		this.bindExtension(ProducerFactory.class).as(Json.class).to(new JsonProducerFactory());
		
		// configure default velocity producer environment with dependency component and extension
		this.bind(
				com.corona.servlet.velocity.ScriptEngine.class
		).to(
				com.corona.servlet.velocity.ScriptEngineImpl.class
		).in(Application.class);
		this.bindExtension(ProducerFactory.class).as(Velocity.class).to(new VelocityProducerFactory());
	}
}

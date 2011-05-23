/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.json;

import com.corona.context.ContextManager;
import com.corona.context.annotation.Inject;
import com.corona.servlet.annotation.Expiration;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.GET;
import com.corona.servlet.annotation.Json;
import com.corona.servlet.annotation.Path;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Velocity;
import com.corona.servlet.annotation.WebResource;
import com.corona.servlet.annotation.Xml;
import com.corona.servlet.freemaker.FreeMakerEngineManager;
import com.corona.servlet.freemaker.FreeMakerEngineManagerImpl;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class JsonContent {

	/**
	 * the context manager
	 */
	@Inject private ContextManager contextManager;
	
	/**
	 * @return the user
	 */
	@Same("/json/user")
	@Json public User get() {
		return new User("AAAA", "BBBB");
	}

	/**
	 * @return the user
	 */
	@Same("/json/user.xml")
	@Expiration
	@Xml public User getXml() {
		return new User("AAAA", "BBBB");
	}
	
	/**
	 * @return the user
	 */
	@Same("/json/user.html")
	@Velocity("/velocity.vm") 
	@GET @Expiration(60 * 1000)
	public User getVelocity() {
		return new User("AAAA", "BBXXXXBB");
	}

	/**
	 * @return the FreeMaker engine
	 */
	private FreeMakerEngineManagerImpl getFreeMakerEngineManager() {
		return (FreeMakerEngineManagerImpl) this.contextManager.get(FreeMakerEngineManager.class);
	}
	
	/**
	 * @return the user
	 */
	@Same("/freemaker.html")
	@FreeMaker("/freemaker.ftl") 
	@GET @Expiration(60 * 1000)
	public User getFreeMaker() {
		
		FreeMakerEngineManagerImpl engine = this.getFreeMakerEngineManager();
		engine.getThemeTemplates().put("main", "/main.ftl");
		engine.getThemeTemplates().put("normal", "/normal.ftl");
		engine.setDefaultThemeName("main");
		
		return new User("AAAA", "BBXXXXBB");
	}

	/**
	 * @return the user
	 */
	@Same("/json/pass.html")
	@Velocity("/velocity.vm") 
	@GET @Expiration(60 * 1000)
	public User getVelocityA() {
		return new User("XXXXXXXXXXXXXXXXXX", "DsadddddddddddddADAD");
	}

	/**
	 * @return the user
	 */
	@Path("/path/{a:.*}/{b}/user.vm")
	@Velocity("/velocity.vm") 
	@GET @Expiration(60 * 1000)
	public User getPathVelocity() {
		return new User("AAAA", "BBBB");
	}
}

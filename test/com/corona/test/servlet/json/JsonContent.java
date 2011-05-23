/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.json;

import com.corona.servlet.annotation.Expiration;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.GET;
import com.corona.servlet.annotation.Json;
import com.corona.servlet.annotation.Path;
import com.corona.servlet.annotation.Same;
import com.corona.servlet.annotation.Velocity;
import com.corona.servlet.annotation.WebResource;
import com.corona.servlet.annotation.Xml;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
@WebResource
public class JsonContent {

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
	 * @return the user
	 */
	@Same("/freemaker.html")
	@FreeMaker("/freemaker.ftl") 
	@GET @Expiration(60 * 1000)
	public User getFreeMaker() {
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

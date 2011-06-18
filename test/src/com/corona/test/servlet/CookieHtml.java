/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet;

import com.corona.component.cookie.CookieManager;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Optional;
import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.CookieParam;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Same;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to test cookie manager </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class CookieHtml {

	/**
	 * cookie manager
	 */
	@Inject private CookieManager cookieManager;
	
	/**
	 * the hello
	 */
	private String hello;
	
	/**
	 * the world
	 */
	private String world;
	
	/**
	 * the hello from cookie
	 */
	@CookieParam("hello") @Optional private String cookieHello;

	/**
	 * the world from cookie
	 */
	@CookieParam("world") @Optional private String cookieWorld;

	/**
	 * @return the hello
	 */
	public String getHello() {
		return hello;
	}

	/**
	 * @return the world
	 */
	public String getWorld() {
		return world;
	}

	/**
	 * @param ihello the input hello
	 * @param iworld the input world
	 * @return this component
	 */
	@Same("/cookie.html")
	@FreeMaker("/cookie.ftl")
	public CookieHtml produce(
			@Param("hello") @Optional final String ihello, @Param("world") @Optional final String iworld) {
		
		if (StringUtil.isBlank(ihello)) {
			this.hello = this.cookieHello;
		} else {
			this.hello = ihello;
		}
		if (StringUtil.isBlank(iworld)) {
			this.world = this.cookieWorld;
		} else {
			this.world = iworld;
		}
		
		if (!StringUtil.isBlank(this.hello)) {
			this.cookieManager.add(this.cookieManager.createCookie("hello", this.hello));
		}
		if (!StringUtil.isBlank(this.world)) {
			this.cookieManager.add(this.cookieManager.createCookie("world", this.world));
		}
		
		return this;
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Params;
import com.corona.servlet.annotation.Same;

/**
 * <p>Test Params injection </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class ParamsInjection {

	/**
	 * @param order the order
	 * @return the order
	 */
	@Same("/params.html")
	@FreeMaker("/params.ftl") 
	public Order html(@Params final Order order) {
		return order;
	}
}

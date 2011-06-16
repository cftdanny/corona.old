/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import com.corona.servlet.annotation.Controller;
import com.corona.servlet.annotation.FreeMaker;
import com.corona.servlet.annotation.Param;
import com.corona.servlet.annotation.Same;

/**
 * <p>Test Params injection </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Controller
public class ParamBeanInjection {

	/**
	 * @param order the order
	 * @return the order
	 */
	@Same("/param/bean.html")
	@FreeMaker("/param-bean.ftl") 
	public Order html(@Param final Order order) {
		return order;
	}
}

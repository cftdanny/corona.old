/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.expression;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.expression.Template;

/**
 * <p>The template test </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TemplateTest {

	/**
	 * the house
	 */
	private House house;

	/**
	 * constructor
	 */
	public TemplateTest() {
		
		house = new House();
		house.setWidth(10);
		house.setHeight(10);
		house.setName("SHA");
	}
	
	/**
	 * simple template
	 */
	@Test void testSimpleTemplate() {
		
		Template template = Template.getTemplate("Shanghai @{name} = @{weight} * @{height}");
		Assert.assertEquals(template.execute(this.house), "Shanghai SHA = 10 * 10");
	}
	
	/**
	 * complicate template
	 */
	@Test void testComplicate() {
		
		Template template = new Template("HOUSE : @{name} = @{weight * height}/@{unit}");
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("unit", "M2");
		Assert.assertEquals(template.execute(house, vars), "HOUSE : SHA = 1000/M2");
		
		template = Template.getTemplate("@{unit} = M * M");
		Assert.assertEquals(template.execute(vars), "M2 = M * M");
	}
}

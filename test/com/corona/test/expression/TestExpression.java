/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.expression;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.expression.Expression;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TestExpression {

	/**
	 * the house
	 */
	private House house;
	
	/**
	 * default constructor
	 */
	public TestExpression() {
		
		house = new House();
		house.setWidth(10);
		house.setHeight(10);
		house.setName("SHA");
	}
	
	/**
	 * test compiled expression
	 */
	@Test public void testRootExpression() {

		Expression expr = Expression.compile("name");
		Assert.assertEquals(expr.get(house), "SHA");
		Assert.assertEquals(expr.get(house, String.class), "SHA");
	}
	
	/**
	 * test context expression
	 */
	@Test public void testRootContextExpression() {

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("discount", 10);
		
		Expression expr = Expression.compile("width * height - discount");
		Assert.assertEquals(expr.get(house, context), 90);
		Assert.assertEquals(expr.get(house, context, Integer.class).intValue(), 90);
	}

	/**
	 * test context expression
	 */
	@Test public void testContextExpression() {

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("discount", 10);
		context.put("profit", 20);
		
		Expression expr = Expression.compile("profit - discount");
		Assert.assertEquals(expr.get(context), 10);
		Assert.assertEquals(expr.get(context, Integer.class).intValue(), 10);
	}
	
	/**
	 * test set expression
	 */
	@Test public void testSetExpression() {
		
		Expression expr = Expression.compile("height");
		expr.set(house, 20);
		Assert.assertEquals(this.house.getHeight().intValue(), 20);
		house.setHeight(10);
	}
	
	/**
	 * test eval
	 */
	@Test public void testEval() {

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("discount", 10);
		context.put("profit", 20);

		Assert.assertEquals(((Integer) Expression.eval("height", this.house)).intValue(), 10);
		Assert.assertEquals(((Integer) Expression.eval("profit - discount", context)).intValue(), 10);
		Assert.assertEquals(((Integer) Expression.eval(
				"height * width - discount", this.house, context
		)).intValue(), 90);
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.util.ConvertUtil;
import com.corona.util.ListUtil;

/**
 * <p>This test case is used to test ParamUtil </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ParamUtilTest {

	/**
	 * test all supported simple types
	 */
	@Test public void testSupportSimpleType() {
		
		Assert.assertEquals("ABCD", ConvertUtil.getAsType("ABCD", String.class));
		
		Assert.assertEquals(10, ConvertUtil.getAsType("10", int.class));
		Assert.assertEquals(new Integer(10), ConvertUtil.getAsType("10", Integer.class));

		Assert.assertEquals(10L, ConvertUtil.getAsType("10", long.class));
		Assert.assertEquals(new Long(10), ConvertUtil.getAsType("10", Long.class));

		Assert.assertEquals(Short.parseShort("10"), ConvertUtil.getAsType("10", short.class));
		Assert.assertEquals(new Short("10"), ConvertUtil.getAsType("10", Short.class));
		
		Assert.assertEquals(Byte.parseByte("10"), ConvertUtil.getAsType("10", byte.class));
		Assert.assertEquals(new Byte("10"), ConvertUtil.getAsType("10", Byte.class));

		Assert.assertEquals(Float.parseFloat("10"), ConvertUtil.getAsType("10", float.class));
		Assert.assertEquals(new Float("10"), ConvertUtil.getAsType("10", Float.class));

		Assert.assertEquals(Double.parseDouble("10"), ConvertUtil.getAsType("10", double.class));
		Assert.assertEquals(new Double("10"), ConvertUtil.getAsType("10", Double.class));

		Assert.assertEquals(Boolean.parseBoolean("true"), ConvertUtil.getAsType("true", boolean.class));
		Assert.assertEquals(new Boolean("true"), ConvertUtil.getAsType("true", Boolean.class));
	}
	
	/**
	 * test parameter to list 
	 */
	@SuppressWarnings("unchecked")
	@Test public void testParameterToList() {
		
		List<Integer> r1 = ConvertUtil.getAsList(new String[] {"10", "20"}, Integer.class);
		Assert.assertEquals(new Integer("10"), r1.get(0));
		Assert.assertEquals(new Integer("20"), r1.get(1));

		List<Integer> r2 = ConvertUtil.getAsList(new String[] {"10", "20"}, int.class);
		Assert.assertEquals(new Integer("10"), r2.get(0));
		Assert.assertEquals(new Integer("20"), r2.get(1));
		
		List<String> r3 = ListUtil.getAsList(new String[] {"10", "20"});
		Assert.assertEquals("10", r3.get(0));
		Assert.assertEquals("20", r3.get(1));
	}
}

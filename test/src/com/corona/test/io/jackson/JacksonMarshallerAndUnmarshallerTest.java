/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.io.jackson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.io.Marshaller;
import com.corona.io.Unmarshaller;
import com.corona.io.jackson.JacksonMarshallerFactory;
import com.corona.io.jackson.JacksonUnmarshallerFactory;

/**
 * <p>Test marshal and unmarshal object with jackson JSON </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JacksonMarshallerAndUnmarshallerTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testMarshallAndUnmarshall() throws Exception {
		
		Product product1 = new Product();
		product1.setName("ABC");
		product1.setPrice(100);
		
		Marshaller<Product> marshaller = new JacksonMarshallerFactory().create(Product.class);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		marshaller.marshal(baos, product1);
		
		Unmarshaller<Product> unmarshaller = new JacksonUnmarshallerFactory().create(Product.class);
		Product product2 = unmarshaller.unmarshal(new ByteArrayInputStream(baos.toByteArray()));
		
		// test whether two object is same
		Assert.assertEquals(product2.getName(), product1.getName());
		Assert.assertEquals(product2.getPrice(), product1.getPrice());
		
	}
}

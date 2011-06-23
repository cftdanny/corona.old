/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.io.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.io.Marshaller;
import com.corona.io.Unmarshaller;
import com.corona.io.jaxb.JaxbMarshallerFactory;
import com.corona.io.jaxb.JaxbUnmarshallerFactory;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JabxMarhsallerAndUnmarshallerTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testMarshallAndUnmarshall() throws Exception {
		
		User user = new User();
		user.setName("ABC");
		user.setPassword("BC");
		
		Marshaller<User> mar = new JaxbMarshallerFactory().create(User.class);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mar.marshal(baos, user);
		
		Unmarshaller<User> unm = new JaxbUnmarshallerFactory().create(User.class);
		User u1 = unm.unmarshal(new ByteArrayInputStream(baos.toByteArray()));
		
		// test 2 object are same
		Assert.assertEquals(u1.getName(), user.getName());
		Assert.assertEquals(u1.getPassword(), user.getPassword());
	}
}

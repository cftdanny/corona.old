/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.request;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.corona.io.Marshaller;
import com.corona.io.MarshallerFactory;
import com.corona.io.jackson.JacksonMarshallerFactory;
import com.corona.mock.AbstractWebsiteTest;

/**
 * <p>Test JSON request </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class JsonRequestByFieldTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testJsonRequestByParam() throws Exception {
		
		// find request path
		String path = this.getFullPath("");
		if (path.endsWith("/")) {
			path = path + "json/field.html";
		} else {
			path = path + "/json/field.html";
		}

		// send json request to server
		Address address = new Address();
		address.setName("ABC");
		address.setStreet("DEF");
		
		Marshaller<Address> marshaller = MarshallerFactory.get(JacksonMarshallerFactory.NAME).create(Address.class); 
		
		URLConnection connection = new URL(path).openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		marshaller.marshal(connection.getOutputStream(), address);
		connection.getOutputStream().close();
		
		// get response
		InputStream input = connection.getInputStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		for (int b = input.read(); b != -1; b = input.read()) {
			output.write(b);
		}
		input.close();
		
		// test result by page
		String result = new String(output.toByteArray());
		Assert.assertTrue(result.contains("ABC"));
		Assert.assertTrue(result.contains("DEF"));
	}
}

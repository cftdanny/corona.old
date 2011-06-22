/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.io.avro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.io.Marshaller;
import com.corona.io.Unmarshaller;
import com.corona.io.avro.AvroMarshallerFactory;
import com.corona.io.avro.AvroUnmarshallerFactory;

/**
 * <p>Test Apache Avro reflection marshaller and unmarshaller </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ReflectMarshallerAndUnmarshallerTest {

	/**
	 * test marshall and unmarshall
	 * @exception Exception if fail
	 */
	@Test public void testReflectMarshallerAndUnmarshaller() throws Exception {
		
		User user = new User();
		user.setName("Eric");
		user.setAge(10);
		
		School school1 = new School();
		school1.setName("SHA");
		school1.setAddress("SHANGHAI");
		user.getSchools().add(school1);

		School school2 = new School();
		school2.setName("BJ");
		school2.setAddress("BEIJING");
		user.getSchools().add(school2);
		
		Marshaller<User> marshaller = new AvroMarshallerFactory().create(User.class);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		marshaller.marshal(bos, user);
		
		Unmarshaller<User> unmarshaller = new AvroUnmarshallerFactory().create(User.class);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		
		// test whether unmarshalled user is same as old user
		User user1 = unmarshaller.unmarshal(bis);
		
		Assert.assertEquals(user1.getName(), user.getName());
		Assert.assertEquals(user1.getAge(), user.getAge());
		
		Assert.assertEquals(user1.getSchools().size(), user.getSchools().size());
		
		// test whether schools are same
		School schoola = user.getSchools().get(0);
		School schoolb = user1.getSchools().get(0);
		Assert.assertEquals(schoola.getName(), schoolb.getName());
		Assert.assertEquals(schoola.getAddress(), schoolb.getAddress());
		
		schoola = user.getSchools().get(1);
		schoolb = user1.getSchools().get(1);
		Assert.assertEquals(schoola.getName(), schoolb.getName());
		Assert.assertEquals(schoola.getAddress(), schoolb.getAddress());
	}

	/**
	 * @throws Exception if error
	 */
	@Test public void testSpecificMarshallerAndUnmarshaller() throws Exception {
		
		Country c1 = new Country();
		c1.setName("CHINA");
		c1.setLocation("ASIA");
		
		Marshaller<Country> marshaller = new AvroMarshallerFactory().create(Country.class);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		marshaller.marshal(bos, c1);
		
		Unmarshaller<Country> unmarshaller = new AvroUnmarshallerFactory().create(Country.class);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		Country c2 = unmarshaller.unmarshal(bis);
		
		// test whether 2 country is same or not
		Assert.assertEquals(c2.getName(), c1.getName());
		Assert.assertEquals(c2.getLocation(), c1.getLocation());
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.io.avro;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class User {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the age
	 */
	private Integer age;

	/**
	 * the schools
	 */
	private List<School> schools = new ArrayList<School>();
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}
	
	/**
	 * @param age the age to set
	 */
	public void setAge(final Integer age) {
		this.age = age;
	}
	
	/**
	 * @return the schools
	 */
	public List<School> getSchools() {
		return schools;
	}
}

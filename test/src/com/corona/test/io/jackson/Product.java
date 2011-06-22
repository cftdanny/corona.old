/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.io.jackson;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Product {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the price
	 */
	private Integer price;

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
	 * @return the price
	 */
	public Integer getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(final Integer price) {
		this.price = price;
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.constantx;

/**
 * <p>A testing component without protocol type </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Computer {

	/**
	 * the brand
	 */
	private String brand;
	
	/**
	 * the model
	 */
	private String model;
	
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(final String brand) {
		this.brand = brand;
	}
	
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	
	/**
	 * @param model the model to set
	 */
	public void setModel(final String model) {
		this.model = model;
	}
}

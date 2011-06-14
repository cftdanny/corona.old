/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Item {

	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the price
	 */
	private Integer price;
	
	/**
	 * the liens
	 */
	private List<Line> lines = new ArrayList<Line>();
	
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
	
	/**
	 * @return the lines
	 */
	public List<Line> getLines() {
		return lines;
	}
}

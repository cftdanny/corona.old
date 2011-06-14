/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.servlet.param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The order </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Order {

	/**
	 * the order NO
	 */
	private String no;
	
	/**
	 * the quantity
	 */
	private Integer qty;
	
	/**
	 * the count
	 */
	private Total total;
	
	/**
	 * the emails
	 */
	private List<String> mails = new ArrayList<String>();
	
	/**
	 * the items
	 */
	private List<Item> items = new ArrayList<Item>();
	
	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}
	
	/**
	 * @param no the no to set
	 */
	public void setNo(final String no) {
		this.no = no;
	}
	
	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}
	
	/**
	 * @param qty the qty to set
	 */
	public void setQty(final Integer qty) {
		this.qty = qty;
	}
	
	/**
	 * @return the total
	 */
	public Total getTotal() {
		return total;
	}
	
	/**
	 * @param total the total to set
	 */
	public void setTotal(final Total total) {
		this.total = total;
	}

	/**
	 * @return the mails
	 */
	public List<String> getMails() {
		return mails;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}
}

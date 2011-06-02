/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.provider;

import com.corona.context.Provider;
import com.corona.context.annotation.Alias;
import com.corona.context.annotation.Application;
import com.corona.context.annotation.Create;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Name;

/**
 * <p>The inventory provider </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Application
@Alias("inventory")
public class InventoryProvider implements Provider<Inventory> {

	/**
	 * the current PCs
	 */
	private Integer current;
	
	/**
	 * how many PCs has been lost
	 */
	private Integer lost = 0;
	
	/**
	 * how many PCs has been returned
	 */
	@Inject("returned") private Integer returned = 0;

	/**
	 * just for testing, not a good habit
	 */
	private InventoryImpl inventory;
	
	/**
	 * @param current the current PCs
	 */
	@Inject public InventoryProvider(@Name("current") final Integer current) {
		this.current = current;
	}
	
	/**
	 * @return how many PCs has been lost
	 */
	public Integer getLost() {
		return lost;
	}
	
	/**
	 * @param lost how many PCs has been lost set
	 */
	@Inject("lost") public void setLost(final Integer lost) {
		this.lost = lost;
	}

	/**
	 * initialize provider
	 */
	@Create public void init() {
		
		this.inventory = new InventoryImpl(this.current);
		this.inventory.setLost(this.lost);
		this.inventory.setReturned(this.returned);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Provider#get()
	 */
	@Override
	public Inventory get() {
		return this.inventory;
	}
}

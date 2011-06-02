/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.provider;

/**
 * <p>The implementation of inventory </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InventoryImpl implements Inventory {

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
	private Integer returned = 0;
	
	/**
	 * @param current the current PCs
	 */
	public InventoryImpl(final Integer current) {
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
	public void setLost(final Integer lost) {
		this.lost = lost;
	}
	
	/**
	 * @return how many PCs has been returned
	 */
	public Integer getReturned() {
		return returned;
	}
	
	/**
	 * @param returned how many PCs has been returned to set
	 */
	public void setReturned(final Integer returned) {
		this.returned = returned;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.context.provider.Inventory#total(int, int)
	 */
	@Override
	public int total(final int in, final int out) {
		return this.current + in + this.returned - out - this.lost;
	}
}

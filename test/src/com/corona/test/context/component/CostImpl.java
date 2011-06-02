/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

import com.corona.context.annotation.Create;
import com.corona.context.annotation.Install;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Name;
import com.corona.context.annotation.Version;

/**
 * <p>The implementation of adder </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Install(dependencies = "java.lang.String")
@Version(2)
public class CostImpl implements Cost {

	/**
	 * the house rent cost
	 */
	private Integer houseRent;
	
	/**
	 * the salary cost of staff
	 */
	@Inject("salary") private Integer salary = 0;
	
	/**
	 * the commission income from supplier 
	 */
	private Integer commission = 0;
	
	/**
	 * the balance value
	 */
	private Integer balance;
	
	/**
	 * @param houseRent the house rent cost
	 */
	@Inject public CostImpl(@Name("houseRent") final Integer houseRent) {
		this.houseRent = houseRent;
	}
	
	/**
	 * the create method
	 */
	@Create public void create() {
		this.balance = this.houseRent + this.salary - this.commission;
	}
	
	/**
	 * @return the commission
	 */
	public Integer getCommission() {
		return commission;
	}
	
	/**
	 * @param commission the commission to set
	 */
	@Inject("commission") public void setCommission(final Integer commission) {
		this.commission = commission;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.test.context.component.Cost#getPrice(int, int)
	 */
	@Override
	public int getPrice(final int spend, final int sell) {
		return sell - spend - this.balance;
	}
}

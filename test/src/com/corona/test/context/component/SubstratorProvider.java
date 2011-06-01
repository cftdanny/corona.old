/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.component;

import com.corona.context.Provider;
import com.corona.context.annotation.Create;
import com.corona.context.annotation.Install;
import com.corona.context.annotation.Inject;
import com.corona.context.annotation.Name;
import com.corona.context.annotation.Startup;

/**
 * <p>The component implemented by provider </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Startup
@Install(dependencies = "java.lang.String")
public class SubstratorProvider implements Provider<Substrator> {

	/**
	 * the first
	 */
	@Inject("first") private Integer first;
	
	/**
	 * the second
	 */
	private Integer second;
	
	/**
	 * the third
	 */
	private Integer third;
	
	/**
	 * @param third the third
	 */
	@Inject public SubstratorProvider(@Name("third") final int third) {
		this.third = third;
	}
	
	/**
	 * @param second the second to set
	 */
	@Inject public void setSecond(@Name("second") final Integer second) {
		this.second = second;
	}

	/**
	 * the create method
	 */
	@Create public void create() {
		this.first = this.first + this.second - this.second;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Provider#get()
	 */
	@Override
	public Substrator get() {
		return new SubstratorImpl(this.first, this.second, this.third);
	}
}

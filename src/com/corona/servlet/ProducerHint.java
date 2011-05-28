/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

/**
 * <p>This component is used to store produce runtime inforamtion, for example, component and outcome. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProducerHint {

	/**
	 * the producer component
	 */
	private Object component;
	
	/**
	 * the outcome of produce method
	 */
	private Object outcome;
	
	/**
	 * default constructor
	 */
	public ProducerHint() {
		// do nothing
	}
	
	/**
	 * @param component the producer component
	 */
	public ProducerHint(final Object component) {
		this.component = component;
	}
	
	/**
	 * @return the producer component
	 */
	public Object getComponent() {
		return component;
	}
	
	/**
	 * @param component the producer component to set
	 */
	public void setComponent(final Object component) {
		this.component = component;
	}
	
	/**
	 * @return the outcome of produce method
	 */
	public Object getOutcome() {
		return outcome;
	}

	/**
	 * @param outcome the outcome of produce method to set
	 */
	public void setOutcome(final Object outcome) {
		this.outcome = outcome;
	}
}

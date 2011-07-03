/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.producing.remote;

/**
 * <p>The result of executing producing method </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Result {

	/**
	 * the request sent from client
	 */
	private Request request;
	
	/**
	 * the method outcome of producing method
	 */
	private Object outcome;
	
	/**
	 * the exception if it is raised
	 */
	private Exception exception;
	
	/**
	 * @param request the request
	 * @param outcome the outcome
	 */
	Result(final Request request, final Object outcome) {
		this.request = request;
		this.outcome = outcome;
	}
	
	/**
	 * @param exception the exception
	 */
	Result(final Exception exception) {
		this.exception = exception;
	}
	
	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}
	
	/**
	 * @return the outcome
	 */
	public Object getOutcome() {
		return outcome;
	}
	
	/**
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}
}

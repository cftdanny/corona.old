/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.handling.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.corona.servlet.HandleException;
import com.corona.servlet.Handler;
import com.corona.servlet.MatchResult;
import com.corona.servlet.Matcher;
import com.corona.servlet.annotation.Controller;

/**
 * <p>This handler is used to produce response if component is annotated with {@link Controller} and
 * its value is not empty </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ComponentHandler implements Handler {

	/**
	 * the component matcher
	 */
	private Matcher matcher;
	
	/**
	 * the children handlers
	 */
	private List<Handler> children = new ArrayList<Handler>();
	
	/**
	 * @param controller the controller that is annotated in component
	 */
	public ComponentHandler(final Controller controller) {
		this.matcher = new ComponentMatcher(this, controller);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Handler#getMatcher()
	 */
	@Override
	public Matcher getMatcher() {
		return this.matcher;
	}

	/**
	 * @return the children handlers
	 */
	List<Handler> getChildren() {
		return children;
	}

	/**
	 * @param handler the new handler to be added
	 */
	public void add(final Handler handler) {
		this.children.add(handler);
	}
	
	/**
	 * sort children handler by its priority
	 */
	public void sort() {

		Collections.sort(this.children, new Comparator<Handler>() {
			public int compare(final Handler o1, final Handler o2) {
				return o1.getMatcher().getPriority() - o2.getMatcher().getPriority();
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Handler#handle(
	 * 	com.corona.servlet.MatchResult, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse
	 * )
	 */
	@Override
	public void handle(
			final MatchResult result, final HttpServletRequest request, final HttpServletResponse response
	) throws HandleException {
		
		((Handler) result.get(ComponentHandler.class.getName())).handle(result, request, response);
	}
}

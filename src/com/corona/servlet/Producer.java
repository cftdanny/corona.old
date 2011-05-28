/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.corona.context.ContextManager;
import com.corona.context.Key;
import com.corona.context.extension.DecoratedMethod;

/**
 * <p>The producer is used to create HTTP response output stream by an annotated method in component. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Producer {

	/**
	 * @return the component key
	 */
	Key<?> getKey();
	
	/**
	 * @return the injection method that can be invoked in context manager 
	 */
	DecoratedMethod getDecoratedMethod();
	
	/**
	 * <p>Create HTTP response output by current context manager, root object. Usually, HTML output can be
	 * produce by JSP, Velocity, FreeMaker, etc. </p>
	 * 
	 * @param contextManager current context manager
	 * @param response the HTTP response
	 * @param component the component of producer
	 * @param data the root data object return by producer method
	 * @param out the HTTP output stream
	 * @throws ProduceException if fail to produce HTTP response
	 */
	void produce(
			ContextManager contextManager, HttpServletResponse response, OutputStream out, 
			Object component, Object data
	) throws ProduceException;
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.freemaker;

import java.io.OutputStream;

import com.corona.context.ContextManager;
import com.corona.servlet.ProduceException;

/**
 * <p>The compiled Velocity template that is used to produce HTTP response. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Script {

	/**
	 * @param contextManager the context manager
	 * @param root the root object
	 * @param out the output stream
	 * @exception ProduceException fail to produce HTTP response
	 */
	void execute(ContextManager contextManager, Object root, OutputStream out) throws ProduceException;
}

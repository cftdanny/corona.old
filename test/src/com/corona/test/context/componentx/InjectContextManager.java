/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.context.componentx;

import com.corona.context.ContextManager;
import com.corona.context.annotation.Inject;

/**
 * <p>This class is used to test inject context manager to component </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InjectContextManager {

	/**
	 * the context manager
	 */
	@Inject private ContextManager contextManager;

	/**
	 * @return the contextManager
	 */
	public ContextManager getContextManager() {
		return contextManager;
	}
}

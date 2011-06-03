/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.context.spi.AbstractScope;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This scope is used to store session variable into HTTP session </p>
 *
 * @author $Author$
 * @version $Id$
 */
class SessionScope extends AbstractScope {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(SessionScope.class);

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Scope#get(com.corona.context.ContextManager, com.corona.context.Key)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> T get(final ContextManager contextManager, final Key<T> key) {
		
		// resolve variable map in session, if doesn't exist, create a new variable map
		HttpSession session = contextManager.get(HttpSession.class);
		Map<Key, Object> variables = (Map<Key, Object>) session.getAttribute(SessionScope.class.getName());
		if (variables == null) {
			variables = new HashMap<Key, Object>();
			session.setAttribute(SessionScope.class.getName(), variables);
		}
		
		this.logger.info("Try to resolve component from cached session repository with key [{0}]", key);
		T component = (T) variables.get(key);
		if (component != null) {
			return component;
		} 

		this.logger.debug("Component with key [{0}] is not cached in session repository, will create it", key);
		Descriptor<T> descriptor = this.getDescriptor(contextManager, key);
		if (descriptor == null) {
			this.logger.error("Component with key [{0}] is not registered, can not create this component", key);
			throw new ConfigurationException(
					"Component with key [{0}] is not registered, can not create this component", key
			);
		}
		
		this.logger.debug("Try to create component with key [{0}], descriptor [{1}]", key, descriptor);
		component = descriptor.getValue(contextManager);
		variables.put(key, component);
		this.logger.info("Component with key [{0}] has been create and cached to session repository", key);
		
		return component;
	}
}

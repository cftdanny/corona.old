/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManagerFactory;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Select;

/**
 * <p>The helper {@link Matcher} that checks GET, POST, PUT and DELETE for HTTP request. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractMatcher implements Matcher {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(AbstractMatcher.class);
	
	/**
	 * whether restrict to access content
	 */
	private Selectors selectors = new Selectors();
	
	/**
	 * @param contextManagerFactory the current context manager factory
	 * @param method the method
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AbstractMatcher(final ContextManagerFactory contextManagerFactory, final Method method) {
		
		for (Annotation annotation : method.getAnnotations()) {
			
			if (annotation.annotationType().isAnnotationPresent(Select.class)) {
				SelectorFactory factory = contextManagerFactory.getExtension(
						SelectorFactory.class, annotation.annotationType()
				);
				if (factory == null) {
					this.logger.error("Select factory for [{0}] in method [{0}] does not exist",
							annotation, method
					);
					throw new ConfigurationException("Select factory for [{0}] in method [{0}] does not exist",
							annotation, method
					);
				}
				
				this.selectors.add(factory.create(contextManagerFactory, method, annotation));
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.Matcher#match(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public MatchResult match(final String path, final HttpServletRequest request) {
		return this.selectors.select(path, request) ? null : this.match(path);
	}
	
	/**
	 * @param path the HTTP request path 
	 * @return the matched result or <code>null</code> if does not matched
	 */
	protected abstract MatchResult match(final String path);
}

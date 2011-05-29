/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ContextUtil;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.context.Visitor;
import com.corona.context.annotation.Application;
import com.corona.context.annotation.Inject;
import com.corona.context.extension.DecoratedMethod;
import com.corona.context.extension.DecoratedMethodFactory;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Match;
import com.corona.servlet.annotation.Produce;
import com.corona.servlet.annotation.Service;
import com.corona.servlet.annotation.WebResource;

/**
 * <p>This handler investigation tools will find all HTTP response producers that are defined in component
 * within context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Investigator implements Visitor {
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(Investigator.class);

	/**
	 * the context manager factory
	 */
	private ContextManagerFactory contextManagerFactory;
	
	/**
	 * all handlers that are defined in context manager factory
	 */
	private List<Handler> handlers;
	
	/**
	 * all component handler keys
	 */
	private List<Key<?>> handlerKeys;
	
	/**
	 * @param contextManagerFactory the current context manager factory
	 */
	Investigator(final ContextManagerFactory contextManagerFactory) {
		this.contextManagerFactory = contextManagerFactory;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Visitor#visit(com.corona.context.Key, com.corona.context.Descriptor)
	 */
	@Override
	public void visit(final Key<?> key, final Descriptor<?> descriptor) {
		
		logger.info("Searching handler with component key [{0}]", key);
		Class<?> componentClass = descriptor.getComponentClass();
		if (componentClass == null) {
			return;
		}
		
		if (Handler.class.isAssignableFrom(key.getProtocolType())) {
			// component key handler, should application scope
			if (descriptor.getScopeType().equals(Application.class)) {
				this.handlerKeys.add(key);
			}
		} else if (componentClass.isAnnotationPresent(WebResource.class)) {
			// producer method handler
			add(key, componentClass);
		}
	}

	/**
	 * @return all handlers that are defined in context manager factory
	 */
	Handlers getHandlers() {
		
		// create empty producer handlers and component handlers
		this.handlers = new ArrayList<Handler>();
		this.handlerKeys = new ArrayList<Key<?>>();
		
		// inspect all components in context manager factory to find all handlers
		this.contextManagerFactory.inspect(this);
		
		// if there are defined component handlers, will resolve from context manager
		if (this.handlerKeys.size() > 0) {
			
			ContextManager contextManager = this.contextManagerFactory.create();
			for (Key<?> key : this.handlerKeys) {
				this.handlers.add((Handler) contextManager.get(key)); 
			}
			contextManager.close();
		}
		
		// sort all handlers by their match priority
		Collections.sort(this.handlers, new Comparator<Handler>() {
			public int compare(final Handler o1, final Handler o2) {
				return o1.getMatcher().getPriority() - o2.getMatcher().getPriority();
			}
		});
		return new Handlers(this.handlers);
	}
	
	/**
	 * @param key the component key
	 * @param componentClass the component class
	 */
	private void add(final Key<?> key, final Class<?> componentClass) {
		
		for (Method method : componentClass.getMethods()) {
			
			Annotation annotation = ContextUtil.findChainedAnnotation(method, Match.class);
			if (annotation != null) {
				
				this.logger.info("Create HTTP request handler with method [{0}]", method);
				try {
					this.handlers.add(new ProducerHandler(
							this.getMatcher(method, annotation), this.getProducer(key, method)
					));
				} catch (Throwable e) {
					this.logger.error("Fail to register HTTP request handler with method [{0}]", e, method);
				}
			}
		}
	}
	
	/**
	 * @param method the method that is annotated with matcher annotation
	 * @param annotation the annotated matcher annotation
	 * @return the matcher
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Matcher getMatcher(final Method method, final Annotation annotation) {
		
		MatcherFactory factory = contextManagerFactory.getExtension(
				MatcherFactory.class, annotation.annotationType()
		);
		if (factory == null) {
			this.logger.error("Matcher factory for annotation [{0}] does not exist", annotation);
			throw new ConfigurationException(
					"Matcher factory for annotation [{0}] does not exist", annotation
			);
		}
		return factory.create(method, annotation);
	}
	
	/**
	 * @param method the method
	 * @return the annotated method
	 */
	DecoratedMethod getMethod(final Method method) {
		
		Class<? extends Annotation> annotationType = Inject.class;
		Annotation annotation = ContextUtil.findInjectAnnotation(method);
		if (annotation != null) {
			annotationType = annotation.annotationType();
		}
		DecoratedMethodFactory factory = this.contextManagerFactory.getExtension(
				DecoratedMethodFactory.class, annotationType
		);
		
		if (factory == null) {
			this.logger.error("Annotated method factory for method [{0}] does not exist", method);
			throw new ConfigurationException("Annotated method factory for method [{0}] does not exist", method);
		}
		
		return factory.create(this.contextManagerFactory, method);
	}
	
	/**
	 * @param key the component key
	 * @param method the method that is annotated with matcher
	 * @return the producer that method will use
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Producer getProducer(final Key<?> key, final Method method) {
		
		Annotation annotation = ContextUtil.findChainedAnnotation(method, Produce.class);
		ProducerFactory factory = this.contextManagerFactory.getExtension(
				ProducerFactory.class, annotation == null ? Service.class : annotation.annotationType()
		);
		
		if (factory == null) {
			this.logger.error("Producer factory for method [{0}] is not defined", method);
			throw new ConfigurationException("Producer factory for method [{0}] is not defined", method);
		}
		
		return factory.create(key, this.getMethod(method));
	}
}

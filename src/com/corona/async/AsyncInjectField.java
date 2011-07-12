/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.async;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import com.corona.component.cookie.CookieManager;
import com.corona.context.AbstractInjectField;
import com.corona.context.ConfigurationException;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.ConvertUtil;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class AsyncInjectField extends AbstractInjectField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(AsyncInjectField.class);
	
	/**
	 * the scheduler name
	 */
	private String name;
	
	/**
	 * @param field the field that is annotated with an annotation type
	 */
	AsyncInjectField(final Field field) {
		
		// construct super class and get parameter name
		super(field);
		
		this.name = field.getAnnotation(Async.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = field.getName();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectField#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {
		
		Object component =  contextManager.get(this.getType(), this.name);
		
		AsyncInterceptor interceptor = new AsyncInterceptor(component);
		return Proxy.newProxyInstance(null, new Class<?>[] {this.getType()}, interceptor);
	}
}

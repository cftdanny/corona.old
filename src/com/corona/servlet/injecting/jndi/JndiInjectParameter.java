/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.jndi;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import javax.naming.InitialContext;

import com.corona.context.AbstractInjectParameter;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Jndi;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class JndiInjectParameter extends AbstractInjectParameter {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(JndiInjectParameter.class);

	/**
	 * the component name
	 */
	private String name = null;

	/**
	 * @param accessible the constructor or method that parameter exists in
	 * @param parameterType the class type of annotated parameter
	 * @param annotations all annotations for parameter
	 */
	JndiInjectParameter(
			final AccessibleObject accessible, final Class<?> parameterType, final Annotation[] annotations
	) {
		
		super(accessible, parameterType, annotations);
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(Jndi.class)) {
				this.name = ((Jndi) annotation).value();
				break;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectParameter#get(com.corona.context.ContextManager)
	 */
	@Override
	public Object get(final ContextManager contextManager) {
		
		InitialContext context = null;
		try {
			context = new InitialContext();
			return context.lookup(this.name);
		} catch (Exception e) {
			this.logger.error(
					"Fail to lookup value for parameter [{0}] in constructor or method [{1}] from JNDI context", 
					this.getType(), this.getAccessible()
			);
			throw new ValueException(
					"Fail to lookup value for parameter [{0}] in constructor or method [{1}] from JNDI context", 
					this.getType(), this.getAccessible()
			);
		} finally {
			
			if (context != null) {
				try {
					context.close();
				} catch (Exception e) {
					this.logger.error("Fail to close JNDI context, just skip this error", e);
				}
			}
		}
	}
}

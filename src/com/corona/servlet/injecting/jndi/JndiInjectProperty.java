/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.jndi;

import java.beans.Introspector;
import java.lang.reflect.Method;

import javax.naming.InitialContext;

import com.corona.context.AbstractInjectProperty;
import com.corona.context.ContextManager;
import com.corona.context.ContextManagerFactory;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Jndi;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a setter method that is annotated with injection annotation. 
 * Its value will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class JndiInjectProperty extends AbstractInjectProperty {
	
	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(JndiInjectProperty.class);

	/**
	 * the name
	 */
	private String name = null;
 
	/**
	 * @param contextManagerFactory the context manager factory
	 * @param property the property that is annotated with {@link Inject}
	 */
	JndiInjectProperty(final ContextManagerFactory contextManagerFactory, final Method property) {
		
		super(contextManagerFactory, property);
		
		this.name = this.getMethod().getAnnotation(Jndi.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = Introspector.decapitalize(this.getMethod().getName().substring(3));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectProperty#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {

		InitialContext context = null;
		try {
			context = new InitialContext();
			return context.lookup(this.name);
		} catch (Exception e) {
			this.logger.error(
					"Fail to lookup value for property [{0}] from JNDI context", this.getMethod()
			);
			throw new ValueException(
					"Fail to lookup value for property [{0}] from JNDI context", this.getMethod()
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

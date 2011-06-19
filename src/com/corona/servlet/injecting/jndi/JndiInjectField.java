/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.jndi;

import java.lang.reflect.Field;

import javax.naming.InitialContext;

import com.corona.context.AbstractInjectField;
import com.corona.context.ContextManager;
import com.corona.context.ValueException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.annotation.Jndi;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class JndiInjectField extends AbstractInjectField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(JndiInjectField.class);
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * @param field the field that is annotated with an annotation type
	 */
	JndiInjectField(final Field field) {
		
		// construct super class and get parameter name
		super(field);
		
		this.name = field.getAnnotation(Jndi.class).value();
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
		
		InitialContext context = null;
		try {
			context = new InitialContext();
			return context.lookup(this.name);
		} catch (Exception e) {
			this.logger.error(
					"Fail to lookup value for field [{0}] from JNDI context", this.getField()
			);
			throw new ValueException(
					"Fail to lookup value for field [{0}] from JNDI context", this.getField()
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

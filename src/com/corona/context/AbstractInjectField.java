/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

import java.lang.reflect.Field;

import com.corona.context.annotation.Optional;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>The helper class of {@link InjectField}. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractInjectField implements InjectField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(AbstractInjectField.class);
	
	/**
	 * the field
	 */
	private Field field;
	
	/**
	 * whether can set null to field
	 */
	private boolean optional = false;
	
	/**
	 * @param field the annotated field
	 */
	protected AbstractInjectField(final Field field) {
		
		this.field = field;
		if (this.field.isAnnotationPresent(Optional.class)) {
			this.optional = true;
		}
		this.field.setAccessible(true);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectField#getField()
	 */
	@Override
	public Field getField() {
		return this.field;
	}
	
	/**
	 * @return the field type
	 */
	protected Class<?> getType() {
		return this.field.getType();
	}
	
	/**
	 * @return whether can set null to field
	 */
	protected boolean isOptional() {
		return optional;
	}
	
	/**
	 * @param optional whether can set null to field to set
	 */
	protected void setOptional(final boolean optional) {
		this.optional = optional;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.InjectField#set(com.corona.context.ContextManager, java.lang.Object)
	 */
	@Override
	public void set(final ContextManager contextManager, final Object component) {
		
		// resolve value from current context manager with annotation configuration
		Object value = this.get(contextManager);
		if ((value == null) && (!this.optional)) {
			this.logger.error("Resolved value is null, but field [{0}] is mandatory.", this.field);
			throw new ValueException("Resolved value is null, but field [{0}] is mandatory.", this.field);
		}
		
		// set field value to resolved value from context manager
		try {
			this.field.set(component, value);
		} catch (Throwable e) {
			this.logger.error("Fail to set resolved value to field [{0}]", e, this.field);
			throw new ValueException("Fail to set resolved value to field [{0}]", e, this.field);
		}
	}
	
	/**
	 * @param contextManager current context manager
	 * @return the resolved value for this field
	 */
	protected abstract Object get(final ContextManager contextManager);
}

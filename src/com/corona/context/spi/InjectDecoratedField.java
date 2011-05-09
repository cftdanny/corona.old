/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Field;

import com.corona.context.ContextManager;
import com.corona.context.CreationException;
import com.corona.context.annotation.Name;
import com.corona.context.annotation.Optional;
import com.corona.context.extension.DecoratedField;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class InjectDecoratedField implements DecoratedField {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(InjectDecoratedField.class);
	
	/**
	 * the field that is annotated with an annotation type
	 */
	private Field field;
	
	/**
	 * whether inject value can be null
	 */
	private boolean optional = false;

	/**
	 * the protocol type of component
	 */
	private Class<?> protocolType;
	
	/**
	 * the component name
	 */
	private String name = null;
	
	/**
	 * @param annotatedField the field that is annotated with an annotation type
	 */
	InjectDecoratedField(final Field annotatedField) {
		
		// store annotated field and make it can set value to it
		this.field = annotatedField;
		this.field.setAccessible(true);
		
		// get inject configuration from annotated field by annotation
		this.protocolType = this.field.getType();
		if (this.field.isAnnotationPresent(Name.class)) {
			this.name = this.field.getAnnotation(Name.class).value();
		}
		this.optional = (this.field.getAnnotation(Optional.class) != null);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedField#getField()
	 */
	@Override
	public Field getField() {
		return this.field;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.extension.DecoratedField#set(com.corona.context.ContextManager, java.lang.Object)
	 */
	@Override
	public void set(final ContextManager contextManager, final Object component) {
		
		// try to resolve field value from current context manager
		Object value = contextManager.get(this.protocolType, this.name);
		if ((value == null) && (!this.optional)) {
			this.logger.error("Fail to resolve value from context manager for field [{0}]", this.field);
			if (this.name == null) {
				throw new CreationException("Fail to resolve component [{0}] for field [{1}]", 
						this.protocolType, this.field
				);
			} else {
				throw new CreationException("Fail to resolve component key [{0}], name [{1}] for field [{2}]", 
						this.protocolType, this.name, this.field
				);
			}
		}
		
		// try to test whether value of field can be null (optional) or not
		if ((value == null) && (!this.optional)) {
			this.logger.error("The value of field [{0}] is mandatory, can not be NULL", this.field);
			throw new CreationException("The value of field [{0}] is mandatory, can not be NULL", this.field);
		}
		
		// set field value to resolved value from context manager
		try {
			this.field.set(component, value);
		} catch (Throwable e) {
			this.logger.error("Fail to set resolved value to field [{0}]", e, this.field);
			throw new CreationException("Fail to set resolved value to field [{0}]", e, this.field);
		}
	}
}

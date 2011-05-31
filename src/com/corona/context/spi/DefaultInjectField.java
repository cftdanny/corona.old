/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.reflect.Field;

import com.corona.context.AbstractInjectField;
import com.corona.context.ContextManager;
import com.corona.context.annotation.Inject;
import com.corona.util.StringUtil;

/**
 * <p>This class is used to register a field that is annotated with injection annotation. Its value
 * will be resolved from container before it can be used for others. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class DefaultInjectField extends AbstractInjectField {

	/**
	 * the component name
	 */
	private String name = null;
	
	/**
	 * @param annotatedField the field that is annotated with an annotation type
	 */
	DefaultInjectField(final Field annotatedField) {
		
		// if injected component name is empty, will change it to nll
		super(annotatedField);
		this.name = annotatedField.getAnnotation(Inject.class).value();
		if (StringUtil.isBlank(this.name)) {
			this.name = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.AbstractInjectField#get(com.corona.context.ContextManager)
	 */
	@Override
	protected Object get(final ContextManager contextManager) {
		return contextManager.get(this.getType(), this.name);
	}
}

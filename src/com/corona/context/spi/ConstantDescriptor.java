/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;

import com.corona.context.ContextManager;
import com.corona.context.Descriptor;
import com.corona.context.annotation.Application;

/**
 * <p>Constant descriptor is created by {@link ConstantBuilder} and register to context manager factory as application
 * scope component.
 * </p>
 * 
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type
 */
class ConstantDescriptor<T> implements Descriptor<T> {

	/**
	 * the value of constant
	 */
	private T value;
	
	/**
	 * @param value the value of component
	 */
	ConstantDescriptor(final T value) {
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		if (this.value != null) {
			if (this.value.toString().startsWith(this.value.getClass().getName() + "@")) {
				return this.value.toString();
			} else {
				return this.value.getClass().getName() + "@" + this.value.toString();
			}
		} else {
			return "(null)";
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getVersion()
	 */
	@Override
	public int getVersion() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getScopeType()
	 */
	@Override
	public Class<? extends Annotation> getScopeType() {
		return Application.class;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getImplementationClass()
	 */
	@Override
	public Class<?> getImplementationClass() {
		return this.value == null ? null : this.value.getClass();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getComponentClass()
	 */
	@Override
	public Class<?> getComponentClass() {
		return this.value == null ? null : this.value.getClass();
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Descriptor#getValue(com.corona.context.ContextManager)
	 */
	@Override
	public T getValue(final ContextManager context) {
		return this.value;
	}
}

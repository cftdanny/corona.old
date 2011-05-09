/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import com.corona.context.Builder;
import com.corona.context.ContextManagerFactory;
import com.corona.context.Key;

/**
 * <p>Constant builder is used to build constant value as component descriptor and then register it to
 * context manager factory. 
 * </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type of component
 */
public class ConstantBuilder<T> implements Builder<T> {

	/**
	 * the constant type of component
	 */
	private Class<T> type;
	
	/**
	 * the component name
	 */
	private String name;
	
	/**
	 * the component value
	 */
	private T value = null;
	
	/**
	 * <p>Create constant builder by an injection type of constant. The value of constant should implement 
	 * this injection type. </p>
	 * 
	 * @param protocolType the injection type of component
	 */
	public ConstantBuilder(final Class<T> protocolType) {
		this.type = protocolType;
	}

	/**
	 * <p>Bind name to component. </p>
	 * 
	 * @param componentName the component name
	 * @return this builder
	 */
	public ConstantBuilder<T> as(final String componentName) {
		this.name = componentName;
		return this;
	}
	
	/**
	 * <p>Set value to a component. </p>
	 * 
	 * @param componentInstance the component value
	 * @return this builder
	 */
	public ConstantBuilder<T> to(final T componentInstance) {
		this.value = componentInstance;
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Builder#build(com.corona.context.ContextManagerFactory)
	 */
	@Override
	public void build(final ContextManagerFactory contextManagerFactory) {
		
		((ContextManagerFactoryImpl) contextManagerFactory).getDescriptors().put(
				new Key<T>(this.type, this.name), new ConstantDescriptor<T>(value)
		);
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Constant Builder");
		sb.append(this.value == null ? "" : ", type (" + this.value.getClass() + ")");
		sb.append(this.value == null ? "" : ", value (" + this.value.toString() + ")");
		return sb.toString();
	}
}

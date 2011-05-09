/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;

import com.corona.context.Builder;
import com.corona.context.ContextManagerFactory;

/**
 * <p>This builder is used to register extension to enhance context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the extension protocol type
 */
public class ExtensionBuilder<T> implements Builder<T> {

	/**
	 * the extension protocol type
	 */
	private Class<T> protocol;
	
	/**
	 * the annotation type for extension
	 */
	private Class<? extends Annotation> annotation;
	
	/**
	 * the extension service provider
	 */
	private T extension;
	
	/**
	 * @param protocolType the extension protocol type
	 */
	public ExtensionBuilder(final Class<T> protocolType) {
		this.protocol = protocolType;
	}
	
	/**
	 * <p>Set annotation for extension. </p>
	 * 
	 * @param annotationType the annotation type for extension
	 * @return this builder
	 */
	public ExtensionBuilder<T> as(final Class<? extends Annotation> annotationType) {
		this.annotation = annotationType;
		return this;
	}
	
	/**
	 * <p>Set service provider for extension. </p>
	 * 
	 * @param extensionInstance the instance of extension provider
	 * @return this builder
	 */
	public ExtensionBuilder<T> to(final T extensionInstance) {
		this.extension = extensionInstance;
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.context.Builder#build(com.corona.context.ContextManagerFactory)
	 */
	@Override
	public void build(final ContextManagerFactory contextManagerFactory) {
		
		((ContextManagerFactoryImpl) contextManagerFactory).getExtensions().add(
				new ExtensionPoint(this.protocol, this.annotation), extension
		);
	}
}

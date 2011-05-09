/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;

/**
 * <p>The extension point is used to register an extension provider to context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ExtensionPoint {

	/**
	 * the annotation type
	 */
	private Class<? extends Annotation> annotationType;
	
	/**
	 * the extension type
	 */
	private final Class<?> extensionType;
	
	/**
	 * @param annotationType the annotation type
	 * @param extensionType the protocol type
	 */
	ExtensionPoint(final Class<?> extensionType, final Class<? extends Annotation> annotationType) {
		this.annotationType = annotationType;
		this.extensionType = extensionType;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.annotationType.hashCode() + 7 * this.extensionType.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		
		if (!(obj instanceof ExtensionPoint)) {
			return false;
		}
		
		ExtensionPoint other = (ExtensionPoint) obj;
		return this.annotationType.equals(other.annotationType) && this.extensionType.equals(other.extensionType);
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Extension, ");
		sb.append("annotation (" + this.annotationType + "), ");
		sb.append("protocol (" + this.extensionType + ")");
		return sb.toString();
	}
}

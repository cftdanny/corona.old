/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context;

/**
 * <p>The key of component that is used to register or find component descriptor in context container. A key
 * is madden up by injection type and component name. If both injection type and component name of key are
 * equal, the key will be equal. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the injection type
 */
public class Key<T> {

	/**
	 * the injection type of component
	 */
	private Class<T> protocolType;
	
	/**
	 * the component name
	 */
	private String componentName;
	
	/**
	 * @param protocolType the injection type of component
	 */
	public Key(final Class<T> protocolType) {
		this(protocolType, null);
	}

	/**
	 * @param protocolType the injection type of component
	 * @param componentName the component name
	 */
	public Key(final Class<T> protocolType, final String componentName) {
		this.protocolType = protocolType;
		this.componentName = componentName;
	}

	/**
	 * @return the component injection type
	 */
	public Class<T> getProtocolType() {
		return this.protocolType;
	}
	
	/**
	 * @return the component name
	 */
	public String getComponentName() {
		return this.componentName;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.protocolType.getName() + (this.componentName == null ? "" : ("@" + this.componentName));
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.protocolType.hashCode() + ((this.componentName) == null ? 0 : this.componentName.hashCode() * 7);
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(final Object obj) {
		
		if (!(obj instanceof Key)) {
			return false;
		}
		
		Key<T> other = (Key<T>) obj;
		if (!this.protocolType.equals(other.protocolType)) {
			return false;
		}
		
		if (this.componentName == null) {
			return other.componentName == null;
		} else {
			return this.componentName.equals(other.componentName);
		}
	}
}

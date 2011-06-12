/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data.sql;

/**
 * <p>This key is used to register a prepared statement by a class and name. </p>
 * 
 * @author $Author$
 * @version $Id$
 */
class Key {

	/**
	 * the class type. Maybe home class or named statement class
	 */
	private Class<?> type;
	
	/**
	 * the statement name
	 */
	private String name;
	
	/**
	 * @param type the class type. Maybe home class or named statement class
	 */
	public Key(final Class<?> type) {
		this(type, null);
	}

	/**
	 * @param type the class type. Maybe home class or named statement class
	 * @param name the statement name
	 */
	public Key(final Class<?> type, final String name) {
		this.type = type;
		this.name = name;
	}

	/**
	 * @return the class type. Maybe home class or named statement class
	 */
	public Class<?> getType() {
		return this.type;
	}
	
	/**
	 * @return the statement name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.type.getName() + (this.name == null ? "" : ("@" + this.name));
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.type.hashCode() + ((this.name) == null ? 0 : this.name.hashCode() * 7);
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		
		if (!(obj instanceof Key)) {
			return false;
		}
		
		Key other = (Key) obj;
		if (!this.type.equals(other.type)) {
			return false;
		}
		
		if (this.name == null) {
			return other.name == null;
		} else {
			return this.name.equals(other.name);
		}
	}
}

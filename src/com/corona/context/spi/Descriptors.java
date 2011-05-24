/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.util.HashMap;
import java.util.Map;

import com.corona.context.ConfigurationException;
import com.corona.context.Descriptor;
import com.corona.context.Key;
import com.corona.context.Visitor;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.util.StringUtil;

/**
 * <p>All registered descriptors in context manager. </p>
 *
 * @author $Author$
 * @version $Id$
 */
class Descriptors {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(Descriptors.class);
	
	/**
	 * all registered descriptors
	 */
	private Map<Key<?>, Descriptor<?>> descriptors = new HashMap<Key<?>, Descriptor<?>>();

	/**
	 * component alias, support to get component by string name
	 */
	private Map<String, Key<?>> componentAlias = new HashMap<String, Key<?>>();
	
	/**
	 * @return all component keys
	 */
	Key<?>[] getKeys() {
		return this.descriptors.keySet().toArray(new Key[0]);
	}
	
	/**
	 * <p>Find component descriptor by its key. If key is not registered, just return <b>null</b>. 
	 * </p>
	 * 
	 * @param <T> the injection type of component
	 * @param key the key of element
	 * @return the descriptor or <b>null</b> if key is not registered
	 */
	@SuppressWarnings("unchecked")
	<T> Descriptor<T> get(final Key<T> key) {
		return (Descriptor<T>) this.descriptors.get(key);
	}
	
	/**
	 * @param name the component alias (component name)
	 * @return the component key about alias
	 */
	Key<?> get(final String name) {
		return this.componentAlias.get(name);
	}
	
	/**
	 * @param visitor the visitor
	 */
	void inspect(final Visitor visitor) {
		
		for (Map.Entry<Key<?>, Descriptor<?>> pair : this.descriptors.entrySet()) {
			visitor.visit(pair.getKey(), pair.getValue());
		}
	}
	
	/**
	 * <p>Register a new element. The key of component must not exist in container before. </p>
	 * 
	 * @param key the element key
	 * @param descriptor the new element
	 */
	void put(final Key<?> key, final Descriptor<?> descriptor) {
		
		// if two components with same key and version, will be configuration error 
		Descriptor<?> previous = this.descriptors.get(key);
		if ((previous != null) && (previous.getVersion() == descriptor.getVersion())) {
			this.logger.error("Component with key [{0}], descriptor [{1}] already exists.", key, descriptor);
			throw new ConfigurationException(
					"Component with key [{0}], class [{1}] already exists.", key, descriptor
			);
		}
		
		// if component (key) is not installed or will install new component (new version) 
		if ((previous == null) || (previous.getVersion() < descriptor.getVersion())) {
			this.logger.info("Register component with key [{0}], descriptor [{1}]", key, descriptor);
			this.descriptors.put(key, descriptor);
		}
		
		// try to register component alias
		if (!StringUtil.isBlank(descriptor.getAlias())) {
			
			if (this.componentAlias.containsKey(descriptor.getAlias())) {
				this.logger.info("Alias [{0}] for component with key [{1}], descriptor [{2}] already exists",
						descriptor.getAlias(), key, descriptor
				);
				throw new ConfigurationException(
						"Alias [{0}] for component with key [{1}], descriptor [{2}] already exists",
						descriptor.getAlias(), key, descriptor
				);
			} else {
				this.componentAlias.put(descriptor.getAlias(), key);
			}
		}
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.util.HashMap;
import java.util.Map;

import com.corona.context.ConfigurationException;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This repository is used to store all extension points for context manager factory </p>
 *
 * @author $Author$
 * @version $Id$
 */
class ExtensionRepository {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(ExtensionRepository.class);
	
	/**
	 * all extension points
	 */
	private Map<ExtensionPoint, Object> extensions = new HashMap<ExtensionPoint, Object>();
	
	/**
	 * @param extensionPoint the extension point
	 * @param extension the extension provider
	 */
	void add(final ExtensionPoint extensionPoint, final Object extension) {
		
		if (this.extensions.containsKey(extensionPoint)) {
			this.logger.error("Extension [{0}] already exist", extensionPoint);
			throw new ConfigurationException("Extension [{0}] already exist", extensionPoint);
		}
		
		this.logger.debug("Register extension [{0}] with implementation [{1}]", extensionPoint, extension);
		this.extensions.put(extensionPoint, extension);
	}
	
	/**
	 * @param extensionPoint the extension point
	 * @return the extension provider
	 */
	Object get(final ExtensionPoint extensionPoint) {
		return this.extensions.get(extensionPoint);
	}
}

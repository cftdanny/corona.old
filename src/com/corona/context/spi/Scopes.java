/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.context.spi;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.corona.context.ConfigurationException;
import com.corona.context.Scope;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;

/**
 * <p>This class is used to store all scopes for context manager factory. </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Scopes {

	/**
	 * the logger
	 */
	private Log logger = LogFactory.getLog(Scopes.class);
	
	/**
	 * all scopes with their annotation as key
	 */
	private Map<Class<? extends Annotation>, Scope> scopes = new HashMap<Class<? extends Annotation>, Scope>();
	
	/**
	 * <p>Get scope by its annotation type. </p>.
	 * 
	 * @param type the annotation type of scope
	 * @return the scope or <code>null</code> if scope of annotation does not exists
	 */
	public Scope get(final Class<? extends Annotation> type) {
		return this.scopes.get(type);
	}
	
	/**
	 * 
	 * @param type the annotation type of scope
	 * @param scope the scope
	 */
	public void add(final Class<? extends Annotation> type, final Scope scope) {
		
		if (this.scopes.containsKey(type)) {
			this.logger.error("Scope witrh annotation [{0}] already registered", type);
			throw new ConfigurationException(
					"Scope witrh annotation [{0}] already registered", type
			); 
		}
		
		this.logger.info("Register scope with annotation [{0}], class [{1}]", type, scope);
		this.scopes.put(type, scope);
	}
}

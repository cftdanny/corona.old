/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * <p>The factory is used to create cypher factory by its name </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class CypherFactory {

	/**
	 * all cypher factories
	 */
	private static Map<String, CypherFactory> factories = null;

	/**
	 * @return the factory name
	 */
	public abstract String getName();

	/**
	 * @return the generated key
	 * @exception CypherException if fail to generate pair key
	 */
	public abstract CertifiedKey generateKey() throws CypherException;

	/**
	 * @param size the key size
	 * @return the generated key
	 * @exception CypherException if fail to generate pair key
	 */
	public abstract CertifiedKey generateKey(int size) throws CypherException;

	/**
	 * @param key the key for cypher
	 * @return the cypher
	 * @exception CypherException if fail to create cypher
	 */
	public abstract Cypher create(CertifiedKey key) throws CypherException;

	/**
	 * @return the default DES cypher factory
	 */
	public static CypherFactory get()  {
		return get(DESCypherFactory.NAME);
	}
	
	/**
	 * @param name the cypher name
	 * @return the cypher factory with specified name or <code>null</code> if can't find
	 */
	public static CypherFactory get(final String name) {
	
		if (factories == null) {
			
			factories = new HashMap<String, CypherFactory>();
			for (CypherFactory factory : ServiceLoader.load(CypherFactory.class)) {
				factories.put(factory.getName(), factory);
			}
		}
		return factories.get(name);
	}
}

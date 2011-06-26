/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.util.Map;

/**
 * <p>The factory that is used to create remote factory </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class ClientFactory {

	/**
	 * utility class
	 */
	private ClientFactory() {
		// do nothing
	}
	
	/**
	 * @param config that configuration in order to create client
	 * @return the default client
	 * @exception RemoteException if fail to create client
	 */
	public static Client create(final Map<String, ?> config) throws RemoteException {
		return create("AVRO", config);
		
	}
	
	/**
	 * @param name the implementation name
	 * @param config that configuration in order to create client
	 * @return the default client
	 * @exception RemoteException if fail to create client
	 */
	public static Client create(final String name, final Map<String, ?> config) throws RemoteException {
		return null;
	}
}

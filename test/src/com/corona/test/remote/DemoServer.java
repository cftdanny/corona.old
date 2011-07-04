/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.remote;

import com.corona.crypto.CertifiedKey;
import com.corona.crypto.Cypher;
import com.corona.crypto.CypherException;
import com.corona.crypto.CypherFactory;
import com.corona.remote.AbstractServer;
import com.corona.remote.RemoteException;

/**
 * <p>Demo server for test case </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DemoServer extends AbstractServer {

	/**
	 * the key
	 */
	public static final CertifiedKey KEY = new CertifiedKey(new byte[] {
			94, 44, 117, 69, 124, -23, 73, -9
	});
	
	/**
	 * the demo server
	 */
	public DemoServer() {
		
		this.getSupportedVersions().add((byte) 10);
		
		this.setAlgorithm("DES");
		this.setServerKey(KEY);
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Server#getClientCypher(java.lang.String)
	 */
	@Override
	public Cypher getClientCypher(final String token) throws RemoteException {
		
		try {
			return CypherFactory.get("DES").create(KEY);
		} catch (CypherException e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Server#getToken(java.lang.String)
	 */
	@Override
	public String getToken(final String token) {
		return token;
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import com.corona.crypto.CertifiedKey;
import com.corona.crypto.Cypher;
import com.corona.crypto.CypherFactory;
import com.corona.crypto.CypherException;

/**
 * <p>The configuration in order to exchange data with remote server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AbstractServer {

	/**
	 * the cypher algorithm
	 */
	private String algorithm;

	/**
	 * the server key
	 */
	private CertifiedKey serverKey;
	
	/**
	 * the cypher to encrypt and decrypt data for remote server
	 */
	private Cypher serverCypher;
	
	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(final String algorithm) {
		this.algorithm = algorithm;
	}
	
	/**
	 * @return the server key
	 */
	public CertifiedKey getServerKey() {
		return serverKey;
	}

	/**
	 * @param serverKey the serve kKey to set
	 */
	public void setServerKey(final CertifiedKey serverKey) {
		this.serverKey = serverKey;
	}

	/**
	 * @return the server cypher
	 * @throws RemoteException if fail to create server cypher
	 */
	public Cypher getServerCypher() throws RemoteException {

		if (this.serverCypher == null) {
			try {
				this.serverCypher = CypherFactory.get(this.algorithm).create(this.serverKey);
			} catch (CypherException e) {
				throw new RemoteException("Fail to create {0} cypher by server key", e, this.algorithm);
			}
		}
		return this.serverCypher;
	}
}

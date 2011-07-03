/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.util.HashSet;
import java.util.Set;

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
public abstract class AbstractServer implements Server {

	/**
	 * whether allow client sends development mode request
	 */
	private boolean eableDevelopmentMode = false;
	
	/**
	 * the supported client versions
	 */
	private Set<Byte> supportedVersions = new HashSet<Byte>();
	
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
	 * @param key the key
	 * @return the new cypher
	 * @throws RemoteException if fail to create cypher
	 */
	protected Cypher getCypher(final CertifiedKey key) throws RemoteException {
		
		try {
			return CypherFactory.get(this.algorithm).create(key);
		} catch (CypherException e) {
			throw new RemoteException("Fail to create {0} cypher by key", e, this.algorithm);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Server#getServerCypher()
	 */
	public Cypher getServerCypher() throws RemoteException {

		if (this.serverCypher == null) {
			this.serverCypher = this.getCypher(this.serverKey);
		}
		return this.serverCypher;
	}
	
	/**
	 * @param eableDevelopmentMode whether allow client sends development mode request
	 */
	public void setEableDevelopmentMode(final boolean eableDevelopmentMode) {
		this.eableDevelopmentMode = eableDevelopmentMode;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Server#isEableDevelopmentMode()
	 */
	@Override
	public boolean isEableDevelopmentMode() {
		return this.eableDevelopmentMode;
	}
	
	/**
	 * @param supportedVersions the supported client versions
	 */
	public void setSupportedVersions(final Set<Byte> supportedVersions) {
		this.supportedVersions = supportedVersions;
	}
	
	/**
	 * @return the supported client versions
	 */
	public Set<Byte> getSupportedVersions() {
		return supportedVersions;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Server#isSupportedVersion(byte)
	 */
	@Override
	public boolean isSupportedVersion(final byte version) {
		return this.supportedVersions.contains(version);
	}
}

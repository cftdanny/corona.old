/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.util.HashMap;
import java.util.Map;

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
public class Configuration {

	/**
	 * the base URL for remote server
	 */
	private String baseURL;
	
	/**
	 * the cipher to encrypt and decrypt data for remote server
	 */
	private Cypher serverCypher;
	
	/**
	 * the cipher to encrypt and decrypt data for local client
	 */
	private Cypher clientCypher;

	/**
	 * the log in URL
	 */
	private String loginURL = "/login";
	
	/**
	 * the log out URL
	 */
	private String logoutURL = "/logout";
	
	/**
	 * the properties
	 */
	private Map<String, Object> properties = new HashMap<String, Object>();
	
	/**
	 * @return the base URL for remote server
	 */
	public String getBaseURL() {
		return baseURL;
	}
	
	/**
	 * @param baseURL the base URL for remote server
	 */
	public void setBaseURL(final String baseURL) {
		this.baseURL = baseURL;
	}
	
	/**
	 * @param algorithm the algorithm for server cipher
	 * @param key the key
	 * @throws RemoteException if fail to create server cipher
	 */
	public void createServerCipher(final String algorithm, final CertifiedKey key) throws RemoteException {
		
		this.serverCypher = null;
		try {
			this.serverCypher = CypherFactory.get(algorithm).create(key);
		} catch (CypherException e) {
			throw new RemoteException("Fail to create {0} cypher by server key", e, algorithm);
		}
	}
	
	/**
	 * @return the server cipher
	 */
	public Cypher getServerCypher() {
		return serverCypher;
	}

	/**
	 * @param algorithm the algorithm for client cipher
	 * @param key the key
	 * @throws RemoteException if fail to create client cipher
	 */
	public void createClientCipher(
			final String algorithm, final CertifiedKey key) throws RemoteException {
		
		this.clientCypher = null;
		try {
			this.clientCypher = CypherFactory.get(algorithm).create(key);
		} catch (CypherException e) {
			throw new RemoteException("Fail to create {0} cypher by client key", e, algorithm);
		}
	}
	
	/**
	 * @return the client cipher
	 */
	public Cypher getClientCypher() {
		return clientCypher;
	}
	
	/**
	 * @return the log in URL
	 */
	public String getLoginURL() {
		return loginURL;
	}
	
	/**
	 * @param loginURL the log in URL to set
	 */
	public void setLoginURL(final String loginURL) {
		this.loginURL = loginURL;
	}
	
	/**
	 * @return the log out URL
	 */
	public String getLogoutURL() {
		return logoutURL;
	}
	
	/**
	 * @param logoutURL the log out URL to set
	 */
	public void setLogoutURL(final String logoutURL) {
		this.logoutURL = logoutURL;
	}

	/**
	 * @param name the property name
	 * @return the property value
	 */
	public Object getProperty(final String name) {
		return this.properties.get(name);
	}
	
	/**
	 * @param name the property name
	 * @param value the property value
	 */
	public void setProperty(final String name, final Object value) {
		this.properties.put(name, value);
	}
}

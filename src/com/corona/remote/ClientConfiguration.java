/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import java.util.HashMap;
import java.util.Map;

import com.corona.crypto.CipherEngine;
import com.corona.crypto.CipherEngineFactory;
import com.corona.crypto.CipherException;

/**
 * <p>The configuration in order to exchange data with remote server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ClientConfiguration {

	/**
	 * the base URL for remote server
	 */
	private String baseURL;
	
	/**
	 * the cipher to encrypt and decrypt data for remote server
	 */
	private CipherEngine serverCipher;
	
	/**
	 * the cipher to encrypt and decrypt data for local client
	 */
	private CipherEngine clientCipher;

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
	 * @param encryptKey the encryption key
	 * @param decryptKey the decryption key
	 * @throws RemoteException if fail to create server cipher
	 */
	public void createServerCipher(
			final String algorithm, final byte[] encryptKey, final byte[] decryptKey) throws RemoteException {
		
		this.serverCipher = null;
		try {
			this.serverCipher = CipherEngineFactory.create(algorithm);
		} catch (CipherException e) {
			throw new RemoteException("Fail to create {0} cipher engine for server", e, algorithm);
		}
		
		try {
			this.serverCipher.setEncryptKey(encryptKey);
			this.serverCipher.setDecryptKey(decryptKey);
		} catch (CipherException e) {
			throw new RemoteException("Fail to set encryption and decryption key for server", e);
		}
	}
	
	/**
	 * @return the server cipher
	 */
	public CipherEngine getServerCipher() {
		return serverCipher;
	}

	/**
	 * @param algorithm the algorithm for client cipher
	 * @param encryptKey the encryption key
	 * @param decryptKey the decryption key
	 * @throws RemoteException if fail to create client cipher
	 */
	public void createClientCipher(
			final String algorithm, final byte[] encryptKey, final byte[] decryptKey) throws RemoteException {
		
		this.clientCipher = null;
		try {
			this.clientCipher = CipherEngineFactory.create(algorithm);
		} catch (CipherException e) {
			throw new RemoteException("Fail to create {0} cipher engine for client", e, algorithm);
		}
		
		try {
			this.clientCipher.setEncryptKey(encryptKey);
			this.clientCipher.setDecryptKey(decryptKey);
		} catch (CipherException e) {
			throw new RemoteException("Fail to set encryption and decryption key for client", e);
		}
	}
	
	/**
	 * @return the client cipher
	 */
	public CipherEngine getClientCipher() {
		return clientCipher;
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

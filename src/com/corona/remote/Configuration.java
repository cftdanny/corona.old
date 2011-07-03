/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote;

import com.corona.crypto.Cypher;
import com.corona.io.avro.AvroMarshallerFactory;

/**
 * <p>The configuration in order to exchange data with remote server </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Configuration {

	/**
	 * the protocol that is used to marshal object and unmarshall stream
	 */
	private String protocol = AvroMarshallerFactory.NAME;
	
	/**
	 * the cypher to encrypt and decrypt data server specified data
	 */
	private Cypher serverCypher;
	
	/**
	 * the cypher to encrypt and decrypt data client specified data
	 */
	private Cypher clientCypher;

	/**
	 * the base URL for remote server
	 */
	private String baseURL;

	/**
	 * the log in URL
	 */
	private String loginURL = "/login";
	
	/**
	 * the log out URL
	 */
	private String logoutURL = "/logout";
	
	/**
	 * @return the protocol that is used to marshal object and unmarshall stream
	 */
	public String getProtocol() {
		return protocol;
	}
	
	/**
	 * @param protocol the protocol that is used to marshal object and unmarshall stream to set
	 */
	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}

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
	 * @return the cypher to encrypt or decrypt data server specified data
	 */
	public Cypher getServerCypher() {
		return serverCypher;
	}

	/**
	 * @param serverCypher the cypher to encrypt or decrypt data server specified data
	 */
	public void setServerCypher(final Cypher serverCypher) {
		this.serverCypher = serverCypher;
	}

	/**
	 * @return the cypher to encrypt or decrypt data client specified data
	 */
	public Cypher getClientCypher() {
		return clientCypher;
	}
	
	/**
	 * @param clientCypher the cypher to encrypt or decrypt data client specified data
	 */
	public void setClientCypher(final Cypher clientCypher) {
		this.clientCypher = clientCypher;
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
}

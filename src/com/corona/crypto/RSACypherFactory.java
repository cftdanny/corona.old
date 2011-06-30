/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * <p>The cipher to encrypt and decrypt data by RSA </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RSACypherFactory extends CypherFactory {

	/**
	 * the factory name
	 */
	public static final String NAME = "RSA";

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CypherFactory#getName()
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CypherFactory#generateKey()
	 */
	@Override
	public CertifiedKey generateKey() throws CypherException {
		return generateKey(256);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CypherFactory#generateKey(int)
	 */
	@Override
	public CertifiedKey generateKey(final int size) throws CypherException {

		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance(RSACypher.ALGORITHM);
		} catch (Exception e) {
			throw new CypherException(
					"Fail to initial [{0}] cypher with key size [{1}]", e, RSACypher.ALGORITHM, size
			);
		}
		
		KeyPair keyPair = generator.generateKeyPair();
		return new CertifiedKey(keyPair.getPublic().getEncoded(), keyPair.getPrivate().getEncoded());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CypherFactory#create(com.corona.crypto.CertifiedKey)
	 */
	@Override
	public Cypher create(final CertifiedKey key) throws CypherException {
		return new RSACypher(key);
	}
}

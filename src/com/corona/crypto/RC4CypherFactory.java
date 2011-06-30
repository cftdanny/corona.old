/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * <p>The cipher to encrypt and decrypt data by RC4 </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RC4CypherFactory extends CypherFactory {

	/**
	 * the factory name
	 */
	public static final String NAME = "RC4";

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
		return generateKey(128);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CypherFactory#generateKey(int)
	 */
	@Override
	public CertifiedKey generateKey(final int size) throws CypherException {

		KeyGenerator generator = null;
		try {
			generator = KeyGenerator.getInstance(RC4Cypher.ALGORITHM);
		} catch (Exception e) {
			throw new CypherException(
					"Fail to get key generator for [{0}] cypher", e, RC4Cypher.ALGORITHM
			);
		}
		
		try {
			generator.init(size);
		} catch (Exception e) {
			throw new CypherException(
					"Fail to initial [{0}] cypher with key size [{1}]", e, RC4Cypher.ALGORITHM, size
			);
		}
		
		SecretKey secretKey = generator.generateKey();
		return new CertifiedKey(secretKey.getEncoded());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CypherFactory#create(com.corona.crypto.CertifiedKey)
	 */
	@Override
	public Cypher create(final CertifiedKey key) throws CypherException {
		return new RC4Cypher(key);
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

/**
 * <p>This cipher engine is used to encrypt or decrypt data </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Cypher {

	/**
	 * @param data the data to be decrypted
	 * @return the decrypted data
	 * @exception CypherException if fail to decrypt data
	 */
	byte[] decrypt(byte[] data) throws CypherException;

	/**
	 * @param data the data to be encrypted
	 * @return the encrypted data
	 * @exception CypherException if fail to encrypt data
	 */
	byte[] encrypt(byte[] data) throws CypherException;
}

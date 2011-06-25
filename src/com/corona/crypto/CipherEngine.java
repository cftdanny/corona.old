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
public interface CipherEngine {

	/**
	 * @return the pair key
	 * @exception CipherException if fail to generate pair key
	 */
	Key generateKey() throws CipherException;

	/**
	 * @param size the key size
	 * @return the pair key
	 * @exception CipherException if fail to generate pair key
	 */
	Key generateKey(int size) throws CipherException;

	/**
	 * @param key the encryption key
	 * @throws CipherException if fail to create cipher with key
	 */
	void setEncryptKey(byte[] key) throws CipherException;
	
	/**
	 * @param key the decryption key
	 * @throws CipherException if fail to create cipher with key
	 */
	void setDecryptKey(byte[] key) throws CipherException;
	
	/**
	 * @param data the data to be decrypted
	 * @return the decrypted data
	 * @exception CipherException if fail to decrypt data
	 */
	byte[] decrypt(byte[] data) throws CipherException;

	/**
	 * @param data the data to be encrypted
	 * @return the encrypted data
	 * @exception CipherException if fail to encrypt data
	 */
	byte[] encrypt(byte[] data) throws CipherException;
}

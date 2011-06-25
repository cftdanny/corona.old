/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

/**
 * <p>The key pair in order to encrypt or decrypt data </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Key {

	/**
	 * the encryption key
	 */
	private byte[] encryptionKey;
	
	/**
	 * the decryption key
	 */
	private byte[] decryptionKey;
	
	/**
	 * @param key the encryption and decryption key
	 */
	public Key(final byte[] key) {
		this(key, key);
	}
	
	/**
	 * @param encryptionKey the encryption key
	 * @param decryptionKey the decryption key
	 */
	public Key(final byte[] encryptionKey, final byte[] decryptionKey) {
		this.encryptionKey = encryptionKey;
		this.decryptionKey = decryptionKey;
	}

	/**
	 * @return the encryption key
	 */
	public byte[] getEncryptionKey() {
		return encryptionKey;
	}
	
	/**
	 * @return the decryption key
	 */
	public byte[] getDecryptionKey() {
		return decryptionKey;
	}
}

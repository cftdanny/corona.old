/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

import com.corona.util.Base64;

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
	private byte[] encryptionKey = null;
	
	/**
	 * the decryption key
	 */
	private byte[] decryptionKey = null;
	
	/**
	 * @param key the encryption and decryption key
	 */
	public Key(final byte[] key) {
		this(key, key);
	}

	/**
	 * @param key the encryption and decryption key
	 */
	public Key(final String key) {
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
	 * @param encryptionKey the encryption key
	 * @param decryptionKey the decryption key
	 */
	public Key(final String encryptionKey, final String decryptionKey) {
		
		try {
			if (encryptionKey != null) {
				this.encryptionKey = Base64.decode(encryptionKey);
			}
			if (decryptionKey != null) {
				this.decryptionKey = Base64.decode(decryptionKey);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Fail to decode cryption key by Base64");
		}
	}

	/**
	 * @return the encryption key
	 */
	public byte[] getEncryptionKey() {
		return encryptionKey;
	}

	/**
	 * @return the encryption key as string
	 */
	public String getEncryptionKeyAsString() {
		return Base64.encodeBytes(encryptionKey);
	}

	/**
	 * @return the decryption key
	 */
	public byte[] getDecryptionKey() {
		return decryptionKey;
	}
	
	/**
	 * @return the decryption key as string
	 */
	public String getDecryptionKeyAsString() {
		return Base64.encodeBytes(decryptionKey);
	}
}

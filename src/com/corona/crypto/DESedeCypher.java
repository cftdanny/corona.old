/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * <p>The cipher to encrypt and decrypt data by DESede </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DESedeCypher implements Cypher {

	/**
	 * the encrypt and decrypt algorithm
	 */
	protected static final String ALGORITHM = "DESede";
	
	/**
	 * the cipher to encrypt data
	 */
	private Cipher encryptor;
	
	/**
	 * the cipher to decrypt data
	 */
	private Cipher decryptor;
	
	/**
	 * @param key the key
	 * @exception CypherException if fail to create cypher
	 */
	public DESedeCypher(final CertifiedKey key) throws CypherException {
		
		if (key.getEncryptionKey() != null) {
			this.setEncryptKey(key.getEncryptionKey());
		}
		if (key.getDecryptionKey() != null) {
			this.setDecryptKey(key.getDecryptionKey());
		}
	}

	/**
	 * @return DESede secret key factory
	 * @throws NoSuchAlgorithmException if fail to get DESede secret key factory
	 */
	private SecretKeyFactory getSecretKeyFactory() throws NoSuchAlgorithmException {
		return SecretKeyFactory.getInstance(ALGORITHM);
	}

	/**
	 * @param key the key
	 * @throws CypherException if fail to create encryption cipher
	 */
	private void setEncryptKey(final byte[] key) throws CypherException {
		
		SecretKey secretKey = null;
		try {
			secretKey = getSecretKeyFactory().generateSecret(new DESedeKeySpec(key));
		} catch (Exception e) {
			throw new CypherException("Fail to create secret key for [{0}] cipher", e, ALGORITHM);
		}
		
		try {
			this.encryptor = Cipher.getInstance(ALGORITHM);
			this.encryptor.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (Exception e) {
			throw new CypherException("Fail to create and initialize [{0}] cipher", e, ALGORITHM);
		}
	}

	/**
	 * @param key the key
	 * @throws CypherException if fail to create encryption cipher
	 */
	private void setDecryptKey(final byte[] key) throws CypherException {

		SecretKey secretKey = null;
		try {
			secretKey = getSecretKeyFactory().generateSecret(new DESedeKeySpec(key));
		} catch (Exception e) {
			throw new CypherException("Fail to create secret key for [{0}] cipher", e, ALGORITHM);
		}

		try {
			this.decryptor = Cipher.getInstance(ALGORITHM);
			this.decryptor.init(Cipher.DECRYPT_MODE, secretKey);
		} catch (Exception e) {
			throw new CypherException("Fail to create and initialize [{0}] cipher", e, ALGORITHM);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.Cypher#decrypt(byte[])
	 */
	@Override
	public byte[] decrypt(final byte[] data) throws CypherException {
		
		try {
			return this.decryptor.doFinal(data);
		} catch (Exception e) {
			throw new CypherException("Fail to decrypt data with [{0}] cipher", e, ALGORITHM);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.Cypher#encrypt(byte[])
	 */
	@Override
	public byte[] encrypt(final byte[] data) throws CypherException {

		try {
			return this.encryptor.doFinal(data);
		} catch (Exception e) {
			throw new CypherException("Fail to encrypt data with [{0}] cipher", e, ALGORITHM);
		}
	}
}

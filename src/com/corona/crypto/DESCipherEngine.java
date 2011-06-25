/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * <p>The cipher to encrypt and decrypt data by DES </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DESCipherEngine implements CipherEngine {

	/**
	 * the encrypt and decrypt algorithm
	 */
	private static final String ALGORITHM = CipherEngineFactory.DES;
	
	/**
	 * the cipher to encrypt data
	 */
	private Cipher encryptor;
	
	/**
	 * the cipher to decrypt data
	 */
	private Cipher decryptor;
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#generateKey()
	 */
	@Override
	public Key generateKey() throws CipherException {
		
		KeyGenerator generator = null;
		try {
			generator = KeyGenerator.getInstance(ALGORITHM);
		} catch (Exception e) {
			throw new CipherException("Fail to get key generator with DES algorithm", e);
		}
		
		SecretKey secretKey = generator.generateKey();
		return new Key(secretKey.getEncoded());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#generateKey(int)
	 */
	@Override
	public Key generateKey(final int size) throws CipherException {

		KeyGenerator generator = null;
		try {
			generator = KeyGenerator.getInstance(ALGORITHM);
		} catch (Exception e) {
			throw new CipherException("Fail to get key generator with DES algorithm", e);
		}
		
		try {
			generator.init(size, SecureRandom.getInstance(ALGORITHM));
		} catch (Exception e) {
			throw new CipherException("Fail to initial seed to key generator with " + ALGORITHM, e);
		}
		
		SecretKey secretKey = generator.generateKey();
		return new Key(secretKey.getEncoded());
	}

	/**
	 * @return DES secret key factory
	 * @throws NoSuchAlgorithmException if fail to get DES secret key factory
	 */
	private SecretKeyFactory getSecretKeyFactory() throws NoSuchAlgorithmException {
		return SecretKeyFactory.getInstance(ALGORITHM);
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#setEncryptKey(byte[])
	 */
	@Override
	public void setEncryptKey(final byte[] key) throws CipherException {
		
		SecretKey secretKey = null;
		try {
			secretKey = getSecretKeyFactory().generateSecret(new DESKeySpec(key));
		} catch (Exception e) {
			throw new CipherException("Fail to create encryption DES secret key", e);
		}
		
		try {
			this.encryptor = Cipher.getInstance(ALGORITHM);
			this.encryptor.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (Exception e) {
			throw new CipherException("Fail to create and initialize DES cipher", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#setDecryptKey(byte[])
	 */
	@Override
	public void setDecryptKey(final byte[] key) throws CipherException {

		SecretKey secretKey = null;
		try {
			secretKey = getSecretKeyFactory().generateSecret(new DESKeySpec(key));
		} catch (Exception e) {
			throw new CipherException("Fail to create decryption DES secret key", e);
		}

		try {
			this.decryptor = Cipher.getInstance(ALGORITHM);
			this.decryptor.init(Cipher.DECRYPT_MODE, secretKey);
		} catch (Exception e) {
			throw new CipherException("Fail to create and initialize decryption DES cipher", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#decrypt(byte[])
	 */
	@Override
	public byte[] decrypt(final byte[] data) throws CipherException {
		
		try {
			return this.decryptor.doFinal(data);
		} catch (Exception e) {
			throw new CipherException("Fail to decrypt data with DES cipher", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#encrypt(byte[])
	 */
	@Override
	public byte[] encrypt(final byte[] data) throws CipherException {

		try {
			return this.encryptor.doFinal(data);
		} catch (Exception e) {
			throw new CipherException("Fail to decrypt data with DES cipher", e);
		}
	}
}

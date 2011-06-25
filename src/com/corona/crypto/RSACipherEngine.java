/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * <p>The cipher to encrypt and decrypt data by DES </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RSACipherEngine implements CipherEngine {

	/**
	 * the encrypt and decrypt algorithm
	 */
	private static final String ALGORITHM = CipherEngineFactory.RSA;
	
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
		
		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance(ALGORITHM);
		} catch (Exception e) {
			throw new CipherException("Fail to get key generator with RSA algorithm", e);
		}
		
		KeyPair keyPair = generator.generateKeyPair();
		return new Key(keyPair.getPublic().getEncoded(), keyPair.getPrivate().getEncoded());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#generateKey(int)
	 */
	@Override
	public Key generateKey(final int size) throws CipherException {

		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance(ALGORITHM);
		} catch (Exception e) {
			throw new CipherException("Fail to get key generator with RSA algorithm", e);
		}
		
		try {
			generator.initialize(size, SecureRandom.getInstance(ALGORITHM));
		} catch (Exception e) {
			throw new CipherException("Fail to initial seed to key generator with " + ALGORITHM, e);
		}
		
		KeyPair keyPair = generator.generateKeyPair();
		return new Key(keyPair.getPublic().getEncoded(), keyPair.getPrivate().getEncoded());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#setEncryptKey(byte[])
	 */
	@Override
	public void setEncryptKey(final byte[] key) throws CipherException {
		
		PublicKey publicKey = null;
		try {
			publicKey = KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(key));
		} catch (Exception e) {
			throw new CipherException("Fail to create encryption RSA public key", e);
		}
		
		try {
			this.encryptor = Cipher.getInstance(ALGORITHM);
			this.encryptor.init(Cipher.ENCRYPT_MODE, publicKey);
		} catch (Exception e) {
			throw new CipherException("Fail to create and initialize RSA cipher", e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.crypto.CipherEngine#setDecryptKey(byte[])
	 */
	@Override
	public void setDecryptKey(final byte[] key) throws CipherException {

		PrivateKey publicKey = null;
		try {
			publicKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(key));
		} catch (Exception e) {
			throw new CipherException("Fail to create encryption RSA private key", e);
		}

		try {
			this.decryptor = Cipher.getInstance(ALGORITHM);
			this.decryptor.init(Cipher.DECRYPT_MODE, publicKey);
		} catch (Exception e) {
			throw new CipherException("Fail to create and initialize decryption RSA cipher", e);
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
			throw new CipherException("Fail to decrypt data with RSA cipher", e);
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
			throw new CipherException("Fail to decrypt data with RSA cipher", e);
		}
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypto;

/**
 * <p>The factory is used to create CipherEngine by name </p>
 *
 * @author $Author$
 * @version $Id$
 */
public final class CipherEngineFactory {

	/**
	 * the DES cipher
	 */
	public static final String DES = "DES";

	/**
	 * the DESede cipher
	 */
	public static final String DESEDE = "DESede";

	/**
	 * the AES cipher
	 */
	public static final String AES = "AES";

	/**
	 * the RSA cipher
	 */
	public static final String RSA = "RSA";

	/**
	 * the RC4 cipher
	 */
	public static final String RC4 = "RC4";

	/**
	 * the Blowfish cipher
	 */
	public static final String BLOWFISH = "Blowfish";

	/**
	 * utility class
	 */
	private CipherEngineFactory() {
		// do nothing
	}
	
	/**
	 * @return the default DES cipher
	 * @exception CipherException if fail to create cipher engine
	 */
	public static CipherEngine create() throws CipherException {
		return create(RSA);
	}
	
	/**
	 * @param name the cipher name
	 * @return the default DES cipher
	 * @exception CipherException if fail to create cipher engine
	 */
	public static CipherEngine create(final String name) throws CipherException {
	
		if (RSA.equals(name)) {
			return new RSACipherEngine();
		} else if (RC4.equals(name)) {
			return new RC4CipherEngine();
		} else if (AES.equals(name)) {
			return new AESCipherEngine();
		} else if (BLOWFISH.equals(name)) {
			return new BlowfishCipherEngine();
		} else if (DES.equals(name)) {
			return new DESCipherEngine();
		} else if (DESEDE.equals(name)) {
			return new DESedeCipherEngine();
		} else {
			throw new CipherException("Cipher with algorithm [{0}] is not supported", name);
		}
	}
}

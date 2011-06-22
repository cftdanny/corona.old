/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypt;

/**
 * <p>This interface is used to encrypt bytes data </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Encryptor {

	/**
	 * @param data the data to be encrypted
	 * @return the encrypted data
	 */
	byte[] encrypt(byte[] data);
}

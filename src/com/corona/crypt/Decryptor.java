/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.crypt;

/**
 * <p>This decryptor is used to decrypt encrypted data </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Decryptor {

	/**
	 * @param data the data to be decrypted
	 * @return the decrypted data
	 */
	byte[] decrypt(byte[] data);
}

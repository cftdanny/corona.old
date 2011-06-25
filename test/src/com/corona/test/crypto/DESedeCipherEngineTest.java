/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.crypto;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.crypto.CipherEngine;
import com.corona.crypto.CipherEngineFactory;
import com.corona.crypto.Key;

/**
 * <p>This test is used to test DESCipherEngine </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class DESedeCipherEngineTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testEncryptAndDecrypt() throws Exception {
		
		CipherEngine cipher = CipherEngineFactory.create(CipherEngineFactory.DESEDE);
		
		Key key = cipher.generateKey();
		cipher.setDecryptKey(key.getDecryptionKey());
		cipher.setEncryptKey(key.getEncryptionKey());
		
		String source = "1234567890123456"; 
		byte[] encrypted = cipher.encrypt(source.getBytes());
		byte[] decrypted = cipher.decrypt(encrypted);
		String target = new String(decrypted);
		
		Assert.assertEquals(target, source);
	}
}

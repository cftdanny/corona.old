/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.crypto;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.crypto.Cypher;
import com.corona.crypto.CypherFactory;
import com.corona.crypto.CertifiedKey;
import com.corona.crypto.RSACypherFactory;

/**
 * <p>This test is used to test DESCipherEngine </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RSACipherEngineTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testEncryptAndDecrypt() throws Exception {
		
		CertifiedKey key = CypherFactory.get(RSACypherFactory.NAME).generateKey();
		Cypher cipher = CypherFactory.get(RSACypherFactory.NAME).create(key);
		
		String source = "1234567890123456"; 
		byte[] encrypted = cipher.encrypt(source.getBytes());
		byte[] decrypted = cipher.decrypt(encrypted);
		String target = new String(decrypted);
		
		Assert.assertEquals(target, source);
	}
}

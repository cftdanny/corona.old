/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.remote;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.corona.crypto.CypherFactory;
import com.corona.mock.AbstractWebsiteTest;
import com.corona.remote.Client;
import com.corona.remote.Configuration;

/**
 * <p>This test is used to test remote service </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class RemoteBasicTest extends AbstractWebsiteTest {

	/**
	 * @throws Exception if fail
	 */
	@Test public void testRemote() throws Exception {
		
		Configuration cfg = new Configuration();
		
		String path = this.getFullPath("");
		if (path.endsWith("/")) {
			cfg.setBaseURL(path.substring(0, path.length() - 1));
		} else {
			cfg.setBaseURL(path);
		}
		cfg.setClientCypher(CypherFactory.get("DES").create(DemoServer.KEY));
		cfg.setServerCypher(CypherFactory.get("DES").create(DemoServer.KEY));
		
		Client client = new Client(cfg);
		client.login("danny", "");
		
		// test whether token is danny
		Assert.assertEquals(client.getToken(), "danny");
		
		// test execute command
		Source source = new Source();
		source.setA(10);
		source.setB(5);
		
		Result result = client.execute("/calculate", source, Result.class);
		Assert.assertEquals(result.getC(), 15);
		Assert.assertEquals(5, result.getD());
		
		// log out
		client.logout();
	}
}

/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.deployment;

import org.testng.annotations.Test;

import com.corona.deployment.Scanner;


/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TestScanner {

	@Test public void testScanner() throws Exception {
		Scanner scanner = new Scanner();
		scanner.scan();
	}
}

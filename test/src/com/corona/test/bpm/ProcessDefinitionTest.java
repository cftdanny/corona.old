/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.bpm;

import org.testng.annotations.Test;

import com.corona.bpm.ProcessManager;
import com.corona.bpm.ProcessManagerFactory;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class ProcessDefinitionTest {

	/**
	 * @throws Exception if fail to load definition
	 */
	@Test public void testInstallDefinition() throws Exception {
		
		ProcessManager manager = ProcessManagerFactory.getInstance();
		manager.install("com/corona/test/bpm/ProcessDefinitionTest.xml");
	}
}

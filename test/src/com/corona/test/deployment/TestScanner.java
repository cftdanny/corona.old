/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.deployment;

import org.testng.annotations.Test;

import com.corona.context.annotation.Name;
import com.corona.context.annotation.Version;
import com.corona.deployment.AbstractInspector;
import com.corona.deployment.ClassDescriptor;
import com.corona.deployment.Scanner;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class TestScanner {

	/**
	 * @throws Exception if fail to scan
	 */
	@Test public void testScanner() throws Exception {
		
		Scanner scanner = new Scanner();
		
		scanner.setPattern("com/corona/test/context/.*");
		scanner.add(new AbstractInspector(scanner) {
			protected void inspectClass(final String className) {
				
				ClassDescriptor descriptor = getClassDescriptor(className);
				if (descriptor != null) {
					if (descriptor.isAnnotationsPresent(Name.class, Version.class)) {
						System.out.println(descriptor.loadAnnotatedClass(Name.class, Version.class));
					}
				}
			}
		});
		scanner.scan();
	}
}

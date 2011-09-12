/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.deployment;

/**
 * <p>This handler is used to test whether an java class file is annotated by specified annotation </p>
 *
 * @author $Author$
 * @version $Id$
 */
public abstract class AbstractInspector implements Inspector {

	/**
	 * The scanner that uses this handler
	 */
	private Scanner scanner;
	
	/**
	 * @param scanner the parent scanner
	 */
	public AbstractInspector(final Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * @param name the class name
	 * @return the class descriptor of name or null if fail to load descriptor
	 */
	protected ClassDescriptor getClassDescriptor(final String name) {
		
		try {
			return ClassDescriptor.forName(this.scanner.getClassLoader(), name);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.deployment.Inspector#inspect(java.lang.String)
	 */
	@Override
	public void inspect(final String name) {
		
		if (name.endsWith(".class")) {
			this.inspectClass(name);
		} else {
			this.inspectResource(name);
		}
	}

	/**
	 * @param className the class name
	 */
	protected void inspectClass(final String className) {
		// do nothing
	}
	
	/**
	 * @param resourceName the resource name
	 */
	protected void inspectResource(final String resourceName) {
		// do nothing
	}
}

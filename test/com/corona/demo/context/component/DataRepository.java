/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.demo.context.component;

/**
 * <p>Testing data repository </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface DataRepository {

	/**
	 * @param id the id
	 * @return the value
	 */
	String find(int id);
}

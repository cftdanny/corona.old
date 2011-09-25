/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.bpm;

import java.util.Map;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Context extends Map<String, Object> {

	/**
	 * @param names the parameter names to be persisted
	 */
	void persist(String... names);
}

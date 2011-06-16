/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.injecting.param;

/**
 * <p>This converter is used to convert parameter value from String to expected value </p>
 *
 * @author $Author$
 * @version $Id$
 */
interface ParamValueConverter {

	/**
	 * @param value the parameter value from HTTP request
	 * @return the converted value
	 */
	Object convert(String value);
}

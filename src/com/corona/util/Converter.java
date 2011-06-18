/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.util;

/**
 * <p>This converter is used to convert source value to expected target value </p>
 *
 * @author $Author$
 * @version $Id$
 */
public interface Converter {

	/**
	 * @param source the source value
	 * @return the converted target value
	 */
	Object convert(Object source);
}

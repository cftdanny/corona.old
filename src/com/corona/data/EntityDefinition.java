/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.data;

/**
 * <p>This class is used to store table definition by an annotation that is annotated in entity class. </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <E> the type of entity class
 */
public interface EntityDefinition<E> {

	/**
	 * @return the primary key definition
	 */
	PrimaryKeyDefinition<E> getPrimarykey(); 
}

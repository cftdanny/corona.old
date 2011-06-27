/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.remote.avro;

import com.corona.io.Marshaller;
import com.corona.io.Unmarshaller;
import com.corona.remote.Context;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <S> the source type
 * @param <T> the target type
 */
class AvroContext<S, T> implements Context<S, T> {

	/**
	 * the marshaller that is used to marshal data sent to server
	 */
	private Marshaller<S> marshaller;
	
	/**
	 * the unmarshaller that is used to unmarshal data received from server
	 */
	private Unmarshaller<T> unmarshaller;
	
	/**
	 * @param marshaller the marshaller that is used to marshal data sent to server
	 * @param unmarshaller the unmarshaller that is used to unmarshal data received from server
	 */
	AvroContext(final Marshaller<S> marshaller, final Unmarshaller<T> unmarshaller) {
		this.marshaller = marshaller;
		this.unmarshaller = unmarshaller;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Context#getMarshaller()
	 */
	@Override
	public Marshaller<S> getMarshaller() {
		return this.marshaller;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.remote.Context#getUnmarshaller()
	 */
	@Override
	public Unmarshaller<T> getUnmarshaller() {
		return this.unmarshaller;
	}
}

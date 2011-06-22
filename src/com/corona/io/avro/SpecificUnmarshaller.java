/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.avro;

import java.io.IOException;
import java.io.InputStream;

import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import com.corona.io.UnmarshalException;
import com.corona.io.Unmarshaller;

/**
 * <p>This unmarshaller is used to unmarshall input stream into Java object by Apache Avro </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type of Java object will unmarshal to
 */
public class SpecificUnmarshaller<T> implements Unmarshaller<T> {

	/**
	 * the encoder factory
	 */
	private static final DecoderFactory DECODER_FACTORY = new DecoderFactory();
	
	/**
	 * the specific datum writer
	 */
	private SpecificDatumReader<T> datumReader;
	
	/**
	 * @param datumReader the specific datum writer
	 */
	SpecificUnmarshaller(final SpecificDatumReader<T> datumReader) {
		this.datumReader = datumReader;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Unmarshaller#unmarshal(java.io.InputStream)
	 */
	@Override
	public T unmarshal(final InputStream in) throws UnmarshalException {
		
		try {
			return this.datumReader.read(null, DECODER_FACTORY.binaryDecoder(in, null));
		} catch (IOException e) {
			throw new UnmarshalException("Fail to marshal input stream to object", e);
		}
	}
}

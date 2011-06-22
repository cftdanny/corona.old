/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.avro;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.avro.generic.GenericContainer;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

import com.corona.io.MarshalException;
import com.corona.io.Marshaller;

/**
 * <p>This marshaller is used to marshal Java object to output stream by Apache Avro </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type of Java object to be marshalled
 */
public class SpecificMarshaller<T extends GenericContainer> implements Marshaller<T> {

	/**
	 * the encoder factory
	 */
	private static final EncoderFactory ENCODER_FACTORY = new EncoderFactory();
	
	/**
	 * the specific datum writer
	 */
	private SpecificDatumWriter<T> datumWriter;
	
	/**
	 * @param datumWriter the specific datum writer
	 */
	SpecificMarshaller(final SpecificDatumWriter<T> datumWriter) {
		this.datumWriter = datumWriter;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Marshaller#marshal(java.io.OutputStream, java.lang.Object)
	 */
	@Override
	public void marshal(final OutputStream out, final T bean) throws MarshalException {
		
		try {
			this.datumWriter.write(bean, ENCODER_FACTORY.binaryEncoder(out, null));
		} catch (IOException e) {
			throw new MarshalException("Fail to marshal object to output stream", e);
		}
	}
}

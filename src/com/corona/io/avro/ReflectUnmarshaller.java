/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.avro;

import java.io.IOException;
import java.io.InputStream;

import org.apache.avro.file.DataFileStream;
import org.apache.avro.reflect.ReflectDatumReader;

import com.corona.io.UnmarshalException;
import com.corona.io.Unmarshaller;

/**
 * <p>The unmarshaller that unmarshal output stream to Java object by Apache Avro </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type that input stream will be unmarshalled to
 */
public class ReflectUnmarshaller<T> implements Unmarshaller<T> {

	/**
	 * the Avro reflect datum writer
	 */
	private ReflectDatumReader<T> datumReader;

	/**
	 * @param datumReader the Avro reflect datum reader
	 */
	ReflectUnmarshaller(final ReflectDatumReader<T> datumReader) {
		this.datumReader = datumReader;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Unmarshaller#unmarshal(java.io.InputStream)
	 */
	@Override
	public T unmarshal(final InputStream in) throws UnmarshalException {
		
		DataFileStream<T> dataStream = null;
		try {
			dataStream = new DataFileStream<T>(in, datumReader);
		} catch (IOException e) {
			throw new UnmarshalException("Fail to create data file stream by input stream and schema", e);
		}
		
		try {
			return dataStream.next();
		} finally {
			try {
				dataStream.close();
			} catch (IOException e) {
				throw new UnmarshalException("Fail to close data file stream", e);
			}
		}
	}
}

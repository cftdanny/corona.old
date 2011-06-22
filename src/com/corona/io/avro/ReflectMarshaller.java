/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.avro;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.reflect.ReflectDatumWriter;

import com.corona.io.MarshalException;
import com.corona.io.Marshaller;

/**
 * <p>Marshal Java object to output stream by Apache Avro </p>
 *
 * @author $Author$
 * @version $Id$
 * @param <T> the type of Java object to be marshalled
 */
public class ReflectMarshaller<T> implements Marshaller<T> {

	/**
	 * the Avro schema
	 */
	private Schema schema;
	
	/**
	 * the Avro reflect datum writer
	 */
	private ReflectDatumWriter<T> datumWriter;
	
	/**
	 * @param schema the Avro schema
	 * @param datumWriter the Avro reflect datum writer
	 */
	ReflectMarshaller(final Schema schema, final ReflectDatumWriter<T> datumWriter) {
		this.schema = schema;
		this.datumWriter = datumWriter;
	}
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.Marshaller#marshal(java.io.OutputStream, java.lang.Object)
	 */
	@Override
	public void marshal(final OutputStream out, final T bean) throws MarshalException {
		
		DataFileWriter<T> dataWriter = null;
		try {
			dataWriter = new DataFileWriter<T>(datumWriter).create(this.schema, out);
		} catch (IOException e) {
			throw new MarshalException("Fail to create data file writer by schema", e);
		}
		
		try {
			dataWriter.append(bean);
		} catch (IOException e) {
			throw new MarshalException("Fail to serialze object to output stream", e);
		} finally {
			
			try {
				dataWriter.close();
			} catch (IOException e) {
				throw new MarshalException("Fail to close data file writer", e);
			}
		}
	}
}

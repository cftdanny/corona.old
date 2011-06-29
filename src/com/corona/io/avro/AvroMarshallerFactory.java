/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.avro;

import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import com.corona.io.Marshaller;
import com.corona.io.MarshallerFactory;

/**
 * <p>The factory to create {@link ReflectMarshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AvroMarshallerFactory extends MarshallerFactory {
	
	/**
	 * the marshaller name
	 */
	public static final String NAME = "avro";
	
	/**
	 * the reflect data
	 */
	private static final ReflectData REFLECT = ReflectData.get();
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.MarshallerFactory#getName()
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.MarshallerFactory#create(java.lang.Class)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> Marshaller<T> create(final Class<T> type) {
		
		if (type.isAssignableFrom(GenericContainer.class)) {
			return (Marshaller<T>) new SpecificMarshaller(new SpecificDatumWriter(type));
		} else {
			Schema schema = REFLECT.getSchema(type);
			ReflectDatumWriter<T> writer = new ReflectDatumWriter<T>(schema);
			
			return new ReflectMarshaller<T>(schema, writer);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.MarshallerFactory#create(java.lang.Class, java.util.Map)
	 */
	@Override
	public <T> Marshaller<T> create(final Class<T> type, final Map<String, Object> context) {
		return this.create(type);
	}
}

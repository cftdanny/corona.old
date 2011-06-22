/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.io.avro;

import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.specific.SpecificDatumReader;

import com.corona.io.Unmarshaller;
import com.corona.io.UnmarshallerFactory;

/**
 * <p>The factory is used to create {@link ReflectUnmarshaller} </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class AvroUnmarshallerFactory implements UnmarshallerFactory {

	/**
	 * the reflect data
	 */
	private static final ReflectData REFLECT = ReflectData.get();
	
	/**
	 * {@inheritDoc}
	 * @see com.corona.io.UnmarshallerFactory#create(java.lang.Class)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> Unmarshaller<T> create(final Class<T> type) {

		if (type.isAssignableFrom(GenericContainer.class)) {
			return (Unmarshaller<T>) new SpecificUnmarshaller(new SpecificDatumReader(type));
		} else {
			Schema schema = REFLECT.getSchema(type);
			ReflectDatumReader<T> reader = new ReflectDatumReader<T>(schema);
			
			return new ReflectUnmarshaller<T>(reader);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.io.UnmarshallerFactory#create(java.lang.Class, java.util.Map)
	 */
	@Override
	public <T> Unmarshaller<T> create(final Class<T> type, final Map<String, Object> context) {
		return this.create(type);
	}
}

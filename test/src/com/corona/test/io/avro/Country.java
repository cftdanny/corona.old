/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.test.io.avro;

import org.apache.avro.AvroRuntimeException;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;

import com.corona.util.ResourceUtil;

/**
 * <p> </p>
 *
 * @author $Author$
 * @version $Id$
 */
public class Country implements SpecificRecord {

	/**
	 * the schema
	 */
	// CHECKSTYLE:OFF
	public static final Schema SCHEMA$ = Schema.parse(ResourceUtil.load(Country.class, "Country.avsc"));
	// CHECKSTYLE:ON
	
	/**
	 * the name
	 */
	private String name;
	
	/**
	 * the location
	 */
	private String location;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * {@inheritDoc}
	 * @see org.apache.avro.generic.IndexedRecord#get(int)
	 */
	@Override
	public Object get(final int index) {
		
		switch (index) {
			case 0:
				return this.name;
				
			case 1:
				return this.location;
				
			default:
				throw new AvroRuntimeException("Invalid index");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.apache.avro.generic.IndexedRecord#put(int, java.lang.Object)
	 */
	@Override
	public void put(final int index, final Object value) {
		
		switch (index) {
			case 0:
				this.name = (String) value;
				break;
				
			case 1:
				this.location = (String) value;
				break;
				
			default:
				throw new AvroRuntimeException("Invalid index");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.apache.avro.generic.GenericContainer#getSchema()
	 */
	@Override
	public Schema getSchema() {
		return SCHEMA$;
	}
}

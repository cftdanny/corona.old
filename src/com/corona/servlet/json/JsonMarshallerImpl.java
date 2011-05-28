/**
 * Copyright (c) 2009 Aurora Software Technology Studio. All rights reserved.
 */
package com.corona.servlet.json;

import java.io.OutputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.corona.context.annotation.Create;
import com.corona.context.annotation.Install;
import com.corona.logging.Log;
import com.corona.logging.LogFactory;
import com.corona.servlet.ProduceException;

/**
 * <p>The implementation of  JSON marshaller is used to generate JSON content by an object. </p>
 *
 * @author $Author$
 * @version $Id$
 */
@Install(dependencies = "org.codehaus.jackson.map.ObjectMapper")
public class JsonMarshallerImpl implements JsonMarshaller {

	/**
	 * the logger
	 */
	private final Log logger = LogFactory.getLog(JsonMarshallerImpl.class);
	
	/**
	 * the JSON generator
	 */
	private ObjectMapper generator = new ObjectMapper();
	
	/**
	 * initial JSON generator with JABX annotation support
	 */
	@Create public void init() {
		this.generator.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
	}

	/**
	 * {@inheritDoc}
	 * @see com.corona.servlet.json.JsonMarshaller#marshal(java.io.OutputStream, java.lang.Object)
	 */
	public void marshal(final OutputStream out, final Object instance) throws ProduceException {
		
		try {
			this.generator.writeValue(out, instance);
		} catch (Throwable e) {
			this.logger.error("Fail to marshal object [{0}] to JSON content", e, instance);
			throw new ProduceException("Fail to marshal object [{0}] to JSON content", e, instance);
		}
	}
}
